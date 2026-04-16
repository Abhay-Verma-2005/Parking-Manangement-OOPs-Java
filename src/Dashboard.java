import java.util.*;
import model.Agency;
import model.VehicleType;
import service.ParkingSystem;
import config.DataStore;

public class Dashboard {

    static Scanner sc = new Scanner(System.in);

    public static void start(Agency agcy, ParkingSystem psys) {

        while (true) {

            System.out.println("\n---- " + agcy.name.toUpperCase() + " Dashboard  ----");
            System.out.println(agcy.desc);
            psys.summary();

            System.out.println("\n1. Add Vehicle");
            System.out.println("2. Checkout Vehicle");
            System.out.println("3. Show All Vehicles");
            System.out.println("4. Show Slots");
            System.out.println("5. Show Profit");
            System.out.println("6. Edit Prices");
            System.out.println("7. Logout");
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

            if (ch == 1) {
                long fb = agcy.slts.stream().filter(s -> !s.occ && s.type == VehicleType.BIKE).count();
                long fc = agcy.slts.stream().filter(s -> !s.occ && s.type == VehicleType.CAR).count();
                long fbs = agcy.slts.stream().filter(s -> !s.occ && s.type == VehicleType.BUS).count();

                if (fb == 0 && fc == 0 && fbs == 0) {
                    System.out.println("No parking slots available in this agency.");
                    continue;
                }

                System.out.println("\nSelect Vehicle Type:");
                if (fb > 0) System.out.println("1 -> Two Wheeler");
                if (fc > 0) System.out.println("2 -> Car");
                if (fbs > 0) System.out.println("3 -> Bus");
                System.out.print("Choose type: ");

                String ts = "";
                int tc;
                try {
                    tc = sc.nextInt();
                    sc.nextLine();
                } catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid input.");
                    continue;
                }

                if (tc == 1 && fb > 0) ts = "bike";
                else if (tc == 2 && fc > 0) ts = "car";
                else if (tc == 3 && fbs > 0) ts = "bus";
                else {
                    System.out.println("Invalid selection or no slots available for this type.");
                    continue;
                }

                System.out.print("Owner Name: ");
                String n = sc.nextLine();

                System.out.print("Vehicle Number: ");
                String num = sc.nextLine();

                psys.addVehicle(n, ts, num);

            } else if (ch == 2) {
                System.out.print("Enter Ticket Number: ");
                String t = sc.nextLine();
                psys.checkout(t);

            } else if (ch == 3) {
                psys.showVehicles();

            } else if (ch == 4) {
                psys.showSlots();

            } else if (ch == 5) {
                psys.profit();

            } else if (ch == 6) {
                System.out.println("\n--- Current Pricing ---");
                System.out.println("Bike: Rs." + agcy.prc.get(VehicleType.BIKE));
                System.out.println("Car: Rs." + agcy.prc.get(VehicleType.CAR));
                System.out.println("Bus: Rs." + agcy.prc.get(VehicleType.BUS));
                
                System.out.println("\nEnter New Prices:");
                try {
                    System.out.print("Bike Price: ");
                    double bp = sc.nextDouble();
                    System.out.print("Car Price: ");
                    double cp = sc.nextDouble();
                    System.out.print("Bus Price: ");
                    double sp = sc.nextDouble();
                    sc.nextLine();

                    agcy.prc.put(VehicleType.BIKE, bp);
                    agcy.prc.put(VehicleType.CAR, cp);
                    agcy.prc.put(VehicleType.BUS, sp);
                    
                    DataStore.save();
                    System.out.println("Prices updated successfully.");
                } catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid number format. Prices not updated.");
                }

            } else if (ch == 7) {
                System.out.println("Logged out.");
                break;

            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}