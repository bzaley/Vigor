package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLDisplay;

public class PlanCreator extends AppCompatActivity {

    private EditText activity;
    private EditText sets;
    private EditText reps;
    private Button savePlan;
    private Button addToPlan;
    private Button prev;
    private Button next;
    private TextView Day;
    private ListView listView;
    private static CustomAdapter adapter;

    ArrayList<ArrayList> days;
    ArrayList<DataModel> dataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_creator);

        activity = (EditText) findViewById(R.id.etActivity);
        sets = (EditText) findViewById(R.id.etSets);
        reps = (EditText) findViewById(R.id.etReps);
        Day = (TextView) findViewById(R.id.DayText);
        listView = (ListView) findViewById(R.id.list);
        savePlan = (Button) findViewById(R.id.savePlan);
        addToPlan = (Button) findViewById(R.id.addToPlan);
        prev = (Button) findViewById(R.id.btnPrev);
        next = (Button) findViewById(R.id.btnNext);

        days = new ArrayList<>();
        dataModels = new ArrayList<>();

        Day.setText("Day: 1");

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAddActivity = activity.getText().toString();
                String toAddSets = sets.getText().toString();
                String toAddReps = reps.getText().toString();
                boolean passed = true;
                if (toAddActivity.equals("")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            PlanCreator.this);
                    alert.setTitle("No activity entered.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    passed = false;
                }
                if (toAddSets.equals("") || toAddReps.equals("")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            PlanCreator.this);
                    alert.setTitle("Amount entered isn't a number.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    passed = false;
                }
                if (passed){
                    dataModels.add(new DataModel(toAddActivity, toAddSets, toAddReps, ""));
                    activity.setText("");
                    sets.setText("");
                    reps.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        savePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        PlanCreator.this);
                alert.setTitle("Are you sure about that?");
                alert.setMessage("Are you sure to delete record?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                return false;
            }
        });
    }
}
