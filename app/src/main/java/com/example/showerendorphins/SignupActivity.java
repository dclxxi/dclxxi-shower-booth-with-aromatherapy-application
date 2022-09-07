package com.example.showerendorphins;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/User/join";  //IPv4 주소 변경해야 함
    private static String addParameterStr = "";

    private FirebaseAuth firebaseAuth;
    Button btn_signup;
    TextView tv_age_set;
    private EditText et_id_set, et_pw_set, et_name_set;
    RadioGroup radioGroup;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();


        et_id_set = findViewById(R.id.et_id_set);
        et_pw_set = findViewById(R.id.et_pw_set);
        et_name_set = findViewById(R.id.et_name_set);
        radioGroup = findViewById(R.id.radioGroup);

        tv_age_set = findViewById(R.id.tv_age_set);
        tv_age_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = (View) View.inflate(SignupActivity.this, R.layout.fragment_age_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(SignupActivity.this);
                dlg.setView(dialogView);

                NumberPicker np_tens = (NumberPicker) dialogView.findViewById(R.id.np_tens);
                NumberPicker np_units = (NumberPicker) dialogView.findViewById(R.id.np_units);
                np_tens.setMinValue(0);
                np_tens.setMaxValue(9);
                np_units.setMinValue(0);
                np_units.setMaxValue(9);

                np_tens.setWrapSelectorWheel(false);
                np_units.setWrapSelectorWheel(false);

                np_tens.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                np_units.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                final int[] age = new int[2];
                np_tens.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        age[0] = newVal;
                    }
                });
                np_units.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        age[1] = newVal;
                    }
                });


                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_age_set.setText(age[0] * 10 + age[1] + "");
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        btn_signup = findViewById(R.id.btn_signup2);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioButton rdoButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String gender = rdoButton.getText().toString().toUpperCase();
                String id = et_id_set.getText().toString();
                String pw = et_pw_set.getText().toString();
                String name = et_name_set.getText().toString();
                String age = tv_age_set.getText().toString();

                if (!id.equals("") && !pw.equals("") && !name.equals("") && !gender.equals("") && !age.equals("")) {
                    saveUserToMySQL(id, pw, name, gender, age);
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignupActivity.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserToMySQL(String id, String pw, String name, String gender, String age) {
        if (gender.equals("여자")) gender = "FEMALE";
        else gender = "MALE";

        addParameterStr = "?";
        addParameterStr += "userId=" + id + "&";
        addParameterStr += "username=" + name + "&";
        addParameterStr += "gender=" + gender + "&";
        addParameterStr += "age=" + age;

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

                    if (returnData!=null) {
                        saveUserToFirebase(id, pw);
                    }
                    isr.close();
                    reader.close();
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, "데이터 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, "데이터 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }
            }
        }.start();
    }

    private void saveUserToFirebase(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
}
