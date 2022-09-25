package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentWaterBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class WaterFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentWaterBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }


    public static WaterFragment newInstance() {
        WaterFragment fragment = new WaterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWaterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setProgress(100);

        bluetoothAware.receive(FragmentIndex.EVALUATION);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
