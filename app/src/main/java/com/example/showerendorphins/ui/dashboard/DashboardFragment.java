package com.example.showerendorphins.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.R;
import com.example.showerendorphins.ShowerInfoDetail;
import com.example.showerendorphins.adapter.ShowerInfoItemAdapter;
import com.example.showerendorphins.databinding.FragmentDashboardBinding;
import com.example.showerendorphins.item.ShowerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    // URL 설정.
    String urlStrFindUserCode = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/User/findUserCode?email=";  //IPv4 주소 변경해야 함

    String urlStrShowerLog = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/ShowerHistory/shower_log_list?usercode=";  //IPv4 주소 변경해야 함

    private FragmentDashboardBinding binding;

    private ListView showerInfoListView;
    private ShowerInfoItemAdapter adapter;
    private ArrayList<ShowerItem> list;

    int usercode = 1;   /* 하드코딩 */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        showerInfoListView = (ListView) rootView.findViewById(R.id.showerInfoListView_custom);
        list = new ArrayList<>();


        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStrShowerLog + usercode);

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
                        Integer showerid = Integer.parseInt(jsonObject.get("id").toString());
                        String createDateStr = jsonObject.get("createDate").toString();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
                        LocalDateTime createDate = LocalDateTime.parse(createDateStr, dateTimeFormatter);
                        double height = Double.parseDouble(jsonObject.get("height").toString());
                        String feeling = jsonObject.get("feeling").toString();
                        double bodyTemperature = Double.parseDouble(jsonObject.get("bodyTemperature").toString());
                        double waterTemperature = Double.parseDouble(jsonObject.get("waterTemperature").toString());
                        JSONObject aroma = new JSONObject(jsonObject.get("aroma").toString());
                        float rating = Float.parseFloat(jsonObject.get("rating").toString());

                        list.add(new ShowerItem(showerid, usercode, height, feeling, bodyTemperature,waterTemperature,
                                aroma.get("koName").toString(), rating, createDate, getContext()));
                    }
                    isr.close();
                    reader.close();
                    is.close();
                    getActivity().runOnUiThread(new Runnable() {
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
        adapter = new ShowerInfoItemAdapter(getContext(), list);
        showerInfoListView.setAdapter(adapter);
        showerInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowerInfoDetail.class);
                intent.putExtra("showerId",list.get(i).getShowerId());
                String date = list.get(i).getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E요일"));
                String time = list.get(i).getDate().format(DateTimeFormatter.ofPattern("a h:mm"));
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("aromaKoname",list.get(i).getAromaKoname());
                intent.putExtra("feeling",list.get(i).getFeeling());
                intent.putExtra("bodyTemperature",list.get(i).getBodyTemperature()+"");
                intent.putExtra("waterTemperature",list.get(i).getWaterTemperature()+"");
                intent.putExtra("height",list.get(i).getHeight()+"");
                intent.putExtra("rating",list.get(i).getRating()+"");

                startActivity(intent);

            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

