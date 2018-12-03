package com.example.vigor.vigor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
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

public class PlanManagerActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {

    ListView planList;
    ArrayList<PlanDataModel> plans;
    CustomPlanAdapter adapter;

    private Button add;

    private SessionController session;
    private String TAG = PlanManagerActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_manager);
        session = new SessionController(getApplicationContext());

        planList = (ListView) findViewById(R.id.PlanManagerLvPlans);
        add = (Button) findViewById(R.id.PlanManagerBtnAdd);

        plans = new ArrayList<>();
        adapter = new CustomPlanAdapter(plans, this);
        planList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        setUpInitialData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanManagerActivity.this, PlanCreatorActivity.class));
            }
        });

        planList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                        , "http://proj309-ad-07.misc.iastate.edu:8080/userPlan/remove/" +
                        session.returnUserID() + "/" + plans.get(position).getPlanName(), null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, "json_req");
                return false;
            }
        });
    }

    private void setUpInitialData() {
        //TODO get plan request URL
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Request.Method.GET,
                "http://proj309-ad-07.misc.iastate.edu:8080/plan/getAll/" + session.returnUserID(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject element = (JSONObject) response.getJSONObject(i);
                                plans.add(new PlanDataModel(element.getString("planName"), element.getBoolean("active")));
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

    public boolean isInt(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars)
            if (Character.isLetter(c))
                return true;
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = planList.getPositionForView(buttonView);

        if (position != ListView.INVALID_POSITION){
            //Toggle the Checkbox
            plans.get(position).setSelected(isChecked);
            adapter.notifyDataSetChanged();

            //then tell server plan is now current or not current
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                    "http://proj309-ad-07.misc.iastate.edu:8080/plan/toggle/"
                            + session.returnUserID()
                            + "/" + plans.get(position).getPlanName(), null, new Response.Listener<JSONObject>() {
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
    }
}
