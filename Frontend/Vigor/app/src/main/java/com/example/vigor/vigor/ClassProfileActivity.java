package com.example.vigor.vigor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassProfileActivity extends Activity {
    private String TAG = ClassProfileActivity.class.getSimpleName();

    private TextView className;
    private TextView instructorName;
    private TextView classDescription;
    private TextView classSchedule;
    private TextView billBoard;
    private Button saveBillboardButton;
    private Button contactInstructorButton;

    private String classIDForURL;
    private ClassDataModel classModel;
    private String jsonClassURL = "http://proj309-ad-07.misc.iastate.edu:8080/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classprofile);

        className = findViewById(R.id.classNameTV);
        instructorName = findViewById(R.id.instructorNameTV);
        classDescription = findViewById(R.id.classDescriptionTV);
        classSchedule = findViewById(R.id.classScheduleTV);
        billBoard = findViewById(R.id.billBoardTV);
        saveBillboardButton = findViewById(R.id.saveWorkoutButt);
        contactInstructorButton = findViewById(R.id.contactButt);

        classIDForURL = getIntent().getStringExtra("targetClassID");

        JsonObjectRequest jsonClassRequest  = new JsonObjectRequest(Request.Method.GET, jsonClassURL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    classModel = new ClassDataModel(
                            response.getInt("classId"),
                            response.getString("className"),
                            response.getInt("instructorId"),
                            response.getString("classDescription"),
                            response.getString("schedule"),
                            response.getString("status"),
                            response.getString("billboard"),
                            response.getBoolean("locked"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addToRequestQueue(jsonClassRequest, "class_json_req");

        className.setText(classModel.getClassName());
        classDescription.setText(classModel.getClassDescription());
        classSchedule.setText(classSchedule.getText().toString() + classModel.getSchedule());
        billBoard.setText(billBoard.getText().toString() + classModel.getBillboard());

    }
}
