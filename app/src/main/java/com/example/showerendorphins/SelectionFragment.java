package com.example.showerendorphins;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.showerendorphins.databinding.FragmentSelectionBinding;
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
import java.util.ArrayList;
import java.util.Objects;

public class SelectionFragment extends Fragment {
    private BluetoothAware bluetoothAware;
    private FragmentSelectionBinding binding;
    private Button btn_selection_save;
    private RadioButton rb1, rb2, rb3;
    private RadioGroup radioGroup;
    private ImageView img_oil;

    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/Aroma/User_Stored_Aroma_Selection?userId=";

    private ArrayList<AromaItem> items;
    private String userId;
    private int aromaId, index;


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

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            items = new ArrayList<>();
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr + userId);

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
                    JSONArray jsonArray = new JSONArray(jsonData);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        AromaItem aromaItem = new AromaItem();

                        aromaItem.setAromaId((Integer) jsonObject.get("aromaId"));
                        aromaItem.setKoName(jsonObject.get("koName").toString());
                        aromaItem.setFeeling(jsonObject.get("feeling").toString());
                        aromaItem.setImgUrl(jsonObject.get("imgURL").toString());

                        items.add(aromaItem);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rb1 = root.findViewById(R.id.rb1);
                            rb2 = root.findViewById(R.id.rb2);
                            rb3 = root.findViewById(R.id.rb3);

                            rb1.setText(items.get(0).getKoName());
                            rb2.setText(items.get(1).getKoName());
                            rb3.setText(items.get(2).getKoName());

                            img_oil = root.findViewById(R.id.img_oil);

                            radioGroup = root.findViewById(R.id.radioGroup);
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                    if (i == R.id.rb1) {
                                        index = 0;
                                    } else if (i == R.id.rb2) {
                                        index = 1;
                                    } else if (i == R.id.rb3) {
                                        index = 2;
                                    }

                                    aromaId = items.get(index).getAromaId();
                                    Glide
                                            .with(getActivity())
                                            .load(items.get(index).getImgUrl())
                                            .centerCrop()
                                            .apply(new RequestOptions().override(110, 110))
                                            .into(img_oil);
                                    img_oil.setTag(items.get(index).getKoName());
                                }
                            });

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

        btn_selection_save = root.findViewById(R.id.btn_selection_save);
        btn_selection_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (items.get(index).getFeeling()) {
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

                bluetoothAware.setAroma(aromaId);
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
