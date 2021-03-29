package com.example.becoapk21;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.Random;

public class Parking extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userId;
    TextView fullName;
    ImageView parkBike;
    ImageView getTheBike;
    TextView parkTicket;
    ImageView chatSu;
    boolean isParked=false;

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
        parkBike=(ImageView) findViewById(R.id.parkBike);
        getTheBike=(ImageView) findViewById(R.id.getTheBike);
        fullName = (TextView)findViewById(R.id.fullName1);
        parkTicket = (TextView)findViewById(R.id.parkTicket);
        getSupportActionBar().hide();


         fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),help.class);
                startActivity(i);

            }
        });
        getTheBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isParked) {
                    isParked = true;
                    Random r = new Random();
                    char c = (char) (r.nextInt(6) + 'a');
                    int num = r.nextInt(20) + 1;
                    String str = " " + c + num;////

                    parkTicket.setText("your spot is: " + str.toUpperCase());
                }

            }
        });
        parkBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isParked){
                   parkTicket.setText("");

               }
               isParked=false;

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