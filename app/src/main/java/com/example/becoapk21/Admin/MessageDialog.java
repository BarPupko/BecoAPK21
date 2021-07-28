package com.example.becoapk21.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/*
                        ManagerDialog.java ---> INFORMATION
            ------------------------------------------------------------
            this intent is part of ManagerControl , this intent contains
            the message that will be shown to the manager once he touches
            the bell , then the message in this intent will be shown.
            this message is only for information.
            -------------------------------------------------------------
 */
public class MessageDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //building an alert , setTitle will be show first then the content of the message.
        //we can enter another button if we like.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(" ברוכ/ה הבא לפנאל הניהול ").setMessage("בפאנל הניהול תוכל/י לעקוב אחר ציוד,משתמשים ולהזין דוחות , בהצלחה!").setPositiveButton("המשך", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        return builder.create();
    }
}
