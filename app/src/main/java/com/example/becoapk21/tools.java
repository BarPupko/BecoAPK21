package com.example.becoapk21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class tools extends AppCompatActivity {

    ImageView chatSu;
    ImageView tool2_grey;
    ImageView tool2_grey2;
    ImageView tool2_grey3;
    ImageView tool;
    ImageView tool1;
    ImageView tool2;
    ImageView tool3;
    ImageView tool4;
    ImageView tool5;
    boolean tool_flag = true;
    boolean tool1_flag = true;
    boolean tool2_flag = true;
    boolean tool3_flag = true;
    boolean tool4_flag = true;
    boolean tool5_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(tools.this, R.color.design_default_color_background));
        //
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        chatSu = (ImageView)findViewById(R.id.chat);
        tool2_grey = (ImageView) findViewById(R.id.tool2_grey);
        tool2_grey2 = (ImageView) findViewById(R.id.tool2_grey2);
        tool2_grey3 = (ImageView) findViewById(R.id.tool2_grey3);
        tool = (ImageView) findViewById(R.id.tool);
        tool1 = (ImageView) findViewById(R.id.tool1);
        tool2 = (ImageView) findViewById(R.id.tool2);
        tool3 = (ImageView) findViewById(R.id.tool3);
        tool4 = (ImageView) findViewById(R.id.tool4);
        tool5 = (ImageView) findViewById(R.id.tool5);

        chatSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),help.class);
                startActivity(i);

            }
        });
        tool2_grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tools.this, "חסר במלאי", Toast.LENGTH_SHORT).show();

            }
        });

        tool2_grey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tools.this, "חסר במלאי", Toast.LENGTH_SHORT).show();

            }
        });
        tool2_grey3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tools.this, "חסר במלאי", Toast.LENGTH_SHORT).show();

            }
        });
        tool1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tool1_flag) {
                    Toast.makeText(tools.this, "הפריט נלקח,נשארו עוד 12", Toast.LENGTH_SHORT).show();
                    tool1_flag = false;
                } else {
                    Toast.makeText(tools.this, "הפריט כבר נלקח", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tool_flag) {
                    Toast.makeText(tools.this, "הפריט נלקח,נשארו עוד 8", Toast.LENGTH_SHORT).show();
                    tool_flag = false;
                } else {
                    Toast.makeText(tools.this, "הפריט כבר נלקח", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tool3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tool3_flag) {
                    Toast.makeText(tools.this, "הפריט נלקח,נשארו עוד 5", Toast.LENGTH_SHORT).show();
                    tool3_flag = false;
                } else {
                    Toast.makeText(tools.this, "הפריט כבר נלקח", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tool4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tool4_flag) {
                    Toast.makeText(tools.this, "הפריט נלקח,נשארו עוד 1", Toast.LENGTH_SHORT).show();
                    tool4_flag = false;
                } else {
                    Toast.makeText(tools.this, "הפריט כבר נלקח", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tool5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tool5_flag) {
                    Toast.makeText(tools.this, "הפריט נלקח,נשארו עוד 6", Toast.LENGTH_SHORT).show();
                    tool5_flag = false;
                } else {
                    Toast.makeText(tools.this, "הפריט כבר נלקח", Toast.LENGTH_SHORT).show();
                }

            }
        }); tool2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tool2_flag) {
                    Toast.makeText(tools.this, "הפריט נלקח,נשארו עוד 4", Toast.LENGTH_SHORT).show();
                    tool2_flag = false;
                } else {
                    Toast.makeText(tools.this, "הפריט כבר נלקח", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}