package com.parkingms.models;

import com.parkingms.interfaces.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity(name = "ParkRecord")
@Table(name = "parking")
public class ParkRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    private String ownerName;

    private String vehicleNo;

    private int vehicleType;

    private int slotNo;

    private long entryTimeMs;

    // Protected constructor for Hibernate and Builder Pattern
    protected ParkRecord() {
        this.entryTimeMs = System.currentTimeMillis();
    }

    public Integer getTicketId() { return ticketId; }
    public String getOwnerName() { return ownerName; }
    public String getVehicleNo() { return vehicleNo; }
    public int getVehicleType() { return vehicleType; }
    public int getSlotNo() { return slotNo; }
    public long getEntryTimeMs() { return entryTimeMs; }

    public String getFormattedTicketNo() {
        return String.format("%04d", ticketId != null ? ticketId : 0);
    }

    public String getTypeLabel() {
        if (vehicleType == Vehicle.typeCar)               return "4 Wheeler";
        else if (vehicleType == Vehicle.typeTwoWheeler)    return "2 Wheeler";
        else if (vehicleType == Vehicle.typeHeavyVehicle)  return "Heavy Vehicle";
        else                                               return "Unknown";
    }

    public String getSlotLabel() {
        if (vehicleType == Vehicle.typeCar)
            return "C-" + String.format("%03d", slotNo);
        else if (vehicleType == Vehicle.typeTwoWheeler)
            return "B-" + String.format("%03d", slotNo);
        else if (vehicleType == Vehicle.typeHeavyVehicle)
            return "H-" + String.format("%03d", slotNo);
        else
            return "?-" + slotNo;
    }

    public static class Builder {
        private ParkRecord record = new ParkRecord();

        public Builder setOwnerName(String ownerName) {
            record.ownerName = ownerName;
            return this;
        }

        public Builder setVehicleNo(String vehicleNo) {
            record.vehicleNo = vehicleNo;
            return this;
        }

        public Builder setVehicleType(int vehicleType) {
            record.vehicleType = vehicleType;
            return this;
        }

        public Builder setSlotNo(int slotNo) {
            record.slotNo = slotNo;
            return this;
        }

        public Builder setEntryTimeMs(long entryTimeMs) {
            record.entryTimeMs = entryTimeMs;
            return this;
        }

        public ParkRecord build() {
            return record;
        }
    }
}
