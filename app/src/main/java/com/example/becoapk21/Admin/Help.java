package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.Parking.ParkingHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Help extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button send;
    EditText userComplaint;
    EditText editTextUserPhoneNumber1;
    //variables for userHelperClass
    String user_phone;
    String user_name;
    String parkingSpot;
    String message;
    String messageFromDB;
    Spinner spinner;
    int messageType;
    boolean parked;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Help.this, R.color.beco));
        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();

        //variables EditText to receive the content
        userComplaint = (EditText) findViewById(R.id.userComplaint);
        spinner = findViewById(R.id.spinner1);
        editTextUserPhoneNumber1 = (EditText) findViewById(R.id.editTextPhoneNumber);
        //get the user_phone from Database.
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");
        messageType=-1;


        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.use_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //send message
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_phone = editTextUserPhoneNumber1.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("user_phone").equalTo(user_phone);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            messageFromDB = snapshot.child(user_phone).child("message").getValue(String.class).trim();
                            if (messageFromDB.equals("")) {
                                reference.child(user_phone).child("message").setValue(userComplaint.getText().toString());//כתיבה ל-DB את תוכן ההודעה
                                reference.child(user_phone).child("messageType").setValue(messageType);//כתיבה ל-DB את סוג ההודעה
                            }
                        }
                        Toast.makeText(Help.this, "במידה והמשתמש קיים , ההודעה נשלחה.", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });





               /* DatabaseReference users_ref = users_instance.getReference("users");
                //generate parking spot in accordance to what is free
                FirebaseDatabase.getInstance().getReference().child("notes")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    ParkingHelperClass user = snapshot.getValue(ParkingHelperClass.class);
                                    UserHelperClass helperClass = new UserHelperClass(user_name,user_phone, parkingSpot, message);
                                    users_ref.child(user_phone).setValue(helperClass);
                                    String name = editTextTextPersonName.getText().toString();
                                    String info = userComplaint.getText().toString();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });*/

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        messageType = position;
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}