package com.example.showerendorphins;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private static String addParameterStr = "";
    // URL 설정.
    String urlStr = "http://192.168.0.103:8080/ShowerHistory/add_shower_log";  //IPv4 주소 변경해야 함
    //    String urlStr =  "http://192.168.0.103:8080/ShowerHistory/add_shower_log?height=160&feeling=HAPPY&bodyTemperature=36.5&aroma=1&rating=5&userid=1";
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
        /*setting parameter*/
        double height = 160.2;
        String feeling = "ANGRY";
        double bodyTemperature = 35.9;
        Integer aroma = 1;
        double rating = 4.5;
        Integer userid = 1;

        addParameterStr = "?";
        addParameterStr += "height=" + height + "&";
        addParameterStr += "feeling=" + feeling + "&";
        addParameterStr += "bodyTemperature=" + bodyTemperature + "&";
        addParameterStr += "aroma=" + aroma + "&";
        addParameterStr += "rating=" + rating + "&";
        addParameterStr += "userid=" + userid;


        binding = FragmentEvaluationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn_save_showerlog = root.findViewById(R.id.btn_save_showerlog);
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
                                        ((MainActivity) getActivity()).replaceFragment(8); //홈 화면으로 화면전환
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
