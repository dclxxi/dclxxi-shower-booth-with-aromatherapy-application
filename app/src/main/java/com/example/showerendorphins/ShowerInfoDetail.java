package com.example.showerendorphins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowerInfoDetail extends AppCompatActivity {

    int usercode = 0;
    TextView shower_date, shower_time,user_bodyTemperature,user_height
            ,shower_waterTemperature, user_feeling, shower_usedAroma
            ,shower_evaluation;
    RatingBar shower_ratingBar_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shower_info_detail);
        shower_date = findViewById(R.id.shower_date);
        shower_time = findViewById(R.id.shower_time);
        user_bodyTemperature = findViewById(R.id.user_bodyTemperature);
        user_height = findViewById(R.id.user_height);
        user_feeling = findViewById(R.id.user_feeling);
        shower_usedAroma = findViewById(R.id.shower_usedAroma);
        shower_evaluation = findViewById(R.id.shower_evaluation);
        shower_ratingBar_detail = findViewById(R.id.shower_ratingBar_detail);

        Intent intent = getIntent();
//        intent.get
        shower_date.setText(intent.getStringExtra("date"));
        shower_time.setText(intent.getStringExtra("time"));
        user_bodyTemperature.setText(intent.getStringExtra("bodyTemperature"));
        user_height.setText(intent.getStringExtra("height"));
//        shower_waterTemperature.setText(intent.getStringExtra("waterTemperature"));
        user_feeling.setText(intent.getStringExtra("feeling"));
        shower_usedAroma.setText(intent.getStringExtra("aromaKoname"));

        float rating = Float.parseFloat(intent.getStringExtra("rating"));
        if (rating > 4.0) {
            shower_evaluation.setText("\"아주 만족한 샤워였어요!\"");
        }else if (rating > 3.0) {
            shower_evaluation.setText("\"기분좋은 샤워였어요!\"");
        }else if (rating > 2.0) {
            shower_evaluation.setText("\"그럭저럭 괜찮은 샤워였어요!\"");
        } else if (rating > 1.0) {
            shower_evaluation.setText("\"조금 아쉬운 샤워였어요.\"");
        } else {
            shower_evaluation.setText("\"매우 불만족한 샤워였어요.\"");
        }
        shower_ratingBar_detail.setRating(rating);

    }

}