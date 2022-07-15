package com.example.showerendorphins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentShowerBinding;

public class ShowerFragment extends Fragment {
    private FragmentShowerBinding binding;

    Button btn_shower_save;

    public static ShowerFragment newInstance() {
        ShowerFragment fragment = new ShowerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShowerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_shower_save = root.findViewById(R.id.btn_shower_save);
        btn_shower_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(4);
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
