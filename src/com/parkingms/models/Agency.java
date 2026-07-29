// Builder Design Pattern
package com.parkingms.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "agencies")
public class Agency {

    @Id
    @Column(name = "agency_id")
    private String agencyId;
    @Column(name = "agency_name")
    private String agencyName;
    @Column(name = "agency_desc")
    private String agencyDesc;
    @Column(name = "password")
    private String password;
    @Column(name = "total_car_slots")
    private int totalCarSlots;
    @Column(name = "total_bike_slots")
    private int totalBikeSlots;
    @Column(name = "total_heavy_veh_slots")
    private int totalHeavyVehSlots;
    @Column(name = "car_rph")
    private int carRPH;
    @Column(name = "bike_rph")
    private int bikeRPH;
    @Column(name = "heavy_veh_rph")
    private int heavyVehRPH;
    @Column(name = "over_time_lmt")
    private int overTimeLmt;
    @Column(name = "over_time_charge")
    private double overTimeCharge;

    // For protecting it from outer class changes - private constructor
    private Agency() {}

    public String getAgencyId() { return agencyId; }
    public String getAgencyName() { return agencyName; }
    public String getAgencyDesc() { return agencyDesc; }
    public int getTotalCarSlots() { return totalCarSlots; }
    public int getTotalBikeSlots() { return totalBikeSlots; }
    public int getTotalHeavyVehSlots() { return totalHeavyVehSlots; }
    public int getCarRPH() { return carRPH; }
    public int getBikeRPH() { return bikeRPH; }
    public int getHeavyVehRPH() { return heavyVehRPH; }
    public String getPassword() { return password; }
    public int getOverTimeLmt() { return overTimeLmt; }
    public double getOverTimeCharge() { return overTimeCharge; }

    //Builder class Agency ke andar hai toh
    // Isliye Builder ko private constructor access karne ki permission hai.
    public static class Builder {

        // making object -> Builder ke andar ek Agency object create ho gaya.
        //Agency object ab Builder ke andar store hai.
        private Agency agency=new Agency();


        public Builder setAgencyId(String agencyId) {
            agency.agencyId = agencyId;
            return this;
        }

        public Builder setAgencyName(String agencyName) {
            agency.agencyName = agencyName;
            return this;
        }

        public Builder setAgencyDesc(String agencyDesc) {
            agency.agencyDesc = agencyDesc;
            return this;
        }

        public Builder setPassword(String password) {
            agency.password = password;
            return this;
        }

        public Builder setTotalCarSlots(int totalCarSlots) {
            agency.totalCarSlots = totalCarSlots;
            return this;
        }

        public Builder setTotalBikeSlots(int totalBikeSlots) {
            agency.totalBikeSlots = totalBikeSlots;
            return this;
        }

        public Builder setTotalHeavyVehSlots(int totalHeavyVehSlots) {
            agency.totalHeavyVehSlots = totalHeavyVehSlots;
            return this;
        }

        public Builder setCarRPH(int carRPH) {
            agency.carRPH = carRPH;
            return this;
        }

        public Builder setBikeRPH(int bikeRPH) {
            agency.bikeRPH = bikeRPH;
            return this;
        }

        public Builder setHeavyVehRPH(int heavyVehRPH) {
            agency.heavyVehRPH = heavyVehRPH;
            return this;
        }

        public Builder setOverTimeLmt(int overTimeLmt) {
            agency.overTimeLmt = overTimeLmt;
            return this;
        }

        public Builder setOverTimeCharge(double overTimeCharge) {
            agency.overTimeCharge = overTimeCharge;
            return this;
        }
        
        public Agency build() {
            return agency;
        }
    }
}
// Agar return this; na hota to new Builder()
//    .setAgencyName(...)
// ke baad chain khatam ho jati.
// Lekin return Builder( return this) ki wajah se dobara Builder mil gaya
// aur yaha reurn this builder ke object ko call kr rha h baar baar.
// Fir .setPassword() chal gayi.

// Bahar kaise laaye? Isliye
//    public Agency build() {
//        return agency;
//    }


//Jab tum likhte ho.   .build() Builder ke andar jo Agency object tha wohencyName("GLA")
//        .setPasswoke andar jo Agency object tha woh1 new Agency.Builder()
//Sabse pehle kya banta hai? ✔ Builder object
//
//Memory:
//+--------------------+ Heap
//| Builder Object     |
//+--------------------+
//Is waqt Agency variable abhi bana hi nahi hai.
//
//
//Step 2
//Builder object bante hi.     private Agency agency = new Agency();
//execute hoti hai. Ab Builder ke andar ek Agency object bhi ban gaya.
//Memory:
//
//+------------------------+ Heap
//| Builder Object          |
//|                         |
//| agency                  |- - - |
//+------------------------+       |
//                                 |
//                                 v
//                         +------------------+
//                         | Agency Object    |
//                         +------------------+
//Yaani
//1 Builder Object + 1 Agency Object Dono Heap me hain.
//
//Step 3
//.setAgencyName("GLA") Builder object pe method call hui. Agency object update hua.
//
//Step 4
//.setPassword("123") Builder object wahi hai. Agency object wahi hai. Sirf values change ho rahi hain.
//Step 5
//.build() Builder ke andar jo Agency object tha woh return ho gaya
