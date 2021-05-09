package com.example.becoapk21.Parking;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ParkingHelperClass {
    int parkingChar;
    int parkingDigit;
    Date parkingTime;
    String parked_name;


    public ParkingHelperClass() {

    }

    public ParkingHelperClass(int parkingChar, Date parkingTime, int parkingDigit, String parked_name) {
        this.parkingChar = parkingChar;
        this.parkingTime = parkingTime;
        this.parkingDigit = parkingDigit;
        this.parked_name = parked_name;
    }

    public String getParked_name() {
        return parked_name;
    }

    public int getParkingSpot() {
        return parkingChar;
    }

    public int getParkingDigit() {
        return parkingDigit;
    }

    public void setParkingSpot(int parkingChar) {
        this.parkingChar = parkingChar;
    }

    public Date getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(Date parkingTime) {
        this.parkingTime = parkingTime;
    }


}
