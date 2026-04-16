# Parking Management System
A lightweight, Object-Oriented Java application to securely manage multiple parking agencies from a single console.

## Features

- **Multi-Agency Support**: Run multiple distinct parking agencies. Data is strictly isolated per business.
- **Smart Menus**: The system tracks live slot capacity. If a vehicle type is physically full, the option hides from the menu to prevent overbooking.
- **Dynamic Pricing**: Edit your parking tariffs directly from the dashboard. New prices are instantly applied to all new checkouts.
- **Automated Checkout**: Computes the exact parked duration and automatically calculates the final bill based on your agency's pricing rates.
- **Local Persistence**: All vehicles, revenues, and pricing data are serialized natively and saved to your hard drive.

## OOP Principles Applied
This project is built directly upon the 4 pillars of Object-Oriented Programming:

1. **Encapsulation**: The `Agency` class strictly safeguards its own `ParkingSlot` instances, `Vehicle` arrays, and `revenue`. No global variables are used—external classes cannot maliciously alter another agency's private logic.
2. **Abstraction**: Complex byte-level file saving and object mapping is completely abstracted behind `DataStore.java`. The rest of the app simply calls `DataStore.save()` without needing to understand how Java File IO works.
3. **Inheritance**: Models inherently utilize interface inheritance by implementing the `Serializable` contract, forcing them to adhere to Java's strict byte-stream serialization hierarchy. 
4. **Polymorphism**: The system utilizes polymorphic behavior through the `VehicleType` enum and generic Java Collections, treating different types of inputs (Bike, Car, Bus) dynamically at runtime without rigid hardcoding.

## Security
- **HashUtil**: A dedicated utility that handles encryption. It applies cryptographic hashing to agency login passwords so plain text is never stored.

## Project Structure
```text
src/
├── Main.java              (Program Entry & Registration CLI)
├── Dashboard.java         (Interactive interface for Agency owners)
├── model/
│   ├── Agency.java        (Primary encapsulated business object)
│   ├── Vehicle.java       (State and timing of parked vehicles)
│   ├── ParkingSlot.java   (Slot availability and assignment)
│   └── VehicleType.java   (Enum: BIKE, CAR, BUS)
├── dao/
│   └── AgencyDAO.java     (Data Retrieval and login validation)
├── service/
│   └── ParkingSystem.java (Core logic for booking / checkout routing)
├── config/
│   └── DataStore.java     (Offline file serialization handling)
└── util/
    ├── HashUtil.java      (Password hashing and ID encryption)
    ├── IdGenerator.java   (Ticket enumeration)
    └── TimeUtil.java      (Time calculation algorithms)
```

## How to Run
1. **Compile**:
   ```bash
   javac -d out src/**/*.java src/*.java
   ```
2. **Run**:
   ```bash
   java -cp out Main
   ```
