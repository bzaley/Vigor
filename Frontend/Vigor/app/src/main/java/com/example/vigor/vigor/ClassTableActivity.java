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

public class ClassTableActivity extends AppCompatActivity {

    private Button addClass;
    private CustomClassAdapter adapter;

    ArrayList<ClassDataModel> classDataModels;

    ListView classes;

    private String TAG = ClassTableActivity.class.getSimpleName();
    private SessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);

        session = new SessionController(getApplicationContext());

        addClass = (Button) findViewById(R.id.classTableBtnEnter);
        classes = (ListView) findViewById(R.id.list);
        classDataModels = new ArrayList<>();
        adapter = new CustomClassAdapter(classDataModels, getApplicationContext());
        classes.setAdapter(adapter);

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
                classData.putBoolean("locked", temp.getLocked());
                launch.putExtras(classData);
                startActivity(launch);
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
                        //TODO Get URL to delete class from server
                        ClassDataModel temp = classDataModels.get(position);
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                                "http://proj309-ad-07.misc.iastate.edu:8080" + temp.getClassName(), null, new Response.Listener<JSONObject>() {
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

    private void setUpInitialData() {
        //TODO get URL to request classes from
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET,
                "http://proj309-ad-07.misc.iastate.edu:8080", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                classDataModels.add(new ClassDataModel(
                                        element.getInt("classID"),
                                        element.getString("classname"),
                                        element.getInt("instructorID"),
                                        element.getString("schedule"),
                                        element.getString("status"),
                                        element.getString("billboard"),
                                        element.getBoolean("locked")));
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            boolean exists = false;
            for (int i=0; i<classDataModels.size(); i++)
                if (classDataModels.get(i).getClassName().equals(data.getStringExtra("classname")))
                    exists = true;
            if (!exists){
                int i = 0;
                data.getIntExtra("classID", i);
                classDataModels.add(new ClassDataModel(
                        i
                        , data.getStringExtra("classname")
                        , session.returnUserID()
                        , data.getStringExtra("schedule")
                        , data.getStringExtra("status")
                        , ""
                        , false));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
