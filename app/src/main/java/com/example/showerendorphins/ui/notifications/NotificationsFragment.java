package com.example.showerendorphins.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.showerendorphins.AromaInfoList;
import com.example.showerendorphins.R;
import com.example.showerendorphins.UserPieChart;
import com.example.showerendorphins.databinding.FragmentNotificationsBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    Button btn_logout,btn_update_userinfo,selectUserScore,selectUserAroma,btn_add_aroma_item;
    TextView textView_username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses도 요청함
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        btn_logout = root.findViewById(R.id.btn_logout);
        btn_update_userinfo = root.findViewById(R.id.btn_update_userinfo);
        btn_add_aroma_item = root.findViewById(R.id.btn_add_aroma_item);
        selectUserScore = root.findViewById(R.id.selectUserScore);
        selectUserAroma = root.findViewById(R.id.selectUserAroma);
        textView_username = root.findViewById(R.id.textView_username);

        textView_username.setText("로그인유저");

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                getActivity().finish();
            }
        });
        btn_update_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPieChart.class);
                startActivity(intent);
            }
        });
        btn_add_aroma_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPieChart.class);
                startActivity(intent);
            }
        });
        selectUserScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserPieChart.class);
                startActivity(intent);
            }
        });

        selectUserAroma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AromaInfoList.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void signOut() {
        mGoogleSignInClient.revokeAccess();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}