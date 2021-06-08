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

public class ParkTheBike extends AppCompatActivity {
    String user_phone;
    String parked_user;
    int parkingDigit;
    int current_spot;
    char parkingSpotLetter;
    Date parkingTime;
    Button addBike;
    char[] parking_spot_letters= new char[100];
    int[] parking_spot_digits = new int[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color

        current_spot = 0;
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(ParkTheBike.this, R.color.beco));
        //

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_the_bike);
        addBike = (Button) findViewById(R.id.accept);
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        parkingTime = new Date();
        parkingSpotLetter = 'A';
        parked_user = "";
        //Generate a parking

 //generate parking spot in accordance to what is free
       FirebaseDatabase.getInstance().getReference().child("parked")
               .addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           ParkingHelperClass user = snapshot.getValue(ParkingHelperClass.class);
                            parking_spot_letters[current_spot] = (char)user.getParkingSpot();
                            parking_spot_digits[current_spot++] = user.getParkingDigit();
                            parked_user=intent.getStringExtra("user_name");
                            //!!!!!פונקציה קודמת למציאת והכנסת אופניים פנויים!!!!!

                          // parking_spots[current_spot] = user.getParkingSpot();

                           //  Toast.makeText(ParkTheBike.this,"your parking spot is: "+parking_spots[current_spot], Toast.LENGTH_SHORT).show();

                       }


                    /*   Arrays.sort(parking_spots, 0, current_spot);
                       int difference = 0;
                       if (parking_spots[0].equals("A1")) {
                           parkingSpot = "A1";
                       } else {
                           for (int i = 0; i < current_spot - 1; i++) {
                               if (parking_spots[i].length() == 2) {
                                   difference = Character.getNumericValue(parking_spots[i + 1].charAt(1)) - Character.getNumericValue(parking_spots[i].charAt(1));
                                   if (difference != 1) {
                                       parkingSpot = Character.toString(parking_spots[i].charAt(0)) + Character.toString((char) (parking_spots[i].charAt(1) + 1));
                                       break;
                                   }
                                   parkingSpot = Character.toString(parking_spots[current_spot - 1].charAt(0)) + Character.toString((char) (parking_spots[current_spot - 1].charAt(1) + 1));
                               }
                               else{

                                   difference = Character.getNumericValue(parking_spots[i + 1].charAt(2)) - Character.getNumericValue(parking_spots[i].charAt(2));
                                   if (difference != 1) {
                                       parkingSpot = Character.toString(parking_spots[i].charAt(0)) +Character.toString(parking_spots[i].charAt(1))+ Character.toString((char) (parking_spots[i].charAt(2) + 1));
                                       break;
                                   }
                                   parkingSpot = Character.toString(parking_spots[current_spot - 1].charAt(0)) + Character.toString(parking_spots[current_spot - 1].charAt(1))+Character.toString((char) (parking_spots[current_spot - 1].charAt(2) + 1));

                               }
                           }

                       }
                       */
                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });





        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current_spot<100) {
                boolean spot_exists = false;

                    outer:
                    for(int charC =65;charC<=70;charC++){
                        for(int Digit=1;Digit<20;Digit++){
                            spot_exists=false;
                           for(int i=0;i<current_spot;i++){
                               if(parking_spot_digits[i]==Digit && (parking_spot_letters[i] == charC)){
                                  spot_exists=true;
                               }
                           }
                            if(!spot_exists){
                               parkingSpotLetter=(char)charC;
                                parkingDigit = Digit;
                                break outer;
                            }

                        }

                    }


                    Toast.makeText(ParkTheBike.this, "your parking spot is: " + parkingSpotLetter + "" + parkingDigit, Toast.LENGTH_SHORT).show();
                    Toast.makeText(ParkTheBike.this, "you name is : " + parked_user, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
                    DatabaseReference parking_ref = users_instance.getReference("parked");
                    ParkingHelperClass helperClass = new ParkingHelperClass(parkingSpotLetter, parkingTime, parkingDigit,parked_user);
                    parking_ref.child(user_phone).setValue(helperClass);
                    Toast.makeText(ParkTheBike.this, "האופניים הופקדו בהצלחה!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ParkTheBike.this, "אין מקום פנוי", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(getApplication(), WelcomeSession.class);
                i.putExtra("user_phone",user_phone);
                startActivity(i);

            }
        });


    }



}
