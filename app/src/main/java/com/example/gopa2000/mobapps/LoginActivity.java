package com.example.gopa2000.mobapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText emailInput;
    private EditText passwordInput;
    private TextView registrationLink;
    private Button loginBtn;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        emailInput = (EditText) findViewById(R.id.input_email);
        passwordInput = (EditText) findViewById(R.id.input_password);
        registrationLink = (TextView) findViewById(R.id.link_signup);
        loginBtn = (Button) findViewById(R.id.btn_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegistrationChoice.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginBtn.setEnabled(false);
        final ProgressDialog progressDialog;

        progressDialog = ProgressDialog.show(this, "", "Authenticating...", true, false);

        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        boolean flag = false;

        // for testing purposes
        if (email.equals("gaand@danda.com")) {
            try {
                sessionManager.createLoginSession(new JSONObject("{email: gaand@danda.com}"));
                onLoginSuccess();
            } catch (JSONException e) {
                Log.e(TAG, "login: ", e);
            }
        } else {

            RequestParams rp = new RequestParams();
            rp.add("email", email);
            rp.add("password", password);

            Log.i(TAG, "run: " + email + "," + password);

            // callback in the JsonHttpResponseHandler object to implement authentication logic
            RESTClient.post("login/", rp, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String reqResult = response.getString("success");
                        if (reqResult.equals("true")) {
                            sessionManager.createLoginSession(response);

                            Log.i(TAG, "onSuccess: " + response.toString());
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
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
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        Log.e(TAG, "onSuccess: " + e.getStackTrace().toString());
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginBtn.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
        loginBtn.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("enter a valid email address");
            valid = false;
        } else {
            emailInput.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordInput.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        return valid;
    }
}
