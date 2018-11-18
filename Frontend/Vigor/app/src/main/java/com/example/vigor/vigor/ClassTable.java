package com.example.vigor.vigor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassTable extends AppCompatActivity {

    private Button addClass;
    private EditText newClassName;
    private CustomClassAdapter adapter;

    ArrayList<ClassDataModel> classDataModels;

    ListView classes;

    private String TAG = ToDoList.class.getSimpleName();
    private SessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);

        addClass = (Button) findViewById(R.id.classTableBtnEnter);
//        newClassName = (EditText) findViewById(R.id.classTableEtManagedClass);

        classes = (ListView) findViewById(R.id.list);
        classDataModels = new ArrayList<>();
        adapter = new CustomClassAdapter(classDataModels, getApplicationContext());
        classes.setAdapter(adapter);

        setUpInitialData();

        session = new SessionController(getApplicationContext());

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ClassTable.this, ClassBuilder.class), 1);
            }
        });

    }

    private void setUpInitialData() {

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, "http://proj309-ad-07.misc.iastate.edu:8080", null,
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

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
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
