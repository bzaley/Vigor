package com.example.adrian.vigor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;

public class Graphing extends AppCompatActivity {
    private Button nextButton;

    TextView jsonresults;
    JSONObject data;

    RequestQueue requestQue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing);

        Button results = (Button) findViewById(R.id.averageBTN);
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] ids = new int[]{R.id.numEditText1, R.id.numEditText2, R.id.numEditText3, R.id.numEditText4, R.id.numEditText5, R.id.numEditText6, R.id.numEditText7};
                //Read in Values
                int j = 0;
                for (int id : ids) {
                    EditText t = (EditText) findViewById(id);
                    if (t.getText().toString().equals("")) {
                        ids[j] = 0;
                    } else {
                        ids[j] = Integer.parseInt(t.getText().toString());
                    }
                    j++;
                }

                Date dates[] = new Date[7];
                for (int i = 0; i < 7; i++){
                    dates[i] = new Date(18, 10, i);
                }
                Graph(ids, dates);
            }
        });

        requestQue = Volley.newRequestQueue(this);
        jsonresults = (TextView) findViewById(R.id.jsonData);
        Button populate = (Button) findViewById(R.id.popButton);
        populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse("steps", "dummyuser");
            }
        });
    }

    private void jsonParse(final String DataSet, final String usrname) {
        String JsonURL = "http://proj309-ad-07.misc.iastate.edu/" + usrname + "/" + DataSet + "/get";
        final int data[] = new int[7];
        final int datesRaw[] = new int[7];


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JsonURL, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray(usrname);
                    for (int i = 0; i < 7; i++){
                        JSONObject day = arr.getJSONObject(i);
                        datesRaw[i] = day.getInt("date");
                        data[i] = day.getInt(DataSet);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Date dateList[] = new Date[7];

        for (int i = 0; i < 7; i++){
            int day = datesRaw[i] % 100;
            int month = datesRaw[i] % 100;
            int year = datesRaw[i] % 100;
            dateList[i] = new Date(year, month, day);
        }

        Graph(data, dateList);
    }

    public void Graph(int days[], Date dates[]){
        double resultNum = 0, temp = 0;

        for (int i = 0; i < 7; i++){
            if (days[i] > temp){
                temp = days[i];
            }
            resultNum += days[i];
        }

        //Print Average
        TextView result = (TextView) findViewById(R.id.resultTextView);
        resultNum = resultNum / 7;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        result.setText(numberFormat.format(resultNum) + "");

        //Initialize Graph
        GraphView revGraph = (GraphView) findViewById(R.id.avgPlot);
        revGraph.removeAllSeries();
//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMinimumFractionDigits(0);
//        nf.setMinimumIntegerDigits(0);
//
//        revGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));

        //Display Graph
        BarGraphSeries<DataPoint> weekRev = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(dates[0], days[0]),
                new DataPoint(dates[1], days[1]),
                new DataPoint(dates[2], days[2]),
                new DataPoint(dates[3], days[3]),
                new DataPoint(dates[4], days[4]),
                new DataPoint(dates[5], days[5]),
                new DataPoint(dates[6], days[6])
        });
        revGraph.addSeries(weekRev);

        //Post Configurations
        weekRev.setDrawValuesOnTop(true);
        weekRev.setValuesOnTopColor(Color.BLACK);
        Viewport view1 = revGraph.getViewport();
        view1.setMinY(0);
        weekRev.setSpacing(5);


        // set date label formatter
        revGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Graphing.this));
        revGraph.getGridLabelRenderer().setNumHorizontalLabels(1); // only 4 because of the space
        revGraph.getGridLabelRenderer().setNumVerticalLabels(5);

// set manual x bounds to have nice steps
        revGraph.getViewport().setMinX(dates[0].getTime() - 50000000);
        revGraph.getViewport().setMaxX(dates[6].getTime() + 50000000);
        revGraph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        revGraph.getGridLabelRenderer().setHumanRounding(false);
    }
}
