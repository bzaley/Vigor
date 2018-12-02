package com.example.vigor.vigor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends Activity {
    private String TAG = LoginActivity.class.getSimpleName();
    private String loginURL = "http://proj309-ad-07.misc.iastate.edu:8080/user/login";
    private String registerURL = "http://proj309-ad-07.misc.iastate.edu:8080/user/signup";
    private String strEmail;
    private String strPass;
    private Button loginButton;
    private Button registerButton;
    private EditText eMail;
    private EditText passWord;
    private SessionController session;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleSignInButton;
    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButt);
        eMail = findViewById(R.id.etUser);
        passWord = findViewById(R.id.etPass);
        registerButton = findViewById(R.id.registerButt);

        session = new SessionController(getApplicationContext());

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton = findViewById(R.id.sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

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
                            Toast.makeText(getApplicationContext(), "Error: " +
                                            error.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String email  = account.getEmail();
                String lastName = account.getFamilyName();
                String firstName = account.getGivenName();
                String googleID = account.getId();
                checkGoogleLogIn(email, lastName, firstName, googleID);
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkGoogleLogIn(final String email, final String lastName, final String firstName, final String googleID) throws JSONException {
        String jsonCheckURL = loginURL;
        JsonObjectRequest jsonGoogleCheckRequest = new JsonObjectRequest(Request.Method.POST,
                jsonCheckURL, makeLoginJsonObject(email, googleID),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Boolean error = null;
                        try {
                            error = response.getBoolean("error");
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
                                requestForUserRole(email, lastName, firstName, googleID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addToRequestQueue(jsonGoogleCheckRequest, "google_reg_req");
    }

    public void requestForUserRole(final String email, final String lastName, final String firstName,
                                   final String googleID) {
        AlertDialog.Builder alert = new AlertDialog
                .Builder(LoginActivity.this);
        alert.setTitle("Please enter your preferred role!");
        final String[] items = {"trainee", "personaltrainer", "instructor"};
        alert.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JsonObjectRequest jsonRequest = null;
                        try {
                            jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                    registerURL, makeRegisterJsonObject(firstName, lastName, email,
                                    googleID, Arrays.asList(items).get(which)),
                                    new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        boolean error = response.getBoolean("error");
                                        if (!error) {
                                            Toast.makeText(getApplicationContext(), "Role " +
                                                            "has been selected, logging in!",
                                                    Toast.LENGTH_LONG).show();

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
                                    VolleyLog.d(TAG, "Error:" + error.getMessage());
                                    Toast.makeText(getApplicationContext(), "Error: " +
                                            error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "reg_req");
                    }
                });
        alert.create().show();
    }

    public JSONObject makeRegisterJsonObject(String firstName, String lastName, String email,
                                             String password, String role) throws JSONException {
        JSONObject returnObject = new JSONObject();
        try {
            returnObject.put("userEmail", email);
            returnObject.put("firstname", firstName);
            returnObject.put("lastname", lastName);
            returnObject.put("password", password);
            returnObject.put("role", role);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnObject;
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
