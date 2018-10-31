package com.example.vigor.vigor;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonRequest {

    private String TAG = JsonRequest.class.getSimpleName();

    private String tag_cancel_json = "json_req";

    public JSONObject makeStepsJsonObject(int userID, int steps, String date) throws JSONException{
       JSONObject objToSend = new JSONObject();
       try {
           objToSend.put("userId", userID);
           objToSend.put("date", date);
           objToSend.put("steps", steps);
       } catch (JSONException e) {
           // prints the generic error trace
           e.printStackTrace();
       }
       return objToSend;
    }

    public JSONObject makeLoginJsonObject(String email, String password) throws JSONException{
        JSONObject objToSend = new JSONObject();
        try {
            objToSend.put("email", email);
            objToSend.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objToSend;
    }
}