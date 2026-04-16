
# Parking Management System

A console-based Java application built using Object-Oriented Programming (OOP) to manage multiple parking agencies independently within a single system.

---

## Overview

This system allows multiple parking agencies to operate in one application while keeping their data completely separate. Each agency manages its own parking slots, vehicles, pricing, and revenue without affecting others.

---

## Features

### Multi-Agency Support
- Multiple parking agencies can be created and managed
- Each agency has its own isolated data (slots, vehicles, revenue)

### Smart Menu System
- Automatically hides vehicle options when slots are full
- Prevents overbooking

### Dynamic Pricing
- Agency owners can update parking charges anytime
- Updated prices are applied instantly to new checkouts

### Automated Billing
- Calculates parking duration using entry and exit time
- Generates total bill automatically

### Data Persistence
- All data is stored locally using Java Serialization
- Data remains محفوظ even after restarting the program

---

## OOP Concepts Used

### Encapsulation
- Each agency manages its own data securely
- No direct external access to internal data

### Abstraction
- File handling and data storage logic is hidden
- Simple method calls like `DataStore.save()` are used

### Inheritance
- Classes implement `Serializable` for object storage

### Polymorphism
- Different vehicle types (Bike, Car, Bus) handled dynamically

---

## Security

- Passwords are encrypted using hashing
- Plain text passwords are never stored

---

## Project Structure

```

src/
├── Main.java              
├── Dashboard.java         
├── model/
│   ├── Agency.java        
│   ├── Vehicle.java      
│   ├── ParkingSlot.java  
│   └── VehicleType.java   
├── dao/
│   └── AgencyDAO.java     
├── service/
│   └── ParkingSystem.java (Core Operations)
├── config/
│   └── DataStore.java     
└── util/
├── HashUtil.java      (Password Encryption)
├── IdGenerator.java   
└── TimeUtil.java      

````

---

## How It Works

1. User registers or logs in as an agency
2. Dashboard is displayed
3. User can:
   - Add vehicles
   - Allocate parking slots
   - Checkout vehicles
   - View reports
4. Data is saved automatically

---

## How to Run

### Compile
```bash
javac -d out src/**/*.java src/*.java
````

### Run

```bash
java -cp out Main
```

---

## Tech Stack

* Java (Core + OOP)
* File Handling (Serialization)
* Collections Framework

---

## Use Case

This project simulates a real-world parking management system where multiple parking businesses can operate independently with secure data handling and automated billing.

