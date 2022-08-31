package com.example.showerendorphins;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class allUserBarChart extends AppCompatActivity {

    // URL 설정.
    String urlStr = "http://192.168.0.103:8080/ShowerHistory/all_rating_count";  //IPv4 주소 변경해야 함


    TextView textView_avg, num_of_data;
    double sum = 0;
    int totalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_bar_chart);

        BarChart chart = findViewById(R.id.bar_chart);
        textView_avg = findViewById(R.id.textView_avg);
        num_of_data = findViewById(R.id.num_of_data);

        ArrayList score = new ArrayList();


        score.add("1점");
        score.add("2점");
        score.add("3점");
        score.add("4점");
        score.add("5점");

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(0f, 0));
        NoOfEmp.add(new BarEntry(0f, 1));
        NoOfEmp.add(new BarEntry(0f, 2));
        NoOfEmp.add(new BarEntry(0f, 3));
        NoOfEmp.add(new BarEntry(0f, 4));

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }

                    String jsonData = buffer.toString();
                    JSONArray jsonArray = new JSONArray(jsonData);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer rating = (int)Float.parseFloat(jsonObject.get("rating").toString());
                        float count = Float.parseFloat(jsonObject.get("count").toString());
                        sum+=rating*count;
                        totalCount+=(int)count;
                        NoOfEmp.set(rating - 1,new BarEntry(count, rating - 1));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            num_of_data.setText(totalCount+"");
                            textView_avg.setText((sum/totalCount)+"");
                            BarDataSet bardataset = new BarDataSet(NoOfEmp, "Satisfaction Score");
                            chart.animateY(1500);
                            BarData data = new BarData(score, bardataset);      // MPAndroidChart v3.X 오류 발생
                            bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
                            chart.setData(data);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}