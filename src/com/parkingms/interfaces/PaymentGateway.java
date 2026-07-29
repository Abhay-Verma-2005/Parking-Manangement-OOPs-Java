package com.parkingms.interfaces;

public interface PaymentGateway {
    boolean processPayment(String enteredPin, String storedPin);
}
