package com.example.ryguy.vigor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class HomeActivity extends AppCompatActivity {

    Button b_view_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        b_view_progress = findViewById(R.id.button2);

        b_view_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ProgressScreen.class);
                startActivity(i);
            }
        });
    }
}
