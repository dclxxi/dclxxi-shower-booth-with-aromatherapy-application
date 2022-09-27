package com.example.showerendorphins.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.showerendorphins.R;
import com.example.showerendorphins.item.ShowerItem;

import java.util.ArrayList;

public class ShowerInfoItemAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private ArrayList<ShowerItem> list = new ArrayList<>();

    class ViewHolder {
        public TextView log_date;
        public TextView feeling;
        public TextView aroma;
        public ImageView feeling_img;
        public RatingBar ratingBar;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ShowerInfoItemAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_shower_info_item, parent, false);
        }

        viewHolder = new ShowerInfoItemAdapter.ViewHolder();
        viewHolder.log_date = (TextView) convertView.findViewById(R.id.item_date);
        viewHolder.feeling = (TextView) convertView.findViewById(R.id.textView_day_feeling);
        viewHolder.aroma = (TextView) convertView.findViewById(R.id.textView_day_aroma);
        viewHolder.feeling_img = (ImageView) convertView.findViewById(R.id.img_feeling);
        viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.shower_ratingBar);

        final ShowerItem showerLog = list.get(position);
        viewHolder.log_date.setText(showerLog.printDateStr());
        if (showerLog.getFeeling().equals("HAPPY")) {
            viewHolder.feeling.setText("행복한");
        } else if (showerLog.getFeeling().equals("SAD")) {
            viewHolder.feeling.setText("우울한");
        } else if (showerLog.getFeeling().equals("ANGRY")) {
            viewHolder.feeling.setText("화나는");
        }
        viewHolder.aroma.setText(showerLog.getAromaKoname());
        viewHolder.feeling_img.setImageDrawable(showerLog.getImg());
        viewHolder.ratingBar.setRating(showerLog.getRating());

        return convertView;
    }
}
