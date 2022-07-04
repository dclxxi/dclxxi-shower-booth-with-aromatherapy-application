package com.example.showerendorphins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    Button btn_signup;
    TextView tv_age_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
