package com.example.becoapk21.Parking;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.becoapk21.R;
import org.json.JSONException;
import org.json.JSONObject;
/*
                       PaymentDetails.java ---> INFORMATION
            ------------------------------------------------------------
            thisClass will show in intent the amount of payment user need to pay
            for the parking.
            -------------------------------------------------------------
 */
public class PaymentDetails extends AppCompatActivity {

    TextView txtId,txtAmount,txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = (TextView)findViewById(R.id.txtId);         //TextView for userID.
        txtAmount=(TextView)findViewById(R.id.textAmount);  //TextView saying how many he need to pay.
        txtStatus=(TextView)findViewById(R.id.txtStatus);   //TextView displaying the amount he need to pay.

        //Get intent
        Intent intent = getIntent();
        //try and catch to get the data from database , if the data not return(catch) it will display null.
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        //showing of the screen the details it receive from the DataBase
    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(response.getString(String.format("$%s",paymentAmount)));
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }
}