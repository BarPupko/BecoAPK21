package com.example.becoapk21.Login_Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.becoapk21.Activities.welcomeSession;
import com.example.becoapk21.R;
import com.example.becoapk21.Admin.help;
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

public class Login extends AppCompatActivity {

    EditText regEmail, regPassword;
    ImageView contact;
    FirebaseAuth fAuth;
    String passfromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        fAuth = FirebaseAuth.getInstance();
        regEmail = (EditText) findViewById(R.id.EmailAddress);
        regPassword = (EditText) findViewById(R.id.AddPassword);
        contact=(ImageView)findViewById(R.id.contact2);

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewMember();
            }
        });

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    loginMember();
                    regEmail.setError("יש להזין אימייל");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPassword.setError("יש להזין סיסמא");
                    return;
                }
                if(password.length() <6){
                    regPassword.setError("יש להזין סיסמא נכונה הכוללת לפחות 6 תווים");
                    return;
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("user_phone").equalTo(email);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            passfromDB = snapshot.child(email).child("user_password").getValue(String.class).trim();
                            if(passfromDB.equals(password)){



                            }
                            else{
                                Toast.makeText(Login.this, passfromDB, Toast.LENGTH_SHORT).show();
                            }
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
                Intent i = new Intent (getApplication(), help.class);
                startActivity(i);
            }
        });


    }


    public void registerNewMember() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void loginMember() {
      //  Intent intent = new Intent(getApplicationContext(), welcomeSession.class);
      //  startActivity(intent);
    }
}