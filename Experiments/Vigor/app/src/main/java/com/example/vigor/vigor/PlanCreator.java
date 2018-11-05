package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlanCreator extends AppCompatActivity {

    private EditText activity;
    private EditText sets;
    private EditText reps;
    private Button savePlan;
    private Button addToPlan;
    private Button prev;
    private Button next;
    private TextView Day;
    private ListView listView;
    private static CustomAdapter adapter;
    public static int index = 0;

    private String TAG = PlanCreator.class.getSimpleName();
    private SessionController session;

    static ArrayList<ArrayList> days;
    static ArrayList<DataModel> dataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_creator);

        session = new SessionController(getApplicationContext());

        activity = (EditText) findViewById(R.id.etActivity);
        sets = (EditText) findViewById(R.id.etSets);
        reps = (EditText) findViewById(R.id.etReps);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        Day = (TextView) findViewById(R.id.DayText);
        listView = (ListView) findViewById(R.id.list);

        savePlan = (Button) findViewById(R.id.savePlan);
        addToPlan = (Button) findViewById(R.id.addToPlan);
        prev = (Button) findViewById(R.id.btnPrev);
        next = (Button) findViewById(R.id.btnNext);

        days = new ArrayList<>();
        dataModels = new ArrayList<>();

        Day.setText("Day: 1");

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAddActivity = activity.getText().toString();
                String toAddSets = sets.getText().toString();
                String toAddReps = reps.getText().toString();
                boolean passed = true;
                if (toAddActivity.equals("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            PlanCreator.this);
                    alert.setTitle("No activity entered.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    passed = false;
                }
                if (toAddSets.equals("") || toAddReps.equals("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            PlanCreator.this);
                    alert.setTitle("Amount entered isn't a number.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    passed = false;
                }
                if (passed) {
                    dataModels.add(new DataModel(toAddActivity, toAddSets, toAddReps, "", ""));
                    activity.setText("");
                    sets.setText("");
                    reps.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        savePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        PlanCreator.this);
                alert.setTitle("Are you sure about that");
                final EditText alertInput = new EditText(PlanCreator.this);
                alert.setView(alertInput);
                alertInput.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String planName = alertInput.getText().toString();
                        if (planName.equals("")){
                            AlertDialog.Builder alert2 = new AlertDialog.Builder(
                                    PlanCreator.this);
                            alert2.setTitle("Are you sure about that");
                            alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert2.show();
                        } else {
                            if (days.size() <= PlanCreator.index)
                                days.add(dataModels);
                            else
                                days.set(PlanCreator.index, dataModels);
                            if (session.returnUserRole().equals("trainer")){
                                JSONArray toSend = new JSONArray();
                                for (int i=0; i<days.size(); i++){
                                    ArrayList temp = days.get(i);
                                    for (int j=0; j<temp.size(); j++){
                                        DataModel tempActivity = (DataModel) temp.get(j);
                                        JSONObject toPut = new JSONObject();
                                        try {
                                            toPut.put("userId", session.returnUserID());
                                            toPut.put("eamil", UserTable.UserEmailString);
                                            toPut.put("planName", planName);
                                            toPut.put("day", (i+1));
                                            toPut.put("exercise", tempActivity.getActivity());
                                            toPut.put("sets", tempActivity.getSets());
                                            toPut.put("reps", tempActivity.getReps());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                String planURL = "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/addTrainerPlan";
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,
                                        planURL, toSend, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Log.d(TAG, response.toString());
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error; " + error.toString());
                                    }
                                });
                                VolleySingleton.getInstance().addToRequestQueue(jsonArrayRequest, "jsonArray_req");
                                dialog.dismiss();
                            } else {
                                JSONArray toSend = new JSONArray();
                                for (int i=0; i<days.size(); i++){
                                    ArrayList temp = days.get(i);
                                    for (int j=0; j<temp.size(); j++){
                                        DataModel tempActivity = (DataModel) temp.get(j);
                                        JSONObject toPut = new JSONObject();
                                        try {
                                            toPut.put("userId", session.returnUserID());
                                            toPut.put("planName", planName);
                                            toPut.put("day", (i+1));
                                            toPut.put("exercise", tempActivity.getActivity());
                                            toPut.put("sets", tempActivity.getSets());
                                            toPut.put("reps", tempActivity.getReps());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                String planURL = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/addUserPlan";
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,
                                        planURL, toSend, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Log.d(TAG, response.toString());
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error; " + error.toString());
                                    }
                                });
                                VolleySingleton.getInstance().addToRequestQueue(jsonArrayRequest, "jsonArray_req");
                                dialog.dismiss();
                            }
                        }
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (days.size() <= PlanCreator.index)
                    days.add(dataModels);
                else
                    days.set(PlanCreator.index, dataModels);
                if (PlanCreator.index > 0) {
                    PlanCreator.index--;
                    dataModels = days.get(PlanCreator.index);
                    Day.setText("Day: " + (PlanCreator.index + 1));
                    adapter = new CustomAdapter(dataModels, getApplicationContext());
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            PlanCreator.this);
                    alert.setTitle("Already on first day.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (days.size() <= PlanCreator.index) {
                    days.add(dataModels);
                } else {
                    days.set(PlanCreator.index, dataModels);
                }
                PlanCreator.index++;
                Day.setText("Day: " + (PlanCreator.index + 1));
                if (days.size() - 1 < index) {
                    dataModels = new ArrayList<>();
//                    days.add(dataModels);
                } else {
                    dataModels = days.get(PlanCreator.index);
                }
                adapter = new CustomAdapter(dataModels, getApplicationContext());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        PlanCreator.this);
                alert.setTitle("Are you sure about that?");
                alert.setMessage("Are you sure to delete record?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataModels.remove(position);
                        adapter.notifyDataSetChanged();
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
                return false;
            }
        });
    }
}
