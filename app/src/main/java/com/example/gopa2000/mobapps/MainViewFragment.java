package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainViewFragment extends Fragment {

    private final String TAG = "MVFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SessionManager sessionManager;
    private ArrayList<CustomCard> cards;
    private CardAdapter cardAdapter;
    private Button btn;
    private SwipeFlingAdapterView flingContainer;
    private Map<String, ?> userDetails;
    // hax
    private static String userEmail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.from(getContext()).inflate(R.layout.fragment_main_view, container, false);

        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        sessionManager = new SessionManager(getContext());
        cards = SessionCache.getInstance().getSessionCards();


        userDetails = sessionManager.getUserDetails();
        userEmail = userDetails.get(DbHelper.KEY_EMAIL).toString();

        final ArrayAdapter<CustomCard> adapter;
        if(userDetails.get(DbHelper.KEY_TYPE).toString().equals(DbHelper.KEY_SEEKER))
            adapter = new CardSwipeAdapterListing(getActivity(), cards);
        else
            adapter = new CardSwipeAdapter(getActivity(), cards);

        flingContainer.setAdapter(adapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener(){
            @Override
            public void removeFirstObjectInAdapter() {
                cards.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(getActivity(), "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {

                Map<String, ?> userDetails = sessionManager.getUserDetails();

                String Liker, Likee;

                if(userDetails.get(DbHelper.KEY_TYPE).equals(DbHelper.KEY_SEEKER)) {
                    Liker = MainViewFragment.userEmail;
                    Likee = ((JobListingClass)o).getOwnerEmail(getActivity());
                } else {
                    Likee = MainViewFragment.userEmail;
                    Liker = ((SeekerClass)o).getEmail();
                }

                SessionCache sessionCache = SessionCache.getInstance();
                sessionCache.addToLikeTable(Liker, Likee);

                RequestParams rp = new RequestParams();
                rp.put("type", "like");
                rp.put("liker", Liker);
                rp.put("likee", Likee);
                RESTClient.post("api/like", rp, new JsonHttpResponseHandler());

                HashMap<String, String> likeTable = sessionCache.getLikeTable();

                Boolean match = false;
                for(Map.Entry<String, ?> entry : likeTable.entrySet()){
                    if( entry.getKey().equals(Likee) && entry.getValue().toString().equals(Liker)) {
                        match = true;
                        break;
                    }
                }

                if(match){
                    // open match activity
                    new AlertDialog.Builder(getContext())
                            .setTitle("Matched!")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    sessionCache.addToMatchTable(Liker, Likee);
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener(){
            @Override
            public void onItemClicked(int itemPosition, Object dataObject){
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), FullScreenCardLayout.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void CustomCardTest(View view){
/*
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        cardAdapter = new CardAdapter(cards);
        recyclerView.setAdapter(cardAdapter);
*/
    }
}
