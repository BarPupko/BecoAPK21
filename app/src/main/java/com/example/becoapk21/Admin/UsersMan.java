package com.example.becoapk21.Admin;

import android.os.Bundle;
import com.example.becoapk21.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Objects;

public class UsersMan extends AppCompatActivity {

    /*
                            UsersMan.java ---> INFORMATION
                ------------------------------------------------------------
                showing the title for Users.java.
                -------------------------------------------------------------
     */
    //user manager Intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set status bar color into Beco color.
        getWindow().setStatusBarColor(ContextCompat.getColor(UsersMan.this, R.color.beco));
        //hide the intent title
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        }
    }
