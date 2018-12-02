package com.example.vigor.vigor;

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

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private CustomAdapter adapter;
    private SessionController session;
    private EditText toAddItem;
    private EditText toAddSets;
    private EditText toAddReps;
    private Button addBtn;
    private String TAG = ToDoListActivity.class.getSimpleName();

    static int Direction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        //initialize the variables for this activity
        session = new SessionController(getApplicationContext());

        listView = (ListView) findViewById(R.id.list);
        toAddItem = (EditText) findViewById(R.id.ToDoEtNewItem);
        toAddSets = (EditText) findViewById(R.id.ToDoEtNewSets);
        toAddReps = (EditText) findViewById(R.id.ToDoEtNewReps);

        addBtn = (Button) findViewById(R.id.ToDoBtnAddItem);

        dataModels = new ArrayList<>();
        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);
        setUpInitialData();

        //Listen for a user to add an activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String enteredItem = toAddItem.getText().toString();
                final String enteredSets = toAddSets.getText().toString();
                final String enteredReps = toAddReps.getText().toString();

                if (enteredItem.equals("")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoListActivity.this);
                    alert.setTitle("No Exercise Entered.");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else if (enteredSets.equals("") | enteredReps.equals("") | !isInt(enteredSets) | !isInt(enteredReps)){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoListActivity.this);
                    alert.setTitle("Amount not entered correctly.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    //Add the single activity to the list
                    dataModels.add(new DataModel(session.returnEmail(), "", enteredItem, enteredSets, enteredReps));
                    adapter.notifyDataSetChanged();
                    //Send new activity to server as a single
                    JSONObject toSend = new JSONObject();
                    String jsonUrlAddNew = "http://proj309-ad-07.misc.iastate.edu:8080/dayExercise/addSingle";
                    try {
                        toSend.put("userEmail", session.returnEmail());
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
                    //Clear the entered fields
                    toAddItem.setText("");
                    toAddSets.setText("");
                    toAddReps.setText("");
                }
            }
        });

        //listen for a user to delete an item and confirm with them if they want to.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(dataModels.get(position).getexercise().equals("null")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoListActivity.this);
                    alert.setTitle("Would you like to go to the next or last day?");
                    alert.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Direction = 1;
                            dialog.dismiss();
                        }
                    });
                    alert.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Direction = 0;
                            dialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("LAST", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Direction = 2;
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    if (Direction == 1 | Direction == 2){
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                "http://proj309-ad-07.misc.iastate.edu:8080/plan/"
                                        + session.returnUserID() + "/"
                                        + dataModels.get(position).getplanName() + "/"
                                        + Direction, null, new Response.Listener<JSONObject>() {
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
                    Direction = 0;
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoListActivity.this);
                    alert.setTitle("Would you like to mark this as complete?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            JSONObject toSend = new JSONObject();
                            String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/dayExercise/completed";
                            DataModel temp = dataModels.get(position);
                            try {
                                toSend.put("userEmail", session.returnEmail());
                                toSend.put("planName", temp.getplanName());
                                toSend.put("exercise", temp.getexercise());
                                toSend.put("sets", Integer.parseInt(temp.getsets()));
                                toSend.put("reps", Integer.parseInt(temp.getreps()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                    jsonUrl, toSend, new Response.Listener<JSONObject>() {
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
                return true;
            }
        });
    }

    private void setUpInitialData() {
        //Load singular activities from server
        String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/dayExercise/get/" +
                session.returnUserID();
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                boolean exists = false;
                                int position = 0;
                                for (int j = 0; j < dataModels.size(); j++) {
                                    if (element.getString("planName").equals(dataModels.get(i).getplanName())) {
                                        exists = true;
                                        position = j;
                                    }
                                }
                                if (!exists) {
                                    // Add Spacer
                                    dataModels.add(new DataModel(
                                            "",
                                            element.getString("planName"),
                                            "null",
                                            "Sets",
                                            "Reps"));
                                }
                                dataModels.add(position, new DataModel(
                                        element.getString("userEmail"),
                                        element.getString("planName"),
                                        element.getString("exercise"),
                                        element.getInt("sets") + "",
                                        element.getInt("reps") + ""));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
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
}