package com.example.ben.android_experimentation;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToScreen(View v){
        Intent openNewActivity;
        switch (v.getId()) {
            case (R.id.btn1):
                openNewActivity = new Intent(this, StatsHome.class);
                startActivity(openNewActivity);
                break;
            case (R.id.btn2):
                openNewActivity = new Intent(this, RoutinesHome.class);
                startActivity(openNewActivity);
                break;
            case (R.id.btn3):
                openNewActivity = new Intent(this, ClassesHome.class);
                startActivity(openNewActivity);
                break;
        }
    }

}

