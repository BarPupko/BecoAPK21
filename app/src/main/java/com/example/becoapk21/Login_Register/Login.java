package com.example.becoapk21.Login_Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.becoapk21.ForgotPassword.ForgetPassword;
import com.example.becoapk21.Intro.IntroActivity;
import com.example.becoapk21.R;
import com.example.becoapk21.Admin.Help;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/*                          Login.java ---> INFORMATION
            ------------------------------------------------------------
            Login intent has 4 buttons , contact(help) , get to parking
            location(using gps) , login (after entering the credentials).
            register(if the user don't register already.)
            -------------------------------------------------------------
 */
public class Login extends AppCompatActivity {

    EditText regPhoneNumber, regPassword;//variables that will store the user phone number and password
    ImageView parkingLocator;//variables store all the parking location.
    ImageView contact; //contact button that will be redirect to Help.java.
    TextView forgotPass;
    String emailfromDB;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.beco));
        //status bar color
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();           //hide intent title.
        setContentView(R.layout.activity_login);

        //attributes
        regPhoneNumber = (EditText) findViewById(R.id.addPhone1);
        regPassword = (EditText) findViewById(R.id.AddPassword);
        contact = (ImageView) findViewById(R.id.contact2);
        parkingLocator = (ImageView) findViewById(R.id.parkingLocator1);
        forgotPass = (TextView) findViewById(R.id.forgotPasswordLogin);
        regPhoneNumber.setText("");
        regPassword.setText("");


        //parking locator
        parkingLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=32.8219025,34.9900802&mode=b"));//mode=b (for bicycle )
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });
        //register option
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewMember();
            }
        });
        //login option
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_phone = regPhoneNumber.getText().toString();
                String password = regPassword.getText().toString();
                //showing the next message if user didn't enter right phone number.
                if (TextUtils.isEmpty(user_phone)) {
                    regPhoneNumber.setError("יש להזין מספר טלפון");
                    return;
                }

                if (user_phone.length() != 10) {//change later to 10 digits
                    regPhoneNumber.setError("יש להזין מספר טלפון בעל 10 ספרות");
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    regPassword.setError("יש להזין סיסמא");
                    return;
                }


                //connect into firebase and enter users DB
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                //create query in which the phones orders in in specific path.
                Query checkUser = reference.orderByChild("user_phone").equalTo(user_phone);
                //check if the user is exists.
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {//If phonenumber exists in DB
                            emailfromDB = snapshot.child(user_phone).child("user_email").getValue(String.class).trim();//pull email from realtime DB to use in Auth
                            String user_name_fromDB = snapshot.child(user_phone).child("user_name").getValue(String.class).trim();//get the user_name from the phone_number we get.
                            //if login successful go to welcome session
                            mAuth.signInWithEmailAndPassword(emailfromDB, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                //calling intro activity.
                                                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                                                intent.putExtra("user_phone", user_phone);
                                                intent.putExtra("user_name", user_name_fromDB);
                                                intent.putExtra("user_email", emailfromDB);
                                                startActivity(intent);//if it exists move to welcome Session intent
                                            } else {
                                                Toast.makeText(Login.this, "אחד מהנתונים שהכנסת שגוי", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Help.class);
                startActivity(i);
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(Intent);
            }
        });


    }


    //move to register intent.
    public void registerNewMember() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


}