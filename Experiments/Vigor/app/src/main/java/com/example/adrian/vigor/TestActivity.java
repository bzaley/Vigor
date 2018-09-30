package com.example.adrian.vigor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText itemActivity;
    private EditText itemAmount;
    private Button addBtn;
    private ListView activitiesList;
    private ListView amountsList;

    private ArrayList<String> activities;
    private ArrayList<String> amounts;

    private ArrayAdapter<String> activityAdapter;
    private ArrayAdapter<String> amountAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        itemActivity = findViewById(R.id.toAddActivities);
        itemAmount = findViewById(R.id.toAddAmounts);
        addBtn = findViewById(R.id.addTo);
        activitiesList = findViewById(R.id.activitiesListItem);
        amountsList = findViewById(R.id.amountsListItem);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addTo:
                String activityEntered = itemActivity.getText().toString();
                String amountEntered = itemAmount.getText().toString();

                activityAdapter.add(activityEntered);
                amountAdapter.add(amountEntered);

                itemActivity.setText("");
                itemAmount.setText("");

                Toast.makeText(this, "Items Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
