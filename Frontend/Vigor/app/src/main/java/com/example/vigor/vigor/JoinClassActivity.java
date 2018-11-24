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

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class JoinClassActivity extends Activity {
    private String TAG = JoinClassActivity.class.getSimpleName();
    private Button checkClass;
    private EditText classIDCode;
    private String strClassCode;

    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinclass);

        session = new SessionController(getApplicationContext());

        checkClass = findViewById(R.id.checkClassButt);
        classIDCode = findViewById(R.id.etClassCode);

        checkClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strClassCode = classIDCode.getText().toString();
                String jsonURL = "http://proj309-ad-07.misc.iastate.edu:8080/";
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                        jsonURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean error = response.getBoolean("error");
                            if (!error) {
                                Toast.makeText(getApplicationContext(), "Class added " +
                                        "successfully! Bringing you back to class list.",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),
                                        ClassViewActivity.class));
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
                VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_join_req");

            }
        });
    }
}
