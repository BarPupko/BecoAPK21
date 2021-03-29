package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class payment extends AppCompatActivity {

    ImageView bit;
    ImageView visa;
    ImageView paypal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(payment.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bit =(ImageView)findViewById(R.id.Bit);
        visa =(ImageView)findViewById(R.id.visa);
        paypal =(ImageView)findViewById(R.id.paypal);


        bit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(),register.class);
                startActivity(i);
            }
        });

        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(),register.class);
                startActivity(i);
            }
        });

       paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(),register.class);
                startActivity(i);
            }
        });

    }
}