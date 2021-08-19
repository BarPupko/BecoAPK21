package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateFee extends AppCompatActivity {
    Double parkingFee;
    Button updateFeeButton;
    EditText editFee;
    TextView currentFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fee);
        updateFeeButton = (Button) findViewById(R.id.updateFeeButton1);
        editFee = (EditText) findViewById(R.id.editFee);
        currentFee = (TextView) findViewById(R.id.currentFee);
        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
        DatabaseReference admin_ref = users_instance.getReference("admin");
        admin_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //משיכת של נתון כמה עולה לדקה לשמור את האופניים
                parkingFee = Double.parseDouble(snapshot.child("parkingFeePerMinute").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        updateFeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(updateFee.this, "המחיר עודכן ", Toast.LENGTH_SHORT).show();
                DatabaseReference users1 = FirebaseDatabase.getInstance().getReference();
                DatabaseReference admin = users1.child("admin");
                admin.child("parkingFeePerMinute").setValue(Double.parseDouble(String.valueOf(editFee.getText())));
                finish();//מחזיר לintent קודם
            }
        });
        //after 30 second this activity will start , and return us to the previous Intent.
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        setParkingFee();
                    }
                },
                500);




//        updateFeeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(updateFee.this, "יש להחנות את האופניים לפני בקשasdasdון", Toast.LENGTH_SHORT).show();
//
////
//            }
//        });

//        updateFeeButton.setOnClickListener(new View.OnClickListener() {
//
//            /*
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
//                DatabaseReference admin_ref = users_instance.getReference("admin");
//                admin_ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        //משיכת של נתון כמה עולה לדקה לשמור את האופניים
//                        snapshot.child("parkingFeePerMinute").setValue(Double.parseDouble(String.valueOf(editFee.getText())));
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }*/
//        });
//    }
    }
    public void setParkingFee(){
        currentFee.setText(Double.toString(parkingFee));
    }
}