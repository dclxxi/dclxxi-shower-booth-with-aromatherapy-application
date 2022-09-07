package com.example.showerendorphins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.showerendorphins.item.AromaItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AromaInfoDetail extends AppCompatActivity {


    private TextView aroma_detail_ko_name, aroma_detail_en_name, aroma_detail_note, aroma_detail_feeling,
            aroma_detail_scent, aroma_detail_summary, aroma_detail_caution;
    private ImageView aroma_detail_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aroma_info_detail);

        Intent intent = getIntent();
        AromaItem aromaItem = intent.getParcelableExtra("aromaItem");
        String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/Aroma/Aroma_Info_Detail?id=" + aromaItem.getAromaId();  //IPv4 주소 변경해야 함

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
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
                    JSONObject jsonObject = new JSONObject(jsonData);

                    aromaItem.setFeeling(jsonObject.get("feeling").toString());
                    aromaItem.setScent(jsonObject.get("scent").toString());
                    aromaItem.setSummary(jsonObject.get("summary").toString());
                    aromaItem.setCaution(jsonObject.get("caution").toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAromaInfoDetail(aromaItem);
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

    private void setAromaInfoDetail(AromaItem aromaItem) {
        aroma_detail_ko_name = findViewById(R.id.aroma_detail_ko_name);
        aroma_detail_en_name = findViewById(R.id.aroma_detail_en_name);
        aroma_detail_note = findViewById(R.id.aroma_detail_note);
        aroma_detail_feeling = findViewById(R.id.aroma_detail_feeling);
        aroma_detail_scent = findViewById(R.id.aroma_detail_scent);
        aroma_detail_summary = findViewById(R.id.aroma_detail_summary);
        aroma_detail_caution = findViewById(R.id.aroma_detail_caution);
        aroma_detail_img = findViewById(R.id.aroma_detail_img);

        aroma_detail_ko_name.setText(aromaItem.getKoName());
        aroma_detail_en_name.setText(aromaItem.getEnName());

        switch (aromaItem.getNote()) {
            case "TOP":
                aroma_detail_note.setText("탑노트");
                break;

            case "MIDDLE":
                aroma_detail_note.setText("미들노트");
                break;

            case "BASE":
                aroma_detail_note.setText("베이스노트");
                break;
        }

        switch (aromaItem.getFeeling()) {
            case "ANGRY":
                aroma_detail_feeling.setText("화날");
                break;

            case "SAD":
                aroma_detail_feeling.setText("슬플");
                break;

            case "HAPPY":
                aroma_detail_feeling.setText("기쁠");
                break;
        }

        aroma_detail_scent.setText(aromaItem.getScent());
        aroma_detail_summary.setText(aromaItem.getSummary());
        aroma_detail_caution.setText(aromaItem.getCaution());
        aroma_detail_img.setTag(aromaItem.getKoName());

        Glide
                .with(this)
                .load(aromaItem.getImgUrl())
                .fitCenter()
                .apply(new RequestOptions().override(300, 300))
                .into(aroma_detail_img);
    }
}