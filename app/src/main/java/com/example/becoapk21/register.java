package com.example.becoapk21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText regEmail, regPassword,fname;
    Button registerEND;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    String userID;
    FirebaseFirestore fStore;

    Button payment;
    ImageView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(register.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        regEmail = (EditText) findViewById(R.id.EmailAddress);
        regPassword = (EditText) findViewById(R.id.addPassword);
        registerEND = (Button) findViewById(R.id.registerEND);
        fname = (EditText)findViewById(R.id.userNameInput);
        payment=(Button)findViewById(R.id.paymentButton);
        contact=(ImageView)findViewById(R.id.contact1);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplication(),help.class);
                startActivity(i);
            }
        });


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),payment.class);
                startActivity(i);
            }
        });
        //save data in firebase on button click
        registerEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("users");

                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                String fname1 = fname.getText().toString();
                if(TextUtils.isEmpty(email)){
                    regEmail.setError("יש לציין אימייל");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    regPassword.setError("יש למלא סיסמא");
                    return;
                }
                if(password.length() < 6){
                    regPassword.setError("הסיסמא חייבת להכיל לפחות 6 תווים");
                    return;
                }

            //    Member member1 = new Member(email, password,fname1);
//               reference.setValue(member1);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          //  FirebaseUser fuser = fAuth.getCurrentUser();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fname1);
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            Toast.makeText(register.this, "המשתמש נוצר", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(register.this, "המשתמש כבר קיים במערכת ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }
}