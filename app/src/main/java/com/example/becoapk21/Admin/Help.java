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
import com.example.becoapk21.Parking.ParkTheBike;
import com.example.becoapk21.Parking.Parking;
import com.example.becoapk21.Parking.ParkingHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalService;
/*
                        Help.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will is used to communicate between the user and
            the manager , to solve problem such as payment,fixing bicycle
            and any other problem that might be.
            -------------------------------------------------------------
 */
public class Help extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button send; //send button
    EditText userComplaint; //user write box to complaint.
    EditText editTextUserPhoneNumber1;
    //variables for userHelperClass
    String user_phone; //user phone that is gets from DataBase
    String user_name;  //user name that its gets from DataBase
    String messageFromDB;
    Spinner spinner; // spinner , or combo box , this variables
    int messageType; //messageType the user.
    int currentAdapterPosition; //user checks its messageType , and the variables show the position in spinner.
    boolean parked; //telling us if the user already parked is bike or not, //!either way it will look like the messages benn sent already.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Help.this, R.color.beco));

        //variables EditText to receive the content
        userComplaint = (EditText) findViewById(R.id.userComplaint);
        spinner = findViewById(R.id.spinner1);
        editTextUserPhoneNumber1 = (EditText) findViewById(R.id.editTextPhoneNumber);
        //get the user_phone from Database.
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");  //getting the variables from the previous intent
        user_name = intent.getStringExtra("user_name");    //getting the variables from the previous intent
        messageType = -1; //default message Type is -1.
        parked = false;   //boolean variables marks that the bicycle not parked.

        //Spinner(ComboBox)
        //R.array.use_category --> using category that has spinner value in 'strings.xml'
        //simple_spinner_item showing the spinner without arguments.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.use_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//showing all the arguments after opening spinner.
        spinner.setAdapter(adapter); //spinner variables get the adapter (the spinner values)
        spinner.setOnItemSelectedListener(this); // get the selected item from the 'use_category' and then show it on the screen.

        //send message
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user already parked the bike.
                user_phone = editTextUserPhoneNumber1.getText().toString();
                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
                DatabaseReference parking_ref = users_instance.getReference("parked");
                Query checkParked = parking_ref.orderByKey().equalTo(user_phone);
                checkParked.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            parked = true;
                        } else {
                            parked = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
                //Query checkParked
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("user_phone").equalTo(user_phone);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            messageFromDB = snapshot.child(user_phone).child("message").getValue(String.class).trim();
                            if (!parked && currentAdapterPosition == 2) {//if user not parked and the he is trying to send message.
                                Toast.makeText(Help.this, "יש להחנות את האופניים לפני בקשה לתיקון", Toast.LENGTH_SHORT).show();
                            } else if (messageFromDB.equals("")) {
                                reference.child(user_phone).child("message").setValue(userComplaint.getText().toString());//writing into the DB the value of message
                                reference.child(user_phone).child("messageType").setValue(messageType);//writing into the DB what the messageType
                            }
                        }

                        Toast.makeText(Help.this, "במידה והמשתמש קיים , ההודעה נשלחה.", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                finish();//get back to the previous intent after sending the message
            }
        });
    }


    //Spinner Section , getting the positing from the menu

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        messageType = position;//getting the position the user selected.
        currentAdapterPosition = position; //another variable to store the currentPosition from the user.
    }

    //if nothing from the spinner is selected , it will show the default option --> 'כללי'
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}