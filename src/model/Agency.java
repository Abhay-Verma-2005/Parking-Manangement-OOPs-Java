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
    public int bike;
    public int car;
    public int bus;
    public int ovLmt;
    public double ovChg;
    
    public List<ParkingSlot> slts = new ArrayList<>();
    public List<Vehicle> vehs = new ArrayList<>();
    public double rev = 0.0;
    public Map<VehicleType, Double> prc = new HashMap<>();

    public Agency(String n, String p, String d, int b, int c, int bs, int ol, double oc) {
        this.id = UUID.randomUUID().toString();
        this.name = n;
        this.pass = p;
        this.desc = d;
        this.bike = b;
        this.car = c;
        this.bus = bs;
        this.ovLmt = ol;
        this.ovChg = oc;
        
        prc.put(VehicleType.BIKE, 10.0);
        prc.put(VehicleType.CAR, 20.0);
        prc.put(VehicleType.BUS, 30.0);
        
        int cid = 1;
        for (int i = 0; i < bike; i++) slts.add(new ParkingSlot(cid++, VehicleType.BIKE));
        for (int i = 0; i < car; i++) slts.add(new ParkingSlot(cid++, VehicleType.CAR));
        for (int i = 0; i < bus; i++) slts.add(new ParkingSlot(cid++, VehicleType.BUS));
    }
}