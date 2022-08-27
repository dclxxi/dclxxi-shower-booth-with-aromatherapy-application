package com.example.showerendorphins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AlertDialog;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AromaInfoList extends AppCompatActivity {

    String urlStr = "http://192.168.219.106:8080/Aroma/All_User_Stored_Aroma_List?userId=" + "test";  //IPv4 주소 변경해야 함

    private ListView customListView;
    private AromaInfoItemAdapter adapter;
    ArrayList<AromaItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aroma_info_list);

        customListView = findViewById(R.id.aromaInfoListView_custom);
        items = new ArrayList<>();

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
                Intent intent = new Intent(AromaInfoList.this, AromaInfoDetail.class);
                AromaItem aromaItem = items.get(position);
                intent.putExtra("aromaItem", aromaItem);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        View dialogView = (View) View.inflate(AromaInfoList.this, R.layout.all_aroma_info_list_dialog, null);

        int[] image = {R.drawable.lavender, R.drawable.lavender, R.drawable.lavender};
        String[] text = {"라벤더", "레몬", "로즈마리"};

        for (int i = 0; i < image.length; i++) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_CHECKBOX, text[i]);
            itemMap.put(TAG_IMAGEVIEW, image[i]);

            dialogItems.add(itemMap);
        }


        SimpleAdapter simpleAdapter = new SimpleAdapter(AromaInfoList.this, dialogItems,
                R.layout.dialog_aroma_info_item,
                new String[]{TAG_CHECKBOX, TAG_IMAGEVIEW},
                new int[]{R.id.dialogItemCheckBox, R.id.dialogItemImageView});

        dialogListView.setAdapter(simpleAdapter);


        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
        */

        return super.onOptionsItemSelected(item);
    }


}

