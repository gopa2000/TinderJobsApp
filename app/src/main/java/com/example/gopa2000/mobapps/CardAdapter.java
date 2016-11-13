package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by gopa2000 on 11/13/16.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final String TAG = "CardAdapter";

    private ArrayList<CustomCard> cards;

    public CardAdapter(ArrayList<CustomCard> cards){
        super();
        this.cards = cards;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        // open profile view
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: Card clicked.");
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
        // add content to card fields
        CustomCard profile = cards.get(position);
        holder.fname.setText(((SeekerClass)profile).getFname());
        holder.lname.setText(((SeekerClass)profile).getLname());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fname;
        public TextView lname;

        public ViewHolder(View view){
            super(view);
            fname = (TextView) view.findViewById(R.id.textView2);
            lname = (TextView) view.findViewById(R.id.textView3);
        }
    }
}
