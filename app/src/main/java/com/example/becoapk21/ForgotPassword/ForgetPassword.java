package com.example.becoapk21.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.becoapk21.Navigation.CarmelRoad;
import com.example.becoapk21.Parking.GetTheBike;
import com.example.becoapk21.Parking.ParkTheBike;
import com.example.becoapk21.R;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {

    Button nextBtn;
    EditText getPhoneNum;
    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        nextBtn = (Button) findViewById(R.id.nextButton);
        getPhoneNum = (EditText) findViewById(R.id.getPhoneNumber);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            //Auth
            @Override
            public void onClick(View v) {
                //
                phoneNum = getPhoneNum.getText().toString();
                if (phoneNum.length() != 10) {
                    getPhoneNum.setError("מספר הטלפון צריך להיות בעל 10 ספרות");
                    return;
                }
                if (phoneNum.charAt(0) != '0' || phoneNum.charAt(1) != '5') {
                    getPhoneNum.setError("מספר הטלפון צריך להתחיל ב05 וספרה נוספת");
                    return;
                }
                for (int i = 0; i < 8; i++) {
                    if (phoneNum.charAt(i) < '0' || phoneNum.charAt(i) > '9') {
                        getPhoneNum.setError("ספרה לא תקינה במספר הטלפון");
                        return;
                    }
                }
                Intent Intent = new Intent(getApplicationContext() , CodeVerfication.class);
                Intent.putExtra("user_phone", phoneNum);
                startActivity(Intent);
            }

        });

    }
}