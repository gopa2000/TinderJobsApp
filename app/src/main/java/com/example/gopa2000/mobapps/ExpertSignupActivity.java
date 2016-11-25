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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ExpertSignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private SessionManager sessionManager;

    private EditText cnameInput;
    private EditText _emailText;
    private EditText _mobileText;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private Button _signupButton;
    private TextView _loginLink;

    private Button btn_info_add_line;
    private LinearLayout parent_layout_info;
    private int hint_info=0;
    private ArrayList<EditText> comp_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_signup);

        btn_info_add_line=(Button)findViewById(R.id.btn_info_addline);
        parent_layout_info = (LinearLayout)findViewById(R.id.p_info_layout);
        cnameInput = (EditText) findViewById(R.id.input_cname);
        _emailText = (EditText) findViewById(R.id.input_email);
        _mobileText = (EditText) findViewById(R.id.input_mobile);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        sessionManager = new SessionManager(getApplicationContext());

        comp_info = new ArrayList<EditText>();
        comp_info.add((EditText)findViewById(R.id.input_cinfo));

        btn_info_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewInfo();
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    protected void createEditTextViewInfo() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 200;
        hint_info++;
        //edittTxt.setHint("editText"+hint);
        edittTxt.setLayoutParams(params);
        // edtTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        edittTxt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        edittTxt.setId(hint_info);
        edittTxt.setTextColor(Color.WHITE);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);
        comp_info.add(edittTxt);
        parent_layout_info.addView(edittTxt);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        /*
        if (!validate()) {
            onSignupFailed();
            return;
        }*/

        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog;

        progressDialog = ProgressDialog.show(this, "", "Authenticating...", true, false);

        String cname = cnameInput.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();

        String compinfo = "";

        // done using Util.encodeTobase64(Bitmap img);
        String img = "";


        boolean first = true;
        for(EditText cinfo : comp_info){
            if(first) { first = false; compinfo+="• ";}
            else compinfo += "_• ";
            compinfo += cinfo.getText().toString();
        }

        RequestParams rp = new RequestParams();
        rp.add("name", cname);
        rp.add("info", compinfo);
        rp.add("mobnum", mobile);
        rp.add("email", email);
        rp.add("password", password);
        rp.add("img", img);

        RESTClient.post("api/employer", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String reqResult = response.getString("success");
                    Log.d(TAG, "onSuccess: " + response.toString());
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

    @Override
    public void onBackPressed() {
    }

    public boolean validate() {
        boolean valid = true;

        String name = cnameInput.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            cnameInput.setError("at least 3 characters");
            valid = false;
        } else {
            cnameInput.setError(null);
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
