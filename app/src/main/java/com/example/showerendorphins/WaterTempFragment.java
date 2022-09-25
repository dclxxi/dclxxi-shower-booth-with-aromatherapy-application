package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentWaterTempBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class WaterTempFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentWaterTempBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }

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

        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setProgress(85);

        bluetoothAware.receive(FragmentIndex.WATER);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
