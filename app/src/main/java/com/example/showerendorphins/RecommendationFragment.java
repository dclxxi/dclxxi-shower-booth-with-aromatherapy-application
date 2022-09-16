package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentRecommendationBinding;

import java.util.Objects;

public class RecommendationFragment extends Fragment {
    int INDEX = 5;
    private BluetoothAware bluetoothAware;
    private FragmentRecommendationBinding binding;
    Button btn_recommendation_accept;
    Button btn_recommendation_refusal;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bluetoothAware = Objects.requireNonNull((BluetoothAware) context);
    }

    public static RecommendationFragment newInstance() {
        RecommendationFragment fragment = new RecommendationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = getArguments();
        String mood = "";
        if (bundle != null) {
            mood = bundle.getString("mood"); // 프래그먼트1에서 받아온 값 넣기
            Toast.makeText(getActivity(), mood, Toast.LENGTH_SHORT).show();


        }

        String aroma = "아로마이름";

        btn_recommendation_accept = root.findViewById(R.id.btn_recommendation_accept);
        btn_recommendation_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAware.setAroma(aroma);
                bluetoothAware.send(aroma);
                ((MainActivity) getActivity()).replaceFragment(7);
            }
        });

        btn_recommendation_refusal = root.findViewById(R.id.btn_recommendation_refusal);
        btn_recommendation_refusal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(6);
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
