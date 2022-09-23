package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentSelectionBinding;
import com.example.showerendorphins.enums.FragmentIndex;

import java.util.Objects;

public class SelectionFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentSelectionBinding binding;
    Button btn_selection_save;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware = Objects.requireNonNull((BluetoothAware) context);
    }

    public static SelectionFragment newInstance() {
        SelectionFragment fragment = new SelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSelectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_selection_save = root.findViewById(R.id.btn_selection_save);
        btn_selection_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAware.receive(FragmentIndex.USER_TEMP);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
