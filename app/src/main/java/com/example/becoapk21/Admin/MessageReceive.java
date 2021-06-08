package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.Parking.ParkingHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageReceive extends AppCompatActivity {
    private EditText mEditText;
    private RelativeLayout mLayout;
    UserHelperClass [] user_array = new UserHelperClass[100];
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_recieve);
        mEditText = (EditText) findViewById(R.id.editText);
        getWindow().setStatusBarColor(ContextCompat.getColor(MessageReceive.this, R.color.beco));

        //
        getSupportActionBar().hide();
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                            user_array[count++] = user;
                        }

                        mLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

                        for(int i=0;i<count;i++){
                            if(user_array[i].getMessage()!=null) {
                                mLayout.addView(createNewTextView(user_array[i].messageString(), i));
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private TextView createNewTextView(String description, int id) {
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView name = new TextView(this);
        lparams.setMargins(150,(id+1)*100,0,0); //location of the text
        name.setId(id);
        name.setLayoutParams(lparams);
        name.setTextColor(Color.WHITE); // text color
//        name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);


        name.setText(description);
        return name;
    }

}