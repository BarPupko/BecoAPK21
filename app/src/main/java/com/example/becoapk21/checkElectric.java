package com.example.becoapk21;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class checkElectric extends AppCompatActivity {

    ImageView RedDrill;
    ImageView YellowDrill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_electric);
        getSupportActionBar().hide();
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(checkElectric.this, R.color.design_default_color_background));
        //

        RedDrill=(ImageView)findViewById(R.id.mapRedDrill);

        String RedDrillLocation = "https://www.google.com/maps/place/Derech+Allenby+75-67,+Haifa/@32.8189343,34.987143,18z/data=!3m1!4b1!4m5!3m4!1s0x151dbbb64220748b:0x7b2bb972191e328d!8m2!3d32.8189504!4d34.9877453";


        RedDrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(RedDrillLocation));
                startActivity(i);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("כלי מתרחק מהאזור")
                .setMessage("כלי מס' 1 של מושבה הגרמנית מתרחק מהאזור ביותר מ100 מטרים")
                .setPositiveButton("המשך",null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(checkElectric.this,"לחץ על המפה על מנת לראות את מיקום הכלי",Toast.LENGTH_LONG).show();
            dialog.dismiss();
            }
        });
    }
}