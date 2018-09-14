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
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
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

    private MessageSender messageSenderCallback;

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
                    Liker = userDetails.get(DbHelper.KEY_EMAIL).toString();
                    Likee = ((JobListingClass)o).getOwnerEmail(getActivity());
                } else {
                    Liker = userDetails.get(DbHelper.KEY_EMAIL).toString();
                    Likee = ((SeekerClass)o).getEmail();
                }

                SessionCache sessionCache = SessionCache.getInstance();
                sessionCache.addToLikeTable(Liker, Likee);

                RequestParams rp = new RequestParams();
                rp.put("type", "like");
                rp.put("liker", Liker);
                rp.put("likee", Likee);
                RESTClient.post("api/like", rp, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                    }
                });

                ArrayList<Like> likeTable = sessionCache.getLikeTable();

                Boolean match = false;
                for(Like entry : likeTable){
                    if( entry.getLikee().equals(Liker) && entry.getLiker().equals(Likee) ) {
                        match = true;
                        break;
                    }
                }

                // for testing
                // match = true;

                if(match){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Hey")
                            .setContentText("You matched with someone!")
                            .show();

                    JSONObject json = new JSONObject();
                    RequestParams rpMatch = new RequestParams();

                    try {
                        if (userDetails.get(DbHelper.KEY_TYPE).equals(DbHelper.KEY_SEEKER)) {
                            json.put("seeker", Liker);
                            json.put("employer", Likee);
                            rpMatch.put("seeker", Liker);
                            rpMatch.put("employer", Likee);
                        } else {
                            json.put("seeker", Likee);
                            json.put("employer", Liker);
                            rpMatch.put("seeker", Liker);
                            rpMatch.put("employer", Likee);
                        }
                    } catch (JSONException e){
                        Log.e(TAG, "onRightCardExit: ", e);
                    }

                    messageSenderCallback.sendMessage("match", json);
                    sessionCache.addToMatchTable(Liker, Likee, userEmail);

                    RESTClient.post("api/matches", rpMatch, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(TAG, "onSuccess: " + response.toString());
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            Log.d(TAG, "onSuccess: " + response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d(TAG, "onFailure: " + errorResponse.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d(TAG, "onFailure: " + errorResponse.toString());
                        }
                    });

                    ListView list = (ListView) getActivity().findViewById(R.id.matches_lv);
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) list.getAdapter();

                    adapter.add(Likee);
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            messageSenderCallback = (MessageSender) context;
        } catch(ClassCastException e) {
            Log.e(TAG, "onAttach: ", e);
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        messageSenderCallback = null;
    }
}
