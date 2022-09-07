package com.example.showerendorphins;

import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateUserInfo extends AppCompatActivity {

    String urlStr = "http://ec2-43-200-238-1.ap-northeast-2.compute.amazonaws.com:8080/User/updateUser";  //IPv4 주소 변경해야 함
    private static String addParameterStr = "";

    TextView tv_id_set, tv_age_set2;
    EditText tv_name_set;
    RadioGroup radioGroup2;
    RadioButton rb_woman2, rb_man2;
    Button btn_signup2;

    int usercode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        tv_id_set = findViewById(R.id.tv_id_set);
        tv_age_set2 = findViewById(R.id.tv_age_set2);
        tv_name_set = findViewById(R.id.tv_name_set);
        radioGroup2 = findViewById(R.id.radioGroup2);
        rb_woman2 = findViewById(R.id.rb_woman2);
        rb_man2 = findViewById(R.id.rb_man2);
        btn_signup2 = findViewById(R.id.btn_signup2);


        Intent intent = getIntent();

        usercode = Integer.parseInt(intent.getStringExtra("code"));
        tv_id_set.setText(intent.getStringExtra("email"));
        tv_name_set.setText(intent.getStringExtra("username"));
        tv_age_set2.setText(intent.getStringExtra("age"));
        String gender = intent.getStringExtra("gender");

        if (gender.equals("MALE")) {
            rb_man2.setChecked(true);
        } else if (gender.equals("FEMALE")) {
            rb_woman2.setChecked(true);
        }

        tv_age_set2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = (View) View.inflate(UpdateUserInfo.this, R.layout.fragment_age_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(UpdateUserInfo.this);
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
                        tv_age_set2.setText(age[0] * 10 + age[1] + "");
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        btn_signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rdoButton = findViewById(radioGroup2.getCheckedRadioButtonId());
                String gender = rdoButton.getText().toString().toUpperCase();
                String id = tv_id_set.getText().toString();
                String name = tv_name_set.getText().toString();
                String age = tv_age_set2.getText().toString();

                if (!id.equals("") && !name.equals("") && !gender.equals("") && !age.equals("")) {
                    saveUserToMySQL(usercode, id, name, gender, age);
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(UpdateUserInfo.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserToMySQL(Integer code, String id, String name, String gender, String age) {

        String returnData = "";
        if (gender.equals("여자")) gender = "FEMALE";
        else gender = "MALE";

        addParameterStr = "?";
        addParameterStr += "code=" + code + "&";
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


                    isr.close();
                    reader.close();
                    is.close();

                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!returnData.equals("")) {
                                Toast.makeText(UpdateUserInfo.this, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdateUserInfo.this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UpdateUserInfo.this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UpdateUserInfo.this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }
            }
        }.start();
    }
}