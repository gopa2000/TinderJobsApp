package com.example.gopa2000.mobapps;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CreateListing extends AppCompatActivity {

    private static final String TAG = "CreateListingActivity";
    private SessionManager sessionManager;

    private EditText cnameInput;
    private EditText jtitleInput;
    private EditText expInput;
    private ArrayList<EditText> listing_jobdesc;
    private ArrayList<EditText> listing_skills;

    private int hint_edu=0;
    private int hint_skills=0;
    private Button btn_jobdesc_add_line;
    private LinearLayout parent_layout_jobdesc;
    private Button btn_skills_add_line;
    private LinearLayout parent_layout_skills;
    private Button _createListingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        sessionManager = new SessionManager(getApplicationContext());

        cnameInput = (EditText) findViewById(R.id.input_cname);
        jtitleInput = (EditText) findViewById(R.id.input_jobtitle);
        expInput = (EditText) findViewById(R.id.input_expreq);

        listing_jobdesc = new ArrayList<EditText>();
        listing_jobdesc.add((EditText)findViewById(R.id.input_jobdesc));
        listing_skills = new ArrayList<EditText>();
        listing_skills.add((EditText)findViewById(R.id.input_skills));

        btn_jobdesc_add_line=(Button)findViewById(R.id.btn_jobdesc_addline);
        parent_layout_jobdesc = (LinearLayout)findViewById(R.id.p_jobdesc_layout);
        btn_skills_add_line=(Button)findViewById(R.id.btn_skills_addline);
        parent_layout_skills = (LinearLayout)findViewById(R.id.p_skills_layout);
        _createListingButton = (Button) findViewById(R.id.btn_create_listing);

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

        _createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createjob();
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

    public void createjob() {
        Log.d(TAG, "Signup");

        /*if (!validate()) {
            onSignupFailed();
            return;
        }*/

        _createListingButton.setEnabled(false);
        final ProgressDialog progressDialog;

        progressDialog = ProgressDialog.show(this, "", "Creating...", true, false);

        String cname = cnameInput.getText().toString();
        String jtitle = jtitleInput.getText().toString();
        String workexp = expInput.getText().toString();

        String jobdescription ="";
        String skills = "";

        // TODO: 11/19/16 IMPLEMENT TAG AND IMAGE
        String tags = "";

        // done using Util.encodeTobase64(Bitmap img);
        String img = "";

        boolean first = true;
        for(EditText skill : listing_skills){
            if(first) { first = false; skills+="• ";}
            else skills += "_• ";
            skills += skill.getText().toString();
        }

        first = true;
        for(EditText jobdesc : listing_jobdesc){
            if(first) { first = false; jobdescription+="• ";}
            else jobdescription += "_• ";
            jobdescription += jobdesc.getText().toString();
        }

        RequestParams rp = new RequestParams();
        rp.add("compname", cname);
        rp.add("jobTitle", jtitle);
        rp.add("expRequired", workexp);
        rp.add("jobdesc", jobdescription);
        rp.add("tags", "");
        rp.add("skillsReq", skills);
        RESTClient.post("api/listing", rp, new JsonHttpResponseHandler(){

        });
        _createListingButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
        progressDialog.dismiss();
    }

}
