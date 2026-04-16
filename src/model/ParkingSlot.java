package model;
import java.io.Serializable;

public class ParkingSlot implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int id;
    public VehicleType type;
    public boolean occ;

    public ParkingSlot(int id, VehicleType t) {
        this.id = id;
        this.type = t;
        this.occ = false;
    }
}
