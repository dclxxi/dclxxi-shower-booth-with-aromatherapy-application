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

import com.example.showerendorphins.databinding.FragmentShowerHeadBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class ShowerHeadFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentShowerHeadBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }
    public static ShowerHeadFragment newInstance() {
        ShowerHeadFragment fragment = new ShowerHeadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShowerHeadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setProgress(20);

        bluetoothAware.receive(FragmentIndex.MOOD);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
