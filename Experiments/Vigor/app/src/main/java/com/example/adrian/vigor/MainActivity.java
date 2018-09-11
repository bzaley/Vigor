package com.example.adrian.vigor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button results = (Button) findViewById(R.id.averageBTN);
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText a = (EditText) findViewById(R.id.numEditText1);
                EditText b = (EditText) findViewById(R.id.numEditText2);
                EditText c = (EditText) findViewById(R.id.numEditText3);
                EditText d = (EditText) findViewById(R.id.numEditText4);
                EditText e = (EditText) findViewById(R.id.numEditText5);
                EditText f = (EditText) findViewById(R.id.numEditText6);
                EditText g = (EditText) findViewById(R.id.numEditText7);

                TextView result = (TextView) findViewById(R.id.resultTextView);

                double[] week = new double[7];
                week[0] = Integer.parseInt(a.getText().toString());
                week[1] = Integer.parseInt(b.getText().toString());
                week[2] = Integer.parseInt(c.getText().toString());
                week[3] = Integer.parseInt(d.getText().toString());
                week[4] = Integer.parseInt(e.getText().toString());
                week[5] = Integer.parseInt(f.getText().toString());
                week[6] = Integer.parseInt(g.getText().toString());

                double resultNum = 0;

                double y,x;
                x = 0.0;
                GraphView revGraph = (GraphView) findViewById(R.id.avgPlot);
                revGraph.removeAllSeries();
                for (int i=0; i<7; i++) {
                    resultNum += week[i];
                    x += 1;
                    y = week[i];
                }

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

                weekRev.setDrawValuesOnTop(true);
                weekRev.setValuesOnTopColor(Color.BLACK);

                resultNum = resultNum/7;
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                result.setText(numberFormat.format(resultNum) + "");


            }
        });
    }

}
