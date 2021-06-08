package com.example.becoapk21.Navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.becoapk21.R;

public class NevePaz extends AppCompatActivity {



    TextView Purple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(NevePaz.this, R.color.beco));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neve_paz);
        getSupportActionBar().hide();
        Purple = (TextView) findViewById(R.id.goToPurpleRoad);
        String purpleMap = "https://www.google.com/maps/dir/32.8211245,34.9968553/Nesher/@32.8161517,34.9895719,14.5z/data=!4m9!4m8!1m0!1m5!1m1!1s0x151dba81e6aaab23:0xf5a48fe039683e6!2m2!1d35.0386443!2d32.7806134!3e1";


        Purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(purpleMap));
                startActivity(i);
            }
        });
    }

}