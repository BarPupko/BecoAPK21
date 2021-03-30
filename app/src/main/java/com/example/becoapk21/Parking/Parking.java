package com.example.becoapk21.Parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.becoapk21.Admin.help;
import com.example.becoapk21.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Parking.this, R.color.design_default_color_background));
        //

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        chatSu=findViewById(R.id.chatSupport);
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        getTheBike =(ImageView) findViewById(R.id.parkBike);
        parkTheBike =(ImageView) findViewById(R.id.getTheBike);
        fullName = (TextView)findViewById(R.id.fullName1);
        parkTicket = (TextView)findViewById(R.id.parkTicket);
        //timer//

        getSupportActionBar().hide();


        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), help.class);
                startActivity(i);
            }
        });

        parkTheBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    parkTicket.setText("your spot is: " + " ");
                    //getPhoneNumber and time
            }
        });

        getTheBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    fullName.setText(documentSnapshot.getString("fName"));


                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }




        });



    }

}