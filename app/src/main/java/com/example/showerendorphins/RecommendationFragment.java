package com.example.showerendorphins;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentRecommendationBinding;

public class RecommendationFragment extends Fragment {
    private FragmentRecommendationBinding binding;
    Button btn_recommendation_accept;
    Button btn_recommendation_refusal;

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


        btn_recommendation_accept = root.findViewById(R.id.btn_recommendation_accept);
        btn_recommendation_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(7);
            }
        });

        btn_recommendation_refusal = root.findViewById(R.id.btn_recommendation_refusal);
        btn_recommendation_refusal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(6);
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
