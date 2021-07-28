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

/*
                        ManagerReceive.java ---> INFORMATION
            ----------------------------------------------------------------
            this intent will present all the users that already registered
            and send any message to the manager.
            each message can be divided into 3 category:
            1.general message
            2.message regarding payment
            3.message regarding fixing
            ----------------------------------------------------------------
 */

public class MessageReceive extends AppCompatActivity {
    private TextView mEditText;
    private RelativeLayout mLayout;//reaching out to the layout to create new textViews that we recieve from the DB.
    UserHelperClass[] user_array = new UserHelperClass[100];//store 100 messages.
    int count = 0;//count how many user we have in DB.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_recieve);
        mEditText = (TextView) findViewById(R.id.editText);
        //color the status bar.
        getWindow().setStatusBarColor(ContextCompat.getColor(MessageReceive.this, R.color.beco));

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
                            if (user_array[i].getMessage()!=null && !user_array[i].getMessage().equals("")) {
                                mLayout.addView(createNewTextView(user_array[i].messageString(), i,user_array[i].getMessageType()));
                                count_user_message++; // count messages.
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @SuppressLint("SetTextI18n")//importing this function to use any text we like ,inside "setText("text")"
    private TextView createNewTextView(String description, int id,int messageType) {
        //creating layOut parameters.
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView name = new TextView(this);//creating new textView.
        lparams.setMargins(30, (id + 1) * 100, 400, 0); //location of the text
        name.setId(id);//give the layout id from the for loop.
        name.setLayoutParams(lparams); //sending into the textView the parameters necessary to create functional textView.
        if(messageType==0) {//if the messageType is equal to 0 the textView will be colored Yellow , 0 stand for 'כללי'
            name.setTextColor(Color.YELLOW); // regular text color
        }else if(messageType==1){//if the messageType is equal to 1 the textView will be colored Blue  , 1 stands for 'תשלום'
            name.setTextColor(Color.BLUE); // payment color
        }else{//if the messageType is equal to 2 the textView will be colored RED , 2 stands for 'תיקון'.
            name.setTextColor(Color.RED); // fixing color
        }
        name.setText(description); // set the description value to the textView.
        Button delBtn = new Button(this); //creating a delete button
        //locate the delete button inside the textView
        final RelativeLayout.LayoutParams buttonLocation = new RelativeLayout.LayoutParams(150, 100);

        buttonLocation.setMargins(650, (id + 1) * 100, 0, 10); //creating textView at certain position.
        delBtn.setLayoutParams(buttonLocation);
        delBtn.setText("X");    //X stands for the symbol inside the button.
        String id1 = Integer.toString(id);
        delBtn.setId(id+100);   //100 is offset for the deleteButton.

        //if user click of the delButton it sends to the "deleteOldTextView" function.
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOldTextView(id,user_array[id].getUser_phone());
            }
        });
        mLayout.addView(delBtn);


        return name;
    }

    //this function will remove the textView From database , using delete button we created before.
    private void deleteOldTextView(int id,String user_phone)
    {
        //TextView userDescription= (TextView)findViewById(getResources().getIdentifier(Integer.toString(id),"id",getPackageName()));
        TextView userDescription= (TextView)findViewById(id);
        TextView deleteButton= (TextView)findViewById(id+100);
        userDescription.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        //locate the value that need to be reset inside the database.
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child("users").child(user_phone).child("message");
        dbNode.setValue("");//set default value inside the db into --> "" --> (means empty and not NULL)


    }
}