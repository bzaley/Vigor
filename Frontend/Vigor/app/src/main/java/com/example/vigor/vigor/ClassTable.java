package com.example.vigor.vigor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClassTable extends AppCompatActivity {

    private Button addClass;
    private EditText newClassName;
    private ListAdapter adapter;

    ArrayList<ClassDataModel> classDataModels;

    ListView classes;

    private String TAG = ToDoList.class.getSimpleName();
    private SessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);

        addClass = (Button) findViewById(R.id.classTableBtnEnter);
//        newClassName = (EditText) findViewById(R.id.classTableEtManagedClass);

        classes = (ListView) findViewById(R.id.list);
        classDataModels = new ArrayList<>();
        adapter = new CustomClassAdapter(classDataModels, getApplicationContext());
        classes.setAdapter(adapter);

        session = new SessionController(getApplicationContext());

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ClassTable.this, ClassBuilder.class), 1);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                classDataModels.add(new ClassDataModel(1, data.getStringExtra("classname"),
                        session.returnUserID(), data.getStringExtra("schedule"), "", "", false));
            }
        }

    }
}
