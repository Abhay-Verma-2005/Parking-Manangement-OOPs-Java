package util;

public class HashUtil {

    public static String hash(String s) {
        int h = 0;
        for (char c : s.toCharArray()) {
            h = 31 * h + c;
        }
        return String.valueOf(Math.abs(h));
    }
}
