package com.example.becoapk21.Parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.Activities.welcomeSession;
import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTheBike extends AppCompatActivity {


    String user_phone;
    TextView parkingSpot;
    TextView amountToPay;
    Date dateFromDB;
    Date currentDate;
    Long timeParked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(GetTheBike.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_the_bike);

        parkingSpot=(TextView)findViewById(R.id.parkingLocation);
        amountToPay=(TextView)findViewById(R.id.amountLeft);

        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");

        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
        DatabaseReference parking_ref = users_instance.getReference("parked");
        Query checkUser = parking_ref.orderByKey().equalTo(user_phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    dateFromDB =(Date) snapshot.child(user_phone).child("parkingTime").getValue();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    currentDate = new Date();
                    timeParked = dateFromDB.getTime()-currentDate.getTime();
                    amountToPay.setText(timeParked.toString());
                }else{
                    Toast.makeText(GetTheBike.this, "ארור = error", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

