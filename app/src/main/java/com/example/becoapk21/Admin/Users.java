 package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.Parking.Parking;
import com.example.becoapk21.Parking.ParkingHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

 public class Users extends AppCompatActivity {
     Date currentDate;
     Double amount_to_pay;
     Double timeParked;
     double parkingFee = 5;
     double conversion = 1000 * 60 * 60;
    private RelativeLayout mLayout;
    private Button delete;
    private EditText mEditText;
    int count=0; //סופר כמה משתמשים יש
    ParkingHelperClass [] user_array = new ParkingHelperClass[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        mEditText = (EditText) findViewById(R.id.editText);
        getWindow().setStatusBarColor(ContextCompat.getColor(Users.this, R.color.beco));
        //
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        FirebaseDatabase.getInstance().getReference().child("parked")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ParkingHelperClass user = snapshot.getValue(ParkingHelperClass.class);
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
    private TextView createNewTextView(String description,int id) {
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView name = new TextView(this);
        lparams.setMargins(150,(id+1)*100,0,0); //location of the text
        name.setId(id);
        name.setLayoutParams(lparams);
        name.setTextColor(Color.WHITE); // text color
//        name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);


        name.setText(description);
        return name;
    }
}