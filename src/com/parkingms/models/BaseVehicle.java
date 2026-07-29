package com.parkingms.models;

import com.parkingms.interfaces.Vehicle;

public abstract class BaseVehicle implements Vehicle {

    private String vehicleNo;
    private int type;
    private long entryTimeMs;
    private long exitTimeMs;

    public BaseVehicle(String vehicleNo, int type) {
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.entryTimeMs = System.currentTimeMillis();
    }

    public String getVehicleNo() { return vehicleNo; }
    public int getType() { return type; }
    public long getEntryTimeMs() { return entryTimeMs; }
    public long getExitTimeMs() { return exitTimeMs; }
    public void setExitTimeMs(long exitTimeMs) { this.exitTimeMs = exitTimeMs; }

    public static class Car extends BaseVehicle {
        public Car(String vehicleNo) {
            super(vehicleNo, Vehicle.typeCar);
        }
    }

    public static class TwoWheeler extends BaseVehicle {
        public TwoWheeler(String vehicleNo) {
            super(vehicleNo, Vehicle.typeTwoWheeler);
        }
    }

    public static class HeavyVehicle extends BaseVehicle {
        public HeavyVehicle(String vehicleNo) {
            super(vehicleNo, Vehicle.typeHeavyVehicle);
        }
    }
}

// super keyword use to access or change the variable of the parent of that class