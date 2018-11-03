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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StepActivity extends AppCompatActivity implements SensorEventListener,
        genericStepDetection {
    private stepMonitor stepMonitor;
    private SensorManager sensorManager;
    private JsonRequest jsonRequest;
    private SessionController session;
    private DateController dateController;
    private String workingDate;
    private Sensor Sensor;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private int tempSteps;
    private int userID;
    private int dateI;
    private Date currentDate;
    private String TAG = StepActivity.class.getSimpleName();

    TextView TvSteps;
    TextView TvDate;
    Button BtnStart;
    Button BtnStop;
    Button BtnPrev;
    Button BtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        stepMonitor = new stepMonitor();
        stepMonitor.registerListener(this);

        session = new SessionController(getApplicationContext());

        dateController = new DateController();
        currentDate = dateController.dateOfToday();

        // Create instance of JSON request class
        jsonRequest = new JsonRequest();

        // Create instance of userID, allowing it to be called upon later.
        userID = session.returnUserID();

        // Pair buttons with their given variables.
        TvSteps = findViewById(R.id.tv_steps);
        TvDate = findViewById(R.id.disdate);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);
        BtnPrev = findViewById(R.id.btn_prev);
        BtnNext = findViewById(R.id.btn_next);

        // TODO properly handle dates later.
        // SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        // final String dateS = sdf.format(Calendar.getInstance().getTime());
        // dateI = Integer.parseInt(dateS);
        TvDate.setText(dateController.returnWorkingDateAsString());

        final String serverURL = "http://proj309-ad-07.misc.iastate.edu:8080";
        final String sendJsonURL = serverURL + "/steps/update";

        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // currentDate = currentDate - 1;
                dateController.subtractDay();
                workingDate = dateController.returnWorkingDateAsString();
                String testURL = serverURL + "/steps/" + userID + "/" + workingDate;
                JsonObjectRequest prevRequest = new JsonObjectRequest(Request.Method.GET,
                        testURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            numSteps = response.getInt("steps");
                            TvDate.setText("" + workingDate);
                            TvSteps.setText(TEXT_NUM_STEPS + numSteps);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error:" + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

                VolleySingleton.getInstance().addToRequestQueue(prevRequest, "steps_req");
            }
        });

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                dateController.addDay();
                workingDate = dateController.returnWorkingDateAsString();
                String testURL = serverURL + "/steps/" + userID + "/" + workingDate;
                JsonObjectRequest nextRequest = new JsonObjectRequest(Request.Method.GET,
                        testURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            numSteps = response.getInt("steps");
                            TvDate.setText("" + workingDate);
                            TvSteps.setText(TEXT_NUM_STEPS + numSteps);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error:" + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
                VolleySingleton.getInstance().addToRequestQueue(nextRequest, "steps_req");
            }
        });

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dateController.setWorkingDateToToday();
                workingDate = dateController.returnWorkingDateAsString();
                String receiveJsonURL = serverURL + "/steps/" + userID + "/" + workingDate;

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                        receiveJsonURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            numSteps = response.getInt("steps");
                            tempSteps = numSteps;
                            TvDate.setText(workingDate);
                            TvSteps.setText(TEXT_NUM_STEPS + numSteps);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error:" + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

                VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "steps_req");
                sensorManager.registerListener(StepActivity.this, Sensor,
                        SensorManager.SENSOR_DELAY_FASTEST);
            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sensorManager.unregisterListener(StepActivity.this);
                JSONObject updateData = null;
                try {
                    updateData = jsonRequest.makeStepsJsonObject(userID, numSteps, workingDate);
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
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
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
            stepMonitor.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }
}