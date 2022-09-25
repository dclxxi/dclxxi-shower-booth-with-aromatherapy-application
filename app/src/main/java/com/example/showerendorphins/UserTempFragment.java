package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentUserTempBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class UserTempFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentUserTempBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }

    public static UserTempFragment newInstance() {
        UserTempFragment fragment = new UserTempFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserTempBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setProgress(70);

        bluetoothAware.receive(FragmentIndex.WATER_TEMP);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
