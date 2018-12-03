package com.example.vigor.vigor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UserTableActivity extends AppCompatActivity {
    private EditText userEmail;
    private Button enter;
    private ListView emailList;

    private ArrayList<String> emails;
    private ArrayAdapter<String> adapter;


    public static String UserEmailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_table);

        userEmail = (EditText) findViewById(R.id.UserTableEtManagedUser);
        emailList = (ListView) findViewById(R.id.UserTableLvEmails);
        enter = (Button) findViewById(R.id.UserTableBtnEnter);

        emails = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emails);
        emailList.setAdapter(adapter);


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.getText().toString().equals("")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            UserTableActivity.this);
                    alert.setTitle("No email entered.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else {
                    UserEmailString = userEmail.getText().toString();
                    adapter.add(UserEmailString);
                    userEmail.setText("");
                    FileHelper.writeData(emails, UserTableActivity.this);
                    startActivity(new Intent(UserTableActivity.this, TrainerToolsActivity.class));
                }
            }
        });

        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        UserTableActivity.this);
                alert.setTitle("Would you like to delete or select this user?");
                alert.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserEmailString = emails.get(position);
                        startActivity(new Intent(UserTableActivity.this, TrainerToolsActivity.class));
                        dialog.dismiss();
                    }
                });
                alert.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emails.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
}
