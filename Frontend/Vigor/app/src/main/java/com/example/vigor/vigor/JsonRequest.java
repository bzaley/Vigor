package com.example.vigor.vigor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class used to house specfic JsonRequest associated methods
 *
 * @author Kirkland Keith
 */
public class JsonRequest {

    private String TAG = JsonRequest.class.getSimpleName();

    /**
     * Creates a JSON Object to update server with the user's current steps for the associated day
     *
     * @param userID ID of the user whose steps are being updated
     * @param steps Number of steps that the server will be updated to
     * @param date Date for which the associated steps will be saved
     * @return JSON object to be sent to server
     * @throws JSONException
     */
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
}