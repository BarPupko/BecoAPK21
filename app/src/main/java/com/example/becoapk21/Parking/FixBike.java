package com.example.becoapk21.Parking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.becoapk21.Admin.Help;
import com.example.becoapk21.Login_Register.Register;
import com.example.becoapk21.R;

public class FixBike extends AppCompatActivity {
        Button moveToFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_bike);

        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(FixBike.this, R.color.design_default_color_background));
        //
        moveToFix = (Button)findViewById(R.id.moveToFix);
        getSupportActionBar().hide();
      moveToFix.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent (getApplication(), Help.class);
              startActivity(i);
              finish();
          }
      }
      );


    }
}