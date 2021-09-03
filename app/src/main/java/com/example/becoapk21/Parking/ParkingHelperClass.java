package com.example.becoapk21.Parking;


import java.util.Date;

/*
                       ParingHelperClass.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will show information regarding bike Track
            and if the user want it will redirect to the BatGalim Track
            -------------------------------------------------------------
 */
public class ParkingHelperClass {
    int parkingChar;
    int parkingDigit;
    Date parkingTime;
    String parked_name;
    Long time;
    Date currentDate;

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    double timeParked;
    double amount_to_pay;
    double parkingFee = 5;
    double conversion = 1000 * 60 * 60;//1000 stands for miliseconds * 60 (convert to second) *60 (convert to min)
    StringBuilder sb = new StringBuilder();


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

    public void setParked_name(String parked_name) {
        this.parked_name = parked_name;
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

   //Create method double calculateFee() returns amount user has to pay in according to the time parked
    public double calculateFee(){
        time=this.getParkingTime().getTime();
        currentDate = new Date();
        timeParked = (double) currentDate.getTime() - time;
        System.out.println(parkingFee);
        amount_to_pay = Math.round((timeParked / conversion) * parkingFee * 100) / 100.;//round to two numbers parkingFee * 100 / 100.
       return amount_to_pay;

    }

    //Create method String parkingSpot() returns parking spot of the user (connect parkingChar and parkingDigit)
    public String getFullParkingSpot() {
        String spotLetter = Character.toString((char)this.getParkingSpot());
        String spotNumber = Integer.toString(this.getParkingDigit());
        return spotLetter+spotNumber;
    }

    //Create method String toString() returns username +parkingSpot + parkingFee as string format without new lines
    public String toString(){
        return String.format("%-10s","Namef:"+ this.getParked_name())+String.format(" %-10s","parkingSpot:"+ this.getFullParkingSpot() )+String.format(" %-10s"," fee:"+ this.calculateFee());
    }



}
