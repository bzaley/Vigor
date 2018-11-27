package com.example.vigor.vigor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ClassHistoryActivity extends Activity {
    private String TAG = ClassViewActivity.class.getSimpleName();

    private Button classHistory;
    private ListView classHistoryList;

    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_classhistory);
//
//        classHistory = findViewById(R.id.histButt);
//        classWorkoutList = findViewById(R.id.listWorkouts);

        session = new SessionController(getApplicationContext());
    }
}
