package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private Button btn_recommendation_accept, btn_recommendation_refusal;
    private TextView tv_recommendation, tv_recommendation_name;
    private ImageView img_recommendation;

    private static String addParameterStr = "";
    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/Aroma/User_Stored_Aroma_Recommendation";

    String email, mood, index = "";
    AromaItem aromaItem;

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

        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setProgress(50);

        Bundle bundle = getArguments();
        if (bundle != null) {
            email = bundle.getString("userId");
            mood = bundle.getString("feeling");

            addParameterStr = "?";
            addParameterStr += "userId=" + email + "&";
            addParameterStr += "feeling=" + mood;

            aromaItem = new AromaItem();
        }

        new Thread() {
            @Override
            public void run() {
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

                    String jsonData = buffer.toString();
                    JSONObject jsonObject = new JSONObject(jsonData);

                    aromaItem.setAromaId((Integer) jsonObject.get("aromaId"));
                    aromaItem.setKoName(jsonObject.get("koName").toString());
                    aromaItem.setImgUrl(jsonObject.get("imgURL").toString());

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            img_recommendation = root.findViewById(R.id.img_recommendation);
                            Glide
                                    .with(getActivity())
                                    .load(aromaItem.getImgUrl())
                                    .centerCrop()
                                    .apply(new RequestOptions().override(200, 200))
                                    .into(img_recommendation);
                            img_recommendation.setTag(aromaItem.getKoName());

                            tv_recommendation = root.findViewById(R.id.tv_recommendation);
                            switch (mood) {
                                case "HAPPY":
                                    tv_recommendation.setText("좋을");
                                    index = "1";
                                    break;

                                case "ANGRY":
                                    tv_recommendation.setText("화날");
                                    index = "2";
                                    break;

                                case "SAD":
                                    tv_recommendation.setText("슬플");
                                    index = "3";
                                    break;
                            }

                            tv_recommendation_name = root.findViewById(R.id.tv_recommendation_name);
                            tv_recommendation_name.setText(aromaItem.getKoName());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        btn_recommendation_accept = root.findViewById(R.id.btn_recommendation_accept);
        btn_recommendation_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAware.setAroma(aromaItem.getAromaId());
                bluetoothAware.send(index);

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

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
