package com.parkingms.services;

import com.parkingms.dao.UserDao;
import com.parkingms.models.User;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private static UserService instance;
    private UserDao userDao;
    private static Scanner sc = new Scanner(System.in);

    private UserService() {
        userDao = new UserDao();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getUsers() {
        return userDao.loadAll();
    }

    public void saveUsers(List<User> users) {
        userDao.saveAll(users);
    }

    public void registerUser() {
        System.out.println("\n--- Register New User ---");
        String name;
        while(true) {
            System.out.print("Full Name: ");
            name = sc.nextLine().trim();
            if(!name.isEmpty()) break;
            System.out.println("Cannot be empty. Try again.");
        }

        String phone;
        while(true) {
            System.out.print("Phone Number (Digits only): ");
            phone = sc.nextLine().trim();
            if(phone.matches("\\d+")) break;
            System.out.println("Invalid phone number. Must contain digits only.");
        }

        String vehicleNo;
        while(true) {
            System.out.print("Vehicle Number: ");
            vehicleNo = sc.nextLine().trim();
            if(!vehicleNo.isEmpty()) break;
            System.out.println("Cannot be empty. Try again.");
        }

        String upi;
        while(true) {
            System.out.print("UPI ID: ");
            upi = sc.nextLine().trim();
            if(!upi.isEmpty()) break;
            System.out.println("Cannot be empty. Try again.");
        }

        String pin;
        while(true) {
            System.out.print("Set 4-digit PIN: ");
            pin = sc.nextLine().trim();
            if(pin.matches("\\d{4}")) break;
            System.out.println("Invalid PIN. Must be exactly 4 digits.");
        }

        List<User> currentUsers = getUsers();
        int maxId = 0;
        for (User u : currentUsers) {
            if (u.getUserId() != null && u.getUserId().startsWith("USR")) {
                try {
                    int idNum = Integer.parseInt(u.getUserId().substring(3));
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Ignore parsing errors for malformed IDs
                }
            }
        }
        
        String userId = "USR" + String.format("%03d", maxId + 1);
        
        User profile = new User.Builder()
                .setRecordId("PROF-" + userId)
                .setUserId(userId)
                .setAgencyId("NONE")
                .setUserName(name)
                .setPhone(phone)
                .setVehicleNo(vehicleNo)
                .setUpiId(upi)
                .setPin(pin)
                .setStatus("PENDING")
                .build();
        currentUsers.add(profile);
        saveUsers(currentUsers);
        System.out.println("\n======================================");
        System.out.println("      USER REGISTERED SUCCESSFULLY    ");
        System.out.println("======================================");
        System.out.println("  User ID    : " + userId);
        System.out.println("  Name       : " + name);
        System.out.println("  Phone      : " + phone);
        System.out.println("  Vehicle No : " + vehicleNo);
        System.out.println("  UPI ID     : " + upi);
        System.out.println("======================================");
        System.out.println("Please note your User ID for future parking.");
    }

    public void showAllUsers() {
        List<User> allUsers = getUsers();
        if (allUsers.isEmpty()) {
            System.out.println("\nNo users registered yet.");
            return;
        }
        System.out.println("\n--- All Users ---");
        for (int i = 0; i < allUsers.size(); i++) {
            User u = allUsers.get(i);
            System.out.println("[" + (i+1)+ "] " + u.getUserId()
                    + " | " + u.getUserName()
                    + " | " + u.getPhone()
                    + " | " + u.getVehicleNo()
                    + " | " + u.getUpiId()
                    + " | " + u.getPayStatus());
        }
        System.out.println("Total: " + allUsers.size());
    }
}
