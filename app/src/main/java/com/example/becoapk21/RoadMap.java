package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RoadMap extends AppCompatActivity {
    TextView BlueRoad;
    TextView GreenRoad;
    TextView YellowRoad;
    TextView PurpleRoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(RoadMap.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_map);
        getSupportActionBar().hide();

        BlueRoad=(TextView)findViewById(R.id.batGalim);
        GreenRoad=(TextView)findViewById(R.id.carmel);
        YellowRoad=(TextView)findViewById(R.id.wadinisnas);
        PurpleRoad=(TextView)findViewById(R.id.navepaz);

        BlueRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , batGalim.class);
                startActivity(Intent);
            }
        });

        GreenRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , carmelRoad.class);
                startActivity(Intent);
            }
        });

        YellowRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , wadiNisnas.class);
                startActivity(Intent);
            }
        });

        PurpleRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , nevePaz.class);
                startActivity(Intent);
            }
        });



    }
}