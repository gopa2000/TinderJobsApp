package com.example.gopa2000.mobapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class ViewEmployerProfile extends AppCompatActivity {

    Map<String, ?> userDetails;
    SessionManager sessionManager;
    TextView viewprofile_cname;
    TextView viewprofile_cinfo;
    TextView viewprofile_email;
    TextView viewprofile_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employer_profile);

        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();

        viewprofile_cname = (TextView) findViewById(R.id.textcard_cname);
        viewprofile_cname.setText(userDetails.get(DbHelper.KEY_NAME).toString());

        viewprofile_cinfo = (TextView) findViewById(R.id.textcard_cinfo);
        String[] cinfo2 = userDetails.get(DbHelper.KEY_INFO).toString().split("_");
        int length = cinfo2.length;
        String cinfo3="";
        for(int i = 0; i<length; i++){
            cinfo3+=cinfo2[i];
            if(i!=(length-1)){
                cinfo3+="\n";
            }
        }
        viewprofile_cinfo.setText(cinfo3);

        viewprofile_email = (TextView) findViewById(R.id.textcard_email);
        viewprofile_email.setText(userDetails.get(DbHelper.KEY_EMAIL).toString());

        viewprofile_mobile = (TextView) findViewById(R.id.textcard_contact);
        viewprofile_mobile.setText(userDetails.get(DbHelper.KEY_MOBNUM).toString());
    }
}
