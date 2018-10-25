package com.example.vigor.vigor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private String strEmail;
    private String strPass;
    private Button loginButton;
    private Button registerButton;
    private EditText eMail;
    private EditText passWord;
    private SessionController session;
    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login);
        eMail = findViewById(R.id.etUser);
        passWord = findViewById(R.id.etPass);
        registerButton = findViewById(R.id.register);

        session = new SessionController(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                strEmail = eMail.getText().toString();
                strPass = passWord.getText().toString();
                if (!strEmail.isEmpty() && !strPass.isEmpty()){
                    try {
                        JSONObject loginInfo = makeLoginJsonObject(strEmail, strPass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            loginURL, loginInfo, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // TODO Figure out what needs to be done on response.
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error:" + error.getMessage());
                        }
                    })
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
