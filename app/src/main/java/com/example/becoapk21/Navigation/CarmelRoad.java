package com.example.becoapk21.Navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.becoapk21.R;
/*
                       CarmelRoad.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will show information regarding bike Track
            and if the user want it will redirect to the Carmel Track
            -------------------------------------------------------------
 */
public class CarmelRoad extends AppCompatActivity {
    TextView green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(CarmelRoad.this, R.color.beco));
        //


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmel_road);
        getSupportActionBar().hide();
        green = (TextView) findViewById(R.id.goToGreenRoad);
        String greenMap = "https://www.google.com/maps/dir/32.8277933,34.9694549/HaGanim+St+48,+Haifa/32.8061605,34.9872437/LAB10+%D7%9E%D7%A2%D7%91%D7%93%D7%AA+%D7%9E%D7%97%D7%A9%D7%91%D7%99%D7%9D+%D7%95%D7%90%D7%9C%D7%A7%D7%98%D7%A8%D7%95%D7%A0%D7%99%D7%A7%D7%94+%D7%91%D7%97%D7%99%D7%A4%D7%94%E2%80%AD/@32.8087937,34.9665136,13.92z/data=!4m26!4m25!1m0!1m15!1m1!1s0x151dbbb66d2fbab7:0xfe603691bc10c79e!2m2!1d34.9884153!2d32.8190177!3m4!1m2!1d34.9799966!2d32.8206112!3s0x151dbbb8e265f871:0xd3f1f6cbb63a7628!3m4!1m2!1d34.9732468!2d32.823262!3s0x151dbbbfd09efdfb:0xecf573959c2901c7!1m0!1m5!1m1!1s0x0:0xc98c7cb4c78e88fa!2m2!1d34.9838782!2d32.7963031!3e1";



        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(greenMap));
                startActivity(i);
            }
        });
    }
}