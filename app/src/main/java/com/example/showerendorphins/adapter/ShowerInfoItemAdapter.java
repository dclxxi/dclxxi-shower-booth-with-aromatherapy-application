package com.example.showerendorphins.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.showerendorphins.R;

import java.util.ArrayList;
import java.util.List;

public class ShowerInfoItemAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    class ViewHolder {
        public TextView itemDate;
    }

    public ShowerInfoItemAdapter(@NonNull Context context, ArrayList list) {
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
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_shower_info_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.itemDate = (TextView) convertView.findViewById(R.id.item_date);

        final String itemText = (String) list.get(position);
        viewHolder.itemDate.setText(itemText);
        viewHolder.itemDate.setTag(itemText);

        return convertView;
    }
}
