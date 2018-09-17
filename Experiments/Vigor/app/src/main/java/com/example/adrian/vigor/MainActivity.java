package com.example.adrian.vigor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button results = (Button) findViewById(R.id.averageBTN);
        nextButton = (Button) findViewById(R.id.nextButton);
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double[] week = new double[8];
                double resultNum = 0, temp = 0;

                //Read in Values
                int[] ids = new int[]{R.id.numEditText1,R.id.numEditText2,R.id.numEditText3,R.id.numEditText4,R.id.numEditText5,R.id.numEditText6,R.id.numEditText7};
                int j = 0;
                for(int id : ids){
                    EditText t = (EditText) findViewById(id);
                    if (t.getText().toString().equals("")){
                        week[j] = 0;
                    }else{
                        week[j] = Integer.parseInt(t.getText().toString());
                        resultNum += week[j];
                        if (week[j] > temp){
                            temp = week[j];
                        }
                    }
                    j++;
                }

                //Print Average
                TextView result = (TextView) findViewById(R.id.resultTextView);
                resultNum = resultNum/7;
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                result.setText(numberFormat.format(resultNum) + "");

                //Initialize Graph
                GraphView revGraph = (GraphView) findViewById(R.id.avgPlot);
                revGraph.removeAllSeries();
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMinimumFractionDigits(0);
                nf.setMinimumIntegerDigits(0);
                revGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));

                //Display Graph
                BarGraphSeries<DataPoint> weekRev = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(1, week[0]),
                        new DataPoint(2, week[1]),
                        new DataPoint(3, week[2]),
                        new DataPoint(4, week[3]),
                        new DataPoint(5, week[4]),
                        new DataPoint(6, week[5]),
                        new DataPoint(7, week[6])
                });
                revGraph.addSeries(weekRev);

                //Post Configurations
                weekRev.setDrawValuesOnTop(true);
                weekRev.setValuesOnTopColor(Color.BLACK);
                Viewport view1 = revGraph.getViewport();
                view1.setMinY(0);
//                view1.setMaxY();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
    }
//
//    public void launchActivity() {
//        Intent intent = new Intent(MainActivity.this, ScreenAttempt.class);
//
//    }
}
