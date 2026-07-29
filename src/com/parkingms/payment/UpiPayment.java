package com.parkingms.payment;

import com.parkingms.interfaces.PaymentGateway;

public class UpiPayment implements PaymentGateway {

    @Override
    public boolean processPayment(String enteredPin, String storedPin) {
        System.out.println(">> Processing UPI Server...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            
        }

        if (enteredPin != null && enteredPin.equals(storedPin)) {
            System.out.println(">> Payment SUCCESSFUL ✓");
            return true;
        } else {
            System.out.println(">> Wrong PIN. Payment Failed ✗");
            return false;
        }
    }
}
