package com.parkingms.services;

import com.parkingms.interfaces.PaymentGateway;
import com.parkingms.models.Agency;
import com.parkingms.interfaces.Vehicle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import com.parkingms.models.Slot;
import com.parkingms.models.ParkRecord;
import com.parkingms.dao.ParkingDao;
import com.parkingms.utils.DNFSorter;

public class ParkingService {

    private static ParkingService instance;

    private ParkingService() {
        parkedVehicles = new ArrayList<>();
        completedRecords = new ArrayList<>();
    }

    public static ParkingService getInstance() {
        if (instance == null) {
            instance = new ParkingService();
        }
        return instance;
    }

    private static Scanner sc = new Scanner(System.in);
    private ParkingDao parkingDao = new ParkingDao();

    private PriorityQueue<Slot> availableCarSlots;
    private PriorityQueue<Slot> availableBikeSlots;
    private PriorityQueue<Slot> availableHeavySlots;

    private List<ParkRecord> parkedVehicles;
    private Map<String, ParkRecord> parkedVehiclesMap;
    private List<ParkRecord> completedRecords;

    private int currentBikeRate;
    private int currentCarRate;
    private int currentHeavyRate;

    private double totalProfit;
    private int ticketCounter;

    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("dd-MMM-yyyy  HH:mm:ss");

    
    private void initSlots(Agency agency) {
        availableCarSlots = new PriorityQueue<>();
        availableBikeSlots = new PriorityQueue<>();
        availableHeavySlots = new PriorityQueue<>();

        for (int i = 1; i <= agency.getTotalCarSlots(); i++)
            availableCarSlots.offer(new Slot(i, Vehicle.typeCar, false));
        for (int i = 1; i <= agency.getTotalBikeSlots(); i++)
            availableBikeSlots.offer(new Slot(i, Vehicle.typeTwoWheeler, false));
        for (int i = 1; i <= agency.getTotalHeavyVehSlots(); i++)
            availableHeavySlots.offer(new Slot(i, Vehicle.typeHeavyVehicle, false));

        currentCarRate = agency.getCarRPH();
        currentBikeRate = agency.getBikeRPH();
        currentHeavyRate = agency.getHeavyVehRPH();

        parkedVehicles.clear();
        parkedVehiclesMap = new HashMap<>();
        completedRecords.clear();
        totalProfit = 0;
        ticketCounter = 0;
        
        loadParkings();
    }

    
    private void loadParkings() {
        parkedVehicles.addAll(parkingDao.loadAll());
        for (ParkRecord r : parkedVehicles) {
            parkedVehiclesMap.put(r.getVehicleNo().toLowerCase(), r);
            
            PriorityQueue<Slot> q = null;
            if (r.getVehicleType() == Vehicle.typeCar) q = availableCarSlots;
            else if (r.getVehicleType() == Vehicle.typeTwoWheeler) q = availableBikeSlots;
            else if (r.getVehicleType() == Vehicle.typeHeavyVehicle) q = availableHeavySlots;
            
            if (q != null) {
                PriorityQueue<Slot> tQ = new PriorityQueue<>();
                while(!q.isEmpty()){
                    Slot s = q.poll();
                    if(s.getSlotNumber() == r.getSlotNo()) s.setOccupied(true);
                    tQ.offer(s);
                }
                q.addAll(tQ);
            }
        }
    }

    private int getFreeCount(PriorityQueue<Slot> q) {
        int count = 0;
        for (Slot s : q) {
            if (!s.isOccupied()) count++;
        }
        return count;
    }

    public void start(Agency agency) {
        initSlots(agency);

        while (true) {
            summary();

            System.out.println("\n1. Add Vehicle");
            System.out.println("2. Checkout Vehicle");
            System.out.println("3. Show All Vehicles");
            System.out.println("4. Show Slots");
            System.out.println("5. Show Profit");
            System.out.println("6. Edit Prices");
            System.out.println("7. Find Vehicle Slot");
            System.out.println("8. Most Visiting Customers");
            System.out.println("9. Logout");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (choice == 1) {
                addVehicle(agency);
            } else if (choice == 2) {
                checkout(agency);
            } else if (choice == 3) {
                showVehicles();
            } else if (choice == 4) {
                showSlots(agency);
            } else if (choice == 5) {
                profit();
            } else if (choice == 6) {
                editPrices(agency);
            } else if (choice == 7) {
                findVehicleSlot();
            } else if (choice == 8) {
                showMostVisitingCustomers();
            } else if (choice == 9) {
                System.out.println("Logged out.");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public void summary() {
        int freeCar = getFreeCount(availableCarSlots);
        int freeBike = getFreeCount(availableBikeSlots);
        int freeHeavy = getFreeCount(availableHeavySlots);
        int totalFree = freeCar + freeBike + freeHeavy;

        System.out.println("Parked: " + parkedVehicles.size()
                + " | Free Slots: " + totalFree
                + " (Car:" + freeCar
                + " Bike:" + freeBike
                + " Heavy:" + freeHeavy + ")");
    }

    private void addVehicle(Agency agency) {
        int freeBike = getFreeCount(availableBikeSlots);
        int freeCar = getFreeCount(availableCarSlots);
        int freeHeavy = getFreeCount(availableHeavySlots);

        if (freeCar == 0 && freeBike == 0 && freeHeavy == 0) {
            System.out.println("No parking slots available in this agency.");
            return;
        }

        System.out.println("\nSelect Vehicle Type:");
        if (freeBike > 0)   System.out.println("1 -> 2 Wheeler  (" + freeBike + " slots)");
        if (freeCar > 0)    System.out.println("2 -> 4 Wheeler  (" + freeCar + " slots)");
        if (freeHeavy > 0)  System.out.println("3 -> Heavy Vehicle (" + freeHeavy + " slots)");
        System.out.print("Choose type: ");

        int typeChoice;
        try {
            typeChoice = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Invalid input.");
            return;
        }

        int vehicleType;
        PriorityQueue<Slot> targetQueue;

        if (typeChoice == 1 && freeBike > 0) {
            vehicleType = Vehicle.typeTwoWheeler;
            targetQueue = availableBikeSlots;
        } else if (typeChoice == 2 && freeCar > 0) {
            vehicleType = Vehicle.typeCar;
            targetQueue = availableCarSlots;
        } else if (typeChoice == 3 && freeHeavy > 0) {
            vehicleType = Vehicle.typeHeavyVehicle;
            targetQueue = availableHeavySlots;
        } else {
            System.out.println("Invalid selection or no slots available for this type.");
            return;
        }

        System.out.print("Enter User ID: ");
        String uId = sc.nextLine().trim();
        // Sort by User ID for Binary Search as per blueprint
                java.util.List<com.parkingms.models.User> allUsers = UserService.getInstance().getUsers();
        java.util.Map<String, com.parkingms.models.User> userMap = new java.util.HashMap<>();
        for (com.parkingms.models.User u : allUsers) {
            userMap.put(u.getUserId().toLowerCase(), u);
        }
        com.parkingms.models.User foundUser = userMap.get(uId.toLowerCase());
        
        if (foundUser == null) {
            System.out.println("register first then park");
            return;
        }

        String ownerName = foundUser.getUserName();
        String vehicleNo = foundUser.getVehicleNo();
        String upiId = foundUser.getUpiId();
        String pinHash = foundUser.getPin();
        String phone = foundUser.getPhone();
        
        if (parkedVehiclesMap.containsKey(vehicleNo.toLowerCase())) {
            System.out.println("Vehicle " + vehicleNo + " is already parked in the lot!");
            return;
        }
        
        System.out.println("Profile found! Auto-filling details...");

        Slot assignedSlot = null;
        PriorityQueue<Slot> tempQ = new PriorityQueue<>();
        while (!targetQueue.isEmpty()) {
            Slot s = targetQueue.poll();
            if (!s.isOccupied() && assignedSlot == null) {
                assignedSlot = s;
                s.setOccupied(true);
            }
            tempQ.offer(s);
        }
        targetQueue.addAll(tempQ);
        
        if (assignedSlot == null) {
            System.out.println("No available slots for this type.");
            return;
        }

        int slotNo = assignedSlot.getSlotNumber();

        ParkRecord record = new ParkRecord.Builder()
            .setOwnerName(ownerName)
            .setVehicleNo(vehicleNo)
            .setVehicleType(vehicleType)
            .setSlotNo(slotNo)
            .build();
        parkingDao.save(record);
        String ticketNo = record.getFormattedTicketNo();

        com.parkingms.models.User newUserRecord = new com.parkingms.models.User.Builder()
                .setRecordId(ticketNo)
                .setUserId(uId)
                .setAgencyId(agency.getAgencyId())
                .setUserName(ownerName)
                .setPhone(phone)
                .setVehicleNo(vehicleNo)
                .setPaymentMethod("UPI")
                .setUpiId(upiId)
                .setPin(pinHash)
                .setAmountDue(0.0)
                .setStatus("PENDING")
                .build();
        
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUserId().equalsIgnoreCase(uId) && allUsers.get(i).getRecordId().startsWith("PROF-")) {
                allUsers.remove(i);
                break;
            }
        }
        allUsers.add(newUserRecord);
        UserService.getInstance().saveUsers(allUsers);

        parkedVehicles.add(record);
        parkedVehiclesMap.put(vehicleNo.toLowerCase(), record);

        System.out.println("\n======================================");
        System.out.println("         PARKING TICKET               ");
        System.out.println("======================================");
        System.out.println("  Ticket : " + ticketNo);
        System.out.println("  Slot   : " + record.getSlotLabel());
        System.out.println("  Vehicle: " + vehicleNo);
        System.out.println("  Owner  : " + ownerName);
        System.out.println("  Type   : " + record.getTypeLabel());
        System.out.println("  Entry  : " + sdf.format(new Date(record.getEntryTimeMs())));
        System.out.println("  Status : PENDING");
        System.out.println("======================================");
        System.out.println("Vehicle parked successfully!");
        
        
    }

    private void freeSlotInQueue(int vType, int sNo) {
        PriorityQueue<Slot> q = null;
        if (vType == Vehicle.typeCar) q = availableCarSlots;
        else if (vType == Vehicle.typeTwoWheeler) q = availableBikeSlots;
        else if (vType == Vehicle.typeHeavyVehicle) q = availableHeavySlots;
        
        if (q == null) return;
        
        PriorityQueue<Slot> tQ = new PriorityQueue<>();
        while(!q.isEmpty()){
            Slot s = q.poll();
            if(s.getSlotNumber() == sNo) s.setOccupied(false);
            tQ.offer(s);
        }
        q.addAll(tQ);
    }

    private void checkout(Agency agency) {
        if (parkedVehicles.isEmpty()) {
            System.out.println("No vehicles currently parked.");
            return;
        }

        System.out.print("Enter Ticket Number: ");
        String ticketNo = sc.nextLine().trim();

        ParkRecord found = null;
        for (ParkRecord r : parkedVehicles) {
            if (r.getFormattedTicketNo().equalsIgnoreCase(ticketNo)) {
                found = r;
                break;
            }
        }

        if (found == null) {
            System.out.println("Ticket not found: " + ticketNo);
            return;
        }

        long exitTimeMs = System.currentTimeMillis();
        double durationHrs = (exitTimeMs - found.getEntryTimeMs()) / 3600000.0;
        int ratePerHr = com.parkingms.services.BillingService.getRateForType(found.getVehicleType(), agency);
        double amountDue = com.parkingms.services.BillingService.calculateBill(durationHrs, ratePerHr, agency);
        
        boolean hasOvertime = (agency.getOverTimeLmt() > 0 && durationHrs > agency.getOverTimeLmt());

        System.out.println("\n======================================");
        System.out.println("           BILL SUMMARY               ");
        System.out.println("======================================");
        System.out.println("  Ticket  : " + found.getFormattedTicketNo());
        System.out.println("  Vehicle : " + found.getVehicleNo());
        System.out.println("  Owner   : " + found.getOwnerName());
        System.out.println("  Slot    : " + found.getSlotLabel());
        System.out.println("  Entry   : " + sdf.format(new Date(found.getEntryTimeMs())));
        System.out.println("  Exit    : " + sdf.format(new Date(exitTimeMs)));
        System.out.println("  Duration: " + String.format("%.2f", durationHrs) + " hours");
        System.out.println("  Rate    : Rs." + ratePerHr + "/hr");
        if (hasOvertime) {
            System.out.println("  Overtime: Rs." + agency.getOverTimeCharge() + "/hr (after " + agency.getOverTimeLmt() + "hrs)");
        }
        System.out.println("  TOTAL   : Rs." + String.format("%.2f", amountDue));
        System.out.println("======================================");

        java.util.List<com.parkingms.models.User> allUsers = UserService.getInstance().getUsers();
        com.parkingms.models.User matchingUser = null;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getRecordId().equalsIgnoreCase(found.getFormattedTicketNo())) {
                matchingUser = allUsers.get(i);
                allUsers.remove(i);
                break;
            }
        }

        String paymentMethod = "CASH";
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. CASH");
        System.out.println("2. UPI");
        
        int payChoice;
        while(true) {
            System.out.print("Choose: ");
            try {
                payChoice = Integer.parseInt(sc.nextLine().trim());
                if (payChoice == 1 || payChoice == 2) break;
            } catch(Exception e) {}
            System.out.println("Invalid choice. Enter 1 or 2.");
        }

        if (payChoice == 2) {
            paymentMethod = "UPI";
        }

        // PIN input — CASH ke liye empty, UPI ke liye 4-digit
        String enteredPin = "";
        String storedPin = (matchingUser != null) ? matchingUser.getPin() : "";

        if (paymentMethod.equals("UPI")) {
            while(true) {
                System.out.print("Enter 4-digit UPI PIN: ");
                enteredPin = sc.nextLine().trim();
                if(enteredPin.matches("\\d{4}")) break;
                System.out.println("Invalid PIN. Must be exactly 4 digits.");
            }
        }

        // Factory se gateway lao — CASH ya UPI
        PaymentGateway gateway = com.parkingms.factories.PaymentFactory.getGateway(paymentMethod);
        boolean paymentSuccess = gateway.processPayment(enteredPin, storedPin);

        if (paymentSuccess) {
            if (matchingUser != null) {
                com.parkingms.models.User updatedUser = new com.parkingms.models.User.Builder()
                        .setRecordId(matchingUser.getRecordId())
                        .setUserId(matchingUser.getUserId())
                        .setAgencyId(matchingUser.getAgencyId())
                        .setUserName(matchingUser.getUserName())
                        .setPhone(matchingUser.getPhone())
                        .setVehicleNo(matchingUser.getVehicleNo())
                        .setPaymentMethod(paymentMethod)
                        .setUpiId(matchingUser.getUpiId())
                        .setPin(matchingUser.getPin())
                        .setAmountDue(amountDue)
                        .setStatus("COMPLETED")
                        .build();
                allUsers.add(updatedUser);
            }
            UserService.getInstance().saveUsers(allUsers);

            parkedVehicles.remove(found);
            parkingDao.delete(found);
            parkedVehiclesMap.remove(found.getVehicleNo().toLowerCase());
            completedRecords.add(found);
            totalProfit += amountDue;

            // Free up the slot
            freeSlotInQueue(found.getVehicleType(), found.getSlotNo());

            System.out.println("Slot " + found.getSlotLabel() + " is now free. Have a safe journey!");
            
        } else {
            if (matchingUser != null) {
                com.parkingms.models.User updatedUser = new com.parkingms.models.User.Builder()
                        .setRecordId(matchingUser.getRecordId())
                        .setUserId(matchingUser.getUserId())
                        .setAgencyId(matchingUser.getAgencyId())
                        .setUserName(matchingUser.getUserName())
                        .setPhone(matchingUser.getPhone())
                        .setVehicleNo(matchingUser.getVehicleNo())
                        .setPaymentMethod(matchingUser.getPaymentMethod())
                        .setUpiId(matchingUser.getUpiId())
                        .setPin(matchingUser.getPin())
                        .setAmountDue(amountDue)
                        .setStatus("REJECTED")
                        .build();
                allUsers.add(updatedUser);
            }
            UserService.getInstance().saveUsers(allUsers);
            // DO NOT return slot to the queue, DO NOT remove from parkedVehicles.
        }
    }

    private void findVehicleSlot() {
        System.out.print("\nEnter Vehicle Number or Ticket Number to search: ");
        String query = sc.nextLine().trim();
        
        if (parkedVehicles.isEmpty()) {
            System.out.println("No vehicles are currently parked.");
            return;
        }

        String normalizedQuery = query.replace(" ", "").toLowerCase();
        ParkRecord found = null;
        
        for (ParkRecord r : parkedVehicles) {
            String normVehicle = r.getVehicleNo().replace(" ", "").toLowerCase();
            if (normVehicle.equals(normalizedQuery) || r.getFormattedTicketNo().equalsIgnoreCase(query)) {
                found = r;
                break;
            }
            try {
                if (Integer.parseInt(r.getFormattedTicketNo()) == Integer.parseInt(query)) {
                    found = r;
                    break;
                }
            } catch (Exception e) {}
        }

        if (found != null) {
            System.out.println("Found! Ticket " + found.getFormattedTicketNo() + " (Vehicle " + found.getVehicleNo() + ") is parked at Slot: " + found.getSlotLabel());
        } else {
            System.out.println("Record '" + query + "' not found in the parking lot.");
        }
    }

    private void showMostVisitingCustomers() {
        System.out.print("\nEnter K (e.g. 5 for Top 5 customers): ");
        int k;
        try {
            k = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Invalid input.");
            return;
        }

        List<String> customerVisits = new java.util.ArrayList<>();
        java.util.List<com.parkingms.models.User> allUsers = UserService.getInstance().getUsers();
        
        for (com.parkingms.models.User u : allUsers) {
            if (!u.getRecordId().startsWith("PROF-")) {
                customerVisits.add(u.getUserName() + " (" + u.getVehicleNo() + ")");
            }
        }

        List<java.util.Map.Entry<String, Integer>> topK = 
            com.parkingms.utils.TopKFrequent.getTopK(customerVisits, k);
            
        if (topK.isEmpty()) {
            System.out.println("No customer data available.");
            return;
        }

        System.out.println("\n--- Top " + topK.size() + " Most Visiting Customers ---");
        System.out.printf("%-30s %-10s%n", "CUSTOMER (VEHICLE)", "VISITS");
        System.out.println("----------------------------------------");
        for (java.util.Map.Entry<String, Integer> entry : topK) {
            System.out.printf("%-30s %-10d%n", entry.getKey(), entry.getValue());
        }
    }

    private void showVehicles() {
        System.out.println("\n--- All Vehicles (Sorted by Payment Status) ---");
        java.util.List<com.parkingms.models.User> allUsers = UserService.getInstance().getUsers();
        java.util.List<com.parkingms.models.User> validUsers = new java.util.ArrayList<>();
        
        for (com.parkingms.models.User u : allUsers) {
            if (!u.getRecordId().startsWith("PROF-")) {
                validUsers.add(u);
            }
        }
        
        DNFSorter.sortByStatus(validUsers);
        
        if (validUsers.isEmpty()) {
            System.out.println("No vehicle records available.");
            return;
        }

        System.out.printf("%-15s %-15s %-15s %-15s %-10s%n", "USER NAME", "VEHICLE NO", "VEHICLE STATUS", "PAYMENT STATUS", "AMOUNT DUE");
        System.out.println("-----------------------------------------------------------------------------");
        for (com.parkingms.models.User u : validUsers) {
            String vStatus = parkedVehiclesMap.containsKey(u.getVehicleNo().toLowerCase()) ? "ACTIVE" : "EXITED";
            System.out.printf("%-15s %-15s %-15s %-15s %.2f%n",
                    u.getUserName(), u.getVehicleNo(), vStatus, u.getPayStatus(), u.getAmountDue());
        }
    }

    public void showSlots(Agency agency) {
        System.out.println("\n--- Slot Overview ---");
        System.out.printf("%-15s %-10s %-15s %-20s%n", "VEHICLE TYPE", "SLOT NO", "STATUS", "TICKET NO");
        System.out.println("------------------------------------------------------------------");
        printQueueSlots("4 Wheeler", availableCarSlots);
        printQueueSlots("2 Wheeler", availableBikeSlots);
        printQueueSlots("Heavy Vehicle", availableHeavySlots);
    }
    
    private void printQueueSlots(String name, PriorityQueue<Slot> q) {
        PriorityQueue<Slot> tQ = new PriorityQueue<>();
        while(!q.isEmpty()){
            Slot s = q.poll();
            String status = "Available";
            String details = "Null";
            if (s.isOccupied()) {
                status = "Occupied";
                for (ParkRecord r : parkedVehicles) {
                    if (r.getSlotNo() == s.getSlotNumber() && r.getTypeLabel().equalsIgnoreCase(name)) {
                        details = r.getFormattedTicketNo();
                        break;
                    }
                }
            }
            System.out.printf("%-15s %-10d %-15s %-20s%n", name, s.getSlotNumber(), status, details);
            tQ.offer(s);
        }
        q.addAll(tQ);
    }

    public void profit() {
        System.out.println("\n--- Profit Summary ---");
        System.out.println("Total Vehicles Served: " + completedRecords.size());
        System.out.println("Total Profit: Rs." + String.format("%.2f", totalProfit));
    }

    private void editPrices(Agency agency) {
        System.out.println("\n--- Current Pricing ---");
        System.out.println("2 Wheeler     : Rs." + currentBikeRate + "/hr");
        System.out.println("4 Wheeler     : Rs." + currentCarRate + "/hr");
        System.out.println("Heavy Vehicle : Rs." + currentHeavyRate + "/hr");
        System.out.println("\nEnter New Prices:");

        try {
            System.out.print("2 Wheeler Price: ");
            int newBikeRate = sc.nextInt();
            System.out.print("4 Wheeler Price: ");
            int newCarRate = sc.nextInt();
            System.out.print("Heavy Vehicle Price: ");
            int newHeavyRate = sc.nextInt();
            sc.nextLine();

            currentBikeRate = newBikeRate;
            currentCarRate = newCarRate;
            currentHeavyRate = newHeavyRate;
            System.out.println("Prices updated successfully.");
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Invalid number format. Prices not updated.");
        }
    }
}
