package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class PlanManager extends AppCompatActivity {

    private ListView planList;
    private Button Continue;

    private SessionController session;
    private String TAG = ToDoList.class.getSimpleName();

    private ArrayList<PlanDataModel> plans;
    private CustomPlanAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_manager);

        session = new SessionController(getApplicationContext());

        planList = (ListView) findViewById(R.id.PlanManagerLvPlans);

        Continue = (Button) findViewById(R.id.PlanManagerBtnContinue);

        plans = new ArrayList<>();
        adapter = new CustomPlanAdapter(plans, getApplicationContext());
        planList.setAdapter(adapter);
        setUpInitialData();




        planList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlanDataModel temp = plans.get(position);
                if (temp.isChecked)
                    temp.unCheck();
                else
                    temp.check();
                plans.set(position, temp);
                adapter.notifyDataSetChanged();

                //then tell server plan is now current
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://proj309-ad-07.misc.iastate.edu:8080/plan/toggle/"
                                + session.returnUserID()
                                + "/" + temp.getPlanName(), null, new Response.Listener<JSONObject>() {
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

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanManager.this, ToDoList.class));
            }
        });
    }

    private void setUpInitialData() {
        //request all plans and whether they're current from server and add them to our list
    }

    public boolean isInt(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars)
            if (Character.isLetter(c))
                return true;
        return false;
    }
}
