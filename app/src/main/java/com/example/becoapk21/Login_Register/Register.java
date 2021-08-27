package com.example.becoapk21.Login_Register;


/*
                        Users.java ---> INFORMATION
            ------------------------------------------------------------
            this intent will present all the users that already parked
            their bicycle , the amount they need to pay and the location
            of the bike inside the parking.
            we can unbound the bicycle from the parking by clicking on X.
            -------------------------------------------------------------
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.Admin.Help;
import com.example.becoapk21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Register extends AppCompatActivity {
    EditText regEmail, regPassword, fname, phoneNumber;
    Button registerEND;
    FirebaseDatabase rootNode;
    boolean isPhoneExists;
    ImageView contact;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(Register.this, R.color.design_default_color_background));
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        isPhoneExists = true;
        regEmail = (EditText) findViewById(R.id.EmailAddress);
        fname = (EditText) findViewById(R.id.userNameInput);
        regPassword = (EditText) findViewById(R.id.addPassword2);
        phoneNumber = (EditText) findViewById(R.id.addPhone);
        registerEND = (Button) findViewById(R.id.registerEND);
        contact = (ImageView) findViewById(R.id.contact1);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Help.class);
                startActivity(i);
            }
        });


        //save data in firebase on button click
        registerEND.setOnClickListener(new View.OnClickListener() {
            //Auth
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                FirebaseDatabase users_instance = FirebaseDatabase.getInstance();
                mAuth=FirebaseAuth.getInstance();
//                reference = rootNode.getReference("users");
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                String fname1 = fname.getText().toString();
                String phoneNum = phoneNumber.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    regEmail.setError("יש לציין אימייל");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    regPassword.setError("יש למלא סיסמא");
                    return;
                }
                if (password.length() < 6) {
                    regPassword.setError("הסיסמא חייבת להכיל לפחות 6 תווים");
                    return;
                }
                if (phoneNum.length() != 10) {
                    phoneNumber.setError("מספר הטלפון צריך להיות בעל 10 ספרות");
                    return;
                }
                if (phoneNum.charAt(0) != '0' || phoneNum.charAt(1) != '5') {
                    phoneNumber.setError("מספר הטלפון צריך להתחיל ב05 וספרה נוספת");
                    return;
                }
                for (int i = 0; i < 8; i++) {
                    if (phoneNum.charAt(i) < '0' || phoneNum.charAt(i) > '9') {
                        phoneNumber.setError("ספרה לא תקינה במספר הטלפון");
                        return;
                    }
                }
                //בדיקת אימייל תקין
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    regEmail.setError("הכנס אימייל תקין");
                    return;
                }



                //*****************************************************.
                //*****************---realTimeDataBase---**************.
                //*****************************************************.
                //CodeVeritication here OTP

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Register.this, "נשלח אימייל לאימות המשתמש", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "אירעה שגיאה נסה שנית", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    UserHelperClass helperClass = new UserHelperClass(fname1, email, phoneNum, "A1", "", 0, false);
                                    FirebaseDatabase.getInstance().getReference("users").child(phoneNum)
                                    .setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "המשתמש נוצר", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(Register.this, "אירעה שגיאה", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });


              /*  DatabaseReference users_ref = users_instance.getReference("users");
                Query checkUserEmail = users_ref.orderByChild("user_email").equalTo(email);
                Query checkUserPhone = users_ref.orderByChild("user_phone").equalTo(phoneNum);

                checkUserEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            checkUserPhone.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {
                                        // Call Intent CodeVerification
                                        mAuth.createUserWithEmailAndPassword(email,password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if(task.isSuccessful()) {
                                                            UserHelperClass helperClass = new UserHelperClass(fname1, password, email, phoneNum, "A1", "", 0, false);
                                                            users_ref.child(phoneNum).setValue(helperClass);
                                                            Toast.makeText(Register.this, "המשתמש נוצר", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                                            startActivity(intent);
                                                        }
                                                        else{
                                                            Toast.makeText(Register.this, "הפלאפון כבר רשום במערכת", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        Toast.makeText(Register.this, "הפלאפון כבר רשום במערכת", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "האימייל כבר רשום במערכת", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

            }
        });

    }
}