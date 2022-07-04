package com.example.showerendorphins;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UserPieChart extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pie_chart);

        pieChart = (PieChart)findViewById(R.id.piechart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new Entry(945f, 0));
        NoOfEmp.add(new Entry(1040f, 1));
        NoOfEmp.add(new Entry(1133f, 2));
        NoOfEmp.add(new Entry(1240f, 3));
        NoOfEmp.add(new Entry(1369f, 4));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Satisfaction Score");

        ArrayList score = new ArrayList();

        score.add("1점");
        score.add("2점");
        score.add("3점");
        score.add("4점");
        score.add("5점");

        PieData data = new PieData(score, dataSet);          // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        pieChart.animateXY(1000, 1000);
    }
}