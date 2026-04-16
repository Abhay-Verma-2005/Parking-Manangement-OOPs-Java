package model;
import java.io.Serializable;

public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    public String nme;
    public String num;
    public VehicleType type;
    public long ent;
    public long ext;
    public int tkt;

    public Vehicle(String nme, String num, VehicleType type, int tkt, long ent) {
        this.nme = nme;
        this.num = num;
        this.type = type;
        this.tkt = tkt;
        this.ent = ent;
        this.ext = 0;
    }
}
