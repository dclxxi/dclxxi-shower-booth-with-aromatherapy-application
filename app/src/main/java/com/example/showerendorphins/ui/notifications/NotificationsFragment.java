package com.example.showerendorphins.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.AromaInfoList;
import com.example.showerendorphins.R;
import com.example.showerendorphins.UserPieChart;
import com.example.showerendorphins.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    TextView updateUserInfo;
    TextView selectUserScore;
    View selectUserAroma;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        updateUserInfo = root.findViewById(R.id.selectUserAroma);
        selectUserScore = root.findViewById(R.id.selectUserScore);
        selectUserAroma = root.findViewById(R.id.selectUserAroma);


        selectUserScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPieChart.class); //fragment라서 activity intent와는 다른 방식
                startActivity(intent);
            }
        });

        selectUserAroma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AromaInfoList.class); //fragment라서 activity intent와는 다른 방식
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}