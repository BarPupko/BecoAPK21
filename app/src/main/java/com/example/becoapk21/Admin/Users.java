 package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.Parking.Parking;
import com.example.becoapk21.Parking.ParkingHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.Date;

/*
                        Users.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will present all the users that already parked
            their bicycle , the amount they need to pay and the location
            of the bike inside the parking.
            we can unbound the bicycle from the parking by clicking on X.
            -------------------------------------------------------------
 */

 public class Users extends AppCompatActivity {
     Date currentDate;
     Double amount_to_pay;
     Double timeParked;
     double parkingFee = 5;
     double conversion = 1000 * 60 * 60;
    private RelativeLayout mLayout;
    private Button delete;
    private TextView mEditText;
    int count=0; //סופר כמה משתמשים יש
    ParkingHelperClass [] user_array = new ParkingHelperClass[100];
    String[] user_phones = new String[100];// שומרים את המספרי טלפונים במערך בישביל לא ליצור חזרה על משתנים.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        mEditText = (TextView) findViewById(R.id.editText);
        getWindow().setStatusBarColor(ContextCompat.getColor(Users.this, R.color.beco));
        //hide name of intent
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        FirebaseDatabase.getInstance().getReference().child("parked")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ParkingHelperClass user = snapshot.getValue(ParkingHelperClass.class);
                            user_phones[count]=snapshot.getKey();
                            user_array[count++] = user;
                        }

                        mLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

                        for(int i=0;i<count;i++){
                            mLayout.addView(createNewTextView(user_array[i].toString(),i));
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    ////////////////////*****PARKING AMOUNT TO PAY********/////////////////


    ////////////////////*****PARKING AMOUNT TO PAY********/////////////////
    @SuppressLint("SetTextI18n")//importing this function to use any text we like ,inside "setText("text")"
    private TextView createNewTextView(String description, int id) {
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView name = new TextView(this);
        lparams.setMargins(150, (id + 1) * 110, 0, 0); //location of the text
        name.setId(id);
        name.setLayoutParams(lparams);
        name.setTextColor(Color.WHITE); // text color
        name.setText(description);
        Button delBtn = new Button(this);
        final RelativeLayout.LayoutParams buttonLocation = new RelativeLayout.LayoutParams(150, 100);

        buttonLocation.setMargins(950, (id + 1) * 100, 0, 0);
        delBtn.setLayoutParams(buttonLocation);
        delBtn.setText("X");
        String id1 = Integer.toString(id);
        delBtn.setId(id+100);//100 is offset for the deleteButton.
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOldTextView(id,user_phones[id]);
            }
        });
        mLayout.addView(delBtn);


        return name;
    }

     private void deleteOldTextView(int id,String user_phone)
     {
         //TextView userDescription= (TextView)findViewById(getResources().getIdentifier(Integer.toString(id),"id",getPackageName()));
         TextView userDescription= (TextView)findViewById(id);
         TextView deleteButton= (TextView)findViewById(id+100);
         userDescription.setVisibility(View.GONE);
         deleteButton.setVisibility(View.GONE);
         DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child("parked").child(user_phone);
         dbNode.setValue(null);


     }
}