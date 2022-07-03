package com.example.showerendorphins.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.showerendorphins.AromaInfoDetail;
import com.example.showerendorphins.R;
import com.example.showerendorphins.item.AromaItem;

import java.util.ArrayList;

public class AromaInfoItemAdapter extends BaseAdapter {

    private ArrayList<AromaItem> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public AromaItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        final  AromaItem item = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_aroma_info_item, viewGroup, false);
        }else {
            View view = new View(context);
            view = (View) convertView;
        }

        TextView aroma_ko_name = convertView.findViewById(R.id.aroma_ko_name);
        TextView aroma_en_name = convertView.findViewById(R.id.aroma_en_name);
        TextView aroma_note =convertView.findViewById(R.id.aroma_note);
        TextView aroma_scent =convertView.findViewById(R.id.aroma_scent);
        ImageView aroma_img = convertView.findViewById(R.id.aroma_img);

        aroma_ko_name.setText(item.getAromaKoName());
        aroma_en_name.setText(item.getAromaEnName());
        aroma_note.setText(item.getNote());
        aroma_scent.setText(item.getScent());

        Glide
                .with(context)
                .load(item.getImgUrl())
                .centerCrop()
                .apply(new RequestOptions().override(130, 200))
                .into(aroma_img);
        aroma_ko_name.setTag(item.getAromaId());

        //각 아이템 선택 event
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, AromaInfoDetail.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return convertView;
    }


    public void addItem(AromaItem aroma){
        items.add(aroma);
    }
}
