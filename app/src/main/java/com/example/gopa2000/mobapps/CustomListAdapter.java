package com.example.gopa2000.mobapps;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Created by gopa2000 on 11/25/16.
 */

public class CustomListAdapter<String> extends ArrayAdapter<String> {
    ArrayList<String> items;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public int getPosition(String item) {
        // TODO Auto-generated method stub
        return super.getPosition(item);
    }

    public void addItem(String s){
        items.add(s);
    }

    public CustomListAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
        super(context, textViewResourceId, items);
    }

}
