package com.example.showerendorphins.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.BluetoothAware;
import com.example.showerendorphins.R;
import com.example.showerendorphins.databinding.FragmentHomeBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class HomeFragment extends Fragment  {
    private BluetoothAware bluetoothAware;
    private FragmentHomeBinding binding;
    ImageView img_start;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware =  Objects.requireNonNull((BluetoothAware)context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        img_start = root.findViewById(R.id.img_start);
        img_start.setClickable(true);
        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_start.setClickable(false);
                bluetoothAware.startScan(FragmentIndex.SERVICE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void connect() {
        bluetoothAware.connect();

    }
}