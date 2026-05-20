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
                long fb = agcy.slts.stream().filter(s -> !s.occ && s.type == VehicleType.TWO_WHEELER).count();
                long fc = agcy.slts.stream().filter(s -> !s.occ && s.type == VehicleType.FOUR_WHEELER).count();
                long fbs = agcy.slts.stream().filter(s -> !s.occ && s.type == VehicleType.HEAVY_VEHICLE).count();

                if (fb == 0 && fc == 0 && fbs == 0) {
                    System.out.println("No parking slots available in this agency.");
                    continue;
                }

                System.out.println("\nSelect Vehicle Type:");
                if (fb > 0) System.out.println("1 -> 2 Wheeler");
                if (fc > 0) System.out.println("2 -> 4 Wheeler");
                if (fbs > 0) System.out.println("3 -> Heavy Vehicle");
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

                if (tc == 1 && fb > 0) ts = "2 wheeler";
                else if (tc == 2 && fc > 0) ts = "4 wheeler";
                else if (tc == 3 && fbs > 0) ts = "heavy vehicle";
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
                System.out.println("2 Wheeler: Rs." + agcy.prc.get(VehicleType.TWO_WHEELER));
                System.out.println("4 Wheeler: Rs." + agcy.prc.get(VehicleType.FOUR_WHEELER));
                System.out.println("Heavy Vehicle: Rs." + agcy.prc.get(VehicleType.HEAVY_VEHICLE));
                
                System.out.println("\nEnter New Prices:");
                try {
                    System.out.print("2 Wheeler Price: ");
                    double bp = sc.nextDouble();
                    System.out.print("4 Wheeler Price: ");
                    double cp = sc.nextDouble();
                    System.out.print("Heavy Vehicle Price: ");
                    double sp = sc.nextDouble();
                    sc.nextLine();

                    agcy.prc.put(VehicleType.TWO_WHEELER, bp);
                    agcy.prc.put(VehicleType.FOUR_WHEELER, cp);
                    agcy.prc.put(VehicleType.HEAVY_VEHICLE, sp);
                    
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