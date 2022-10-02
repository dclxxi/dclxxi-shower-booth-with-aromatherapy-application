package com.example.showerendorphins;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.showerendorphins.adapter.CustomChoiceListViewAdapter;
import com.example.showerendorphins.item.ListViewItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AromaInfoModifyList extends AppCompatActivity {

    String getUrlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/Aroma/All_User_Stored_Aroma_Modify_List?userId=";
    String postUrlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/Aroma/Modify_User_Stored_AromaList";

    private CustomChoiceListViewAdapter adapter;
    private ListView customListView;
    private ArrayList<ListViewItem> items;
    private Spinner spinner_mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aroma_info_list_modify);

        customListView = findViewById(R.id.aromaInfoModifyListView_custom);
        items = new ArrayList<>();

        String userId = getIntent().getStringExtra("userId");

        ProgressDialogCustom progressDialog = new ProgressDialogCustom(this); //다이얼로그 선언
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        progressDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게

        progressDialog.show(); //로딩화면 보여주기

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getUrlStr + userId);

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
                        ListViewItem listViewItem = new ListViewItem();

                        listViewItem.setAromaId((Integer) jsonObject.get("aromaId"));
                        listViewItem.setKoName(jsonObject.get("koName").toString());
                        listViewItem.setEnName(jsonObject.get("enName").toString());
                        listViewItem.setImgUrl(jsonObject.get("imgURL").toString());
                        listViewItem.setChecked((Boolean) jsonObject.get("isChecked"));

                        items.add(listViewItem);
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

        adapter = new CustomChoiceListViewAdapter(this, items);
        customListView.setAdapter(adapter);
    }

    private void modifyUserStoredAroma() {
        String userId = getIntent().getStringExtra("userId");
        List<Integer> aromaId = new ArrayList<>();

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(postUrlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    OutputStream os = null;
                    InputStream is = null;
                    ByteArrayOutputStream baos = null;

                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    SparseBooleanArray checkedItems = customListView.getCheckedItemPositions();

                    /*for (int i = 0; i < items.size(); i++) {
                        if (checkedItems.get(i)) {
                            aromaId.add(items.get(i).getAromaId());
                        }
                    }
                    UserStoredAroma userStoredAroma = new UserStoredAroma(userId, aromaId);*/

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId", userId);

                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < items.size(); i++) {
                        if (checkedItems.get(i)) {
                            jsonArray.put(items.get(i).getAromaId());
                        }
                    }

                    jsonObject.put("aromaId", jsonArray);

                    String s = jsonObject.toString();

//                    jsonObject.put("userStoredAroma", userStoredAroma);

                    os = conn.getOutputStream();
                    os.write(jsonObject.toString().getBytes());
                    os.flush();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//                        StringBuilder response = new StringBuilder();
//                        String responseLine = null;
//                        while ((responseLine = br.readLine()) != null) {
//                            response.append(responseLine.trim());
//                        }

                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        byte[] byteBuffer = new byte[1024];
                        byte[] byteData = null;
                        int nLength = 0;
                        while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            baos.write(byteBuffer, 0, nLength);
                        }
                        byteData = baos.toByteArray();
                        String response = new String(byteData);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AromaInfoList.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("사용자 보유 아로마 오일 편집").setMessage("선택하신 아로마 오일을 저장하시겠습니까?.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                modifyUserStoredAroma();
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
//                String userId = getIntent().getStringExtra("userId");
//                intent.putExtra("userId", userId);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ((AromaInfoList) AromaInfoList.CONTEXT).onResume();
//                startActivity(intent);
                finish(); // 액티비티 종료
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return super.onOptionsItemSelected(item);
    }


/*    private void runToastAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    String code = jsonObject.get("appcode").toString();
                    if ("0".equals(code)) {
                        Toast.makeText(getApplicationContext(), "    ", Toast.LENGTH_SHORT).show();
                    } else if ("1".equals(code)) {
                        Toast.makeText(getApplicationContext(), "    ", Toast.LENGTH_SHORT).show();
                    }
                    sub.setEnabled(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}
