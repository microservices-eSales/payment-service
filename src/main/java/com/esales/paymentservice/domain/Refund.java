package com.esales.paymentservice.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "refunds")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private Float amount;
    private String status;

}