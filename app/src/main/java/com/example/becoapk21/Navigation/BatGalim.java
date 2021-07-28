package com.example.becoapk21.Navigation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.becoapk21.R;
/*
                       BatGalim.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will show information regarding bike Track
            and if the user want it will redirect to the BatGalim Track
            -------------------------------------------------------------
 */
public class BatGalim extends AppCompatActivity {
    TextView Blue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(BatGalim.this, R.color.beco));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_galim);
        getSupportActionBar().hide();

        Blue = (TextView) findViewById(R.id.goToBlueRoad);
        String blueMap = "https://www.google.com/maps/dir/32.8219025,34.9900802/HaGanim+St+48,+Haifa/32.8252939,34.9840263/32.8290044,34.974554/32.8283861,34.972726/32.8310194,34.9695923/32.8311191,34.9668147/32.8321165,34.9720852/32.8358266,34.9807981/@32.8320267,34.9724174,15.85z/data=!4m16!4m15!1m0!1m5!1m1!1s0x151dbbb66d2fbab7:0xfe603691bc10c79e!2m2!1d34.9884153!2d32.8190177!1m0!1m0!1m0!1m0!1m0!1m0!1m0!3e1";
//        Blue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(blueMap));
//                startActivity(i);
//            }
//        });
        Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent googleMaps = new Intent(Intent.ACTION_VIEW);
//                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=32.8219025,34.9900802&daddr=32.8252939,34.9840263&daddr=32.8290044,34.974554&mode=l";
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//
//                //googleMaps.setData(Uri.parse("geo:32.8219025,34.9900802?z=19"));
//                startActivity(Intent.createChooser(intent, "Select an application"));

                /*if(googleMaps.resolveActivity(getPackagerManager()) != null) {

                    startActivity(googleMaps);

                }*/
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=32.8219025,34.9900802&mode=b"));//mode=b (for bicycle )
                    i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });

    }
}