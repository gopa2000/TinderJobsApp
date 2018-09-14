package com.example.gopa2000.mobapps;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements MessageSender, DispServiceCallback {

    private String TAG = "MainActivity";
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

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

        /***************** Session Specific ******************/
        activities.add(this);
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

        downloadChats();

        /////////////////////// DBG ///////////////////////////////////////////////////////
        Log.d(TAG, "onCreate: Going to print user details.");
        for(Map.Entry<String, ?> entry : userDetails.entrySet()){
            Log.d("MainActivity map", entry.getKey() + ": " + entry.getValue().toString());
        }
        ///////////////////////////////////////////////////////////////////////////////////
        if(userDetails.get(DbHelper.KEY_TYPE).toString().equals(DbHelper.KEY_SEEKER)) {
            sessionCache.setSessionCards(dbHelper.getListings());
        }
        else {
            sessionCache.setSessionCards(dbHelper.getSeekers());
        }

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
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            socketService = ((SocketListener.LocalBinder) iBinder).getService();
            socketService.dispServicecb(MainActivity.this);

            if(socketService != null){
                Log.i("service-bind", "Service bound successfully!");
                connectToRooms(sessionCache.getSessionMatches());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        activities.remove(this);
        doUnbindService();
    }

    public static void finishAll() {
        for(Activity activity:activities)
            activity.finish();
    }

    private void connectToRooms(ArrayList<Match> matchList){
        for(Match m:matchList) {
            Log.d(TAG, "connectToRooms: " + m.toString());
            String roomID = Match.generateChatroomToken(m.getSeeker(),m.getEmployer());

            JSONObject joinChatObj = new JSONObject();
            try {
                joinChatObj.put("room", "room");
            } catch (JSONException e) {
                Log.e(TAG, "onCreate: ", e);
            }

            Log.d(TAG, "connectToRooms: sending request to join " + roomID);
            socketService.sendMessage("subscribe", joinChatObj);
        }
    }

    private void downloadChats(){
        RequestParams requestParams = new RequestParams();
        RESTClient.get("api/chat/" + userDetails.get(DbHelper.KEY_EMAIL).toString(), requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(TAG, "onSuccess: " + response.toString());
            }
        });
    }

    @Override
    public void displayMatch(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Hey")
                        .setContentText("You matched with someone!")
                        .show();
            }
        });
    };

    @Override
    public void addToLiketable(Like like){

    }
}
