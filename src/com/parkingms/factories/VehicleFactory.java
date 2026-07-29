package com.parkingms.factories;

import com.parkingms.interfaces.Vehicle;
import com.parkingms.models.BaseVehicle;

public final class VehicleFactory {

    private VehicleFactory() { }

    public static int readVehicleType(String input) {
        try {
        String type = input.trim().toUpperCase().split("")[0];

        switch (type) {
            case "CAR":
            case "VAN":
            case "CAB":
            case "FOUR":
                return Vehicle.typeCar;

            case "BIKE":
            case "SCOOTER":
            case "SCOTY":
            case "TWO":
                return Vehicle.typeTwoWheeler;

            case "TRUCK":
            case "BUS":
            case "HEAVY":
                return Vehicle.typeHeavyVehicle;

            default:
                System.out.println("Invalid vehicle type");
                return -1;
        }
    } 
    catch (Exception e) {
        throw new IllegalArgumentException("Vehicle type cannot be empty.");
    }
}
    public static Vehicle createVehicle(int vehicleType, String vehicleNo) {
        switch (vehicleType) {
            case Vehicle.typeCar:
                return new BaseVehicle.Car(vehicleNo);

            case Vehicle.typeTwoWheeler:
                return new BaseVehicle.TwoWheeler(vehicleNo);

            case Vehicle.typeHeavyVehicle:
                return new BaseVehicle.HeavyVehicle(vehicleNo);

            default:
                throw new IllegalArgumentException("Invalid vehicle type code: " + vehicleType);
        }
    }
}