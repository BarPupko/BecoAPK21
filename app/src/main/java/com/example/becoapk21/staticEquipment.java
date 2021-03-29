package com.example.becoapk21;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class staticEquipment extends AppCompatActivity {

    ImageView toolsbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_electric);
        getSupportActionBar().hide();
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(staticEquipment.this, R.color.design_default_color_background));
        //

        toolsbox=(ImageView)findViewById(R.id.toolbox);

        String toolsBoxLocation = "https://www.google.com/maps/place/HaGanim+St+48,+Haifa/@32.8107282,34.9899207,14.19z/data=!4m5!3m4!1s0x151dbbb66d2fbab7:0xfe603691bc10c79e!8m2!3d32.8190178!4d34.9884188";


        toolsbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(toolsBoxLocation));
                startActivity(i);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("משקל חריג")
                .setMessage("שים לב משקל הארגז,נמוך.")
                .setPositiveButton("המשך",null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(staticEquipment.this,"לחץ על המפה על מנת לראות את מיקום הארגז",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }
}