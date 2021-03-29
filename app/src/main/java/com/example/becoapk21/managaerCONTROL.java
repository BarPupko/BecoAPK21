package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class managaerCONTROL extends AppCompatActivity {

    ImageView bell;//the image I want to call from xml
    ImageView tools;
    ImageView reports;
    ImageView userManagement;

    boolean seeMessage;//if its false we get the image with empty notifications.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(managaerCONTROL.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managaer_c_o_n_t_r_o_l);
        getSupportActionBar().hide();

        bell = (ImageView) findViewById(R.id.bell); // calling the image by ID
        tools = (ImageView) findViewById(R.id.allTools);
        userManagement = (ImageView) findViewById(R.id.userManagement);
        seeMessage = false;//set seeMessage to false (there is message inside)


        //when I click on the button "bell" , its open the dialog class I choose , and (messageDialog.class) I see the message.
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                seeMessage = true; //if it true , please send the notification picture if empty notifications
                if(seeMessage)
                bell.setImageResource(R.drawable.notificationzero);
            }
        });

        userManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),users.class);
                startActivity(i);
            }
        });


    }




    public void openDialog() {
        messageDialog messageDialog = new messageDialog();
        messageDialog.show(getSupportFragmentManager(), "example dialog");
    }

}