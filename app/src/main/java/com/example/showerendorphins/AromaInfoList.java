package com.example.showerendorphins;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.showerendorphins.adapter.AromaInfoItemAdapter;
import com.example.showerendorphins.item.AromaItem;

public class AromaInfoList extends AppCompatActivity {

    private ListView customListView;
    private AromaInfoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aroma_info_list);


        customListView = findViewById(R.id.aromaInfoListView_custom);
        adapter = new AromaInfoItemAdapter();

        adapter.addItem(new AromaItem(1,"라벤더", "Lavender", "미들노트", "우아함", "https://res.cloudinary.com/the-open-garden/image/upload/c_scale,dpr_1.100000023841858,f_auto,w_768/v1/Botanical%20Glossary/Lavender/alpha_assets/200617_WELEDA_LAVANDE_ROUGH_01TSP"));
        adapter.addItem(new AromaItem(2,"레몬", "Lemon", "탑노트", "상큼", "https://cdn.kormedi.com/wp-content/uploads/2020/11/marat-musabirov.jpg"));
        adapter.addItem(new AromaItem(3,"로즈마리", "Rosemary", "미들노트", "상쾌", "https://t1.daumcdn.net/cfile/tistory/99407B495AEFF3B339"));
        adapter.addItem(new AromaItem(4,"시나몬", "Cinnamon", "미들노트", "날카로움", "https://t1.daumcdn.net/cfile/tistory/998CE34D5C784E9511"));
        adapter.addItem(new AromaItem(5,"로즈", "Rose", "미들노트", "우아함", "https://image.yes24.com/momo/TopCate3330/MidCate2/332912005.jpg"));


        customListView.setAdapter(adapter);





    }



}

