package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentHeightBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class HeightFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentHeightBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }
//
//    public static HeightFragment newInstance() {
//        HeightFragment fragment = new HeightFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHeightBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bluetoothAware.receive(FragmentIndex.SHOWER_HEAD);

//        bluetoothAware.startScan(3);
//        ((MainActivity) getActivity()).replaceFragment(3);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
