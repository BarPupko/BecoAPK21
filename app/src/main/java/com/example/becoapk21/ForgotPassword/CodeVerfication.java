package com.example.becoapk21.ForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.Login_Register.Register;
import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CodeVerfication extends AppCompatActivity {
    EditText code;
    FirebaseDatabase rootNode;
    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verfication);

        code=(EditText)findViewById(R.id.codeFromUser);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("user_phone");
        rootNode = FirebaseDatabase.getInstance();

        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
        //                reference = rootNode.getReference("users");

        //*****************************************************.
        //*****************---realTimeDataBase---**************.
        //*****************************************************.
        DatabaseReference users_ref = users_instance.getReference("users");
        Query checkUserPhone = users_ref.orderByChild("user_phone").equalTo(phoneNum);
        checkUserPhone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //OTP
                    Toast.makeText(CodeVerfication.this, phoneNum, Toast.LENGTH_SHORT).show();

                } else {
                    finish();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}