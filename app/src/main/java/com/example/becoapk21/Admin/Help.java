package com.example.becoapk21.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.R;

public class Help extends AppCompatActivity {

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Help.this, R.color.design_default_color_background));


        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Login.class);
                startActivity(i);
                Toast.makeText(Help.this, "ההודעה נשלחה", Toast.LENGTH_SHORT).show();

            }
        });

    }

    ;

}