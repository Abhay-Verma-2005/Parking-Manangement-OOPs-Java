package com.parkingms.interfaces;

import java.io.Serializable;
// Interface for Vehicle class

public interface Vehicle extends Serializable {

    int typeCar = 0;
    int typeTwoWheeler = 1;
    int typeHeavyVehicle = 2;

    String getVehicleNo();
    int getType();
    long getEntryTimeMs();
    long getExitTimeMs();
    void setExitTimeMs(long exitTimeMs);
}











//Serializable is a marker interface in Java.
//        import java.io.Serializable;
//
//public class Car implements Serializable {
//}
//It has no methods.
//public interface Serializable {
//}
//Its only purpose is to tell the JVM:
//        "This object is allowed to be converted into a stream of bytes."
//That process is called serialization.
//        Why do we need it?
//Suppose you have
//Car car = new Car("UP85AB1234");
//Normally this object exists only in RAM (Heap).
//
//        +----------------------+Heap
//        | Car Object           |
//        | vehicleNo=UP85AB1234 |
//        +----------------------+
//If the program closes, the object disappears.
//To save it permanently (file/database/network), Java first converts it into bytes.
//Car Object
//      |
//              |
//Serialization
//      |
//v
//101010010010101001...
//        |
//v
//vehicle.dat
//        Later,
//vehicle.dat
//      |
//Deserialization
//      |
//v
//Car Object
//The object is recreated exactly as it was.
//Example
//import java.io.*;
//
//class Car implements Serializable {
//
//    String number;
//
//    Car(String number) {
//        this.number = number;
//    }
//}
//Save object
//Car car = new Car("UP85AB1234");
//
//ObjectOutputStream out =
//        new ObjectOutputStream(new FileOutputStream("car.dat"));
//
//out.writeObject(car);
//out.close();
//Read object
//ObjectInputStream in =
//        new ObjectInputStream(new FileInputStream("car.dat"));
//
//Car car = (Car) in.readObject();
//
//System.out.println(car.number);
//Output
//        UP85AB1234



// Car Object
//(vehicleNo = UP85AB1234)
//        │
//        ▼
//Serialization
//(writeObject)
//        │
//        ▼
//car.dat
//(Binary Bytes)
//        │
//        ▼
//Deserialization
//(readObject)
//        │
//        ▼
//Car Object
//(vehicleNo = UP85AB1234)
