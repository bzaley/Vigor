package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
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
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ToDoList extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private EditText toAddItem;
    private EditText toAddSets;
    private EditText toAddReps;
    private Button addBtn;
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        final String dateS = sdf.format(Calendar.getInstance().getTime());

        dataModels = new ArrayList<>();

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        addToList("Training Goals", "Sets", "Reps", "null");
        //Load activities from server
        String jsonUrl;
        if (TrainerToDoList.isTrainer){
            jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/findIncomplete/userId/181021";
        } else {
            jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/findIncomplete/userId/181021";
        }
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                element.getInt("userId");
                                String activity = element.getString("exerciseId");
                                element.getInt("date");
                                String sets = element.getInt("sets") + "";
                                String reps = element.getInt("reps") + "";
                                element.getInt("complete");
                                String assignedby = element.getString("assignedBy");
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

        addToList("Set Goals", "Sets", "Reps", "null");

        //listen for a user to delete an item and confirm with them if they want to.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!(dataModels.get(position).getAssignedBy().equals("null"))){
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
//                Snackbar.make(view, dataModel.getName() + "\n" + dataModel.getType() + "", Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
                }
                return true;
            }
        });

        //Listen for a user to add an activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredItem = toAddItem.getText().toString();
                String enteredSets = toAddSets.getText().toString();
                String enteredReps = toAddReps.getText().toString();
                addToList(enteredItem, enteredSets, enteredReps, "Assigned By; Me");
                toAddItem.setText("");
                toAddSets.setText("");
                toAddReps.setText("");

                JSONObject toSend = null;
                String jsonUrlAddNew;
                if (TrainerToDoList.isTrainer){
                    jsonUrlAddNew = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/findIncomplete/userId/181021";
                    try {
                        toSend = makeJsonObjectTrainer(1, TrainerToDoList.mangedUser, getDayOfWeek(dateS), enteredItem, Integer.parseInt(enteredSets), Integer.parseInt(enteredReps), 0, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    jsonUrlAddNew = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/findIncomplete/userId/181021";
                    try {
                        toSend = makeJsonObjectIndividual(1, getDayOfWeek(dateS), enteredItem, Integer.parseInt(enteredSets), Integer.parseInt(enteredReps), dateS);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        if (isInt(Sets) && isInt(Reps)) {
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
        } else if (!(Activity.equals(""))) {
            if (Sets.equals(""))
                Sets = "0";
            else if (Reps.equals(""))
                Reps = "0";
            dataModels.add(new DataModel(Activity, Sets, Reps));
            adapter.notifyDataSetChanged();
        }
    }

    public JSONObject makeJsonObjectIndividual(int userID, String Day, String activity, int sets, int reps, String Date) throws JSONException {
        JSONObject objToSend = new JSONObject();
        try {
            objToSend.put("userId", userID);
            objToSend.put("dayOfWeek", Day);
            objToSend.put("ecerciseId", activity);
            objToSend.put("sets", sets);
            objToSend.put("reps", reps);
            objToSend.put("completeDate", Date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objToSend;
    }

    public JSONObject makeJsonObjectTrainer(int ptID, int userID, String Day, String activity, int sets, int reps, int complete, int repeat) throws JSONException {
        JSONObject objToSend = new JSONObject();
        try {
            objToSend.put("ptID", ptID);
            objToSend.put("userId", userID);
            objToSend.put("Day", Day);
            objToSend.put("exerciseId", activity);
            objToSend.put("sets", sets);
            objToSend.put("reps", reps);
            objToSend.put("complete", complete);
            objToSend.put("repeat", repeat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objToSend;
    }

    public String getDayOfWeek(String dateCurrent) {
        int DateWhole = Integer.parseInt(dateCurrent);
        int day = DateWhole % 100;
        DateWhole = DateWhole / 100;
        int month = DateWhole % 100;
        DateWhole = DateWhole / 100;
        int year = DateWhole % 100;
        DateWhole = DateWhole / 100;
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1: return "monday";
            case 2: return "tuesday";
            case 3: return "wednesday";
            case 4: return "thursday";
            case 5: return "friday";
            case 6: return "saturday";
            case 7: return "sunday";
        }
        return null;
    }
}