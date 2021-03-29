package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class ManagerSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(ManagerSession.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_session);
        getSupportActionBar().hide();

    }
}