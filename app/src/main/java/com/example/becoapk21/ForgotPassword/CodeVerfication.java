package com.example.becoapk21.ForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.becoapk21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CodeVerfication extends AppCompatActivity {
    //attributes
    String phoneNum;
    String user_email;
    String type = "";   //who's calling the code verification intent.
    Button sendEmailButton;
    FirebaseAuth mAuth; //using user authentication to verify / change password
    String emailfromDB; //retrieving email from dataBase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verfication);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();//connecting to the auth database
        sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
        phoneNum = intent.getStringExtra("user_phone");//used to check correct email and phone
        user_email = intent.getStringExtra("user_email");//email used to send the reset login
        type = intent.getStringExtra("verifyType");//figuring what need to be done , if equal to 'reset password' then
        getSupportActionBar().hide();//hide the intent title.
        getWindow().setStatusBarColor(ContextCompat.getColor(CodeVerfication.this, R.color.beco));


        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();

        //*****************************************************.
        //*****************---realTimeDataBase---**************.
        //*****************************************************.

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference users_ref = users_instance.getReference("users");//get to 'users' table
                Query checkUserPhone = users_ref.orderByChild("user_phone").equalTo(phoneNum);//check if the phone number exists in the DB
                checkUserPhone.addListenerForSingleValueEvent(new ValueEventListener() {//calling this function , to change or retrieve information.
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {//
                        //check if phone number in users database
                        if (snapshot.exists()) {
                            //OTP
                            if (type.equals("reset_password")) {//check if user trying to reset password
                                emailfromDB = snapshot.child(phoneNum).child("user_email").getValue(String.class).trim();//get email related to phonenum user entered
                                if (emailfromDB.equals(user_email)) {//if email from DB related to phonenum equal to email user entered send reset email.
                                    mAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CodeVerfication.this, "נשלח אימייל לאיפוס סיסמא", Toast.LENGTH_SHORT).show();
                                            } else { // send password reset email was not successful
                                                Toast.makeText(CodeVerfication.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                                            }
                                            sendEmailButton.setEnabled(false);
                                        }
                                    });
                                } else {//if email user entered is different from the one in DB print error
                                    Toast.makeText(CodeVerfication.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                                }

                                /*******************************************************/
                                /*         check if user trying verify email.          */
                                /*******************************************************/

                            } else if (type.equals("email_verify")) {
                                FirebaseUser user = mAuth.getCurrentUser();//get current user
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {//if the info match's
                                        Toast.makeText(CodeVerfication.this, "נשלח אימייל לאימות המשתמש", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CodeVerfication.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            sendEmailButton.setEnabled(false);

                        } else { // Phone number entered does no exist in DB
                            Toast.makeText(CodeVerfication.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }
                    //if user return back and don't continue the process
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });


    }


}