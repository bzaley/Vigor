package com.example.vigor.vigor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class SessionController {
    private static String TAG = SessionController.class.getSimpleName();

    SharedPreferences sharedPref;
    Editor spEditor;
    Context context;

    public SessionController(Context context) {
        this.context = context;
        sharedPref = this.context.getSharedPreferences(FILENAME, PRIVATE_MODE);
        spEditor = sharedPref.edit();
    }

    public void attemptLogin(boolean isLoggedIn, String uID) {
        spEditor.putBoolean(field_value, isLoggedIn);

        if (isLoggedIn) {
            spEditor.putString(uIDFieldValue, uID);
        }
    }

    public boolean isLoggedIn {

    }
}
