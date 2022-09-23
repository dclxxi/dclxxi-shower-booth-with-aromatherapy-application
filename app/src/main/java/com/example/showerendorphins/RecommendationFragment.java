package com.example.showerendorphins;

import android.content.Context;
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

import com.example.showerendorphins.databinding.FragmentRecommendationBinding;
import com.example.showerendorphins.enums.FragmentIndex;
import com.example.showerendorphins.item.AromaItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class RecommendationFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentRecommendationBinding binding;
    Button btn_recommendation_accept;
    Button btn_recommendation_refusal;

    private static String addParameterStr = "";
    String urlStr = "http://192.168.219.103:8080/Aroma/User_Stored_Aroma_Recommendation";

    String email, mood;


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
        if (bundle != null) {
            email = bundle.getString("userId");
            mood = bundle.getString("feeling");

            addParameterStr = "?";
            addParameterStr += "userId=" + email + "&";
            addParameterStr += "feeling=" + mood;
        }

        new Thread() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                try {
                    URL url = new URL(urlStr + addParameterStr);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }

                    is.close();
                    isr.close();
                    reader.close();

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            int aromaId =  Integer.parseInt(buffer.toString());

                            btn_recommendation_accept = root.findViewById(R.id.btn_recommendation_accept);
                            btn_recommendation_accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bluetoothAware.setAroma(aromaId);

                                    switch (mood) {
                                        case "HAPPY":
                                            bluetoothAware.send("1");
                                            break;
                                        case "ANGRY":
                                            bluetoothAware.send("2");
                                            break;
                                        case "SAD":
                                            bluetoothAware.send("3");
                                            break;
                                    }
                                    ((MainActivity) getActivity()).replaceFragment(FragmentIndex.USER_TEMP);
                                }
                            });

                            btn_recommendation_refusal = root.findViewById(R.id.btn_recommendation_refusal);
                            btn_recommendation_refusal.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((MainActivity) getActivity()).replaceFragment(FragmentIndex.SELECTION);
                                }
                            });
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
