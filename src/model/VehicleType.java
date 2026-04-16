package model;

public enum VehicleType {
    BIKE, CAR, BUS;

    public static VehicleType fromString(String t) {
        if (t == null) return null;
        switch (t.toLowerCase().trim()) {
            case "bike": return BIKE;
            case "car": return CAR;
            case "bus": return BUS;
            default: return null;
        }
    }
}
