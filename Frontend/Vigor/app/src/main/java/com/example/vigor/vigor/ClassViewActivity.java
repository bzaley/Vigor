package com.example.vigor.vigor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassViewActivity extends Activity {
    private String TAG = ClassViewActivity.class.getSimpleName();
    private String jsonURL = "http://proj309-ad-07.misc.iastate.edu:8080/";

    private Button addClass;
    private Button classHistory;
    private ListView classList;
    private CustomClassAdapter adapter;
    private ArrayList<ClassDataModel> classModels;

    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclasses);

        addClass = findViewById(R.id.addClassButt);
        classHistory = findViewById(R.id.histButt);
        classList = findViewById(R.id.listClasses);

        session = new SessionController(getApplicationContext());
        classModels = new ArrayList<>();

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET, jsonURL,
                null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = response.getJSONObject(i);
                                classModels.add(new ClassDataModel(
                                        element.getInt("classID"),
                                        element.getString("classname"),
                                        element.getInt("instructorID"),
                                        element.getString("classDescription"),
                                        element.getString("schedule"),
                                        element.getString("status"),
                                        element.getString("billboard"),
                                        element.getBoolean("locked")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance().addToRequestQueue(jsonArrRequest, "json_req");

        adapter = new CustomClassAdapter(classModels, getApplicationContext());
        classList.setAdapter(adapter);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JoinClassActivity.class));
            }
        });

        classHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClassHistoryActivity.class));
            }
        });

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassDataModel classValue = (ClassDataModel) classList.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ClassProfileActivity.class);
                intent.putExtra("targetClassID", classValue.getClassId());
                startActivity(intent);
            }
        });
    }
}