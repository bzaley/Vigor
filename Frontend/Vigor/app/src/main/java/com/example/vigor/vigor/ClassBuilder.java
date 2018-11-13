package com.example.vigor.vigor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class ClassBuilder extends AppCompatActivity {

    private EditText Name, Description, Start, End;

    private Button Done;

    private CheckBox Mon, Tues, Wed, Thurs, Fri, Sat, Sun;

    private TextView ID;

    private int ClassID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_builder);

        Name = (EditText) findViewById(R.id.ClassBuilderEtName);
        Description = (EditText) findViewById(R.id.ClassBuilderEtDesc);
        Start = (EditText) findViewById(R.id.ClassBuilderEtStart);
        End = (EditText) findViewById(R.id.ClassBuilderEtEnd);

        Done = (Button) findViewById(R.id.ClassBuilderBtnDone);

        Mon = (CheckBox) findViewById(R.id.ClassBuilderCbMon);
        Tues = (CheckBox) findViewById(R.id.ClassBuilderCbTues);
        Wed = (CheckBox) findViewById(R.id.ClassBuilderCbWed);
        Thurs = (CheckBox) findViewById(R.id.ClassBuilderCbThurs);
        Fri = (CheckBox) findViewById(R.id.ClassBuilderCbFri);
        Sat = (CheckBox) findViewById(R.id.ClassBuilderCbSat);
        Sun = (CheckBox) findViewById(R.id.ClassBuilderCbSun);

        ID = (TextView) findViewById(R.id.ClassBuilderTvID);
        Random rand = new Random();
        ClassID = rand.nextInt(999999);
        char arr[] = new char[6];
        for (int i=0; i<6; i++)
            arr[i] = Character.forDigit(ClassID%10, 10);
        ID.setText("ID: " + arr);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Put Name
                intent.putExtra("classname", Name.getText().toString());
                //Put ID
                intent.putExtra("ID", ClassID);
                //Put Description
                intent.putExtra("description", Description.getText().toString());
                //Make Schedule
                String Shcedule = "";
                int[] ids = new int[]{R.id.ClassBuilderCbMon, R.id.ClassBuilderCbTues, R.id.ClassBuilderCbWed,
                        R.id.ClassBuilderCbThurs, R.id.ClassBuilderCbFri, R.id.ClassBuilderCbSat, R.id.ClassBuilderCbSun};
                //Read in Values
                for (int id : ids) {
                    CheckBox t = (CheckBox) findViewById(id);
                    if (t.isChecked()) {
                        Shcedule += t.getText().toString().toLowerCase().charAt(0);
                    }
                }
                Shcedule += " " + Start.getText().toString() + "-" + End.getText().toString();
                intent.putExtra("schedule", Shcedule);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
