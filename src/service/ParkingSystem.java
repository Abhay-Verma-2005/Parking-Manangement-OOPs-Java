package service;

import model.*;
import config.DataStore;
import util.IdGenerator;
import util.TimeUtil;

public class ParkingSystem {

    public Agency curr;

    public void loadSlots(Agency a) {
        curr = a;
    }

    public void addVehicle(String n, String ts, String num) {
        VehicleType t = VehicleType.fromString(ts);
        if (t == null) return;

        ParkingSlot pt = null;
        for (ParkingSlot s : curr.slts) {
            if (!s.occ && s.type == t) {
                pt = s;
                break;
            }
        }

        if (pt == null) {
            System.out.println("No slot available for type: " + t);
            return;
        }

        pt.occ = true;
        int tkt = IdGenerator.next();
        long tm = TimeUtil.now();

        Vehicle v = new Vehicle(n, num, t, tkt, tm);
        curr.vehs.add(v);

        System.out.println("Ticket: " + tkt + " | Slot: " + pt.id);
        DataStore.save();
    }

    public void checkout(String t) {
        int tk;
        try {
            tk = Integer.parseInt(t.trim());
        } catch (Exception e) {
            System.out.println("Invalid ticket number.");
            return;
        }

        Vehicle tv = null;
        for (Vehicle v : curr.vehs) {
            if (v.tkt == tk) {
                tv = v;
                break;
            }
        }

        if (tv == null) {
            System.out.println("Ticket not found.");
            return;
        }
        if (tv.ext > 0) {
            System.out.println("This ticket has already been checked out.");
            return;
        }

        long st = tv.ent;
        long end = TimeUtil.now();

        double hr = (end - st) / (1000.0 * 60 * 60);
        if (hr < 1) hr = 1;

        Double rt = curr.prc.get(tv.type);
        if (rt == null) rt = 0.0;

        double nrmHrs = Math.min(hr, curr.ovLmt);
        double extHrs = Math.max(0, hr - curr.ovLmt);

        double amt = Math.round(((nrmHrs * rt) + (extHrs * curr.ovChg)) * 100.0) / 100.0;

        tv.ext = end;

        curr.rev += amt;

        for (ParkingSlot s : curr.slts) {
            if (s.occ && s.type == tv.type) {
                s.occ = false;
                break;
            }
        }

        System.out.println("\n--- RECEIPT ---");
        System.out.println("Owner Name: " + tv.nme);
        System.out.println("Vehicle Number: " + tv.num);
        System.out.println("Vehicle Type: " + tv.type);
        System.out.println("Time Arrived: " + TimeUtil.format(tv.ent));
        System.out.println("Time Departed: " + TimeUtil.format(tv.ext));
        System.out.println("Hours: " + String.format("%.2f", hr) + " (Overtime: " + String.format("%.2f", extHrs) + ")");
        System.out.println("Amount: Rs." + amt);
        System.out.println("---------------");
        DataStore.save();
    }

    public void showVehicles() {
        if (curr.vehs.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        System.out.println("\n--- Vehicles ---");
        for (Vehicle v : curr.vehs) {
            String st = v.ext > 0 ? "CHECKED_OUT" : "PARKED";
            System.out.printf("Number: %-10s | Type: %-4s | Ticket: %-6s | Status: %s | Arrived: %s%n", v.num, v.type, v.tkt, st, TimeUtil.format(v.ent));
        }
    }

    public void showSlots() {
        System.out.println("\n--- Slots ---");
        for (ParkingSlot s : curr.slts) {
            System.out.printf("Slot %2d | %-4s | %s%n", s.id, s.type, (s.occ ? "OCCUPIED" : "FREE"));
        }
    }

    public void profit() {
        System.out.println("Total Profit for Agency: Rs." + curr.rev);
    }

    public void summary() {
        long fr = curr.slts.stream().filter(s -> !s.occ).count();
        long oc = curr.slts.stream().filter(s -> s.occ).count();
        System.out.println("Total Slots: " + curr.slts.size() + " | Occupied: " + oc + " | Free: " + fr);
    }
}
