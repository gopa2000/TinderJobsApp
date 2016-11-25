package com.example.gopa2000.mobapps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class CreateListing extends AppCompatActivity {

    private SessionManager sessionManager;

    private int hint_edu=0;
    private int hint_skills=0;
    private ArrayList<EditText> listing_jobdesc;
    private ArrayList<EditText> listing_skills;
    private Button btn_jobdesc_add_line;
    private LinearLayout parent_layout_jobdesc;
    private Button btn_skills_add_line;
    private LinearLayout parent_layout_skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        sessionManager = new SessionManager(getApplicationContext());

        listing_jobdesc = new ArrayList<EditText>();
        listing_jobdesc.add((EditText)findViewById(R.id.input_jobdesc));
        listing_skills = new ArrayList<EditText>();
        listing_skills.add((EditText)findViewById(R.id.input_skills));

        btn_jobdesc_add_line=(Button)findViewById(R.id.btn_jobdesc_addline);
        parent_layout_jobdesc = (LinearLayout)findViewById(R.id.p_jobdesc_layout);
        btn_skills_add_line=(Button)findViewById(R.id.btn_skills_addline);
        parent_layout_skills = (LinearLayout)findViewById(R.id.p_skills_layout);

        btn_jobdesc_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewJobDesc();
            }
        });

        btn_skills_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewSkills();
            }
        });

    }

    protected void createEditTextViewJobDesc() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 200;
        hint_edu++;
        //edittTxt.setHint("editText"+hint);
        edittTxt.setLayoutParams(params);
        // edtTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        edittTxt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        edittTxt.setId(hint_edu);
        edittTxt.setTextColor(Color.WHITE);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);
        listing_jobdesc.add(edittTxt);
        parent_layout_jobdesc.addView(edittTxt);
    }

    protected void createEditTextViewSkills() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 200;
        hint_skills++;
        //edittTxt.setHint("editText"+hint);
        edittTxt.setLayoutParams(params);
        // edtTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        edittTxt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        edittTxt.setId(hint_skills);
        edittTxt.setTextColor(Color.WHITE);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);
        listing_skills.add(edittTxt);
        parent_layout_skills.addView(edittTxt);
    }
}
