package model;

import java.util.Set;

public class VehicleType {

    public static final String BIKE = "BIKE";
    public static final String CAR = "CAR";
    public static final String BUS = "BUS";

    private static final Set<String> TYPES = Set.of(BIKE, CAR, BUS);

    public static String fromString(String t) {
        if (t == null) return null;
        String val = t.trim().toUpperCase();
        return TYPES.contains(val) ? val : null;
    }
}
