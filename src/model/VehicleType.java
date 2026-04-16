package model;

public class VehicleType {

    public static final String BIKE = "BIKE";
    public static final String CAR = "CAR";
    public static final String BUS = "BUS";

    public static String fromString(String t) {
        if (t == null) return null;
        String val = t.trim().toUpperCase();

        if (val.equals(BIKE) || val.equals(CAR) || val.equals(BUS)) {
            return val;
        }
        return null;
    }
}
