package com.example.gopa2000.mobapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Map;

public class FullScreenCardLayoutListing extends AppCompatActivity {

    Map<String, ?> userDetails;
    SessionManager sessionManager;
    TextView viewprofile_cname;
    TextView viewprofile_cinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_card_layout_listing);

    }
}
