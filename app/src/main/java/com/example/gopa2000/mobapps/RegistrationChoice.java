package com.example.gopa2000.mobapps;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationChoice extends AppCompatActivity {

    private Button seekerBtn;
    private Button employerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice);

        seekerBtn = (Button) findViewById(R.id.seeker_btn);
        seekerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                seekerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_rounded_corners2));
            }
        });

        employerBtn = (Button) findViewById(R.id.employer_btn);
        employerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                employerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_rounded_corners2));
            }
        });
    }
}
