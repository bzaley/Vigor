package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TrainerToDoList extends AppCompatActivity {
    public static int mangedUser;
    public static boolean isTrainer = false;
    Button userselect;
    EditText userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_to_do_list);

        userID = (EditText) findViewById(R.id.etID);

        userselect = (Button) findViewById(R.id.button2);
        userselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTrainer = true;
                if (userID.getText().toString().equals("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            TrainerToDoList.this);
                    alert.setTitle("Amount entered isn't a number.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    mangedUser = Integer.parseInt(userID.getText().toString());
                    userID.setText("");
                    startActivity(new Intent(TrainerToDoList.this, ToDoList.class));
                }
            }
        });
    }
}
