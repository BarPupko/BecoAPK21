package com.example.becoapk21.Parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*
                       ParkTheBike.java ---> INFORMATION
            ------------------------------------------------------------
            This class manages data from all the intents , regarding the parking.

            -------------------------------------------------------------
 */
public class ParkTheBike extends AppCompatActivity {
    String user_phone;
    String parked_user;
    int parkingDigit;
    int current_spot;
    char parkingSpotLetter;
    Date parkingTime;
    Button addBike;
    char[] parking_spot_letters= new char[100];//array contains letters
    int[] parking_spot_digits = new int[100];//array contains digits
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color

        current_spot = 0;//current spot starting from 0
        getSupportActionBar().hide();//hide intent title.
        getWindow().setStatusBarColor(ContextCompat.getColor(ParkTheBike.this, R.color.beco));//status color

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_the_bike);
        addBike = (Button) findViewById(R.id.accept);//btn to add new bike
        Intent intent = getIntent();//return the intent that start the activity
        user_phone = intent.getStringExtra("user_phone");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//date formate
        parkingTime = new Date();//new object of Date
        parkingSpotLetter = 'A'; //default value for letter
        parked_user = ""; //default value for user name
        //Generate a parking


        //generate parking spot in accordance to what is free
       FirebaseDatabase.getInstance().getReference().child("parked")
               .addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           ParkingHelperClass user = snapshot.getValue(ParkingHelperClass.class);//get arguments from ParkingHelperClass
                            parking_spot_letters[current_spot] = (char)user.getParkingSpot();//parking_spot_letters array receive new arguments.
                            parking_spot_digits[current_spot++] = user.getParkingDigit();//parking_spot_digits array receive new arguments.
                       }

                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
        //get user name
        parked_user=intent.getStringExtra("user_name");


        //trying add bike to the database
        addBike.setOnClickListener(new View.OnClickListener() {//complexity O(n^2)
            @Override
            public void onClick(View v) {
                //check if there are available spots
                if(current_spot<100) {
                boolean spot_exists = false; // check if spot already exits , if not i will give user this spot.
                //creating a place for a new bike
                    outer://getting out from the loop if we find a spot that does not exist.
                    for(int charC =65;charC<=70;charC++){//first end the first letter//A-B-C-D-E
                        for(int Digit=1;Digit<=20;Digit++){//find new available digit in this letter
                            spot_exists=false;//boolean variable check if the place is exist or not until we Prove otherWise.
                            //loop that checks if there empty space , if it does find empty space , spot_exists get true.
                           for(int i=0;i<current_spot;i++){
                               if(parking_spot_digits[i]==Digit && (parking_spot_letters[i] == charC)){//arrays contains existing values.
                                  spot_exists=true;
                               }
                           }
                            if(!spot_exists){//if spot not exist it will give this user new spot using letter and digits.
                               parkingSpotLetter=(char)charC;
                                parkingDigit = Digit;
                                break outer; // get back to the outer loop.
                            }
                        }

                    }

                    //information for user , if it find's him a spot in parking.
                    Toast.makeText(ParkTheBike.this, "your parking spot is: " + parkingSpotLetter + "" + parkingDigit, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase users_instance = FirebaseDatabase.getInstance();//get current user
                    DatabaseReference parking_ref = users_instance.getReference("parked");//enter current user to parked
                    ParkingHelperClass helperClass = new ParkingHelperClass(parkingSpotLetter, parkingTime, parkingDigit,parked_user);
                    parking_ref.child(user_phone).setValue(helperClass);//enter inside 'parked' table the values of helperClass variable
                    Toast.makeText(ParkTheBike.this, "האופניים הופקדו בהצלחה!", Toast.LENGTH_SHORT).show();
                }
                else{//error for user , if there are not empty space for user.
                    Toast.makeText(ParkTheBike.this, "אין מקום פנוי", Toast.LENGTH_SHORT).show();
                }
                //move those variable to welcome session
                Intent i = new Intent(getApplication(), WelcomeSession.class);
                i.putExtra("user_phone",user_phone);
                startActivity(i);

            }
        });


    }



}
