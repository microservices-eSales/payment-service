package com.esales.paymentservice.web.rest.VM;

import com.esales.paymentservice.domain.Refund;
import lombok.Data;

import java.util.UUID;

@Data
public class RefundVM {
    private UUID paymentId;
    private Float amount;
    private String status;

    public RefundVM(Refund refund) {
        this.paymentId = refund.getPayment().getOrderId();
        this.amount = refund.getAmount();
        this.status = refund.getStatus();
    }
}
