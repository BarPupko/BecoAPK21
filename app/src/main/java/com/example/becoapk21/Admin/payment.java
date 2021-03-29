package com.example.becoapk21.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.becoapk21.R;
import com.example.becoapk21.Login_Register.register;

public class payment extends AppCompatActivity {

    ImageView paypal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(payment.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paypal =(ImageView)findViewById(R.id.paypal);


       paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(),register.class);
                startActivity(i);
            }
        });

    }
}