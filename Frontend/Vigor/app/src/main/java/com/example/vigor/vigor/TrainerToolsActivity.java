package com.example.vigor.vigor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainerToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_tools);

        Button Plan = (Button) findViewById(R.id.TrainerToolsBtnCreatePlan);
        Plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrainerToolsActivity.this, PlanCreatorActivity.class));
            }
        });

        Button Single = (Button) findViewById(R.id.TrainerToolsBtnAddSingle);
        Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainerToolsActivity.this, TrainerAddSinglesActivity.class));
            }
        });
    }
}
