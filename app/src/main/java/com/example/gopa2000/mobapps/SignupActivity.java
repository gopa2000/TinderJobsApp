package com.example.gopa2000.mobapps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
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

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private SessionManager sessionManager;

    private EditText fnameInput;
    private EditText lnameInput;
    private EditText _emailText;
    private EditText _mobileText;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private Button _signupButton;
    private TextView _loginLink;
    private EditText eduInput;

    private Button btn_edu_add_line;
    private LinearLayout parent_layout_edu;
    private Button btn_exp_add_line;
    private LinearLayout parent_layout_exp;
    private Button btn_skills_add_line;
    private LinearLayout parent_layout_skills;
    private int hint_edu=0;
    private int hint_exp=0;
    private int hint_skills=0;
    private ArrayList<EditText> seeker_edu;
    private ArrayList<EditText> seeker_exp;
    private ArrayList<EditText> seeker_skills;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sessionManager = new SessionManager(getApplicationContext());

        fnameInput = (EditText) findViewById(R.id.input_fname);
        lnameInput = (EditText) findViewById(R.id.input_lname);
        _emailText = (EditText) findViewById(R.id.input_email);
        _mobileText = (EditText) findViewById(R.id.input_mobile);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        eduInput = (EditText) findViewById(R.id.input_education);

        btn_edu_add_line=(Button)findViewById(R.id.btn_edu_addline);
        parent_layout_edu = (LinearLayout)findViewById(R.id.p_edu_layout);
        btn_exp_add_line=(Button)findViewById(R.id.btn_exp_addline);
        parent_layout_exp = (LinearLayout)findViewById(R.id.p_exp_layout);
        btn_skills_add_line=(Button)findViewById(R.id.btn_skills_addline);
        parent_layout_skills = (LinearLayout)findViewById(R.id.p_skills_layout);

        seeker_edu = new ArrayList<EditText>();
        seeker_edu.add((EditText)findViewById(R.id.input_education));
        seeker_exp = new ArrayList<EditText>();
        seeker_exp.add((EditText)findViewById(R.id.input_work_exp));
        seeker_skills = new ArrayList<EditText>();
        seeker_skills.add((EditText)findViewById(R.id.input_skills));

        btn_edu_add_line.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewEdu();
            }
        });

        btn_exp_add_line.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewExp();
            }
        });

        btn_skills_add_line.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewSkills();
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
/*
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
    }

    protected void createEditTextViewEdu() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 5;
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
        seeker_edu.add(edittTxt);
        parent_layout_edu.addView(edittTxt);
    }

    protected void createEditTextViewExp() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 5;
        hint_exp++;
        //edittTxt.setHint("editText"+hint);
        edittTxt.setLayoutParams(params);
        // edtTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        edittTxt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        edittTxt.setId(hint_exp);
        edittTxt.setTextColor(Color.WHITE);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);
        seeker_exp.add(edittTxt);
        parent_layout_exp.addView(edittTxt);
    }

    protected void createEditTextViewSkills() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 5;
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
        seeker_skills.add(edittTxt);
        parent_layout_skills.addView(edittTxt);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        /*if (!validate()) {
            onSignupFailed();
            return;
        }*/

        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog;

        progressDialog = ProgressDialog.show(this, "", "Authenticating...", true, false);

        String fname = fnameInput.getText().toString();
        String lname = lnameInput.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String education = eduInput.getText().toString();

        String skills = "[";
        String workExp = "[";

        // TODO: 11/19/16 IMPLEMENT TAG AND IMAGE
        String tags = "";

        // done using Util.encodeTobase64(Bitmap img);
        String img = "";


        boolean first = true;
        for(EditText skill : seeker_skills){
            if(first) { first = false; }
            else skills += ",";
            skills += skill.getText().toString();
        }
        skills += "]";

        first = true;
        for(EditText work : seeker_exp) {
            if(first) { first = false; }
            else workExp += ",";

            workExp += work.getText().toString();
        }
        workExp += "]";

        RequestParams rp = new RequestParams();
        rp.add("salut", "");
        rp.add("fname", fname);
        rp.add("lname", lname);
        rp.add("img", "");
        rp.add("education", education);
        rp.add("workexp", workExp);
        rp.add("skills", skills);
        rp.add("mobnum", mobile);
        rp.add("email", email);
        rp.add("password", password);
        rp.add("tags", tags);

        RESTClient.post("api/seeker", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String reqResult = response.getString("success");
                    if (reqResult.equals("true")) {
                        sessionManager.createLoginSession(response);

                        Log.i(TAG, "onSuccess: " + response.toString());
                        onSignupSuccess();
                    } else {
                        onSignupFailed();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: " + e.getStackTrace().toString());
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseArray) {
                try {
                    JSONObject response = responseArray.getJSONObject(0);

                    String reqResult = response.getString("success");
                    if (reqResult.equals("true")) {
                        sessionManager.createLoginSession(response);

                        Log.i(TAG, "onSuccess: " + response.toString());
                        onSignupSuccess();
                    } else {
                        onSignupFailed();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: " + e.getStackTrace().toString());
                }
            }
        });

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = fnameInput.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            fnameInput.setError("at least 3 characters");
            valid = false;
        } else {
            fnameInput.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
