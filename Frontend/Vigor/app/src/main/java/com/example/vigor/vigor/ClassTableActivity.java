package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

/**
 * @author Adrian Hamill
 * This activity creates a Listview with a CustomArrayAdapter that shows a
 * list of the Classes managed by the current user
 */
public class ClassTableActivity extends AppCompatActivity {

    ArrayList<ClassDataModel> classDataModels;
    ListView classes;
    private CustomClassAdapter ClassAdapter;
    private Button addClass;
    private String TAG = ClassTableActivity.class.getSimpleName();

    private SessionController session;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);

        session = new SessionController(getApplicationContext());

        addClass = (Button) findViewById(R.id.classTableBtnEnter);
        classes = (ListView) findViewById(R.id.list);

        classDataModels = new ArrayList<>();
//        classDataModels.add(new ClassDataModel(30012, "bo bo bo", 21, "mwf 2:10-3", "cancelled", "water aerobics", false));
        ClassAdapter = new CustomClassAdapter(classDataModels, getApplicationContext());
        classes.setAdapter(ClassAdapter);
        setUpInitialData();

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ClassTableActivity.this,
                        ClassBuilderActivity.class), 1);
            }
        });

        classes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassDataModel temp = classDataModels.get(position);
                Intent launch = new Intent(ClassTableActivity.this, ClassUpdaterActivity.class);
                Bundle classData = new Bundle();
                classData.putString("classname", temp.getClassName());
                classData.putInt("classid", temp.getClassId());
                classData.putString("billboard", temp.getBillboard());
                classData.putString("status", temp.getStatus());
                classData.putBoolean("locked", temp.getLocked());
                launch.putExtras(classData);
                startActivityForResult(launch, 2);
            }
        });

        classes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ClassTableActivity.this);
                alert.setTitle("Would you like to delete this class?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClassDataModel temp = classDataModels.get(position);
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                "http://proj309-ad-07.misc.iastate.edu:8080/classes/delete/" + temp.getClassId(), null, new Response.Listener<JSONObject>() {
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
                        //Remove class from list user sees.
                        classDataModels.remove(position);
                        ClassAdapter.notifyDataSetChanged();
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
                return true;
            }
        });
    }

    /**
     * Pull, organize and display the data from the server
     */
    private void setUpInitialData() {
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET,
                "http://proj309-ad-07.misc.iastate.edu:8080/classes/getallinstructorclasses/" + session.returnUserID(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                String descTemp = "";
                                if (element.getString("status") != null)
                                    descTemp = element.getString("status");
                                if (descTemp.equals("null"))
                                    descTemp = "";
                                classDataModels.add(new ClassDataModel(
                                        element.getInt("classId"),
                                        element.getString("className"),
                                        element.getInt("instructorId"),
                                        element.getString("classDescription"),
                                        element.getString("schedule"),
                                        descTemp,
                                        element.getString("billboard"),
                                        element.getBoolean("locked")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ClassAdapter.notifyDataSetChanged();
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
     * When either the ClassBuilderActivity or the ClassUpdaterActivity returns
     * data to this activity, process and update the date returned.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            boolean exists = false;
            for (int i = 0; i < classDataModels.size(); i++)
                if (classDataModels.get(i).getClassName().equals(data.getStringExtra("classname")))
                    exists = true;
            if (!exists) {
                classDataModels.add(new ClassDataModel(
                        data.getIntExtra("classID", 0)
                        , data.getStringExtra("classname")
                        , data.getIntExtra("instructorid", 0)
                        , data.getStringExtra("description")
                        , data.getStringExtra("schedule")
                        , data.getStringExtra("status")
                        , ""
                        , false));
                ClassAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            int index = 0;
            for (int i = 0; i < classDataModels.size(); i++)
                if (classDataModels.get(i).getClassName().equals(data.getStringExtra("classname")))
                    index = i;
            classDataModels.set(index, new ClassDataModel(
                    classDataModels.get(index).getClassId(),
                    classDataModels.get(index).getClassName(),
                    classDataModels.get(index).getInstructorId(),
                    classDataModels.get(index).getClassDescription(),
                    classDataModels.get(index).getSchedule(),
                    data.getStringExtra("status"),
                    data.getStringExtra("billboard"),
                    classDataModels.get(index).getLocked()));
            ClassAdapter.notifyDataSetChanged();
        }
    }
}

