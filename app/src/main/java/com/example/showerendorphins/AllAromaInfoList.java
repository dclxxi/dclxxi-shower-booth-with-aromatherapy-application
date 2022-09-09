package com.example.showerendorphins;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/Aroma/All_Aroma_List";  //IPv4 주소 변경해야 함
    private ListView customListView;
    private AromaInfoItemAdapter adapter;
    ArrayList<AromaItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_aroma_info_list);

        customListView = findViewById(R.id.all_aromaInfoListView_custom);
        items = new ArrayList<>();

        ProgressDialogCustom progressDialog = new ProgressDialogCustom(this); //다이얼로그 선언
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        progressDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게

        progressDialog.show(); //로딩화면 보여주기

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
                    JSONArray jsonArray = new JSONArray(jsonData);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        AromaItem aromaItem = new AromaItem();

                        aromaItem.setAromaId((Integer) jsonObject.get("id"));
                        aromaItem.setKoName(jsonObject.get("koName").toString());
                        aromaItem.setEnName(jsonObject.get("enName").toString());
                        aromaItem.setNote(jsonObject.get("note").toString());
                        aromaItem.setScent(jsonObject.get("scent").toString());
                        aromaItem.setImgUrl(jsonObject.get("imgURL").toString());

                        items.add(aromaItem);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();   //progress dialog 종료
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
                Intent intent = new Intent(AllAromaInfoList.this, AromaInfoDetail.class);
                AromaItem aromaItem = items.get(position);
                intent.putExtra("aromaItem", aromaItem);
                startActivity(intent);
            }
        });
    }

}