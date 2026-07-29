// Using Builder Design Pattern

package com.parkingms.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "record_id")
    private String recordId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "agency_id")
    private String agencyId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "vehicle_no")
    private String vehicleNo;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "upi_id")
    private String upiId;
    @Column(name = "pin")
    private String pin;
    @Column(name = "amount_due")
    private double amountDue;
    @Column(name = "pay_status")
    private String payStatus;         // P,C,F

    private User() {}

    public String getRecordId() { return recordId; }
    public String getUserId() { return userId; }
    public String getAgencyId() { return agencyId; }
    public String getUserName() { return userName; }
    public String getPhone() { return phone; }
    public String getVehicleNo() { return vehicleNo; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getUpiId() { return upiId; }
    public String getPin() { return pin; }
    public double getAmountDue() { return amountDue; }
    public String getPayStatus() { return payStatus; }

    public static class Builder {
        private User user = new User();

        public Builder setRecordId(String recordId) {
            user.recordId = recordId;
            return this;
        }

        public Builder setUserId(String userId) {
            user.userId = userId;
            return this;
        }

        public Builder setAgencyId(String agencyId) {
            user.agencyId = agencyId;
            return this;
        }

        public Builder setUserName(String userName) {
            user.userName = userName;
            return this;
        }

        public Builder setPhone(String phone) {
            user.phone = phone;
            return this;
        }

        public Builder setVehicleNo(String vehicleNo) {
            user.vehicleNo = vehicleNo;
            return this;
        }

        public Builder setPaymentMethod(String paymentMethod) {
            user.paymentMethod = paymentMethod;
            return this;
        }

        public Builder setUpiId(String upiId) {
            user.upiId = upiId;
            return this;
        }

        public Builder setPin(String pin) {
            user.pin = pin;
            return this;
        }

        public Builder setAmountDue(double amountDue) {
            user.amountDue = amountDue;
            return this;
        }

        public Builder setStatus(String status) {
            user.payStatus = status;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
