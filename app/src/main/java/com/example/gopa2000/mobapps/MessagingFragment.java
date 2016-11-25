package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MessagingFragment extends Fragment {

    private static String TAG = "MessagingFragment ";

    private ListView listview;
    private SessionManager sessionManager;
    private SessionCache sessionCache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messaging, container, false);
        listview = (ListView) v.findViewById(R.id.matches_lv);

        sessionManager = new SessionManager(getContext());
        sessionCache = SessionCache.getInstance();

        ArrayList<Match> matchList = sessionCache.getSessionMatches();
        ArrayList<String> viewList = new ArrayList<>();

        final Map userDetails = sessionManager.getUserDetails();

        if(userDetails.get(DbHelper.KEY_TYPE).equals("seeker")){
            for(Match m:matchList){
                viewList.add(m.getEmployer());
                Log.d(TAG, "onCreateView: ");
            }
        }

        else if (userDetails.get(DbHelper.KEY_TYPE).equals("employer")){
            for(Match m:matchList){
                viewList.add(m.getSeeker());
            }
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                viewList
        );

        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayAdapter.notifyDataSetChanged();

                String recEmail = arrayAdapter.getItem(i);
                String curEmail = userDetails.get(DbHelper.KEY_EMAIL).toString();

                String room;
                if(userDetails.get(DbHelper.KEY_TYPE).toString().equals("SEEKER"))
                    room = Match.generateChatroomToken(curEmail, recEmail);
                else
                    room = Match.generateChatroomToken(recEmail, curEmail);

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("room", room);

                startActivity(intent);
            }
        });

        return v;
    }
}
