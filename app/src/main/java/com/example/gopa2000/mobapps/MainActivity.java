package com.example.gopa2000.mobapps;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());

        // TODO: 11/4/16
        // Start login activity only if not already logged in

        sessionManager.checkLogin();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(getResources().getDrawable(R.drawable.profileicon)));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(getResources().getDrawable(R.drawable.homeicon)));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(getResources().getDrawable(R.drawable.chaticon)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final NonSwipeableViewPager viewPager = (NonSwipeableViewPager) findViewById(R.id.view_pager);

        final CustomPagerAdapter pagerAdapter = new CustomPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

}
