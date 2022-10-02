package com.example.showerendorphins.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.showerendorphins.AromaInfoModifyList;
import com.example.showerendorphins.R;
import com.example.showerendorphins.item.ListViewItem;

import java.util.ArrayList;

public class CustomChoiceListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private Context context;
    private ArrayList<ListViewItem> list = new ArrayList<ListViewItem>();
    private int listCnt = 0;

    class ViewHolder {
        public ImageView aroma_img;
        public TextView aroma_ko_name;
        public TextView aroma_en_name;
        public Spinner aroma_mood;
    }

    // ListViewAdapter 생성자
    public CustomChoiceListViewAdapter(@NonNull Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    // Adapter에 사용되는 데이터의 개수 리턴
    @Override
    public int getCount() {
        return list.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View 리턴
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final CustomChoiceListViewAdapter.ViewHolder viewHolder;

        // "listview_aroma_info_item_modify" Layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_aroma_info_item_modify, parent, false);
        }

        viewHolder = new CustomChoiceListViewAdapter.ViewHolder();

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        viewHolder.aroma_img = convertView.findViewById(R.id.aroma_img_modify);
        viewHolder.aroma_ko_name = convertView.findViewById(R.id.aroma_ko_name_modify);
        viewHolder.aroma_en_name = convertView.findViewById(R.id.aroma_en_name_modify);
        viewHolder.aroma_mood = convertView.findViewById(R.id.spinner_mood);

        // Data Set(listview_aroma_info_item_modify)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = list.get(position);
        // 아이템 내 각 위젯에 데이터 반영
        Glide
                .with(context)
                .load(listViewItem.getImgUrl())
                .centerCrop()
                .apply(new RequestOptions().override(200, 200))
                .into(viewHolder.aroma_img);
        viewHolder.aroma_img.setTag(listViewItem.getKoName());
        viewHolder.aroma_ko_name.setText(listViewItem.getKoName());
        viewHolder.aroma_en_name.setText(listViewItem.getEnName());

        ArrayList mood_array = new ArrayList();
        mood_array.add("--");
        mood_array.add("HAPPY");
        mood_array.add("SAD");
        mood_array.add("ANGRY");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context.getApplicationContext(), android.R.layout.simple_spinner_item, mood_array);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.aroma_mood.setAdapter(arrayAdapter);

        if (listViewItem.isChecked()) {
            ((ListView) parent).setItemChecked(position, true);
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    // 아이템 데이터 추가
    public void addItem(ListViewItem listViewItem) {
        list.add(0, listViewItem);
        listCnt = list.size();
        this.notifyDataSetChanged();
    }

    public void updateList(ArrayList<ListViewItem> listViewItem) {
        list = listViewItem;
        listCnt = list.size();
        this.notifyDataSetChanged();
    }
}