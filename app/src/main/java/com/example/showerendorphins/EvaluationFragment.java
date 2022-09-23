package com.example.showerendorphins;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.databinding.FragmentEvaluationBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class EvaluationFragment extends Fragment {

    // URL 설정.

    private static String addParameterStr = "";

    // URL 설정.
    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/ShowerHistory/add_shower_log";
    private FragmentEvaluationBinding binding;
    Button btn_save_showerlog;

    public static EvaluationFragment newInstance() {
        EvaluationFragment fragment = new EvaluationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = binding.getRoot();
        btn_save_showerlog = root.findViewById(R.id.btn_save_showerlog);
        RatingBar ratingbar = root.findViewById(R.id.ratingBar);
        Bundle bundle = getArguments();
        if (bundle != null) {
            /*setting parameter*/
            String email = bundle.getString("email");
            double height = Double.parseDouble(bundle.getString("height"));
            String feeling = bundle.getString("feeling");
            double bodyTemperature = Double.parseDouble(bundle.getString("bodyTemperature"));
            double waterTemperature = Double.parseDouble(bundle.getString("waterTemperature"));
            Integer aroma = Integer.parseInt(bundle.getString("aroma"));
            double rating = ratingbar.getRating();
            Integer userid = 1;

            addParameterStr = "?";
            addParameterStr += "height=" + height + "&";
            addParameterStr += "feeling=" + feeling + "&";
            addParameterStr += "bodyTemperature=" + bodyTemperature + "&";
            addParameterStr += "waterTemperature=" + waterTemperature + "&";
            addParameterStr += "aroma=" + aroma + "&";
            addParameterStr += "rating=" + rating + "&";
            addParameterStr += "email=" + email;

        }
        binding = FragmentEvaluationBinding.inflate(inflater, container, false);

        btn_save_showerlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "데이터를 저장합니다...\n잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();

                new Thread() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        try {
                            URL url = new URL(urlStr + addParameterStr);

                            InputStream is = url.openStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            StringBuffer buffer = new StringBuffer();
                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }

                            String returnData = buffer.toString();

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    if (returnData.isEmpty()) {
                                        Toast.makeText(getContext(), "데이터 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "샤워기록을 저장하였습니다!", Toast.LENGTH_SHORT).show();
                                        ((MainActivity) getActivity()).replaceFragment(11); //홈 화면으로 화면전환
                                    }
                                }
                            });


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "데이터 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "데이터 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }
                    }
                }.start();
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