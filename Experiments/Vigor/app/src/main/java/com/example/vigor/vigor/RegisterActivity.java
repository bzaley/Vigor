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

public class RegisterActivity extends Activity {
    private String TAG = RegisterActivity.class.getSimpleName();
    private String registerURL = "https://proj309-ad-07.misc.iastate.edu:8080/register";
    private EditText registerFirstName;
    private EditText registerLastName;
    private EditText registerEmail;
    private EditText registerPass;
    private EditText confirmPass;
    private Button registerButton;
    private Button switchToLoginButton;
    private SessionController session;

    // String fields to be sent to server
    private String strRegFirstName;
    private String strRegLastName;
    private String strRegEmail;
    private String strRegPass;
    private String strConfirmPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFirstName = findViewById(R.id.firstNameET);
        registerLastName = findViewById(R.id.lastNameET);
        registerEmail = findViewById(R.id.emailET);
        registerPass = findViewById(R.id.passET);
        confirmPass = findViewById(R.id.confirmET);
        registerButton = findViewById(R.id.regButton);
        switchToLoginButton = findViewById(R.id.logButton);

        session = new SessionController(getApplicationContext());

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                strRegFirstName = registerFirstName.getText().toString();
                strRegLastName = registerLastName.getText().toString();
                strRegEmail = registerEmail.getText().toString();
                strRegPass = registerPass.getText().toString();
                strConfirmPass = confirmPass.getText().toString();
                JSONObject registerInfo = null;

                if (!strRegFirstName.isEmpty() && !strRegLastName.isEmpty()
                        && !strRegEmail.isEmpty() && !strRegPass.isEmpty()
                        && !strConfirmPass.isEmpty()) {
                    try {
                        if (strRegPass.equals(strConfirmPass)) {
                            registerInfo = makeRegisterJsonObject(strRegFirstName, strRegLastName,
                                    strRegEmail, strRegPass);
                        } else {
                            Toast.makeText(getApplicationContext(), "Your password fields" +
                                            " do not match",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            registerURL, registerInfo, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean error = response.getBoolean("error");
                                if (!error) {
                                    Toast.makeText(getApplicationContext(), "New user has " +
                                                    "been registered. Login to view your account!",
                                            Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(RegisterActivity.this,
                                            LoginActivity.class));
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
                            VolleyLog.d(TAG, "Error:" + error.getMessage());
                        }
                    });
                    VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "reg_req");
                } else {
                    Toast.makeText(getApplication(), "Please enter the credentials in the " +
                            "above fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

        switchToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public JSONObject makeRegisterJsonObject(String firstName, String lastName, String email,
                                             String password) throws JSONException {
        JSONObject returnObject = new JSONObject();
        try {
            returnObject.put("first name", firstName);
            returnObject.put("last name", lastName);
            returnObject.put("email", email);
            returnObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnObject;
    }
}
