package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class whatsTheProblem extends AppCompatActivity {

    ImageView puncher;
    ImageView chatSu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        //test
        getWindow().setStatusBarColor(ContextCompat.getColor(whatsTheProblem.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_the_problem);
        getSupportActionBar().hide();

        puncher=(ImageView)findViewById(R.id.puncher);
        chatSu=(ImageView)findViewById(R.id.chat);

        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),help.class);
                startActivity(i);

            }
        });

        puncher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                puncherClass();

            }
        });
    }

    public void puncherClass() {
        Intent intent = new Intent(this, tools.class);
        startActivity(intent);
    }

}