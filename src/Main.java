import java.util.*;
import config.DataStore;
import model.Agency;
import dao.AgencyDAO;
import service.ParkingSystem;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AgencyDAO adao = new AgencyDAO();
    static ParkingSystem psys = new ParkingSystem();

    public static void main(String[] args) {

        DataStore.init();

        System.out.println("  PARKING MANAGEMENT SYSTEM");

        while (true) {
            System.out.println("\n1. Register Agency");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int ch;
            try {
                ch = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (ch == 1) reg();
            else if (ch == 2) log();
            else if (ch == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static void reg() {
        System.out.print("Agency Name: ");
        String n = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        System.out.print("Description: ");
        String d = sc.nextLine();

        int b = rd("Bike slots: ");
        int c = rd("Car slots: ");
        int bs = rd("Bus slots: ");

        int ol = rd("Overtime Limit (Hrs): ");
        
        System.out.print("Overtime Fine/Hr (Rs.): ");
        double oc;
        try {
            oc = sc.nextDouble();
        } catch (Exception e) {
            oc = 0.0;
        }
        sc.nextLine();

        adao.register(n, p, d, b, c, bs, ol, oc);
    }

    static void log() {
        System.out.print("Agency Name: ");
        String n = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        Agency a = adao.login(n, p);

        if (a != null) {
            System.out.println("Login successful! Welcome, " + a.name);
            psys.loadSlots(a);
            Dashboard.start(a, psys);
            
            psys.loadSlots(null);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static int rd(String m) {
        System.out.print(m);
        int x;
        try {
            x = sc.nextInt();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Invalid number, defaulting to 0.");
            return 0;
        }
        sc.nextLine();
        return x;
    }
}