package com.example.showerendorphins.ui.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.AromaInfoList;
import com.example.showerendorphins.AromaInfoModifyList;
import com.example.showerendorphins.LoginActivity;
import com.example.showerendorphins.R;
import com.example.showerendorphins.UpdateUserInfo;
import com.example.showerendorphins.UserPieChart;
import com.example.showerendorphins.UserSatisfaction;
import com.example.showerendorphins.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationsFragment extends Fragment {
    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/User/findUser?email=";  //IPv4 주소 변경해야 함

    private FragmentNotificationsBinding binding;
    private FirebaseAuth mAuth;
    Button btn_logout, btn_update_userinfo,btn_add_aroma_item, selectUserAroma, purchaseAroma, selectUserScore,  aromaRanking;
    TextView textView_username;

    String code, userId, username, gender, age;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn_logout = root.findViewById(R.id.btn_logout);
        btn_update_userinfo = root.findViewById(R.id.btn_update_userinfo);
        btn_add_aroma_item = root.findViewById(R.id.btn_add_aroma_item);
        selectUserScore = root.findViewById(R.id.selectUserScore);
        selectUserAroma = root.findViewById(R.id.selectUserAroma);
        purchaseAroma = root.findViewById(R.id.purchaseAroma);
        aromaRanking = root.findViewById(R.id.aromaRanking);
        textView_username = root.findViewById(R.id.textView_username);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btn_update_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateUserInfo.class);
                intent.putExtra("code", code);
                intent.putExtra("email", userId);
                intent.putExtra("username", username);
                intent.putExtra("gender", gender);
                intent.putExtra("age", age);
                startActivity(intent);
            }
        });
        btn_add_aroma_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AromaInfoModifyList.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        selectUserScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPieChart.class);
                intent.putExtra("code", code);
                startActivity(intent);
            }
        });

        selectUserAroma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AromaInfoList.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        aromaRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserSatisfaction.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        return root;
    }

    private void signOut() {
//        mGoogleSignInClient.revokeAccess();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail() // email addresses도 요청함
//                .build();

//        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);

        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr + email);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }

                    String jsonData = buffer.toString();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    code = jsonObject.get("code").toString();
                    userId = jsonObject.get("userId").toString();
                    username = jsonObject.get("username").toString();
                    gender = jsonObject.get("gender").toString();
                    age = jsonObject.get("age").toString();

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            textView_username.setText(username);
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
    }
}