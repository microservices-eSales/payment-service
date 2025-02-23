package com.esales.paymentservice.web.rest.VM;

import com.esales.paymentservice.domain.Payment;
import com.esales.paymentservice.domain.enums.PaymentMethod;
import com.esales.paymentservice.domain.enums.StatusP;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentVM {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("orderId")
    private UUID orderId;

    @JsonProperty("amount")
    private Float amount;

    @JsonProperty("paymentMethod")
    private PaymentMethod paymentMethod;

    @JsonProperty("status")
    private StatusP status;

    public PaymentVM(Payment payment) {
        this.id = payment.getId();
        this.orderId = payment.getOrderId();
        this.amount = payment.getAmount();
        this.paymentMethod = payment.getPaymentMethod();
        this.status = payment.getStatus();
    }

    public PaymentVM() {}
}