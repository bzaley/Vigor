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

/**
 * @author Adrian Hamill
 * This activity allows the user to view a comprehensive todolist of custom
 * exercise objects. these objects are pulled from the server and denoted
 * by the plans marked current in the PlanManagerActivity. They can the mark
 * specific items complete and move days of individual plans.
 */
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

    /**
     *
     * @param savedInstanceState
     */
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

                //Check to see if the Items entered fit within our scope of acceptable char types
                if (enteredItem.equals("")) {
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
                } else if (enteredSets.equals("") | enteredReps.equals("") | !isInt(enteredSets) | !isInt(enteredReps)) {
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
                    //Send then new activity to the server as a single activity
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                            "http://proj309-ad-07.misc.iastate.edu:8080/exercise/check/" + toAddItem.getText().toString(), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("exists")){
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
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(
                                            ToDoListActivity.this);
                                    alert.setTitle("Activity does not exist in our list.");
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            }
        });

        //listen for a user to delete an item and confirm with them if they want to.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Check if the user is incrementing a plan or marking an activity as complete
                if (dataModels.get(position).getuserEmail().equals("null")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoListActivity.this);
                    alert.setTitle("Would you like to go to the next or last day?");
                    alert.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                                    "http://proj309-ad-07.misc.iastate.edu:8080/plan/"
                                            + session.returnUserID() + "/"
                                            + dataModels.get(position).getplanName() + "/1",
                                    null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    dataModels = new ArrayList<>();
                                    adapter = new CustomAdapter(dataModels, getApplicationContext());
                                    listView.setAdapter(adapter);
                                    setUpInitialData();
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
                    alert.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("LAST", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                                    "http://proj309-ad-07.misc.iastate.edu:8080/plan/"
                                            + session.returnUserID() + "/"
                                            + dataModels.get(position).getplanName() + "/2", null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    dataModels = new ArrayList<>();
                                    adapter = new CustomAdapter(dataModels, getApplicationContext());
                                    listView.setAdapter(adapter);
                                    setUpInitialData();
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
                    alert.show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoListActivity.this);
                    alert.setTitle("Would you like to mark this as complete?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            JSONObject toSend = new JSONObject();
                            String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/dayExercise/markComplete";
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

    /**
     * Pull initial data from the server, organizes and display it
     */
    private void setUpInitialData() {
        //Load singular activities from server
        String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/dayExercise/get/" +
                session.returnUserID();
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Iterate through the given array of values and add them
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
                                    // Add Spacer because it wasn't already there
                                    dataModels.add(new DataModel(
                                            "null",
                                            element.getString("planName"),
                                            element.getString("planName"),
                                            "Sets",
                                            "Reps"));
                                    dataModels.add(new DataModel(
                                            element.getString("userEmail"),
                                            element.getString("planName"),
                                            element.getString("exercise"),
                                            element.getInt("sets") + "",
                                            element.getInt("reps") + ""));
                                } else {
                                    dataModels.add(position, new DataModel(
                                            element.getString("userEmail"),
                                            element.getString("planName"),
                                            element.getString("exercise"),
                                            element.getInt("sets") + "",
                                            element.getInt("reps") + ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Update the adapter
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

    /**
     * Make the menu for the activity
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Return the selected item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check if a string is made up of ints
     * @param name
     * @return
     */
    public boolean isInt(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars)
            if (Character.isLetter(c))
                return true;
        return false;
    }
}