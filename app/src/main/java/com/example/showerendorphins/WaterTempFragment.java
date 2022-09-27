package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentWaterTempBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class WaterTempFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentWaterTempBinding binding;
    private TextView tv_water;

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            /*setting parameter*/
            String waterTemp = bundle.getString("waterTemperature");
            tv_water = root.findViewById(R.id.tv_water);
            tv_water.setText(waterTemp);
        }
        bluetoothAware.receive(FragmentIndex.WATER);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
