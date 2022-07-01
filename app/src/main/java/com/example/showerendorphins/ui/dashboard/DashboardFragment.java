package com.example.showerendorphins.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.R;
import com.example.showerendorphins.adapter.ShowerInfoItemAdapter;
import com.example.showerendorphins.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ListView showerInfoListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataSet =new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        //===== 테스트를 위한 더미 데이터 생성 ===================
        for (int i = 1; i<10; i++) {
            dataSet.add("2022-0" + i+"-"+(i+10));
        }
        //========================================================

        showerInfoListView = (ListView) rootView.findViewById(R.id.showerInfoListView_custom);
        adapter= new ShowerInfoItemAdapter(getContext(),dataSet);
        showerInfoListView.setAdapter(adapter);
        showerInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) view.findViewById(R.id.item_date).getTag().toString();
                Toast.makeText(getContext(), selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        String strText = (String) l.getItemAtPosition(position);
        Toast.makeText(this.getContext(), "클릭: " + position +" " + strText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
class ShowerInfo {
    private String itemDate;

    public ShowerInfo(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemDate() {
        return itemDate;
    }

}