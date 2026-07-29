package com.parkingms.services;

import java.util.List;
import java.util.Scanner;
import com.parkingms.dao.AgencyDao;
import com.parkingms.models.Agency;
import com.parkingms.interfaces.AgencyInterface;

public class AgencyService implements AgencyInterface {

    private static AgencyService instance;
    private AgencyDao adao;
    private static Scanner sc = new Scanner(System.in);

    private AgencyService() {
        adao = new AgencyDao();
    }

    public static AgencyService getInstance() {
        if (instance == null) {
            instance = new AgencyService();
        }
        return instance;
    }

    // Public wrapper Implemetation for making this private
    public void Registration() {
        registerAgency();
    }

    // Private implementation — hidden from outside
    private void registerAgency() {
        String agencyName = readString("Agency Name: ");
        String password = readString("Password: ");
        String agencyDesc = readString("Description: ");

        int bikeSlots = expReadInt("2 Wheeler Slots: ");
        int carSlots = expReadInt("4 Wheeler Slots: ");
        int heavySlots = expReadInt("Heavy Vehicle Slots: ");

        double bikeRPH = expReadDouble("2 Wheeler Price/Hr (Rs.): ");
        double carRPH = expReadDouble("4 Wheeler Price/Hr (Rs.): ");
        double heavyRPH = expReadDouble("Heavy Vehicle Price/Hr (Rs.): ");

        int overTimeLimit = expReadInt("Overtime Limit (Hrs): ");
        double overTimeCharge = expReadDouble("Overtime Fine/Hr (Rs.): ");

        // Builder pattern
        String agencyId = java.util.UUID.randomUUID().toString();
        Agency agency = new Agency.Builder()
                .setAgencyId(agencyId)
                .setAgencyName(agencyName)
                .setPassword(password)
                .setAgencyDesc(agencyDesc)
                .setTotalBikeSlots(bikeSlots)
                .setTotalCarSlots(carSlots)
                .setTotalHeavyVehSlots(heavySlots)
                .setBikeRPH((int) bikeRPH)
                .setCarRPH((int) carRPH)
                .setHeavyVehRPH((int) heavyRPH)
                .setOverTimeLmt(overTimeLimit)
                .setOverTimeCharge(overTimeCharge)
                .build();

        List<Agency> agenciesList = adao.loadAll();
        agenciesList.add(agency);
        adao.saveAll(agenciesList);
        System.out.println("Agency '" + agencyName + "' registered successfully!");
    }

    public void Login() {
        loginAgency();
    }

    // Private (hidden from outside)
    private void loginAgency() {
        List<Agency> agencies = adao.loadAll();
        if (agencies.isEmpty()) {
            System.out.println("No agencies registered yet. Please register first.");
            return;
        }

        System.out.println("\n--- Registered Agencies ---");
        for (int i = 0; i < agencies.size(); i++) {
            System.out.println((i+1) + ". " + agencies.get(i).getAgencyName());
        }
        System.out.print("Select Agency (Number): ");

        int agencyChoice;
        try{
            agencyChoice = sc.nextInt();
            sc.nextLine();
        }
        catch (Exception e) {
            sc.nextLine();
            System.out.println("Invalid input.");
            return;
        }
        if(agencyChoice < 1 || agencyChoice > agencies.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        // password management
        Agency selected=agencies.get(agencyChoice-1);
        System.out.print("Password: ");
        String pwd = sc.nextLine();

        if (!pwd.equals(selected.getPassword())) {
            System.out.println("Wrong password.");
            return;
        }

        System.out.println("Login successful!");
        ParkingService.getInstance().start(selected);
    }

    private String readString(String msg) {
        while (true) {
            System.out.print(msg);
            String val = sc.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("Cannot be empty. Please try again.");
        }
    }



    //handling the Invalid Input
    private double expReadDouble(String v) {
        while (true) {
            System.out.print(v);
            try {
                double val = sc.nextDouble();
                sc.nextLine(); // consume newline
                if (val >= 0) return val;
                System.out.println("Value cannot be negative. Try again.");
            } catch (Exception e) {
                sc.nextLine(); // clear invalid input
                System.out.println("Invalid number. Try again.");
            }
        }
    }
    private int expReadInt(String v) {
        while (true) {
            System.out.print(v);
            try {
                int val = sc.nextInt();
                sc.nextLine(); // consume newline
                if (val >= 0) return val;
                System.out.println("Value cannot be negative. Try again.");
            } catch (Exception e) {
                sc.nextLine(); // clear invalid input
                System.out.println("Invalid number. Try again.");
            }
        }
    }

}
