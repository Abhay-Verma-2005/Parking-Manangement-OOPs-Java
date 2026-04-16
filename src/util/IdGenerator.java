package util;

public class IdGenerator {
    static int id = 1;

    public static int next() {
        return id++;
    }
}