package com.esales.paymentservice.service;

import com.esales.paymentservice.domain.Payment;
import com.esales.paymentservice.domain.Transaction;
import com.esales.paymentservice.domain.enums.PaymentMethod;
import com.esales.paymentservice.domain.enums.StatusP;
import com.esales.paymentservice.repository.PaymentRepository;
import com.esales.paymentservice.repository.RefundRepository;
import com.esales.paymentservice.repository.TransactionRepository;
import com.esales.paymentservice.web.errors.PaymentNotFoundException;
import com.esales.paymentservice.web.errors.PaymentProcessingException;
import com.esales.paymentservice.web.errors.RefundProcessingException;
import com.esales.paymentservice.web.errors.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RefundRepository refundRepository;


    public Payment processPayment(UUID orderId, Float amount, PaymentMethod paymentMethod, String stripeToken) {
        try {
            Stripe.apiKey = stripeApiKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int)(amount * 100));
            params.put("currency", "eur");
            params.put("source", stripeToken);
            params.put("description", "Commande " + orderId);

            Charge charge = Charge.create(params);

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setAmount(amount);
            payment.setPaymentMethod(paymentMethod);
            payment.setStatus(StatusP.COMPLETED);

            paymentRepository.save(payment);

            Transaction transaction = new Transaction();
            transaction.setPayment(payment);
            transaction.setReference(charge.getId());
            transaction.setStatus("SUCCESS");
            transaction.setDate(LocalDateTime.now());
            transactionRepository.save(transaction);

            return payment;

        } catch (StripeException e) {
            throw new PaymentProcessingException("Le paiement a échoué: " + e.getMessage());
        }
    }

    public com.esales.paymentservice.domain.Refund processRefund(UUID paymentId, Float amount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Paiement non trouvé"));

        try {
            Transaction transaction = transactionRepository.findByPaymentAndStatus(payment, "SUCCESS")
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction non trouvée"));

            Map<String, Object> params = new HashMap<>();
            params.put("charge", transaction.getReference());
            params.put("amount", (int)(amount * 100));

            Refund stripeRefund = Refund.create(params);

            com.esales.paymentservice.domain.Refund refund = new com.esales.paymentservice.domain.Refund();
            refund.setPayment(payment);
            refund.setAmount(amount);
            refund.setStatus("COMPLETED");
            return refundRepository.save(refund);

        } catch (StripeException e) {
            throw new RefundProcessingException("Le remboursement a échoué: " + e.getMessage());
        }
    }}
