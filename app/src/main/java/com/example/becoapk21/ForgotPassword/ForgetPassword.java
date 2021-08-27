package com.example.becoapk21.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

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
        type = "reset_password";
        nextBtn = (Button) findViewById(R.id.nextButton);
        getEmail = (EditText) findViewById(R.id.getEmail);
        getPhone = (EditText) findViewById(R.id.getPhoneNumber);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            //Auth
            @Override
            public void onClick(View v) {
                //
                user_email = getEmail.getText().toString();
                user_phone = getPhone.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    getEmail.setError("הכנס אימייל תקין");
                    return;
                }
                Intent Intent = new Intent(getApplicationContext() , CodeVerfication.class);
                Intent.putExtra("user_email", user_email);
                Intent.putExtra("user_phone", user_phone);
                Intent.putExtra("verifyType", type);
                startActivity(Intent);
            }

        });

    }
}