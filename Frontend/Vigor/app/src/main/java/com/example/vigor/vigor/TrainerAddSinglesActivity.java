package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrainerAddSinglesActivity extends AppCompatActivity {

    static ArrayList<DataModel> dataModels;
    private ListView listView;
    private CustomAdapter adapter;

    private EditText activity;
    private EditText sets;
    private EditText reps;

    private Button addToPlan;
    private Button saveItems;

    private String TAG = TrainerAddSinglesActivity.class.getSimpleName();
    private SessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_add_singles);

        session = new SessionController(getApplicationContext());

        activity = (EditText) findViewById(R.id.TrainerAddSingleEtNewItem);
        sets = (EditText) findViewById(R.id.TrainerAddSingleEtNewSets);
        reps = (EditText) findViewById(R.id.TrainerAddSingleEtNewReps);

        addToPlan = (Button) findViewById(R.id.TrainerAddSingleBtnAddItem);
        saveItems = (Button) findViewById(R.id.TrainerAddSingleBtnSaveitems);

        dataModels = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);

        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAddActivity = activity.getText().toString();
                String toAddSets = sets.getText().toString();
                String toAddReps = reps.getText().toString();
                //make sure the activity is entered correctly
                if (toAddActivity.equals("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            TrainerAddSinglesActivity.this);
                    alert.setTitle("No activity entered.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else if (toAddSets.equals("") || toAddReps.equals("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            TrainerAddSinglesActivity.this);
                    alert.setTitle("Amount entered isn't a number.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    dataModels.add(new DataModel(UserTableActivity.UserEmailString, "", toAddActivity, toAddSets, toAddReps));
                    activity.setText("");
                    sets.setText("");
                    reps.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        saveItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        TrainerAddSinglesActivity.this);
                alert.setTitle("Are you sure you'd like to save?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i<dataModels.size(); i++){
                            DataModel temp = dataModels.get(i);
                            JSONObject toSend = new JSONObject();
                            try {
                                toSend.put("userEmail", UserTableActivity.UserEmailString);
                                toSend.put("exercise", temp.getexercise());
                                toSend.put("sets", Integer.parseInt(temp.getsets()));
                                toSend.put("reps", Integer.parseInt(temp.getreps()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                    "http://proj309-ad-07.misc.iastate.edu:8080/dayExercise/addSingle", toSend, new Response.Listener<JSONObject>() {
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
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
}
