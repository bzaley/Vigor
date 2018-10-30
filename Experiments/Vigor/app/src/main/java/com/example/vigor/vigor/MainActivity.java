package com.example.vigor.vigor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView temp = (TextView) findViewById(R.id.QuickViewText);
        String mystring=new String("Quick View");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        temp.setText(content);

        temp = (TextView) findViewById(R.id.QuickAccessText);
        mystring=new String("Quick Access");
        content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        temp.setText(content);

        temp = (TextView) findViewById(R.id.WelcomeText);
        mystring=new String("Welcome Back " + "!");
        temp.setText(mystring);

        Button graphButton = (Button) findViewById(R.id.graphButton);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Graphing.class));
            }
        });

        Button todoButton = (Button) findViewById(R.id.toDoButton);
        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        Button stepButton = (Button) findViewById(R.id.pedometer);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StepActivity.class));
            }
        });
    }
}
