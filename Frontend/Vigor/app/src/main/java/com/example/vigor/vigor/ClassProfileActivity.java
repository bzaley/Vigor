package com.example.vigor.vigor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity to showcase a class's general information to an attending user. Also allows for user to
 * save the class's latest associated workout to their workout history.
 *
 * @author Kirkand Keith
 */
public class ClassProfileActivity extends Activity {
    private String TAG = ClassProfileActivity.class.getSimpleName();

    private TextView className;
    private TextView classDescription;
    private TextView classSchedule;
    private TextView billBoard;
    private Button saveBillboardButton;
    private Button contactInstructorButton;
    private EditText alertText;
    private String jsonInstructorURL;
    private String strInstructorName;
    private String strInstructorContact;
    private Bundle receivedData;

    private SessionController session;
    private DateController dateController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classprofile);

        className = findViewById(R.id.classNameTV);
        classDescription = findViewById(R.id.classDescriptionTV);
        classSchedule = findViewById(R.id.classScheduleTV);
        billBoard = findViewById(R.id.billBoardTV);
        saveBillboardButton = findViewById(R.id.saveWorkoutButt);
        contactInstructorButton = findViewById(R.id.contactButt);

        session = new SessionController(getApplicationContext());
        dateController = new DateController();

        receivedData = getIntent().getExtras();
        if (receivedData != null) {
            className.setText(receivedData.getString("className"));
            String descriptText = classDescription.getText().toString() +
                    receivedData.getString("description");
            classDescription.setText(descriptText);
            String scheduleText = classSchedule.getText().toString() +
                    receivedData.getString("schedule") ;
            classSchedule.setText(scheduleText);
            String billboardText = billBoard.getText().toString() +
                    receivedData.getString("billboard");
            billBoard.setText(billboardText);
        }

        contactInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    jsonInstructorURL = "http://proj309-ad-07.misc.iastate.edu:8080/user/" +
                            receivedData.getInt("instructorID");
                    JsonObjectRequest jsonInstructorRequest = new JsonObjectRequest(Request.Method.GET,
                            jsonInstructorURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                strInstructorName = response.getString("firstname") + " "
                                        + response.getString("lastname");
                                strInstructorContact = response.getString("userEmail");
                                AlertDialog.Builder alert = new AlertDialog
                                        .Builder(ClassProfileActivity.this);
                                alert.setTitle("Instructor's Contact Information");
                                alert.setMessage("Instructor: " + strInstructorName + "\nEmail: "
                                        + strInstructorContact);
                                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.create().show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    VolleySingleton.getInstance().addToRequestQueue(jsonInstructorRequest, "instructor_json_req");
            }
        });

        saveBillboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (receivedData.getBoolean("locked")) {
                    AlertDialog.Builder alert = new AlertDialog
                            .Builder(ClassProfileActivity.this);
                    alert.setTitle("Any additional notes for this session?");
                    alertText = new EditText(alert.getContext());
                    alert.setView(alertText);
                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String notes = alertText.getText().toString();
                            JSONObject newBillBoard = makeBillboardJson(session.returnUserID(),
                                    receivedData.getInt("classID"),
                                    dateController.returnWorkingDateAsString(),
                                    receivedData.getString("billboard"), notes);
                            String jsonHistoryURL = "http://proj309-ad-07.misc.iastate.edu:8080/" +
                                    "classHistory/add";
                            JsonObjectRequest jsonHistoryRequest =
                                    new JsonObjectRequest(Request.Method.POST, jsonHistoryURL,
                                            newBillBoard, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            VolleySingleton.getInstance().addToRequestQueue(jsonHistoryRequest,
                                    "history_json_req");
                            dialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Billboard was not saved!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    alert.create().show();
                } else {
                    Toast.makeText(getApplicationContext(), "The class billboard is currently "
                                    + "unavailable. Refresh the page or contact your instructor " +
                                    "for more information.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Creates a JSON that represents the class workout
     *
     * @param userId ID associated with the user who is saving the workout to their history
     * @param classId ID associated with the class hosting the workout
     * @param date The date the workout was added to a user's history
     * @param billBoard The actual workout to be saved
     * @param notes Any notes or comments the user would make on the workout
     * @return JSON Object to be sent to the server for history addition
     */

    public JSONObject makeBillboardJson (int userId, int classId, String date, String billBoard,
                                   String notes) {
        JSONObject returnObject = new JSONObject();
        try {
            returnObject.put("userId", userId);
            returnObject.put("classId", classId);
            returnObject.put("date", date);
            returnObject.put("billBoard", billBoard);
            returnObject.put("notes", notes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnObject;
    }
}
