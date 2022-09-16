package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentHeightBinding;

import java.util.Objects;

public class HeightFragment extends Fragment {
    int INDEX = 2;
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

        bluetoothAware.receive(INDEX);
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
