<table>
  <tr>
    <td width="60%">

## Parking Management System

A console-based Java application built using Object-Oriented Programming (OOP) to manage multiple parking agencies independently within a single system.

- Multi-agency support
- Vehicle entry & exit management
- Parking slot allocation
- Billing and receipt generation
- Clean modular OOP design

   </td>

   <td width="40%" align="right">
      <img 
        src="https://private-user-images.githubusercontent.com/74038190/242390692-0b335028-1d3d-4ee5-b5b3-a373d499be7e.gif?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NzkyNzQyNDEsIm5iZiI6MTc3OTI3Mzk0MSwicGF0aCI6Ii83NDAzODE5MC8yNDIzOTA2OTItMGIzMzUwMjgtMWQzZC00ZWU1LWI1YjMtYTM3M2Q0OTliZTdlLmdpZj9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNjA1MjAlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjYwNTIwVDEwNDU0MVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTI5ZWQ3YmNiNmFhZjhjMDhkNTAxNWY0Nzk1NTMzNGRkNjhlNDkwNzYxYTI2OTRhOTI0NTBkYTI3MjU1M2MyM2ImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JnJlc3BvbnNlLWNvbnRlbnQtdHlwZT1pbWFnZSUyRmdpZiJ9.bCyVunwjLk6lTLtMIaH50Cq-wlv-0QqbNyjR6se9CFg" 
        width="300" 
        alt="Coder GIF"
      />
   </td>
  </tr>
</table>



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

