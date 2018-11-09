package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserTable extends AppCompatActivity {
    EditText userEmail;
    Button enter;

    public static String UserEmailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_table);

        userEmail = (EditText) findViewById(R.id.UserTableEtManagedUser);
        enter = (Button) findViewById(R.id.UserTableBtnEnter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEmailString = userEmail.getText().toString();
                if (UserEmailString.equals("")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            UserTable.this);
                    alert.setTitle("No email entered.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    startActivity(new Intent(UserTable.this, TrainerTools.class));
                }
            }
        });

    }

}
