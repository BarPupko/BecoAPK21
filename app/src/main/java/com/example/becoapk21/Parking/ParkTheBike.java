package com.example.becoapk21.Parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.becoapk21.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ParkTheBike extends AppCompatActivity {
    FirebaseAuth fAuth;
    String userId;
    TextView fullName;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_the_bike);









        //Onclick for button

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    fullName.setText(documentSnapshot.getString("fName"));
                    if(documentSnapshot.getString("email").equals("barpupco@gmail.com") || documentSnapshot.getString("email").equals("carmit1995@walla.com")){
                       // isAdmin=true;
                    }
                    else{
                  //      isAdmin=false;
                    }
                } else {
                    System.out.println("No document");
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }
}