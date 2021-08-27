package com.example.becoapk21.ForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.Login_Register.Register;
import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class CodeVerfication extends AppCompatActivity {
    EditText code;
    FirebaseDatabase rootNode;
    String phoneNum;
    String user_email;
    String type = "";
    Button sendEmailButton;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verfication);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
        phoneNum = intent.getStringExtra("user_phone");
        user_email = intent.getStringExtra("user_email");
        type = intent.getStringExtra("verifyType");
        if(type!=null){
            Toast.makeText(CodeVerfication.this, type, Toast.LENGTH_SHORT).show();
        }
        rootNode = FirebaseDatabase.getInstance();

        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
        //                reference = rootNode.getReference("users");

        //*****************************************************.
        //*****************---realTimeDataBase---**************.
        //*****************************************************.
        sendEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseReference users_ref = users_instance.getReference("users");
                Query checkUserPhone = users_ref.orderByChild("user_phone").equalTo(phoneNum);
                checkUserPhone.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //OTP
                            if(type.equals("reset_password")) {
                                mAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CodeVerfication.this, "נשלח אימייל לאיפוס סיסמא", Toast.LENGTH_SHORT).show();
                                        } else { // send password reset email was not succesful
                                            Toast.makeText(CodeVerfication.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                                        }
                                        sendEmailButton.setEnabled(false);
                                        Timer sendEmailDelay = new Timer();
                                        sendEmailDelay.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                sendEmailButton.setEnabled(true);
                                            }
                                        }, 60000);
                                    }
                                });
                            }
                            else if(type.equals("email_verify")) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
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
                            Timer sendEmailDelay = new Timer();
                            sendEmailDelay.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    sendEmailButton.setEnabled(true);
                                }
                            }, 60000);
                        } else { // Phone number entered does no exist in DB
                            Toast.makeText(CodeVerfication.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });


    }




}