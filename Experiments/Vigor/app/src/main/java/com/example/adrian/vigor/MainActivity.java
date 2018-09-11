package com.example.adrian.vigor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> weekRev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button results = (Button) findViewById(R.id.averageBTN);
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText a = (EditText) findViewById(R.id.firstNumEditText);
                EditText b = (EditText) findViewById(R.id.secondNumEditText);
                EditText c = (EditText) findViewById(R.id.thirdNumEditText);
                EditText d = (EditText) findViewById(R.id.fourthNumEditText);
                EditText e = (EditText) findViewById(R.id.fithNumEditText);
                EditText f = (EditText) findViewById(R.id.sixthNumEditText);
                EditText g = (EditText) findViewById(R.id.seventhNumEditText);

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
                weekRev = new LineGraphSeries<DataPoint>();

                for (int i=0; i<7; i++) {
                    resultNum += week[i];
                    x += 1;
                    y = week[i];
                    weekRev.appendData(new DataPoint(x, y), true, 8);
                }
                revGraph.addSeries(weekRev);
                resultNum = resultNum/7;
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                result.setText(numberFormat.format(resultNum) + "");


            }
        });
    }
}
