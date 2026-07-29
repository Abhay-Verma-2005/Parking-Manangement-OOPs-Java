package com.parkingms.factories;

import com.parkingms.payment.CashPayment;
import com.parkingms.interfaces.PaymentGateway;
import com.parkingms.payment.UpiPayment;

public class PaymentFactory {

    private PaymentFactory() {}

    public static PaymentGateway getGateway(String method) {
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }

        switch (method.toUpperCase()) {
            case "UPI":
                return new UpiPayment();
            case "CASH":
                return new CashPayment();
            default:
                throw new IllegalArgumentException("Unknown payment method: " + method);
        }
    }
}
