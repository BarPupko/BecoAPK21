package com.example.becoapk21.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.becoapk21.Admin.managaerCONTROL;
import com.example.becoapk21.Navigation.RoadMap;
import com.example.becoapk21.Parking.Parking;
import com.example.becoapk21.R;
import com.example.becoapk21.Admin.help;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class welcomeSession<first_name> extends AppCompatActivity {

    private ImageView parking;//park Bicycle
    FirebaseAuth fAuth;
    String userId;
    TextView fullName;
    FirebaseFirestore fStore;
    ImageView map;
    ImageView fix;
    ImageView man;
    ImageView chatSu;
    String first_name;
    boolean isAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(welcomeSession.this, R.color.design_default_color_background));
        //ghf
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_session);
        getSupportActionBar().hide();

        chatSu=(ImageView)findViewById(R.id.chatSupport);
        fix=(ImageView)findViewById(R.id.fix1);
        map=(ImageView)findViewById(R.id.map);
        fullName = (TextView) findViewById(R.id.fullName2);
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        man = (ImageView)findViewById(R.id.man);

        fStore = FirebaseFirestore.getInstance();

        parking = (ImageView) findViewById(R.id.parking);
        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parking();
            }




        });

        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), help.class);
                startActivity(i);

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), RoadMap.class);
                startActivity(i);

            }
        });


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {

                    fullName.setText(documentSnapshot.getString("fName"));
                    if(documentSnapshot.getString("email").equals("barpupco@gmail.com") || documentSnapshot.getString("email").equals("carmit1995@walla.com")){
                        isAdmin=true;

                    }
                    else{
                        isAdmin=false;
                    }


                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });



        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdmin) {
                    Intent i = new Intent(getApplication(), managaerCONTROL.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(welcomeSession.this, "אתה לא מנהל", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void parking() {
        Intent intent = new Intent(this, Parking.class);
        startActivity(intent);
    }
}
