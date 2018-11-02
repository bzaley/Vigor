package com.example.vigor.vigor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    private String TAG = LoginActivity.class.getSimpleName();
    private String loginURL = "https://proj309-ad-07.misc.iastate.edu:8080/login";
    private String strEmail;
    private String strPass;
    private Button loginButton;
    private Button registerButton;
    private EditText eMail;
    private EditText passWord;
    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButt);
        eMail = findViewById(R.id.etUser);
        passWord = findViewById(R.id.etPass);
        registerButton = findViewById(R.id.registerButt);

        session = new SessionController(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                strEmail = eMail.getText().toString();
                strPass = passWord.getText().toString();
                JSONObject loginInfo = null;
                if (!strEmail.isEmpty() && !strPass.isEmpty()){
                    try {
                        loginInfo = makeLoginJsonObject(strEmail, strPass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            loginURL, loginInfo, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean error = response.getBoolean("error");
                                if (!error) {
                                    session.attemptLogin(true,
                                            response.getInt("userId"),
                                            response.getString("userEmail"),
                                            response.getString("firstname"),
                                            response.getString("lastname"),
                                            response.getString("role"));
                                    startActivity(new Intent(LoginActivity.this,
                                            MainActivity.class));
                                    finish();
                                } else {
                                    String errorReceived = response.getString("errorMsg");
                                    Toast.makeText(getApplicationContext(), errorReceived,
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d( TAG, "Error:" + error.getMessage());
                        }
                    });
                    VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "login_req");
                }
                else {
                    if (strEmail.isEmpty() && strPass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter your " +
                                "credentials!", Toast.LENGTH_SHORT).show();
                    } else if (strEmail.isEmpty() && !strPass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter your email!",
                                Toast.LENGTH_SHORT).show();
                    } else if (!strEmail.isEmpty() && strPass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter your password!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }

    public JSONObject makeLoginJsonObject(String email, String password) throws JSONException {
        JSONObject objToSend = new JSONObject();
        try {
            objToSend.put("email", email);
            objToSend.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objToSend;
    }
}
