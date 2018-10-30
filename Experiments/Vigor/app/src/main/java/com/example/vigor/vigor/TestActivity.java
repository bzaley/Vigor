package com.example.vigor.vigor;

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

public class TestActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText toAdd;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lvItems = (ListView) findViewById(R.id.lvItems);
        addBtn = (Button) findViewById(R.id.btnAddItem);
        toAdd = (EditText) findViewById(R.id.etNewItem);

        addBtn.setOnClickListener(this);

        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItem:
                String enteredItem = toAdd.getText().toString();
                if (!(enteredItem.equals(""))) {
                    itemsAdapter.add(enteredItem);

                    Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                    toAdd.setText("");
                }

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
