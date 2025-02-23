package com.esales.paymentservice.service.DTO;

import lombok.Data;
import java.util.UUID;

@Data
public class RefundRequestDTO {
    private Float amount;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
