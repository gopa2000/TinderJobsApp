package com.example.gopa2000.mobapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfileTabFragment extends Fragment {

    SessionManager sessionManager;
    private TextView textView;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        HashMap<String,String> userDetails = sessionManager.getUserDetails();

        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(userDetails.get(SessionManager.KEY_EMAIL));

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sessionManager.logoutUser();
            }
        });

        return view;
    }
}
