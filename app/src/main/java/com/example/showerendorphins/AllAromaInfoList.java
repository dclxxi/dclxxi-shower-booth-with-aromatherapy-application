package com.example.showerendorphins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.showerendorphins.adapter.AromaInfoItemAdapter;
import com.example.showerendorphins.item.AromaItem;

import java.util.ArrayList;

public class AllAromaInfoList extends AppCompatActivity {


    private ListView customListView;
    private AromaInfoItemAdapter adapter;
    ArrayList<AromaItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_aroma_info_list);

        customListView = findViewById(R.id.all_aromaInfoListView_custom);
        items = new ArrayList<>();

        items.add(new AromaItem(1, "라벤더", "Lavender", "미들노트", "우아함", "https://res.cloudinary.com/the-open-garden/image/upload/c_scale,dpr_1.100000023841858,f_auto,w_768/v1/Botanical%20Glossary/Lavender/alpha_assets/200617_WELEDA_LAVANDE_ROUGH_01TSP"));
        items.add(new AromaItem(2, "레몬", "Lemon", "탑노트", "상큼", "https://cdn.kormedi.com/wp-content/uploads/2020/11/marat-musabirov.jpg"));
        items.add(new AromaItem(3, "로즈마리", "Rosemary", "미들노트", "상쾌", "https://t1.daumcdn.net/cfile/tistory/99407B495AEFF3B339"));
        items.add(new AromaItem(4, "시나몬", "Cinnamon", "미들노트", "날카로움", "https://t1.daumcdn.net/cfile/tistory/998CE34D5C784E9511"));
        items.add(new AromaItem(5, "로즈", "Rose", "미들노트", "우아함", "https://image.yes24.com/momo/TopCate3330/MidCate2/332912005.jpg"));


        customListView.setAdapter(adapter);


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