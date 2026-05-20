package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Agency implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String id;
    public String name;
    public String pass;
    public String desc;
    public int twoWheeler;
    public int fourWheeler;
    public int heavyVehicle;
    public int ovLmt;
    public double ovChg;
    
    public List<ParkingSlot> slts = new ArrayList<>();
    public List<Vehicle> vehs = new ArrayList<>();
    public double rev = 0.0;
    public Map<VehicleType, Double> prc = new HashMap<>();

    public Agency(String n, String p, String d, int b, int c, int bs, int ol, double oc, double p2, double p4, double ph) {
        this.id = UUID.randomUUID().toString();
        this.name = n;
        this.pass = p;
        this.desc = d;
        this.twoWheeler = b;
        this.fourWheeler = c;
        this.heavyVehicle = bs;
        this.ovLmt = ol;
        this.ovChg = oc;
        
        prc.put(VehicleType.TWO_WHEELER, p2);
        prc.put(VehicleType.FOUR_WHEELER, p4);
        prc.put(VehicleType.HEAVY_VEHICLE, ph);
        
        int cid = 1;
        for (int i = 0; i < twoWheeler; i++) slts.add(new ParkingSlot(cid++, VehicleType.TWO_WHEELER));
        for (int i = 0; i < fourWheeler; i++) slts.add(new ParkingSlot(cid++, VehicleType.FOUR_WHEELER));
        for (int i = 0; i < heavyVehicle; i++) slts.add(new ParkingSlot(cid++, VehicleType.HEAVY_VEHICLE));
    }
}