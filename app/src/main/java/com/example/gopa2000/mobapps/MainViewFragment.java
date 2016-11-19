package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class MainViewFragment extends Fragment {

    private final String TAG = "MVFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SessionCache sessionCache;
    private ArrayList<CustomCard> cards;
    private CardAdapter cardAdapter;
    private Button btn;
    private SwipeFlingAdapterView flingContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.from(getContext()).inflate(R.layout.fragment_main_view, container, false);

        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        sessionCache = SessionCache.getInstance();
        cards = sessionCache.getSessionCards();

        // test card layout
        //CustomCardTest(view);


        final CardSwipeAdapter adapter = new CardSwipeAdapter(getActivity(), cards);
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
                Toast.makeText(getActivity(), "Right!", Toast.LENGTH_SHORT).show();
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
