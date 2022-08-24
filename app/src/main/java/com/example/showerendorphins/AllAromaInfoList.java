package com.example.showerendorphins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.showerendorphins.adapter.AromaInfoItemAdapter;
import com.example.showerendorphins.item.AromaItem;

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

public class AllAromaInfoList extends AppCompatActivity {

    // URL 설정.
    String urlStr = "http://192.168.0.103:8080/Aroma/All_Aroma_List";  //IPv4 주소 변경해야 함

    private ListView customListView;
    private AromaInfoItemAdapter adapter;
    ArrayList<AromaItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_aroma_info_list);

        customListView = findViewById(R.id.all_aromaInfoListView_custom);
        items = new ArrayList<>();

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
                        String koName = jsonObject.get("koName").toString();
                        String enName = jsonObject.get("enName").toString();
                        String note = jsonObject.get("note").toString();
                        String scent = jsonObject.get("scent").toString();
                        String imgURL = jsonObject.get("imgURL").toString();
                        items.add(new AromaItem(i+1,koName , enName, note, scent, imgURL));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
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

        adapter = new AromaInfoItemAdapter(this, items);
        customListView.setAdapter(adapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                startActivity(new Intent(AllAromaInfoList.this, AromaInfoDetail.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

        });
    }

}