package dao;

import config.DataStore;
import model.Agency;
import util.HashUtil;

public class AgencyDAO {

    public void register(String n, String p, String d, int b, int c, int bs, int ol, double oc) {
        if (DataStore.agcs.containsKey(n)) {
            System.out.println("Agency with name '" + n + "' already exists!");
            return;
        }

        Agency a = new Agency(n, HashUtil.hash(p), d, b, c, bs, ol, oc);
        DataStore.agcs.put(n, a);
        DataStore.save();
        System.out.println("Agency registered successfully!");
    }

    public Agency login(String n, String p) {
        Agency a = DataStore.agcs.get(n);
        if (a == null) return null;

        String hp = HashUtil.hash(p);
        if (a.pass.equals(hp)) {
            return a;
        }
        return null;
    }
}