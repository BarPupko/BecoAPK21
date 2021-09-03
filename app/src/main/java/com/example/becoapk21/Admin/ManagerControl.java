package com.example.becoapk21.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.becoapk21.R;
/*
                        ManagerControl.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will present all function the manager can do
            such as:
            1.bell ring will show general instruction for the manager.
            2.intent to manage any user that already parked their bike.
            3.intent to manage any new message regarding any issue.
            -------------------------------------------------------------
 */
public class ManagerControl extends AppCompatActivity {

    ImageView bell;//the image I want to call from xml
    ImageView userManagement;  //picture to intent that's showing us all users including the parking location and amount of money to pay.
    ImageView messagesIntent;  //picture to intent that's showing us all messages from users who sent message
    ImageView updateFee;       //
    ImageView updateManagement;//
    boolean seeMessage;//if its false we get the image with empty notifications.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(ManagerControl.this, R.color.beco));
        getSupportActionBar().hide(); //hide status bar title

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managaer_c_o_n_t_r_o_l);


        bell = (ImageView) findViewById(R.id.bell); // calling the image by ID
        userManagement = (ImageView) findViewById(R.id.userManagement);
        messagesIntent = (ImageView) findViewById(R.id.messagesIntent);
        updateFee = (ImageView) findViewById(R.id.updateFee);
        updateManagement =(ImageView) findViewById(R.id.updateManagement);
        seeMessage = false;//set seeMessage to false (there is message inside)


        //when I click on the button "bell" , its open the dialog class I choose , and (messageDialog.class) I see the message.
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                seeMessage = true; //if it true , please send the notification picture if empty notifications
                if(seeMessage)
                bell.setImageResource(R.drawable.notificationzero);//bell will display zero notification
            }
        });

        // by clicking on messageIntent we will move into next Intent (userManagement) class)
        userManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Users.class);
                startActivity(i);
            }
        });

        // by clicking on messageIntent we will move into next Intent (messageReceive class)
        messagesIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), MessageReceive.class);
                startActivity(i);
            }
        });

        // by clicking on messageIntent we will move into next Intent (updateFee class)
        updateFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplication(),updateFee.class);
                startActivity(i);
            }
        });

        // by clicking on messageIntent we will move into next Intent (updateFee class)
        updateManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),updateManagement.class);
                startActivity(i);
            }
        });


    }



    //open dialog message if user clicks the bell.
    public void openDialog() {
        MessageDialog messageDialog = new MessageDialog();//moving into another intent called MessageDialog and present the content of the dialog.
        messageDialog.show(getSupportFragmentManager(), "example dialog");
    }

}