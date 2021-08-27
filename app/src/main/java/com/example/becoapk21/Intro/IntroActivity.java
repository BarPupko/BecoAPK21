package com.example.becoapk21.Intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.becoapk21.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("fresh food","test",R.drawable.applogo));
        mList.add(new ScreenItem("fast","test",R.drawable.applogo1));
        mList.add(new ScreenItem("food","test",R.drawable.applogo2));

        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

    }
}