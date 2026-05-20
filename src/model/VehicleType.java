package model;

public enum VehicleType {
    TWO_WHEELER, FOUR_WHEELER, HEAVY_VEHICLE;

    public static VehicleType fromString(String t) {
        if (t == null) return null;
        switch (t.toLowerCase().trim()) {
            case "2 wheeler":
            case "two_wheeler":
            case "twowheeler": return TWO_WHEELER;
            case "4 wheeler":
            case "four_wheeler":
            case "fourwheeler": return FOUR_WHEELER;
            case "heavy vehicle":
            case "heavy_vehicle":
            case "heavyvehicle": return HEAVY_VEHICLE;
            default: return null;
        }
    }
}
