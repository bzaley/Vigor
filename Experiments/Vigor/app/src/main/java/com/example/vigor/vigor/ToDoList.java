package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class ToDoList extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private SessionController session;
    private EditText toAddItem;
    private EditText toAddSets;
    private EditText toAddReps;
    private Button addBtn;
    private Button nextBtn;
    private Button prevBtn;
    private Button changePlanBtn;
    private String TAG = ToDoList.class.getSimpleName();
    private String planName = "";
    private String trainerPlanName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        //Ask user if they'd like to load in one of their plans
        AlertDialog.Builder alert = new AlertDialog.Builder(
                ToDoList.this);
        alert.setTitle("Would you like to Load a plan?");
        final EditText alertInput = new EditText(ToDoList.this);
        alert.setView(alertInput);
        alertInput.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                planName = alertInput.getText().toString();
                setUpInitialData(planName, trainerPlanName);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setUpInitialData(planName, trainerPlanName);
                dialog.dismiss();
            }
        });
        alert.show();

        //Ask user if they'd like to load a trainer's plan for them
        alert = new AlertDialog.Builder(ToDoList.this);
        final EditText alertInput2 = new EditText(ToDoList.this);
        alert.setTitle("Would you like to load a trainer's plan?");
        alert.setView(alertInput2);
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                trainerPlanName = alertInput2.getText().toString();
                setUpInitialData(planName, trainerPlanName);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setUpInitialData(planName, trainerPlanName);
                dialog.dismiss();
            }
        });
        alert.show();

        //initialize the variables for this activity
        session = new SessionController(getApplicationContext());

        listView = (ListView) findViewById(R.id.list);
        toAddItem = (EditText) findViewById(R.id.ToDoEtNewItem);
        toAddSets = (EditText) findViewById(R.id.ToDoEtNewSets);
        toAddReps = (EditText) findViewById(R.id.ToDoEtNewReps);

        addBtn = (Button) findViewById(R.id.ToDoBtnAddItem);
        nextBtn = (Button) findViewById(R.id.ToDoBtnNext);
        prevBtn = (Button) findViewById(R.id.ToDoBtnLast);
        changePlanBtn = (Button) findViewById(R.id.ToDoBtnChangePlan);

        dataModels = new ArrayList<>();

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        setUpInitialData(planName, trainerPlanName);

        //Listen for a user to add an activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String enteredItem = toAddItem.getText().toString();
                final String enteredSets = toAddSets.getText().toString();
                final String enteredReps = toAddReps.getText().toString();
                //Check if it's a trainer adding thing a single activity
                if (!(session.returnUserRole().equals("personaltrainer"))) {
                    //Add the single activity to the list
                    addToList(enteredItem, enteredSets, enteredReps, "individual", "", "");
                    //Send new activity to server as a single from a user
                    JSONObject toSend = new JSONObject();
                    String jsonUrlAddNew = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/addUserSingle";
                    try {
                        toSend.put("userId", session.returnUserID());
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
                } else {
                    //Add the single activity to the list
                    addToList(enteredItem, enteredSets, enteredReps, "trainer", "", session.returnUserID() + "");
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoList.this);
                    alert.setTitle("Which user is this for?");
                    final EditText alertInput3 = new EditText(ToDoList.this);
                    alert.setView(alertInput3);
                    alertInput3.setInputType(InputType.TYPE_CLASS_TEXT);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Send new activity to server as a single from a trainer
                            JSONObject toSend = new JSONObject();
                            try {
                                toSend.put("trainerId", session.returnUserID());
                                toSend.put("email", alertInput3.getText().toString());
                                toSend.put("planName", "");
                                toSend.put("day", -2);
                                toSend.put("exercise", enteredItem);
                                toSend.put("sets", Integer.parseInt(enteredSets));
                                toSend.put("reps", Integer.parseInt(enteredReps));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                    "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/addTrainerSingle", toSend, new Response.Listener<JSONObject>() {
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
                    alert.show();
                }

                //Clear the entered fields
                toAddItem.setText("");
                toAddSets.setText("");
                toAddReps.setText("");
            }
        });

        //Increment which day the plan you are using is on
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ask them which plan they'd like to Increment
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ToDoList.this);
                alert.setTitle("Which plan would you like to Increment");
                alertInput.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setPositiveButton("TRAINER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Send the server a request to Increment
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/next/" +
                                        session.returnUserID() + "/" + trainerPlanName, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //When the server responds, update the data the user sees
                                        try {
                                            Toast.makeText(ToDoList.this, response.getString("success"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dataModels = new ArrayList<>();
                                        adapter = new CustomAdapter(dataModels, getApplicationContext());
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        setUpInitialData(planName, trainerPlanName);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ToDoList.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("USER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Send the server a request to Increment
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/next/" +
                                        session.returnUserID() + "/" + planName, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //When the server responds, update the data the user sees
                                        try {
                                            Toast.makeText(ToDoList.this, response.getString("success"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dataModels = new ArrayList<>();
                                        adapter = new CustomAdapter(dataModels, getApplicationContext());
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        setUpInitialData(planName, trainerPlanName);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ToDoList.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        //Decrement which day the plan you are using is on
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ask them which plan they'd like to Increment
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ToDoList.this);
                alert.setTitle("Which plan would you like to Increment");
                alertInput.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setPositiveButton("TRAINER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Send the server a request to Decrement
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/last/" +
                                        session.returnUserID() + "/" + trainerPlanName, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //When the server responds, update the data the user sees
                                        try {
                                            Toast.makeText(ToDoList.this, response.getString("success"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dataModels = new ArrayList<>();
                                        adapter = new CustomAdapter(dataModels, getApplicationContext());
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        setUpInitialData(planName, trainerPlanName);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ToDoList.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("USER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Send the server a request to Decrement
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/last/" +
                                        session.returnUserID() + "/" + planName, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //When the server responds, update the data the user sees
                                        try {
                                            Toast.makeText(ToDoList.this, response.getString("success"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dataModels = new ArrayList<>();
                                        adapter = new CustomAdapter(dataModels, getApplicationContext());
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        setUpInitialData(planName, trainerPlanName);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ToDoList.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        //Listen for if the user what's to change what plan they're on.
        changePlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ask them which plan they'd like to change to
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ToDoList.this);
                alert.setTitle("Would you like to change your plan?");
                final EditText alertInput = new EditText(ToDoList.this);
                alert.setView(alertInput);
                alertInput.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //record the result in planName and update the data the user sees
                        planName = alertInput.getText().toString();
                        setUpInitialData(planName, trainerPlanName);
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

        //listen for a user to delete an item and confirm with them if they want to.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!(dataModels.get(position).getAssignedBy().equals("null"))) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoList.this);
                    alert.setTitle("Are you sure about that?");
                    alert.setMessage("Would you like to mark this as complete?");
                    alert.setPositiveButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //pull the current item out of the list and send it to the server.
                            DataModel temp = dataModels.get(position);
                            dataModels.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            if (temp.getAssignedBy().equals("individual")){
                                JSONObject toSend = new JSONObject();
                                try {
                                    toSend.put("userId", session.returnUserID());
                                    if (!(temp.getPlanName().equals("single"))) {
                                        toSend.put("planName", temp.getPlanName());
                                    } else {
                                        toSend.put("planName", "");
                                    }
                                    toSend.put("exercise", temp.getActivity());
                                    toSend.put("sets", temp.getSets());
                                    toSend.put("reps", temp.getReps());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                        "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/save", toSend, new Response.Listener<JSONObject>() {
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
                                VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                            } else {
                                JSONObject toSend = new JSONObject();
                                try {
                                    toSend.put("trainerId", Integer.parseInt(temp.getTrainerId()));
                                    toSend.put("email", session.returnEmail());
                                    if (!(temp.getPlanName().equals("single"))) {
                                        toSend.put("planName", temp.getPlanName());
                                    } else {
                                        toSend.put("planName", "");
                                    }
                                    toSend.put("exercise", temp.getActivity());
                                    toSend.put("sets", temp.getSets());
                                    toSend.put("reps", temp.getReps());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                        "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/save", toSend, new Response.Listener<JSONObject>() {
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
                                VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                            }
                        }
                    });
//                    alert.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
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

    private void setUpInitialData(String PlanName, String TrainerPlanName) {
        dataModels = new ArrayList<>();
        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //If the user decided to load a plan execute this.
        if (!(PlanName.equals(""))){
            addToList("Personal Goals", "Sets", "Reps", "null", "", "");
            String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/getPlan/" +
                    session.returnUserID() + "/" + PlanName;
            JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject element = (JSONObject) response.getJSONObject(i);
                            element.getInt("userId");
                            String PlanName = element.getString("planName");
                            String activity = element.getString("exercise");
                            String sets = element.getInt("sets") + "";
                            String reps = element.getInt("reps") + "";
                            addToList(activity, sets, reps, "individual", PlanName, "");
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

        if (!(TrainerPlanName.equals(""))){
            addToList("Trainer Goals", "Sets", "Reps", "null", "", "");
            String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/getTrainerPlan/" +
                    session.returnUserID() + "/" + TrainerPlanName;
            JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject element = (JSONObject) response.getJSONObject(i);
                            String trainerId = element.getInt("trainerId") + "";
                            element.getString("email");
                            String PlanName = element.getString("planName");
                            String activity = element.getString("exercise");
                            String sets = element.getInt("sets") + "";
                            String reps = element.getInt("reps") + "";
                            addToList(activity, sets, reps, "trainer", PlanName, trainerId);
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

        //Load singular activities from server
        String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/get/" +
                session.returnUserID();
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
                                addToList(activity, sets, reps, "individual", "single", "");
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


        //Load singular activities from server
        jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/getTrainerExercises/" +
                session.returnUserID();
        jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                String trainerId = element.getInt("trainerId") + "";
                                element.getString("email");
                                String assignedby = element.getString("planName");
                                String activity = element.getString("exercise");
                                String sets = element.getInt("sets") + "";
                                String reps = element.getInt("reps") + "";
                                addToList(activity, sets, reps, "trainer", "single", trainerId);
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

    public void addToList(String Activity, String Sets, String Reps, String AssignedBy, String PlanName, String trainerId) {
        //If the list item is just a divider.
        if (AssignedBy.equals("null")) {
            dataModels.add(new DataModel(Activity, Sets, Reps, AssignedBy, "", ""));
            adapter.notifyDataSetChanged();
        }
        //If the List item is assigned by someone.
        else if (AssignedBy.equals("individual") || AssignedBy.equals("trainer")) {
            //if the sets and reps are actually entered
            if (isInt(Sets) || isInt(Reps) || Sets.equals("") || Reps.equals("")) {
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
            } else {
                //if the Triner is assigning an activity
                if (AssignedBy.equals(session.returnUserRole().equals("trainer"))) {
                    int index = 0;
                    for (int i = 0; i < dataModels.size(); i++) {
                        if (dataModels.get(i).getAssignedBy().equals("null"))
                            index++;
                        if (index == 2) ;
                        index = i;
                    }
                    dataModels.add(index - 1, new DataModel(Activity, Sets, Reps, AssignedBy, PlanName, trainerId));
                    adapter.notifyDataSetChanged();
                } else {
                //If the user is assigning an activity
                    dataModels.add(new DataModel(Activity, Sets, Reps, AssignedBy, PlanName, trainerId));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}