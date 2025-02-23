package com.esales.paymentservice.web.errors;


public class RefundProcessingException extends RuntimeException {
    public RefundProcessingException(String message) {
        super(message);
    }
}
