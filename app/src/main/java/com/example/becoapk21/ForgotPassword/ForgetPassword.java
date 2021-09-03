package com.example.becoapk21.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.becoapk21.R;

public class ForgetPassword extends AppCompatActivity {

    Button nextBtn;
    EditText getEmail;
    String user_email;
    EditText getPhone;
    String user_phone;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().hide();//hide intent title
        getWindow().setStatusBarColor(ContextCompat.getColor(ForgetPassword.this, R.color.beco));

        //attributes
        type = "reset_password"; //variable regarding the reset_password
        nextBtn = (Button) findViewById(R.id.nextButton);
        getEmail = (EditText) findViewById(R.id.getEmail);
        getPhone = (EditText) findViewById(R.id.getPhoneNumber);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            //Auth
            @Override
            public void onClick(View v) {
                //user enter email and phone number to verify.
                user_email = getEmail.getText().toString();
                user_phone = getPhone.getText().toString();
                //check if email regex are correct.
                if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    getEmail.setError("הכנס אימייל תקין");
                    return;
                }
                //send variables to next intent.
                Intent Intent = new Intent(getApplicationContext() , CodeVerfication.class);
                Intent.putExtra("user_email", user_email);
                Intent.putExtra("user_phone", user_phone);
                Intent.putExtra("verifyType", type);
                startActivity(Intent);
            }

        });

    }
}