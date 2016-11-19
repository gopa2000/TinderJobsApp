package com.example.gopa2000.mobapps;

import android.content.Context;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Puru on 11/17/2016.
 */

public class CardSwipeAdapterListing extends ArrayAdapter<CustomCard>{
    private static class ViewHolder {
        public TextView fname;
        public TextView lname;
    }

    public CardSwipeAdapterListing(Context context, ArrayList<CustomCard> cards){
        super(context, R.layout.card__layout_listing, cards);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomCard card = getItem(position);

        CardSwipeAdapterListing.ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card__layout_listing, parent, false);

            //viewHolder.fname = (TextView) convertView.findViewById(R.id.textView2);
            //viewHolder.lname = (TextView) convertView.findViewById(R.id.textView3);
        } else {
            viewHolder = (CardSwipeAdapterListing.ViewHolder) convertView.getTag();
        }

        //viewHolder.fname.setText(((SeekerClass)card).getFname());
        //viewHolder.lname.setText(((SeekerClass)card).getLname());

        return convertView;
    }
}
