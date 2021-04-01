package com.example.becoapk21.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.becoapk21.Admin.managaerCONTROL;
import com.example.becoapk21.Navigation.RoadMap;
import com.example.becoapk21.Parking.Parking;
import com.example.becoapk21.R;
import com.example.becoapk21.Admin.help;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

//static

public class welcomeSession<first_name> extends AppCompatActivity {
static Random rnd = new Random();

    private ImageView parking;//park Bicycle
    FirebaseAuth fAuth;
    TextView fullName;
    ImageView map;
    ImageView fix;
    ImageView man;
    ImageView chatSu;
    TextView didYouKnowText;
    int random_number;
    boolean isAdmin = false;
    String user_phone;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(welcomeSession.this, R.color.design_default_color_background));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_session);
        getSupportActionBar().hide();
        //Get data from calling intent
        Intent intent = getIntent();
         user_phone = intent.getStringExtra("user_phone");
         user_name = intent.getStringExtra("user_name");
        Toast.makeText(welcomeSession.this, user_phone, Toast.LENGTH_SHORT).show();
        //Get data from calling intent
        chatSu = (ImageView) findViewById(R.id.chatSupport);
        fix = (ImageView) findViewById(R.id.fix1);
        map = (ImageView) findViewById(R.id.map);
        didYouKnowText = (TextView) findViewById(R.id.didYouKnowNote);

        fullName = (TextView) findViewById(R.id.fullName2);

        fAuth = FirebaseAuth.getInstance();
        random_number=rnd.nextInt(2);
        man = (ImageView) findViewById(R.id.man);

        String[] arr = {"רכיבה על אופניים מגבירה את הריכוז וממריצה את המוח.", "לאכול שווארמה טעים אבל לא בהכרח בריא"};
        didYouKnowText.setText(arr[random_number]);


        parking = (ImageView) findViewById(R.id.parking);
        fullName.setText(user_name);
        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parking();
            }

        });

        if (user_phone.equals("0526333")) {
            isAdmin = true;
        } else {
            isAdmin = false;
        }
        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), help.class);
                startActivity(i);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), RoadMap.class);
                startActivity(i);

            }
        });

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    Intent i = new Intent(getApplication(), managaerCONTROL.class);
                    startActivity(i);
                } else {
                    Toast.makeText(welcomeSession.this, "אתה לא מנהל", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void parking() {
        Intent intent = new Intent(this, Parking.class);
        intent.putExtra("user_name",user_name);
        intent.putExtra("user_phone",user_phone);
        startActivity(intent);
    }
}
