package com.example.vigor.vigor;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Looper;
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

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class StepActivity extends AppCompatActivity implements SensorEventListener,
        genericStepDetection {
    private stepMonitor stepMonitor;
    private SensorManager sensorManager;
    private JsonRequest jsonRequest;
    private SessionController session;
    private DateController dateController;
    private WebSocketClient socketClient;
    private String workingDate;
    private Sensor Sensor;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private static final String serverURL = "http://proj309-ad-07.misc.iastate.edu:8080";
    private static final String updateStepsURL = serverURL + "/steps/update";
    private int numSteps;
    private int tempSteps;
    private int userID;
    private String TAG = StepActivity.class.getSimpleName();
    private String tempMessage;

    TextView TvSteps;
    TextView TvDate;
    Button BtnStart;
    Button BtnStop;
    Button BtnPrev;
    Button BtnNext;

    EditText EtGoalSteps;
    Button BtnGoals;

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

        // Create instance of JSON request class
        jsonRequest = new JsonRequest();

        // Create instance of userID, allowing it to be called upon later.
        userID = session.returnUserID();

        //websocket link and draft
        String ws = "ws://proj309-ad-07.misc.iastate.edu:8080/websocket/steps/"
                + session.returnUserID();
        Draft[] drafts = {new Draft_6455()};

        // Initialize and start the websocket
        try {
            socketClient = new WebSocketClient(new URI(ws), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Looper.prepare();
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    tempMessage = message;
                    StepActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), tempMessage
                                            + " You have reached your goal for total steps today!" +
                                            " Keep going!", Toast.LENGTH_LONG).show();
                            sendNotification(tempMessage + " You have reached your goal for total" +
                                    " steps today! Keep going!");
                        }
                    });
                    sendNotification(tempMessage + " You have reached your goal for total" +
                            " steps today! Keep going!");
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "onClose() returned: " + s);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage());
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        //Connect websocket
        socketClient.connect();

        // Pair buttons with their given variables.
        TvSteps = findViewById(R.id.tv_steps);
        TvDate = findViewById(R.id.disdate);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);
        BtnPrev = findViewById(R.id.btn_prev);
        BtnNext = findViewById(R.id.btn_next);

        EtGoalSteps = findViewById(R.id.stepsGoalET);
        BtnGoals = findViewById(R.id.btn_goal);

        TvDate.setText(dateController.returnWorkingDateAsString());

        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
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
                        updateStepsURL, updateData, new Response.Listener<JSONObject>() {
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

        BtnGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dateController.setWorkingDateToToday();
                workingDate = dateController.returnWorkingDateAsString();
                String strStepsGoal = EtGoalSteps.getText().toString();
                String goalURL = serverURL + "/steps/updateStepGoal/" + session.returnUserID()
                        + "/" + workingDate + "/" + strStepsGoal;
                JsonObjectRequest goalRequest = new JsonObjectRequest(Request.Method.POST,
                        goalURL, null, new Response.Listener<JSONObject>() {
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
                VolleySingleton.getInstance().addToRequestQueue(goalRequest, "goal_req");
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
        if(numSteps >= (tempSteps + 5)) {
            tempSteps = numSteps;
            JSONObject updateData = null;
            try {
                updateData = jsonRequest.makeStepsJsonObject(userID, numSteps, workingDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                    updateStepsURL, updateData, new Response.Listener<JSONObject>() {
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
            VolleySingleton.getInstance().addToRequestQueue(jsonRequest, "json_mid_req");
        }
    }

    public void sendNotification(String message) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(getApplicationContext()).
                setContentTitle("Vigor").setContentText(message).
                setContentTitle("Step Update!").setSmallIcon(R.drawable.ic_launcher_foreground).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notify);
    }
}