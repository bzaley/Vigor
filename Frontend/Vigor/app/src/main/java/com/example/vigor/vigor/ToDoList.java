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
    private CustomAdapter adapter;
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

        //Listen for a user to add an activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String enteredItem = toAddItem.getText().toString();
                final String enteredSets = toAddSets.getText().toString();
                final String enteredReps = toAddReps.getText().toString();
                //Check if it's a trainer adding thing or a trainee
                if (!(session.returnUserRole().equals("personaltrainer"))) {
                    //Add the single activity to the list
                    addToList("", session.returnEmail(), enteredItem, enteredSets, enteredReps, "");
                    //Send new activity to server as a single from a user
                    JSONObject toSend = new JSONObject();
                    String jsonUrlAddNew = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/addUserSingle";
                    try {
                        toSend.put("userId", session.returnUserID());
                        //Server recognises single activities from users as activities with no plan name and day = -1
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoList.this);
                    alert.setTitle("Which user is this for?");
                    final EditText alertInput3 = new EditText(ToDoList.this);
                    alert.setView(alertInput3);
                    alertInput3.setInputType(InputType.TYPE_CLASS_TEXT);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Add the single activity to the list
                            addToList(session.returnUserID() + "", alertInput3.getText().toString(), enteredItem, enteredSets, enteredReps, "");
                            //Send new activity to server as a single from a trainer
                            JSONObject toSend = new JSONObject();
                            try {
                                toSend.put("trainerId", session.returnUserID());
                                toSend.put("email", alertInput3.getText().toString());
                                //Server recognises single activities from trainers as activities with no plan name and  day = -2
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

        //Listen for the user to increment which day the plan is on
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
                alert.setNeutralButton("USER", new DialogInterface.OnClickListener() {
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
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        //Listen for the user to decrement which day the plan is on
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ask them which plan they'd like to decrement
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ToDoList.this);
                alert.setTitle("Which plan would you like to Increment");
                alertInput.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setPositiveButton("TRAINER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Send the server a request to decrement
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
                alert.setNeutralButton("USER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Send the server a request to decrement
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
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                if (!(dataModels.get(position).getExercise().equals("null"))) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            ToDoList.this);
                    alert.setTitle("Are you sure about that?");
                    alert.setMessage("Would you like to mark this as complete?");
                    alert.setPositiveButton("COMPLETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //pull the current item out of the list.
                            DataModel temp = dataModels.get(position);
                            dataModels.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            //Start making the sending url
                            String JSONUrl = "";
                            JSONObject toSend = new JSONObject();
                            try {
                                //If the item is a user made activity
                                if (temp.getTrainerId().equals("")) {
                                    toSend.put("userId", session.returnUserID());
                                    JSONUrl = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/save";
                                }
                                //if the item is a trainer made activity
                                else {
                                    toSend.put("trainerId", Integer.parseInt(temp.getTrainerId()));
                                    toSend.put("email", session.returnEmail());
                                    JSONUrl = "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/save";
                                }
                                toSend.put("planName", temp.getPlanName());
                                toSend.put("exercise", temp.getExercise());
                                toSend.put("sets", temp.getSets());
                                toSend.put("reps", temp.getReps());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //finally send it
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                    JSONUrl, toSend, new Response.Listener<JSONObject>() {
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
                    });
                    //If they didn't mean to mark it as complete
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

    private void setUpInitialData(String PlanName, String TrainerPlanName) {
        dataModels = new ArrayList<>();
        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //If the user decided to load a plan execute this.
        if (!(PlanName.equals(""))) {
            addToList("-1", "", "My Goals", "Sets", "Reps", "");
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
                            addToList("", session.returnEmail(),
                                    element.getString("exercise"),
                                    element.getInt("sets") + "",
                                    element.getInt("reps") + "",
                                    element.getString("planName"));
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

        if (!(TrainerPlanName.equals(""))) {
            addToList("-2", "", "Training Goals", "Sets", "Reps", "");
            String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/trainerExercise/getTrainerPlan/" +
                    session.returnUserID() + "/" + TrainerPlanName;
            JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject element = (JSONObject) response.getJSONObject(i);
                            addToList(element.getInt("trainerId") + "",
                                    element.getString("email"),
                                    element.getString("exercise"),
                                    element.getInt("sets") + "",
                                    element.getInt("reps") + "",
                                    element.getString("planName"));
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
                                addToList("",
                                        session.returnEmail(),
                                        element.getString("exercise"),
                                        element.getInt("sets") + "",
                                        element.getInt("reps") + "",
                                        "");
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
                                addToList(element.getInt("trainerId") + "",
                                        element.getString("email"),
                                        element.getString("exercise"),
                                        element.getInt("sets") + "",
                                        element.getInt("reps") + "",
                                        element.getString("planName"));
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

    public void addToList(String TrainerId, String Email, String Exercise, String Sets, String Reps, String PlanName) {
        int index = 0;

        //If the list item is just a divider.
        if (Exercise.equals("Training Goals")) {
            dataModels.add(0, new DataModel(TrainerId, Email, Exercise, Sets, Reps, PlanName));
            adapter.notifyDataSetChanged();
        } else if (Exercise.equals("My Goals")) {
            for(int i=0; i<dataModels.size(); i++){
                if (!(dataModels.get(i).getTrainerId().equals(""))){
                    index = i;
                }
            }
            dataModels.add(index, new DataModel(TrainerId, Email, Exercise, Sets, Reps, PlanName));
        } else if (isInt(Sets) || isInt(Reps) || Sets.equals("") || Reps.equals("")) {
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
            if (!(TrainerId.equals(""))) {
                index = 0;
                for (int i = 0; i < dataModels.size(); i++) {
                    if (dataModels.get(i).getSets().equals("Sets"))
                        index++;
                    if (index == 2)
                        index = i;
                    break;
                }
                dataModels.add(index, new DataModel(TrainerId, Email, Exercise, Sets, Reps, PlanName));
                adapter.notifyDataSetChanged();
            } else {
                //If the user is assigning an activity
                dataModels.add(new DataModel(TrainerId, Email, Exercise, Sets, Reps, PlanName));
                adapter.notifyDataSetChanged();
            }
        }
    }
}