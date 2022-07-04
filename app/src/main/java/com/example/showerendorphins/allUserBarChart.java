package com.example.showerendorphins;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class allUserBarChart extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_bar_chart);

        BarChart chart = findViewById(R.id.bar_chart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));

        ArrayList score = new ArrayList();


        score.add("1점");
        score.add("2점");
        score.add("3점");
        score.add("4점");
        score.add("5점");


        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Satisfaction Score");
        chart.animateY(1500);
        BarData data = new BarData(score, bardataset);      // MPAndroidChart v3.X 오류 발생
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        chart.setData(data);
    }
}