package com.example.gopa2000.mobapps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/13/16.
 */

public class CardSwipeAdapter extends ArrayAdapter<CustomCard>{
    private static class ViewHolder {
        public TextView fname;
        public TextView lname;
    }

    public CardSwipeAdapter(Context context, ArrayList<CustomCard> cards){
        super(context, R.layout.card_layout, cards);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomCard card = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_layout, parent, false);

            viewHolder.fname = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.lname = (TextView) convertView.findViewById(R.id.textView3);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.fname.setText(((SeekerClass)card).getFname());
        viewHolder.lname.setText(((SeekerClass)card).getLname());

        return convertView;
    }
}
