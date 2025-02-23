package com.esales.paymentservice.service.DTO;

import com.esales.paymentservice.domain.enums.PaymentMethod;
import lombok.Data;
import java.util.UUID;

@Data
public class PaymentRequestDTO {
    private UUID orderId;
    private Float amount;

    @Override
    public String toString() {
        return "PaymentRequestDTO{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", stripeToken='" + stripeToken + '\'' +
                '}';
    }

    private PaymentMethod paymentMethod;
    private String stripeToken;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}