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

//        Draft[] drafts = {new Draft_6455()};
//
//                /**
//                 * If running this on an android device, make sure it is on the same network as your
//                 * computer, and change the ip address to that of your computer.
//                 * If running on the emulator, you can use localhost.
//                 */
//                String w = "ws://10.26.13.93:8080/websocket";
//
//                try {
//                    Log.d("Socket:", "Trying socket");
//                    cc = new WebSocketClient(new URI(w),(Draft) drafts[0]) {
//                        @Override
//                    public void onMessage(String message) {
//                        Log.d("", "run() returned: " + message);
//                            String s=t1.getText().toString();
//                            //t1.setText("hello world");
//                            //Log.d("first", "run() returned: " + s);
//                            //s=t1.getText().toString();
//                            //Log.d("second", "run() returned: " + s);
//                            t1.setText(s+" Server:"+message);
//                    }
//
//                        @Override
//                        public void onOpen(ServerHandshake handshake) {
//                            Log.d("OPEN", "run() returned: " + "is connecting");
//                        }
//
//                        @Override
//                        public void onClose(int code, String reason, boolean remote) {
//                            Log.d("CLOSE", "onClose() returned: " + reason);
//                        }
//
//                        @Override
//                        public void onError(Exception e)
//                        {
//                            Log.d("Exception:", e.toString());
//                        }
//                    };
//                }
//                catch (URISyntaxException e) {
//                    Log.d("Exception:", e.getMessage().toString());
//                    e.printStackTrace();
//                }
//                cc.connect();

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

        //listen for a user to delete an item and confirm with them if they want to.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
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
                return true;
            }
        });

        //Load activities from server
        String jsonUrl = "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/findIncomplete/userId/181021";
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

        //Listen for a user to add an activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredItem = toAddItem.getText().toString();
                String enteredSets = toAddSets.getText().toString();
                String enteredReps = toAddReps.getText().toString();
                addToList(enteredItem, enteredSets, enteredReps,  "Assigned By; Me");
                toAddItem.setText("");
                toAddSets.setText("");
                toAddReps.setText("");

                JSONObject toSend = null;
                try {
                    toSend = makeStepsJsonObject(1, 2, dateS, Integer.parseInt(enteredSets), Integer.parseInt(enteredReps), 0, "Me");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://proj309-ad-07.misc.iastate.edu:8080/userExercise/userid/addexercise", toSend, new Response.Listener<JSONObject>() {
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

    public JSONObject makeStepsJsonObject(int userID, int activity, String date, int sets, int reps, int complete, String assignedBy) throws JSONException{
        JSONObject objToSend = new JSONObject();
        try {
            objToSend.put("userId", userID);
            objToSend.put("exerciseId", 1);
            objToSend.put("date", date);
            objToSend.put("sets", sets);
            objToSend.put("reps", reps);
            objToSend.put("complete", complete);
            objToSend.put("assignedBy", assignedBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objToSend;
    }
}