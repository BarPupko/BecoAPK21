package com.example.becoapk21.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.becoapk21.Login_Register.UserHelperClass;
import com.example.becoapk21.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateManagement extends AppCompatActivity {

    private TextView mEditText;
    private RelativeLayout mLayout;//reaching out to the layout to create new textViews that we recieve from the DB.
    UserHelperClass[] user_array = new UserHelperClass[100];//store 100 messages.
    int count = 0;//count how many user we have in DB.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_management);
        //color the status bar.
        getWindow().setStatusBarColor(ContextCompat.getColor(updateManagement.this, R.color.beco));

        //hide the intent title
        getSupportActionBar().hide();

        //connection to the database
        FirebaseDatabase.getInstance().getReference().child("users")//get data from users.
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                            user_array[count++] = user; // if user inside data base then count.
                        }

                        mLayout = (RelativeLayout) findViewById(R.id.relativeLayout); //mLayout get an id from xml , then we will locate all messages inside this place.
                        int count_user_message = 0; //we will use it later to display how many messages there are.
                        for (int i = 0; i < count; i++) {
                            //display the messages that are not null and not empty.
                            if (!user_array[i].getUser_phone().equals("0526333")){//hard coded admin
                                mLayout.addView(createNewTextView(i, user_array[i].getIsAdmin(), user_array[i].getUser_name(),user_array[i].getUser_phone()));
                            count_user_message++; // count messages.
                        }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @SuppressLint("SetTextI18n")
//importing this function to use any text we like ,inside "setText("text")"
    private TextView createNewTextView(int id, boolean isAdmin, String userName,String userPhone) {
        //creating layOut parameters.
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView name = new TextView(this);//creating new textView.
        lparams.setMargins(30, (id + 1) * 100, 400, 0); //location of the text
        name.setId(id);//give the layout id from the for loop.
        name.setLayoutParams(lparams); //sending into the textView the parameters necessary to create functional textView.
        if (isAdmin) {//if the messageType is equal to 0 the textView will be colored Yellow , 0 stand for 'כללי'
            name.setTextColor(Color.GREEN); // regular text color
        } else {
            name.setTextColor(Color.RED); // regular text color
        }
        name.setText(userName+" "+userPhone); // set the description value to the textView.
        Button delBtn = new Button(this); //creating a delete button
        //locate the delete button inside the textView
        final RelativeLayout.LayoutParams buttonLocation = new RelativeLayout.LayoutParams(150, 100);

        buttonLocation.setMargins(650, (id + 1) * 100, 0, 10); //creating textView at certain position.
        delBtn.setLayoutParams(buttonLocation);
        if (isAdmin) {
            delBtn.setText("V");    //X stands for the symbol inside the button.
        } else {
            delBtn.setText("^");    //X stands for the symbol inside the button.

        }

        String id1 = Integer.toString(id);
        delBtn.setId(id + 100);   //100 is offset for the deleteButton.

        //if user click of the delButton it sends to the "deleteOldTextView" function.
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAdminStatus(id, user_array[id].getUser_phone(),user_array[id].getIsAdmin());
            }
        });
        mLayout.addView(delBtn);


        return name;
    }

    //this function will remove the textView From database , using delete button we created before.
    private void changeAdminStatus(int id, String user_phone,boolean isAdmin) {
        //TextView userDescription= (TextView)findViewById(getResources().getIdentifier(Integer.toString(id),"id",getPackageName()));
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child("users").child(user_phone).child("isAdmin");

        dbNode.setValue(!isAdmin);//if user is admin we want to make him not admin
        finish();
        //locate the value that need to be reset inside the database.
    }
}