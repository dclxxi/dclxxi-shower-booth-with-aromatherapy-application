package com.example.showerendorphins.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.showerendorphins.R;
import com.example.showerendorphins.item.AromaItem;

import java.util.ArrayList;

public class AromaInfoItemAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private ArrayList<AromaItem> list = new ArrayList<AromaItem>();

    class ViewHolder {
        public TextView aroma_ko_name;
        public TextView aroma_en_name;
        public TextView aroma_note;
        public TextView aroma_scent;
        public ImageView aroma_img;
    }

    public AromaInfoItemAdapter(@NonNull Context context, ArrayList list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final AromaInfoItemAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_aroma_info_item, parent, false);
        }

        viewHolder = new AromaInfoItemAdapter.ViewHolder();
        viewHolder.aroma_ko_name = convertView.findViewById(R.id.aroma_ko_name);
        viewHolder.aroma_en_name = convertView.findViewById(R.id.aroma_en_name);
        viewHolder.aroma_note = convertView.findViewById(R.id.aroma_note);
        viewHolder.aroma_scent = convertView.findViewById(R.id.aroma_scent);
        viewHolder.aroma_img = convertView.findViewById(R.id.aroma_img);

        final AromaItem aromaItem = list.get(position);
        viewHolder.aroma_ko_name.setText(aromaItem.getKoName());
        viewHolder.aroma_en_name.setText(aromaItem.getEnName());
        viewHolder.aroma_note.setText(aromaItem.getNote());
        viewHolder.aroma_scent.setText(aromaItem.getScent());
        Glide
                .with(context)
                .load(aromaItem.getImgUrl())
                .centerCrop()
                .apply(new RequestOptions().override(200, 200))
                .into(viewHolder.aroma_img);
        viewHolder.aroma_img.setTag(aromaItem.getKoName());
        return convertView;
    }

}