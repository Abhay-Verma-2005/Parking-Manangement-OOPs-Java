package com.parkingms;

import com.parkingms.services.AgencyService;
import com.parkingms.services.UserService;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--------PARKING MANAGEMENT SYSTEM--------");
        while (true) {
            System.out.println("\n1. Register Agency");
            System.out.println("2. Login");
            System.out.println("3. Register User");
            System.out.println("4. View All Users");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int input;
            try {
                input = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Please enter a valid number.");
                continue;
            }
            if      (input == 1) AgencyService.getInstance().Registration();
            else if (input == 2) AgencyService.getInstance().Login();
            else if (input == 3) UserService.getInstance().registerUser();
            else if (input == 4) UserService.getInstance().showAllUsers();
            else if (input == 5) {
                System.out.println("Thank You for Visiting! ");
                return;
            }
            else System.out.println("Invalid choice. ");
        }
    }
}
