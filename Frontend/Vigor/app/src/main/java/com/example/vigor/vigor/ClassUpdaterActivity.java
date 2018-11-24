package com.example.vigor.vigor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClassUpdaterActivity extends AppCompatActivity {

    private TextView ClassName;
    private TextView ClassID;
    private EditText Billboard;
    private Button Lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_updater);

        ClassName = (TextView) findViewById(R.id.ClassUpdaterTvClassName);
        ClassID = (TextView) findViewById(R.id.ClassUpdaterTvClassID);
        Billboard = (EditText) findViewById(R.id.ClassUpdaterEtBillboard);
        Lock = (Button) findViewById(R.id.ClassUpdaterBtnLock);

        Bundle receivedData = getIntent().getExtras();
        if (receivedData != null){
            ClassName.setText(receivedData.getString("classname"));
            ClassID.setText(receivedData.getInt("classid"));
            Billboard.setText(receivedData.getString("billboard"));
            if (receivedData.getBoolean("locked")){
                Lock.setText("Unlock");
                Billboard.setEnabled(false);
                Billboard.setClickable(false);
            } else {
                Lock.setText("Lock");
                Billboard.setEnabled(true);
                Billboard.setClickable(true);
            }
        }

        Lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Lock.getText().toString().equals("Lock")){
                    Lock.setText("Unlock");
                    Billboard.setEnabled(false);
                    Billboard.setClickable(false);

                    //TODO send Lock to server
                } else {
                    Lock.setText("Lock");
                    Billboard.setEnabled(true);
                    Billboard.setClickable(true);

                    //TODO send unlock and new billboard to server
                }
            }
        });
    }
}
