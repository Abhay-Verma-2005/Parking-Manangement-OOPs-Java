package com.parkingms.models;

public class Slot implements Comparable<Slot> {
    private int slotNumber;
    private int type;
    private boolean occupied;

    public Slot(int slotNumber, int type, boolean occupied) {
        this.slotNumber = slotNumber;
        this.type = type;
        this.occupied = occupied;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getType() {
        return type;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public int compareTo(Slot other) {
        // false (available) comes before true (occupied)
        if (this.occupied != other.occupied) {
            return Boolean.compare(this.occupied, other.occupied);
        }
        // If both have the same status, sort by slot number ascending
        return Integer.compare(this.slotNumber, other.slotNumber);
    }
}
