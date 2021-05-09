package com.example.becoapk21.Admin;

import android.os.Bundle;

import com.example.becoapk21.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class UsersMan extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(UsersMan.this, R.color.design_default_color_background));
        //
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_man);

    }
}