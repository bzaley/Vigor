package com.example.vigor.vigor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ToDoList extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private EditText toAddItem;
    private EditText toAddSets;
    private EditText toAddReps;
    private Button addBtn;
    private Button nextBtn;
    private Button prevBtn;
    private String TAG = ToDoList.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        listView = (ListView) findViewById(R.id.list);
        toAddItem = (EditText) findViewById(R.id.etNewItem2);
        toAddSets = (EditText) findViewById(R.id.etNewSets);
        toAddReps = (EditText) findViewById(R.id.etNewReps);

        addBtn = (Button) findViewById(R.id.btnAddItem2);
        nextBtn = (Button) findViewById(R.id.ToDoBtnNext);
        prevBtn = (Button) findViewById(R.id.ToDoBtnLast);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        final String dateS = sdf.format(Calendar.getInstance().getTime());

        dataModels = new ArrayList<>();

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        setUpInitialData();

        //Listen for a user to add an activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredItem = toAddItem.getText().toString();
                String enteredSets = toAddSets.getText().toString();
                String enteredReps = toAddReps.getText().toString();
                if (!(TrainerToDoList.isTrainer)) {
                    addToList(enteredItem, enteredSets, enteredReps, "single");
                    //Send new activity to server as a single
                    JSONObject toSend = new JSONObject();
                    String jsonUrlAddNew = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/addUserSingle";
                    try {
                        toSend.put("userID", 1);
                        toSend.put("plan", "");
                        toSend.put("day", -1);
                        toSend.put("exercise", enteredItem);
                        toSend.put("sets", Integer.parseInt(enteredSets));
                        toSend.put("reps", Integer.parseInt(enteredReps));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            jsonUrlAddNew, toSend, new Response.Listener<JSONObject>() {
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
                toAddItem.setText("");
                toAddSets.setText("");
                toAddReps.setText("");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/next/userId/planName", new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                dataModels = new ArrayList<>();
                adapter = new CustomAdapter(dataModels, getApplicationContext());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                setUpInitialData();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "http://proj309-ad-07.misc.iastate.edu:8080/userExercis/last/userId/planNamee", new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                dataModels = new ArrayList<>();
                adapter = new CustomAdapter(dataModels, getApplicationContext());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                setUpInitialData();
            }
        });

        //listen for a user to delete an item and confirm with them if they want to.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!(dataModels.get(position).getAssignedBy().equals("null"))) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoList.this);
                    alert.setTitle("Are you sure about that?");
                    alert.setMessage("Are you sure to delete record?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataModel temp = dataModels.get(position);
                            dataModels.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            JSONObject toSend = new JSONObject();
                            try {
                                toSend.put("userId",1);
                                if (!(temp.getAssignedBy().equals("single"))) {
                                    toSend.put("plan","plan");
                                } else {
                                    toSend.put("plan","");
                                }
                                toSend.put("exercise","");
                                toSend.put("sets","");
                                toSend.put("reps","");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String jsonUrlComplete = "http://proj309-ad-07.misc.iastate.edu:8080//userExercise/save";
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                    jsonUrlComplete, toSend, new Response.Listener<JSONObject>() {
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
                        }

                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
//                DataModel dataModel = dataModels.get(position);
//
//                Snackbar.make(view, dataModel.getActivity() + "\n" + dataModel.getType() + "", Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
                }
                return true;
            }
        });
    }

    private void setUpInitialData() {

        addToList("Trainer Goals", "Sets", "Reps", "null");
        addToList("Personal Goals", "", "", "null");
//        Load activities from server
        String jsonUrl = "http: //proj309-ad-07.misc.iastate.edu:8080/userExercise/getPlan/" + "userID" + "/planName";
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                element.getInt("userId");
                                String assignedby = element.getString("planName");
                                String activity = element.getString("exercise");
                                String sets = element.getInt("sets") + "";
                                String reps = element.getInt("reps") + "";
                                addToList(activity, sets, reps, assignedby);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance().addToRequestQueue(jsonArrRequest, "json_req");

        jsonUrl = "http: //proj309-ad-07.misc.iastate.edu:8080/userExercise/get/" + "userID";
        jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                element.getInt("userId");
                                String assignedby = element.getString("planName");
                                String activity = element.getString("exercise");
                                String sets = element.getInt("sets") + "";
                                String reps = element.getInt("reps") + "";
                                addToList(activity, sets, reps, assignedby);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance().addToRequestQueue(jsonArrRequest, "json_req");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }

    public boolean isInt(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars)
            if (Character.isLetter(c))
                return true;
        return false;
    }

    public void addToList(String Activity, String Sets, String Reps, String AssignedBy) {
        if (AssignedBy.equals("null")) {
            dataModels.add(new DataModel(Activity, Sets, Reps, AssignedBy));
            adapter.notifyDataSetChanged();
        } else if (AssignedBy.equals("plan") || AssignedBy.equals("single")) {
            if (isInt(Sets) || isInt(Reps)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ToDoList.this);
                alert.setTitle("Amount entered isn't a number.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                if (!(Activity.equals("")) || !(AssignedBy.equals("null"))) {
                    if (Sets.equals("")) {
                        Sets = "0";
                    } else if (Reps.equals("")) {
                        Reps = "0";
                    }
                }
            } else {
                if (AssignedBy.equals("plan")) {
                    int index = 0;
                    for (int i = 0; i < dataModels.size(); i++) {
                        if (dataModels.get(i).getAssignedBy().equals("null"))
                            index++;
                        if (index == 2) ;
                        index = i;
                    }
                    dataModels.add(index - 1, new DataModel(Activity, Sets, Reps, AssignedBy));
                    adapter.notifyDataSetChanged();
                } else {
                    dataModels.add(new DataModel(Activity, Sets, Reps, AssignedBy));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}