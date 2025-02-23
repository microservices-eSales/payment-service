package com.esales.paymentservice.web.rest.controllers;

import com.esales.paymentservice.domain.Payment;
import com.esales.paymentservice.domain.Refund;
import com.esales.paymentservice.service.DTO.PaymentRequestDTO;
import com.esales.paymentservice.service.DTO.RefundRequestDTO;
import com.esales.paymentservice.service.PaymentService;
import com.esales.paymentservice.web.rest.VM.PaymentVM;
import com.esales.paymentservice.web.rest.VM.RefundVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@RestController
@RequestMapping(value = "/payments", produces = "application/json")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PaymentVM> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        Payment payment = paymentService.processPayment(
                paymentRequest.getOrderId(),
                paymentRequest.getAmount(),
                paymentRequest.getPaymentMethod(),
                paymentRequest.getStripeToken()
        );
        System.out.println(paymentRequest);
        PaymentVM paymentVM = new PaymentVM(payment);
        return ResponseEntity.ok(paymentVM);
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<RefundVM> processRefund(@PathVariable UUID paymentId,
                                                  @RequestBody RefundRequestDTO refundRequest) {
        Refund refund = paymentService.processRefund(paymentId, refundRequest.getAmount());
        RefundVM refundVM = new RefundVM(refund);
        return ResponseEntity.ok(refundVM);
    }
}
