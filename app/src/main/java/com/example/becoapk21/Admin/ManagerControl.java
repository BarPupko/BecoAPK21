package com.example.becoapk21.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.Login_Register.Login;
import com.example.becoapk21.R;

public class ManagerControl extends AppCompatActivity {

    ImageView bell;//the image I want to call from xml
    ImageView reports;
    ImageView userManagement;
    ImageView messagesIntent;

    boolean seeMessage;//if its false we get the image with empty notifications.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(ManagerControl.this, R.color.beco));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managaer_c_o_n_t_r_o_l);
        getSupportActionBar().hide();

        bell = (ImageView) findViewById(R.id.bell); // calling the image by ID
        userManagement = (ImageView) findViewById(R.id.userManagement);
        messagesIntent = (ImageView) findViewById(R.id.messagesIntent);
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
                Intent i = new Intent(getApplication(), Users.class);
                startActivity(i);
            }
        });

        messagesIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), MessageReceive.class);
                startActivity(i);
            }
        });



    }




    public void openDialog() {
        MessageDialog messageDialog = new MessageDialog();
        messageDialog.show(getSupportFragmentManager(), "example dialog");
    }

}