package com.example.becoapk21.Parking;

import java.sql.Time;
import java.util.List;

public class ParkingHelperClass {
    List<String> parkingSpots;
    List<Time> timeArr;

    public ParkingHelperClass(List<String> parkingSpots,List<Time>timeArr) {
        this.parkingSpots=parkingSpots;
        this.timeArr=timeArr;

    }

    public List<String> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<String> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public List<Time> getTimeArr() {
        return timeArr;
    }

    public void setTimeArr(List<Time> timeArr) {
        this.timeArr = timeArr;
    }


}
