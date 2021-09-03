package com.example.becoapk21.Intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.example.becoapk21.Activities.WelcomeSession;
import com.example.becoapk21.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnGetStarted;
    Animation btnAnim;
    String user_phone;
    String user_name;
    int position = 0;//give us the position of the intent we want to go to,default is '0'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("user_phone");
        user_name = intent.getStringExtra("user_name");
        //when this activity is about to be launch , we need to check if its opened before or not
        if(restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(),WelcomeSession.class);
            mainActivity.putExtra("user_phone",user_phone);
            mainActivity.putExtra("user_name",user_name);
            startActivity(mainActivity);
            finish();
        }


        setContentView(R.layout.activity_intro);


        //hide the action bar
        getSupportActionBar().hide();

        //ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);


        //fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("ברוכים הבאים", "חניון האופניים החדיש BECO."+"\n"+"כאן תוכלו למצוא מגוון פעולות ושירותים.", R.drawable.bicycle));
        mList.add(new ScreenItem("חנייה", "באפליקציה זו תוכלו לחנות את האופניים בכל עת, "+"\n"+"בדרך קלה ובטוחה.", R.drawable.putbikeontherail));
        mList.add(new ScreenItem("מסלולים", "תוכלו למצוא מגוון מסלולים , לפי דרגת קושי.", R.drawable.colormap));
        mList.add(new ScreenItem("אבטחה", "השירות מאובטח באבטחה מתקדמת של google.", R.drawable.shield));
        mList.add(new ScreenItem("שמחים שהצטרפתם", " ", R.drawable.applogo));

        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);


        //setup table with viewpager
        tabIndicator.setupWithViewPager(screenPager);


        //next button click.Listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);

                }
                //when we reach to the last screen
                if (position == mList.size()) {
                    loadLastScreen();
                }
            }


        });


        //tablayout
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //get stated button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open login activity
                Intent welcomeSession = new Intent(getApplicationContext(), WelcomeSession.class);
                welcomeSession.putExtra("user_phone",user_phone);
                welcomeSession.putExtra("user_name",user_name);
                startActivity(welcomeSession);

                //use shared preferences
                savePrefsData();
                finish();

            }
        });
    }


    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();

    }

    //function check if intro already opened
    private boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }


    //show the GetStated button and hide the indicator and the next buttons
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        //setup animation
        btnGetStarted.setAnimation(btnAnim);

    }
}