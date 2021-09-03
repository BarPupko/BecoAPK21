package com.example.becoapk21.Navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.becoapk21.R;

/*
                      RoadMap.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will show a selection of tracks across haifa
            the user will pick one of the tracks and will be redirect
            to the intent.
            -------------------------------------------------------------
 */
public class RoadMap extends AppCompatActivity {
    TextView blueRoad;
    TextView greenRoad;
    TextView yellowRoad;
    TextView purpleRoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(RoadMap.this, R.color.beco));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_map);
        getSupportActionBar().hide();

        blueRoad =(TextView)findViewById(R.id.batGalim);
        greenRoad =(TextView)findViewById(R.id.carmel);
        yellowRoad =(TextView)findViewById(R.id.wadinisnas);
        purpleRoad =(TextView)findViewById(R.id.navepaz);

        blueRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , BatGalim.class);
                startActivity(Intent);
            }
        });

        greenRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , CarmelRoad.class);
                startActivity(Intent);
            }
        });

        yellowRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , WadiNisnas.class);
                startActivity(Intent);
            }
        });

        purpleRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext() , NevePaz.class);
                startActivity(Intent);
            }
        });



    }
}