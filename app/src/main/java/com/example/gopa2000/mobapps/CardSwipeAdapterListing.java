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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Puru on 11/17/2016.
 */

public class CardSwipeAdapterListing extends ArrayAdapter<CustomCard>{
    private static class ViewHolder {
        public TextView cname;
        public TextView jobtitle;
        public TextView industry;
        public TextView expreq;
        public TextView jobdesc;
        public TextView skillsreq;
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

            viewHolder.cname = (TextView) convertView.findViewById(R.id.textcard_cname);
            viewHolder.jobtitle = (TextView) convertView.findViewById(R.id.textcard_jobtitle);
            viewHolder.industry = (TextView) convertView.findViewById(R.id.textcard_industry);
            viewHolder.expreq = (TextView) convertView.findViewById(R.id.textcard_expreq);
            viewHolder.jobdesc = (TextView) convertView.findViewById(R.id.textcard_jobdesc);
            viewHolder.skillsreq = (TextView) convertView.findViewById(R.id.textcard_skillsreq);

        } else {
            viewHolder = (CardSwipeAdapterListing.ViewHolder) convertView.getTag();
        }

        viewHolder.cname.setText(((JobListingClass) card).getCompanyName());
        viewHolder.jobtitle.setText(((JobListingClass)card).getJobTitle());
        viewHolder.industry.setText(((JobListingClass)card).getTags());
        viewHolder.expreq.setText(((JobListingClass) card).getExpRequired());
        viewHolder.jobdesc.setText(((JobListingClass) card).getJobDescription());
        viewHolder.skillsreq.setText(((JobListingClass) card).getSkillsRequired());

        return convertView;
    }
}
