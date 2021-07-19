package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.Parking.ParkingHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageReceive extends AppCompatActivity {
    private TextView mEditText;
    private RelativeLayout mLayout;
    UserHelperClass[] user_array = new UserHelperClass[100];
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_recieve);
        mEditText = (TextView) findViewById(R.id.editText);
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

                        mLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                        int count_user_message = 0;
                        for (int i = 0; i < count; i++) {
                            if (user_array[i].getMessage() != null) {
                                mLayout.addView(createNewTextView(user_array[i].messageString(), i));
                                count_user_message++;
                            }
                        }
                       // for(int i=0; i<count_user_message;i++){

                      //  }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @SuppressLint("SetTextI18n")//importing this function to use any text we like ,inside "setText("text")"
    private TextView createNewTextView(String description, int id) {
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView name = new TextView(this);
        lparams.setMargins(150, (id + 1) * 100, 0, 0); //location of the text
        name.setId(id);
        name.setLayoutParams(lparams);
        name.setTextColor(Color.WHITE); // text color
        name.setText(description);
        Button delBtn = new Button(this);
        final RelativeLayout.LayoutParams buttonLocation = new RelativeLayout.LayoutParams(150, 100);

        buttonLocation.setMargins(650, (id + 1) * 100, 0, 10);
        delBtn.setLayoutParams(buttonLocation);
        delBtn.setText("X");
        String id1 = Integer.toString(id);
        delBtn.setId(id+100);//100 is offset for the deleteButton.
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOldTextView(id,user_array[id].getUser_phone());
            }
        });
        mLayout.addView(delBtn);


        return name;
    }

    private void deleteOldTextView(int id,String user_phone)
    {
        //TextView userDescription= (TextView)findViewById(getResources().getIdentifier(Integer.toString(id),"id",getPackageName()));
        TextView userDescription= (TextView)findViewById(id);
        TextView deleteButton= (TextView)findViewById(id+100);
        userDescription.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child("users").child(user_phone).child("message");
        dbNode.setValue("");


    }
}