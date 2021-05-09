package com.example.becoapk21.Parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.Config.Config;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTheBike extends AppCompatActivity {


    String user_phone;
    TextView parkingSpot;
    TextView amountToPay;
    String parkingSpotString;
    Long time;
    char parking;
    int parkingDigit;
    Date currentDate;
    double timeParked;
    double amount_to_pay;
    double parkingFee = 5;
    double conversion = 1000 * 60 * 60;


    //PAYPAL SDK VARIABLES
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    Button payment;


    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(GetTheBike.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_the_bike);

        parkingSpot = (TextView) findViewById(R.id.parkingLocation);
        amountToPay = (TextView) findViewById(R.id.amountLeft);

        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");

        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
        DatabaseReference parking_ref = users_instance.getReference("parked");
        Query checkUser = parking_ref.orderByKey().equalTo(user_phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    time = (Long) snapshot.child(user_phone).child("parkingTime").child("time").getValue();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    currentDate = new Date();
                    timeParked = (double) currentDate.getTime() - time;
                    amount_to_pay = Math.round((timeParked / conversion) * parkingFee * 100) / 100.;//round to two numbers
                    amountToPay.setText(Double.toString(amount_to_pay) + " ש''ח ");

                    //get the parkingSpot
                    parking =  (char)Math.toIntExact((long)snapshot.child(user_phone).child("parkingSpot").getValue());
                    parkingDigit =  Math.toIntExact((long)snapshot.child(user_phone).child("parkingDigit").getValue());
                    parkingSpotString=parking+""+parkingDigit;
                    parkingSpot.setText(parkingSpotString);

                } else {
                    Toast.makeText(GetTheBike.this, "ארור = error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //PAYPAL//PAYPAL//PAYPAL
        Intent payPalIntent = new Intent(this, PayPalService.class);
        payPalIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(payPalIntent);


        payment = (Button) findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }


        });

    }//END OF ON CREATE

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        //delete entry for phone number on successful paymen

                        //delete node
                        startActivity(new Intent(this,GetTheBike.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount_to_pay)
                        );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //delete parked entry for phone number
                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child("parked").child(user_phone);
                dbNode.setValue(null);
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this, "Invalid", Toast.LENGTH_LONG).show();
        }
    }

    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount_to_pay), "USD",
                "Pay for parking", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);


    }
}

