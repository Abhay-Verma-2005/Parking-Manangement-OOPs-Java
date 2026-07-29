package com.parkingms.services;

import com.parkingms.models.Agency;
import com.parkingms.interfaces.Vehicle;

public class BillingService {

    public static double calculateBill(double durationHrs, int ratePerHr,
                                       Agency agency) {
        if (durationHrs < 1) durationHrs = 1;

        double amountDue = durationHrs * ratePerHr;

        if (agency.getOverTimeLmt() > 0 && durationHrs > agency.getOverTimeLmt()) {
            double overtimeHrs = durationHrs - agency.getOverTimeLmt();
            amountDue += overtimeHrs * agency.getOverTimeCharge();
        }

        return amountDue;
    }

    public static int getRateForType(int vehicleType, Agency agency) {
        if (vehicleType == Vehicle.typeCar)              return agency.getCarRPH();
        else if (vehicleType == Vehicle.typeTwoWheeler)   return agency.getBikeRPH();
        else if (vehicleType == Vehicle.typeHeavyVehicle) return agency.getHeavyVehRPH();
        else                                              return 0;
    }
}
