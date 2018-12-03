package com.example.vigor.vigor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Adrian H
 * This activity allows a user of typw instructor to open and update the
 * information for a classtehy manage
 */
public class ClassUpdaterActivity extends AppCompatActivity {

    private TextView ClassName;
    private TextView ClassID;
    private EditText Billboard;
    private EditText Status;
    private Button Lock;
    private int classIDint;
    private String TAG = ClassUpdaterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_updater);

        ClassName = (TextView) findViewById(R.id.ClassUpdaterTvClassName);
        ClassID = (TextView) findViewById(R.id.ClassUpdaterTvClassID);
        Billboard = (EditText) findViewById(R.id.ClassUpdaterEtBillboard);
        Status = (EditText) findViewById(R.id.ClassUpdaterEtStatus);
        Lock = (Button) findViewById(R.id.ClassUpdaterBtnLock);

        Bundle receivedData = getIntent().getExtras();
        if (receivedData != null) {
            ClassName.setText(receivedData.getString("classname"));
            classIDint = receivedData.getInt("classid");
            ClassID.setText("ID: " + classIDint);
            Billboard.setText(receivedData.getString("billboard"));
            Status.setText(receivedData.getString("status"));
            if (receivedData.getBoolean("locked")) {
                Lock.setText("Unlock");
                Billboard.setEnabled(false);
                Billboard.setClickable(false);
                Status.setEnabled(false);
                Status.setClickable(false);
            } else {
                Lock.setText("Lock");
                Billboard.setEnabled(true);
                Billboard.setClickable(true);
                Status.setEnabled(true);
                Status.setClickable(true);
            }
        }

        Lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Lock.getText().toString().equals("Lock")) {
                    Lock.setText("Unlock");
                    Billboard.setEnabled(false);
                    Billboard.setClickable(false);
                    Status.setEnabled(false);
                    Status.setClickable(false);

                    JSONObject toSend = new JSONObject();
                    try {
                        toSend.put("newBillboard", Billboard.getText().toString());
                        toSend.put("newStatus", Status.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String sendUrl = "http://proj309-ad-07.misc.iastate.edu:8080/classes/padlock/" + classIDint + "/" + true;
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            sendUrl,
                            toSend, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
//                            Toast.makeText(getApplicationContext(), "Correct " + response.toString(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error:" + error.getMessage());
//                            Toast.makeText(getApplicationContext(),error.toString(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_req");

                    Intent intent = new Intent();
                    //Put Billboard
                    intent.putExtra("billboard", Billboard.getText().toString());
                    //Put Status
                    intent.putExtra("status", Status.getText().toString());
                    //Put Lock Status
                    intent.putExtra("locked", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Lock.setText("Lock");
                    Billboard.setEnabled(true);
                    Billboard.setClickable(true);
                    Status.setEnabled(true);
                    Status.setClickable(true);

                    String sendUrl = "http://proj309-ad-07.misc.iastate.edu:8080/classes/padlock/" + classIDint + "/" + false;
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            sendUrl,
                            new JSONObject(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
//                            Toast.makeText(getApplicationContext(),response.toString(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error:" + error.getMessage());
//                            Toast.makeText(getApplicationContext(),error.toString(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_req");
                }
            }
        });
    }
}
