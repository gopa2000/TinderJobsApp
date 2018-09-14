package com.example.gopa2000.mobapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class FullScreenCardLayout extends AppCompatActivity {

    Map<String, ?> userDetails;
    SessionManager sessionManager;
    TextView viewprofile_fname;
    TextView viewprofile_lname;
    TextView viewprofile_exp;
    TextView viewprofile_edu;
    TextView viewprofile_skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_card_layout);

        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();

        viewprofile_fname = (TextView) findViewById(R.id.textcard_fname);
        viewprofile_fname.setText(userDetails.get(DbHelper.KEY_FNAME).toString());

        viewprofile_lname = (TextView) findViewById(R.id.textcard_lname);
        viewprofile_lname.setText(userDetails.get(DbHelper.KEY_LNAME).toString());

        viewprofile_exp = (TextView) findViewById(R.id.textcard_exp);
        String[] workexperience2 = userDetails.get(DbHelper.KEY_WORKEXP).toString().split("_");
        int length = workexperience2.length;
        String workexperience3="";
        for(int i = 0; i<length; i++){
            workexperience3+=workexperience2[i];
            if(i!=(length-1)){
                workexperience3+="\n";
            }
        }
        viewprofile_exp.setText(workexperience3);

        viewprofile_edu = (TextView) findViewById(R.id.textcard_edu);
        String[] education2 = userDetails.get(DbHelper.KEY_EDUCATION).toString().split("_");
        length = education2.length;
        String education3="";
        for(int i = 0; i<length; i++){
            education3+=education2[i];
            if(i!=(length-1)){
                education3+="\n";
            }
        }
        viewprofile_edu.setText(education3);

        viewprofile_skills = (TextView) findViewById(R.id.textcard_skills);
        String[] skills2 = userDetails.get(DbHelper.KEY_SKILLS).toString().split("_");
        length = skills2.length;
        String skills3="";
        for(int i = 0; i<length; i++){
            skills3+=skills2[i];
            if(i!=(length-1)){
                skills3+="\n";
            }
        }
        viewprofile_skills.setText(skills3);
    }
}
