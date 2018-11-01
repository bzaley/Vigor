package com.example.vigor.vigor;

import android.nfc.Tag;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ArrayList<String> items;
    private ArrayList<String> Amounts;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayAdapter<String> amountAdapter;
    private ListView listItems;
    private ListView listAmounts;
    private EditText toAddItem;
    private EditText toAddAmount;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listItems = (ListView) findViewById(R.id.lvItems);
        toAddItem = (EditText) findViewById(R.id.etNewItem);
        listAmounts = (ListView) findViewById(R.id.lvAmounts);
        toAddAmount = (EditText) findViewById(R.id.etNewAmount);

        addBtn = (Button) findViewById(R.id.btnAddItem);
        addBtn.setOnClickListener(this);

        items = new ArrayList<String>();
        Amounts = new ArrayList<String>();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listItems.setAdapter(itemsAdapter);
        listItems.setOnItemClickListener(this);

        amountAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Amounts);
        listAmounts.setAdapter(amountAdapter);
        listAmounts.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItem:
                String enteredItem = toAddItem.getText().toString();
                String enteredAmount = toAddAmount.getText().toString();
                if (!(enteredItem.equals(""))) {
                    itemsAdapter.add(enteredItem);
                    if (enteredAmount.equals(""))
                        enteredAmount = "0";
                    amountAdapter.add(enteredAmount);
                    Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                    toAddItem.setText("");
                    toAddAmount.setText("");
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
