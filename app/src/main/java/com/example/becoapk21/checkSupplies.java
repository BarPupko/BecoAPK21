package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class checkSupplies extends AppCompatActivity {

    ImageView electric;
    ImageView toolbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(checkSupplies.this, R.color.design_default_color_background));
        //
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_supplies);

    electric=(ImageView)findViewById(R.id.elecrictool);
    toolbox=(ImageView)findViewById(R.id.toolbox);

    electric.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplication(),checkElectric.class);
            startActivity(i);
        }
    });

    toolbox.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplication(),staticEquipment.class);
            startActivity(i);
        }
    });

//    toolbox.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent i = new Intent(getApplication(),toolboxActivity.class);
//            startActivity(i);
//        }
//    });

    }
}