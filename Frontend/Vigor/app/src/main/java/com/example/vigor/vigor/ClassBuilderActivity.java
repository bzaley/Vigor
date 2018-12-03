package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Adrian H
 * This Activity allows users of the "instructor" type to build
 * and save classes they run to our database. Once done it
 * displays the class ID for the newly created class and then returns
 * the fully made class object to the previous activity.
 *
 * @return      The Full set of data for a new class.
 */
public class ClassBuilderActivity extends AppCompatActivity {

    private EditText Name, Description, Start, End, Billboard;

    private Button Done;

    private CheckBox Mon, Tues, Wed, Thurs, Fri, Sat, Sun;

    private String TAG = ClassBuilderActivity.class.getSimpleName();
    private SessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_builder);
        session = new SessionController(getApplicationContext());

        //Set up all the input fields of the activity.
        Name = (EditText) findViewById(R.id.ClassBuilderEtName);
        Description = (EditText) findViewById(R.id.ClassBuilderEtDesc);
        Start = (EditText) findViewById(R.id.ClassBuilderEtStart);
        End = (EditText) findViewById(R.id.ClassBuilderEtEnd);
        Billboard = (EditText) findViewById(R.id.ClassBuilderEtBillboard);

        Done = (Button) findViewById(R.id.ClassBuilderBtnDone);

        Mon = (CheckBox) findViewById(R.id.ClassBuilderCbMon);
        Tues = (CheckBox) findViewById(R.id.ClassBuilderCbTues);
        Wed = (CheckBox) findViewById(R.id.ClassBuilderCbWed);
        Thurs = (CheckBox) findViewById(R.id.ClassBuilderCbThurs);
        Fri = (CheckBox) findViewById(R.id.ClassBuilderCbFri);
        Sat = (CheckBox) findViewById(R.id.ClassBuilderCbSat);
        Sun = (CheckBox) findViewById(R.id.ClassBuilderCbSun);

        //Listen for the user to signify they are done building the class
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Confirm the user wants to save the class
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ClassBuilderActivity.this);
                alert.setTitle("Are you sure about that?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Read in Values
                        int[] ids = new int[]{R.id.ClassBuilderCbMon, R.id.ClassBuilderCbTues, R.id.ClassBuilderCbWed,
                                R.id.ClassBuilderCbThurs, R.id.ClassBuilderCbFri, R.id.ClassBuilderCbSat, R.id.ClassBuilderCbSun};
                        //Make Schedule
                        String Schedule = "";
                        for (int id : ids) {
                            CheckBox t = (CheckBox) findViewById(id);
                            if (t.isChecked()) {
                                if (t.getText().toString().equals("Thursday")) {
                                    Schedule += "R";
                                } else if (t.getText().toString().equals("Saturday") || t.getText().toString().equals("Sunday")) {
                                    Schedule += t.getText().toString().charAt(0);
                                    Schedule += t.getText().toString().toLowerCase().charAt(1);
                                } else {
                                    Schedule += t.getText().toString().charAt(0);
                                }
                                Schedule += " ";
                            }
                        }
                        Schedule += " " + Start.getText().toString() + "-" + End.getText().toString();
                        final String finalSchedule = Schedule;

                        //Create the Class object to send to the server.
                        JSONObject toSend = new JSONObject();
                        try {
                            toSend.put("className", Name.getText().toString());
                            toSend.put("instructorId", session.returnUserID());
                            toSend.put("classDescription", Description.getText().toString());
                            toSend.put("schedule", Schedule);
                            toSend.put("billboard", Billboard.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //send the object to the server
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                "http://proj309-ad-07.misc.iastate.edu:8080/classes/newclass", toSend, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //receive and display the newly made class ID for this class
                                int id = 0;
                                try {
                                    id = response.getInt("classId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                final int finalint = id;
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        ClassBuilderActivity.this);
                                alert.setTitle("New ClassID; " + finalint);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Finally finish the activity and pass back the data to the previous activity.
                                        Intent intent = new Intent();
                                        //Put ClassID
                                        intent.putExtra("classid", finalint);
                                        //Put Name
                                        intent.putExtra("classname", Name.getText().toString());
                                        //Put ID
                                        intent.putExtra("ID", finalint);
                                        //Put Description
                                        intent.putExtra("description", Description.getText().toString());
                                        //Put Schedule
                                        intent.putExtra("schedule", finalSchedule);
                                        //Put Billboard
                                        intent.putExtra("billboard", Billboard.getText().toString());
                                        //Put Status
                                        intent.putExtra("status", "");
                                        setResult(RESULT_OK, intent);
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                alert.show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error:" + error.getMessage());
                            }
                        });
                        //Add this request to the que
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
        });
    }
}
