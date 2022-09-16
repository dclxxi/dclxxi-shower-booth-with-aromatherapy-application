package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentMoodBinding;

import java.util.Objects;

public class MoodFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentMoodBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }

    public static MoodFragment newInstance() {
        MoodFragment fragment = new MoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bluetoothAware.startScan(5);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ((MainActivity) getActivity()).replaceFragment(5);
//            }
//        }, 1000); // 1000밀리 초 동안 딜레이


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
