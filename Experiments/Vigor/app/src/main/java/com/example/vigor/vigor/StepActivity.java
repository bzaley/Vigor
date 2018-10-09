package com.example.vigor.vigor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StepActivity extends AppCompatActivity implements SensorEventListener, genericStepDetection {
    private TextView textView;
    private stepMonitor simpleStepDetector;
    private SensorManager sensorManager;
    private JsonRequest jsonRequest;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private int userID;
    private String TAG = StepActivity.class.getSimpleName();

    TextView TvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new stepMonitor();
        simpleStepDetector.registerListener(this);

        // Create instance of JSON request class
        jsonRequest = new JsonRequest();

        // TODO Determine user's ID
        userID = 1;

        TvSteps = findViewById(R.id.tv_steps);
        Button BtnStart = findViewById(R.id.btn_start);
        Button BtnStop = findViewById(R.id.btn_stop);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        final String dateS = sdf.format(Calendar.getInstance().getTime());
        // final int date = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
        final String serverURL = "http://proj309-ad-07.misc.iastate.edu:8080";
        final String sendJsonURL = serverURL + "/steps/update";
        final String receiveJsonURL = serverURL + "/steps/" + userID + "/" + dateS;

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                        receiveJsonURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            numSteps = response.getInt("steps");
                            TvSteps.setText(TEXT_NUM_STEPS + numSteps);
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

                VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_req");
                sensorManager.registerListener(StepActivity.this, accel,
                        SensorManager.SENSOR_DELAY_FASTEST);
            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sensorManager.unregisterListener(StepActivity.this);
                JSONObject updateData = null;
                try {
                    updateData = jsonRequest.makeStepsJsonObject(userID, numSteps, dateS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                        sendJsonURL, updateData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error:" + error.getMessage());
                    }
                });

                VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_req");
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }
}