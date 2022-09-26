package com.example.showerendorphins;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

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

public class UserPieChart extends AppCompatActivity {

    // URL 설정.
    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/ShowerHistory/user_rating_count?usercode=";  //IPv4 주소 변경해야 함
    //    http://localhost:8080/ShowerHistory/user_rating_count?usercode=1
    PieChart pieChart;
    public static final int[] LIBERTY_COLORS = {
            Color.rgb(42, 109, 130), Color.rgb(118, 174, 175),
            Color.rgb(136, 180, 187), Color.rgb(148, 212, 212), Color.rgb(207, 248, 246)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pie_chart);

        String usercode = getIntent().getStringExtra("code");

        pieChart = (PieChart) findViewById(R.id.piechart);
        TextView textHistoryIsNull = findViewById(R.id.visibleTextWhenShowerHistoryIsNull);
        setTitle("PieChartActivity");
        pieChart.setUsePercentValues(true);
//        pieChart.setDrawHoleEnabled(false);

        pieChart.setCenterTextSize(21f);

        pieChart.setExtraOffsets(5, 10, 5, 5);

        Legend l = pieChart.getLegend();
        l.setTextSize(15f);
//        l.setEnabled(false);  //대체 왜 레이블이 안뜰까...

        ProgressDialogCustom progressDialog = new ProgressDialogCustom(this); //다이얼로그 선언
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        progressDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게

        progressDialog.show(); //로딩화면 보여주기

        new Thread() {
            @Override
            public void run() {
                int sum = 0;
                int total = 0;

                try {
                    URL url = new URL(urlStr + usercode);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }

                    is.close();
                    isr.close();
                    reader.close();

                    String jsonData = buffer.toString();

                    if (jsonData.equals("[]\n")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();    //progress dialog 종료
                                textHistoryIsNull.setVisibility(View.VISIBLE);
                                pieChart.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        ArrayList NoOfEmp = new ArrayList();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Integer rating = (int) Float.parseFloat(jsonObject.get("rating").toString());
                            float count = Float.parseFloat(jsonObject.get("count").toString());
                            sum += rating * count;
                            total += count;
                            NoOfEmp.add(new Entry(count, rating - 1));
                        }

                        double finalSum = sum;
                        int finalTotal = total;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();   //progress dialog 종료

                                PieDataSet dataSet = new PieDataSet(NoOfEmp, "Satisfaction Score");
                                dataSet.setSliceSpace(3f);

                                ArrayList score = new ArrayList();

                                score.add("5점");
                                score.add("4점");
                                score.add("3점");
                                score.add("2점");
                                score.add("1점");


                                pieChart.setCenterText("평균\n만족도\n" + String.format("%.2f", finalSum / finalTotal) + "점");

                                PieData data = new PieData(score, dataSet);
                                data.setValueTextSize(18f);
                                data.setValueFormatter(new PercentFormatter());
                                data.setValueTextColor(Color.WHITE);
                                pieChart.setData(data);
                                dataSet.setColors(LIBERTY_COLORS);
                                pieChart.animateXY(1000, 1000);
                            }
                        });
                    }
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