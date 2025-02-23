package com.esales.paymentservice.repository;

import com.esales.paymentservice.domain.Payment;
import com.esales.paymentservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findByPaymentAndStatus(Payment payment, String success);
}
