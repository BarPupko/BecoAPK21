package com.example.becoapk21.Parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.becoapk21.Activities.welcomeSession;
import com.example.becoapk21.Admin.help;
import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.Login_Register.Register;
import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ParkTheBike extends AppCompatActivity {
    String user_phone;

    String parkingSpot;
    Date parkingTime;
    Button addBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(ParkTheBike.this, R.color.design_default_color_background));
        //

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_the_bike);
        addBike = (Button) findViewById(R.id.accept);
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        parkingTime = new Date();
        parkingSpot = "A1";



        addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
                DatabaseReference parking_ref = users_instance.getReference("parked");
                ParkingHelperClass helperClass = new ParkingHelperClass(parkingSpot,parkingTime);
                parking_ref.child(user_phone).setValue(helperClass);
                Toast.makeText(ParkTheBike.this, "האופניים הופקדו בהצלחה!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplication(), Parking.class);
                startActivity(i);
            }
        });


    }

}
