package com.example.showerendorphins.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.ProgressDialogCustom;
import com.example.showerendorphins.R;
import com.example.showerendorphins.ShowerInfoDetail;
import com.example.showerendorphins.adapter.ShowerInfoItemAdapter;
import com.example.showerendorphins.databinding.FragmentDashboardBinding;
import com.example.showerendorphins.item.ShowerItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


    String urlStrShowerLog = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/ShowerHistory/shower_log_list?email=";
//    String urlStrShowerLog = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/ShowerHistory/shower_log_list?email=";

    private FragmentDashboardBinding binding;
    private FirebaseAuth mAuth;
    private ListView showerInfoListView;
    private ShowerInfoItemAdapter adapter;
    private ArrayList<ShowerItem> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        showerInfoListView = (ListView) rootView.findViewById(R.id.showerInfoListView_custom);
        TextView textHistoryIsNull = rootView.findViewById(R.id.visibleTextWhenShowerHistoryIsNull);
        list = new ArrayList<>();

        ProgressDialogCustom progressDialog = new ProgressDialogCustom(this.getContext()); //다이얼로그 선언
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        progressDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게

        progressDialog.show(); //로딩화면 보여주기

        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStrShowerLog + email);

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
                    if (jsonData.equals("[]\n")) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();	//progress dialog 종료
                                textHistoryIsNull.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
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

                            list.add(new ShowerItem(showerid, height, feeling, bodyTemperature,waterTemperature,
                                    aroma.get("koName").toString(), rating, createDate, getContext()));
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();	//progress dialog 종료
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                    isr.close();
                    reader.close();
                    is.close();


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

