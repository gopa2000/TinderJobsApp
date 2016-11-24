package com.example.gopa2000.mobapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONObject;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MessageSender {

    private String TAG = "MainActivity";

    DbHelper dbHelper;
    SessionManager sessionManager;
    SessionCache sessionCache;
    Map<String, ?> userDetails;
    protected SocketListener socketService;
    private boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sessionManager = new SessionManager(getApplicationContext());

        // Connect to background socket
        Intent serviceIntent = new Intent(MainActivity.this, SocketListener.class);
        startService(serviceIntent);
        doBindService();

        dbHelper = new DbHelper(getApplicationContext());

        sessionCache = SessionCache.getInstance();
        userDetails = sessionManager.getUserDetails();

        sessionCache.setMatched(dbHelper.getMatchTable());
        sessionCache.generateSessionMatches(userDetails.get(DbHelper.KEY_EMAIL).toString());

        // Log.i(TAG, "onCreate: " + userDetails.toString());

        Log.d(TAG, "onCreate: Going to print user details.");
        for(Map.Entry<String, ?> entry : userDetails.entrySet()){
            Log.d("MainActivity map", entry.getKey() + ": " + entry.getValue().toString());
        }

        if(userDetails.get(DbHelper.KEY_TYPE).toString().equals(DbHelper.KEY_SEEKER))
            sessionCache.setSessionCards(dbHelper.getListings());
        else
            sessionCache.setSessionCards(dbHelper.getSeekers());


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

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            socketService = ((SocketListener.LocalBinder) iBinder).getService();

            if(socketService != null){
                Log.i("service-bind", "Service bound successfully!");

                //do whatever you want to do after successful binding
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            socketService = null;
        }
    };

    private void doBindService() {
        bindService(new Intent(MainActivity.this, SocketListener.class), serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
        if(socketService!=null){
            socketService.IsBoundable();
        }
    }


    private void doUnbindService() {
        if (isBound) {
            // Detach our existing connection.
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    @Override
    public void sendMessage(String msg, JSONObject json){
        socketService.sendMessage(msg, json);
    }
}
