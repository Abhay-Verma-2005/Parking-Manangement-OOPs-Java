package util;

public class TimeUtil {
    public static long now() {
        return System.currentTimeMillis();
    }

    public static String format(long ms) {
        if (ms == 0) return "N/A";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, HH:mm");
        return sdf.format(new java.util.Date(ms));
    }
}
