package com.example.becoapk21.Parking;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ParkingHelperClass {
    String parkingSpot;
    Date parkingTime;

    public ParkingHelperClass(){

    }
    public ParkingHelperClass(String parkingSpot, Date parkingTime) {
        this.parkingSpot = parkingSpot;
        this.parkingTime = parkingTime;

    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Date getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(Date parkingTime) {
        this.parkingTime = parkingTime;
    }


}
