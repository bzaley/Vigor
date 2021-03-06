package com.example.vigor.vigor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private SessionController session;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionController(getApplicationContext());
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        Button btnMakePlan = (Button) findViewById(R.id.MainBtnMakePlan);
        if (session.returnUserRole().equals("personaltrainer")){
            btnMakePlan.setText("Trainer Tools");
        } else if (session.returnUserRole().equals("instructor")){
            btnMakePlan.setText("Class Creator");
        }

        TextView temp = (TextView) findViewById(R.id.MainTvQuickView);
        String mystring=new String("Quick View");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        temp.setText(content);

        temp = (TextView) findViewById(R.id.MainTvQuickAccess);
        mystring=new String("Quick Access");
        content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        temp.setText(content);

        temp = (TextView) findViewById(R.id.MainTvWelcome);
        mystring=new String("Welcome Back " + session.returnFirstName() + "!");
        temp.setText(mystring);

        ImageView profile = (ImageView) findViewById(R.id.MainProfileImage);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        Button graphButton = (Button) findViewById(R.id.MainBtnClassView);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClassViewActivity.class));
            }
        });

        Button todoButton = (Button) findViewById(R.id.MainBtnToDo);
        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ToDoListActivity.class));
            }
        });

        Button stepButton = (Button) findViewById(R.id.MainBtnPedometer);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StepActivity.class));
            }
        });

        Button logoutButton = (Button) findViewById(R.id.MainBtnlogoutButt);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.attemptLogout();
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });

        btnMakePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.returnUserRole().equals("personaltrainer")){
                    startActivity(new Intent(MainActivity.this, UserTableActivity.class));
                } else if (session.returnUserRole().equals("instructor")) {
                    startActivity(new Intent(MainActivity.this, ClassTableActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, PlanManagerActivity.class));
                }
            }
        });

    }
}
