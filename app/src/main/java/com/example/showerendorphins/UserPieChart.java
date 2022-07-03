package com.example.showerendorphins;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UserPieChart extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pie_chart);

        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setEntryLabelTextSize(20);


        ArrayList yValues = new ArrayList();

        yValues.add(new PieEntry(40,"5점"));
        yValues.add(new PieEntry(30,"4점"));
        yValues.add(new PieEntry(10,"3점"));
        yValues.add(new PieEntry(10,"2점"));
        yValues.add(new PieEntry(10,"1점"));

//        Description description = new Description();
//        description.setText("만족도 점수"); //라벨
//        description.setTextSize(18);
//        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"만족도 점수");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(18f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.GRAY);

        pieChart.setData(data);


    }
}