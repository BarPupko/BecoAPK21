package com.example.becoapk21.Parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.Admin.Help;
import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class Parking extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userId;
    TextView fullName;
    ImageView getTheBike;
    ImageView parkTheBike;
    TextView parkTicket;
    ImageView chatSu;
    String user_phone;
    String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(Parking.this, R.color.beco));
        //


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        chatSu=findViewById(R.id.chatSupport);
        getTheBike =(ImageView) findViewById(R.id.parkBike);
        parkTheBike =(ImageView) findViewById(R.id.getTheBike);
        fullName = (TextView)findViewById(R.id.fullName1);
        parkTicket = (TextView)findViewById(R.id.parkTicket);
        //timer//

        //Get data from calling intent
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");
        user_name = intent.getStringExtra("user_name");

        fullName.setText(user_name);
        Toast.makeText(Parking.this, "you name is : " + user_name, Toast.LENGTH_SHORT).show();




        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Help.class);
                startActivity(i);
            }
        });

        parkTheBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
                DatabaseReference parking_ref = users_instance.getReference("parked");

                Query checkParked = parking_ref.orderByKey().equalTo(user_phone);
                checkParked.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(Parking.this, "האופניים כבר בחנייה", Toast.LENGTH_SHORT).show();


                        }
                        else{
                            Intent i = new Intent(getApplication(),ParkTheBike.class);
                            i.putExtra("user_phone",user_phone);
                            i.putExtra("user_name",user_name);
                            startActivity(i);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        getTheBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
                DatabaseReference parking_ref = users_instance.getReference("parked");

                Query checkParked = parking_ref.orderByKey().equalTo(user_phone);
                checkParked.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Intent i = new Intent(getApplication(),GetTheBike.class);
                            i.putExtra("user_phone",user_phone);
                            i.putExtra("user_name",user_name);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(Parking.this, "אין אופניים בחנייה", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }

}