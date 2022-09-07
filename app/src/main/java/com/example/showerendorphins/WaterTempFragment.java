package com.example.showerendorphins;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentUserTempBinding;
import com.example.showerendorphins.databinding.FragmentWaterTempBinding;

public class WaterTempFragment extends Fragment {
    private FragmentWaterTempBinding binding;

    public static WaterTempFragment newInstance() {
        WaterTempFragment fragment = new WaterTempFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWaterTempBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).replaceFragment(9);
            }
        }, 1000); // 1000밀리 초 동안 딜레이

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
