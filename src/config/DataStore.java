package config;

import model.Agency;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    public static Map<String, Agency> agcs = new HashMap<>();
    private static final String DIR = "data";
    private static final String PATH = DIR + "/agencies.dat";

    public static void init() {
        File d = new File(DIR);
        if (!d.exists()) d.mkdirs();

        load();
        System.out.println("Data" + agcs.size());
    }

    @SuppressWarnings("unchecked")
    private static void load() {
        File f = new File(PATH);
        if (!f.exists()) return;

        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
            agcs = (Map<String, Agency>) is.readObject();
        } catch (Exception e) {
            System.out.println("Empty");
            agcs = new HashMap<>();
        }
    }

    public static void save() {
        File f = new File(PATH);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(agcs);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
