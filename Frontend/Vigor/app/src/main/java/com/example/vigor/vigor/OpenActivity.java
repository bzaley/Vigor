package com.example.vigor.vigor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class OpenActivity extends Activity {

    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        // TODO Create Splash Screen image.

        session = new SessionController(getApplicationContext());

        // if checkLogin returns true, user is already logged; Screen should switch to MainActivity
        // Otherwise, switch to LoginActivity to allow user to login
        if(session.checkLogin()) {
            startActivity(new Intent(OpenActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(OpenActivity.this, LoginActivity.class));
        }
        finish();
    }
}
