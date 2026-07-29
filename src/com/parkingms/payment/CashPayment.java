package com.parkingms.payment;

import com.parkingms.interfaces.PaymentGateway;

public class CashPayment implements PaymentGateway {

    @Override
    public boolean processPayment(String enteredPin, String storedPin) {
        System.out.println(">> Processing CASH Payment...");
        System.out.println(">> Payment SUCCESSFUL ✓");
        return true;
    }
}
