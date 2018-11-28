package com.example.vigor.vigor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ClassHistoryActivity extends Activity {
    private String TAG = ClassViewActivity.class.getSimpleName();
    private ListView classHistoryList;
    private ClassWorkoutAdapter adapter;
    private ArrayList<ClassWorkoutDataModel> workoutDataModels;

    private SessionController session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classhistory);

        classHistoryList = findViewById(R.id.listWorkouts);

        session = new SessionController(getApplicationContext());
        workoutDataModels = new ArrayList<>();

        //TODO populate workoutDataModels

        adapter = new ClassWorkoutAdapter(workoutDataModels, getApplicationContext());
        classHistoryList.setAdapter(adapter);

        classHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassWorkoutDataModel activityValue = (ClassWorkoutDataModel)
                        classHistoryList.getItemAtPosition(position);
                //TODO Open small screen with full info of item.
                AlertDialog.Builder alert = new AlertDialog
                        .Builder(ClassHistoryActivity.this);
                alert.setTitle(activityValue.getClassDescription());
                alert.setMessage(activityValue.getClassNotes());
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });
    }
}
