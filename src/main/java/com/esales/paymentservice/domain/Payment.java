package com.esales.paymentservice.domain;

import com.esales.paymentservice.domain.enums.Payment_method;
import com.esales.paymentservice.domain.enums.StatusP;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;
    private Float amount;
    private Payment_method payment_method;
    private StatusP status;

}