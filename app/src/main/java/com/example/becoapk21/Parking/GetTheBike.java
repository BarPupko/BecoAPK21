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
import android.os.Looper;
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


/*
                      GetTheBike.java ---> INFORMATION
            ------------------------------------------------------------
            PayPal api intent , will receive the amount of money the user
            need to pay from the database , and this information will moved
            to PayPal Api then the user will see the amount of money he need to pay
            he will need to connect into his user , and then to getThePay.
            if the pay is successful the user will get his bike back and his information from the
            database will be removed.
            -------------------------------------------------------------
 */

public class GetTheBike extends AppCompatActivity {



    String user_phone;
    TextView parkingSpot;
    TextView amountToPay;
    double amount_to_pay;
    double parkingFee = 55;//10 אג' לדקה
    Button payment;

    //PAYPAL SDK VARIABLES
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(GetTheBike.this, R.color.beco));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_the_bike);

        parkingSpot = (TextView) findViewById(R.id.parkingLocation);
        amountToPay = (TextView) findViewById(R.id.amountLeft);

        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");

        //after 30 second this activity will start , and return us to the previous Intent.
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                },
                30000);

        //////////////////////////////////////////////////////////////////////

        FirebaseDatabase users_instance = FirebaseDatabase.getInstance();//get current user
        DatabaseReference admin_ref = users_instance.getReference("admin"); //
        admin_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //משיכת של נתון כמה עולה לדקה לשמור את האופניים
                 parkingFee = Double.parseDouble(snapshot.child("parkingFeePerMinute").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference parking_ref = users_instance.getReference("parked");
        Query checkUser = parking_ref.orderByKey().equalTo(user_phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ParkingHelperClass user = snapshot.child(user_phone).getValue(ParkingHelperClass.class);

                    user.setParkingFee(parkingFee);//update parkingFee from database
                    amount_to_pay = user.calculateFee();
                    amountToPay.setText(Double.toString(amount_to_pay) + "שח");
                    Toast.makeText(GetTheBike.this, "תשלום: אגורות לדקה" + Double.toString(parkingFee) , Toast.LENGTH_SHORT).show();


                    //get the parkingSpot
                    parkingSpot.setText(user.getFullParkingSpot());


                } else {
                    Toast.makeText(GetTheBike.this, "error", Toast.LENGTH_SHORT).show();
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
                        //delete entry for phone number on successful payment

                        //delete node
                        startActivity(new Intent(this, GetTheBike.class)
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
                //  dbNode.setValue(null);//מחיקה במידה ונצטרך בעת ביטול
                Toast.makeText(this, "פעולת תשלום , בוטלה.", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this, "אין מספיק כסף בחשבון.", Toast.LENGTH_LONG).show();
        }
    }


    //moving to payment intent.
    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount_to_pay), "ILS",//Paypal showing the currency user need to pay
                "Pay for parking", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
}

