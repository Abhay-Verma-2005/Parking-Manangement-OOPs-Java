package config;
import model.Agency;
import java.io.*;
import java.util.*;
public class DataStore {
    public static Map<String, Agency> agcs = new HashMap<>();
    private static final String DIR = "data";
    private static final String PATH = DIR + "/agencies.txt";

    public static void init() {
        File d=new File(DIR);
        if (!d.exists()) d.mkdirs();    // data folder deleted then cover up ,for ease in cloning repo and for new developer
        load();
        System.out.println("         Total Agency :" + agcs.size());
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    private static void load() {
        File f = new File(PATH);
        try ( ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
            agcs = (Map<String, Agency>) is.readObject();
        } catch (Exception e) {
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
