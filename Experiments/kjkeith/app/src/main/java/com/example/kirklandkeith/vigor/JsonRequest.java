package com.example.kirklandkeith.vigor;

import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class JsonRequest {

    private String TAG = JsonRequest.class.getSimpleName();

    private String tag_cancel_json = "json_req";

    public void createSendJsonToServer(View view, String url) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
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

        VolleySingleton.getInstance().addToRequestQueue(jsonRequest, tag_cancel_json);
    }
}
