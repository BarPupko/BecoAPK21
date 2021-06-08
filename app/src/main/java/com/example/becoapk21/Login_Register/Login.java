  package com.example.becoapk21.Login_Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.Parking.ParkTheBike;
import com.example.becoapk21.R;
import com.example.becoapk21.Admin.Help;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText regPhoneNumber, regPassword;
    ImageView parkingLocator;
    ImageView contact;
    String passfromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.beco));
//        getWindow().setNavigationBarColor(ContextCompat.getColor(Login.this, R.color.beco));
        //status bar color
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        regPhoneNumber = (EditText) findViewById(R.id.addPhone1);
        regPassword = (EditText) findViewById(R.id.AddPassword);
        contact=(ImageView)findViewById(R.id.contact2);
        parkingLocator=(ImageView)findViewById(R.id.parkingLocator1);



        //parking locator
       parkingLocator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(Intent.ACTION_VIEW,
                       Uri.parse("google.navigation:q=32.8219025,34.9900802&mode=b"));//mode=b (for bicycle )
               i.setPackage("com.google.android.apps.maps");
               startActivity(i);
           }
       });
        //register option
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewMember();
            }
        });
        //login option
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_phone = regPhoneNumber.getText().toString();
                String password = regPassword.getText().toString();

                if(TextUtils.isEmpty(user_phone)){
                    loginMember();
                    regPhoneNumber.setError("יש להזין מספר טלפון");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPassword.setError("יש להזין סיסמא");
                    return;
                }
                if(password.length() <6){
                    regPassword.setError("יש להזין סיסמא נכונה הכוללת לפחות 6 תווים");
                    return;
                }


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("user_phone").equalTo(user_phone);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            passfromDB = snapshot.child(user_phone).child("user_password").getValue(String.class).trim();
                            if(passfromDB.equals(password)){
                            String user_name_fromDB = snapshot.child(user_phone).child("user_name").getValue(String.class).trim();//get the user_name from the phone_number we get.
                            Intent intent = new Intent(getApplicationContext(), WelcomeSession.class);
                            intent.putExtra("user_phone",user_phone);
                            intent.putExtra("user_name",user_name_fromDB);
                            //if login successful go to welcome session.
                            startActivity(intent);

                            }
                            else{
                                Toast.makeText(Login.this, passfromDB, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(), Help.class);
                startActivity(i);
            }
        });


    }


    public void registerNewMember() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void loginMember() {
      //  Intent intent = new Intent(getApplicationContext(), welcomeSession.class);
      //  startActivity(intent);
    }



}