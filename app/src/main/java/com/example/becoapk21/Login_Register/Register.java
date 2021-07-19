package com.example.becoapk21.Login_Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.Admin.Help;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText regEmail, regPassword,fname, phoneNumber;
    Button registerEND;
    String userID;
    FirebaseDatabase rootNode;
    boolean isPhoneExists;
    boolean phoneExists = false;
    boolean email_exists;
    boolean phone_exists;
    Button payment;
    ImageView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Register.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        isPhoneExists = true;

        regEmail = (EditText) findViewById(R.id.EmailAddress);
        fname = (EditText)findViewById(R.id.userNameInput);
        regPassword = (EditText) findViewById(R.id.addPassword2);
        phoneNumber = (EditText)findViewById(R.id.addPhone);
        registerEND = (Button) findViewById(R.id.registerEND);
        contact=(ImageView)findViewById(R.id.contact1);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(), Help.class);
                startActivity(i);
            }
        });


        //save data in firebase on button click
        registerEND.setOnClickListener(new View.OnClickListener() {
            //Auth
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("users");
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                String fname1 = fname.getText().toString();
                String phoneNum = phoneNumber.getText().toString();
                if(TextUtils.isEmpty(email)){
                    regEmail.setError("יש לציין אימייל");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPassword.setError("יש למלא סיסמא");
                    return;
                }
                if(password.length() < 6){
                    regPassword.setError("הסיסמא חייבת להכיל לפחות 6 תווים");
                    return;
                }
                //*****************************************************.
                //*****************---realTimeDataBase---**************.
                //*****************************************************.
                DatabaseReference users_ref = users_instance.getReference("users");
                Query checkUserEmail = users_ref.orderByChild("user_email").equalTo(email);
                Query checkUserPhone= users_ref.orderByChild("user_phone").equalTo(phoneNum);

                checkUserEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            checkUserPhone.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.exists()){
                                        UserHelperClass helperClass = new UserHelperClass(fname1, password, email, phoneNum, "A1","",0);
                                        users_ref.child(phoneNum).setValue(helperClass);
                                        Toast.makeText(Register.this, "המשתמש נוצר", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Register.this, "הפלאפון כבר רשום במערכת", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(Register.this, "האימייל כבר רשום במערכת", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });











                //לעשות בדיקת קלט למספר טלפון
                /*
                if(phoneNum.length() == 10){
                    regPassword.setError("הסיסמא חייבת להכיל לפחות 6 תווים");
                    return;
                }
                */

            //    Member member1 = new Member(email, password,fname1);
//               reference.setValue(member1);



            }
        });

    }
}