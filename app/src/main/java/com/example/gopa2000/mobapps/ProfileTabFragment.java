package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProfileTabFragment extends Fragment {

    DbHelper dbHelper;
    SessionCache sessionCache;
    Map<String, ?> userDetails;
    protected SocketListener socketService;

    SessionManager sessionManager;
    private TextView textView;
    private Button button;
    private TextView view_matches;
    private TextView create_listing;
    private TextView emp_edit_profile;
    private TextView emp_view_profile;
    private TextView view_profile;
    private TextView edit_profile;
    private TextView profile_tab_name;
    private ImageView profile_tab_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        final ViewPager vp=(ViewPager) getActivity().findViewById(R.id.view_pager);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        Map<String,?> userDetails = sessionManager.getUserDetails();

        TextView logout = (TextView) view.findViewById(R.id.logout);
        profile_tab_name = (TextView) view.findViewById(R.id.profile_tab_name);
        profile_tab_img = (ImageView) view.findViewById(R.id.profile_tab_img);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

        userDetails = sessionManager.getUserDetails();

        if (userDetails.get(DbHelper.KEY_TYPE).toString().equals(DbHelper.KEY_SEEKER)){

            String name = userDetails.get(DbHelper.KEY_FNAME).toString();
            String image = userDetails.get(DbHelper.KEY_IMG).toString();

            profile_tab_name.setText(name);
            //profile_tab_img.setImageBitmap(image);

            view_matches = (TextView) view.findViewById(R.id.empmatches);
            view_matches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vp.setCurrentItem(2);
                }
            });

            //TODO: Remove create listing

            create_listing = (TextView) view.findViewById(R.id.createlisting);
            create_listing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start the Signup activity
                    Intent intent = new Intent(getContext(), CreateListing.class);
                    startActivity(intent);
                }
            });

            view_profile=(TextView) view.findViewById(R.id.empviewprofile);
            view_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start the Signup activity
                    Intent intent = new Intent(getContext(), ViewSeekerProfile.class);
                    startActivity(intent);
                }
            });

            edit_profile=(TextView) view.findViewById(R.id.empeditprofile);
            edit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start the Signup activity
                    Intent intent = new Intent(getContext(), EditSeekerProfile.class);
                    startActivity(intent);
                }
            });
        }else{

            String name = userDetails.get(DbHelper.KEY_NAME).toString();
            String image = userDetails.get(DbHelper.KEY_IMG).toString();

            profile_tab_name.setText(name);
            //profile_tab_img.setImageBitmap(image);

            view_matches = (TextView) view.findViewById(R.id.empmatches);
            view_matches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vp.setCurrentItem(2);
                }
            });

            emp_view_profile = (TextView) view.findViewById(R.id.empviewprofile);
            emp_view_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start the Signup activity
                    Intent intent = new Intent(getContext(), ViewEmployerProfile.class);
                    startActivity(intent);
                }
            });

            emp_edit_profile = (TextView) view.findViewById(R.id.empeditprofile);
            emp_edit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start the Signup activity
                    Intent intent = new Intent(getContext(), EditEmployerProfile.class);
                    startActivity(intent);
                }
            });

            create_listing = (TextView) view.findViewById(R.id.createlisting);
            create_listing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start the Signup activity
                    Intent intent = new Intent(getContext(), CreateListing.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}
