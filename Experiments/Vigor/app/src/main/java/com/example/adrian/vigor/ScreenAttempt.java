package com.example.adrian.vigor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScreenAttempt extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private EditText itemET;
    private Button btn1;
    private ListView todoList;

    private ArrayList<String> listItems;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_attempt);

//        configureBackButton();

        itemET = findViewById(R.id.text_item_1);
        btn1 = findViewById(R.id.add_item_btn);
        todoList = findViewById(R.id.itemList);

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        todoList.setAdapter(listAdapter);

        btn1.setOnClickListener(this);
        todoList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_item_btn:
                String itemEntered = itemET.getText().toString();
                listAdapter.add(itemEntered);
                itemET.setText("");
                FileHelper.writeData(listItems, this);
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listItems.remove(position);
        listAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }

    public void configureBackButton(View view) {
        finish();
    }

//    private void configureBackButton(){
//        Button bB = (Button) findViewById(R.id.backButton);
//        bB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
}
