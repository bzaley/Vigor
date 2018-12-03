package com.example.vigor.vigor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity page to list the user's previous class workouts. When a list item is clicked, more
 * detailed information on the activity appears in an AlertDialog.
 *
 * @author Kirkland Keith
 */
public class ClassHistoryActivity extends Activity {
    private String TAG = ClassViewActivity.class.getSimpleName();
    private ListView classHistoryList;
    private ClassWorkoutAdapter adapter;
    private ArrayList<ClassWorkoutDataModel> workoutDataModels;

    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classhistory);

        classHistoryList = findViewById(R.id.listWorkouts);

        session = new SessionController(getApplicationContext());
        workoutDataModels = new ArrayList<>();
        adapter = new ClassWorkoutAdapter(workoutDataModels, getApplicationContext());
        classHistoryList.setAdapter(adapter);

        String jsonURL = "http://proj309-ad-07.misc.iastate.edu:8080/classHistory/getAll/" +
                session.returnUserID();

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject element = response.getJSONObject(i);
                        workoutDataModels.add(new ClassWorkoutDataModel(
                                element.getInt("classId"),
                                element.getString("notes"),
                                element.getString("billBoard"),
                                element.getString("date")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        VolleySingleton.getInstance().addToRequestQueue(jsonArrRequest, "history_json_req");

        classHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassWorkoutDataModel activityValue = (ClassWorkoutDataModel)
                        classHistoryList.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog
                        .Builder(ClassHistoryActivity.this);
                alert.setTitle(activityValue.getClassBillBoard());
                alert.setMessage(activityValue.getClassNotes());
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });
    }
}
