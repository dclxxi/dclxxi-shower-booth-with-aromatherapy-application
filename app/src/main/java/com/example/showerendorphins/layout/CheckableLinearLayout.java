package com.example.showerendorphins.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.example.showerendorphins.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() { // 현재 Checked 상태 리턴
        CheckBox cb_aroma_modify = findViewById(R.id.aroma_cb_modify);

        return cb_aroma_modify.isChecked();
    }

    @Override
    public void toggle() { // 현재 Checked 상태 변경 (UI에 반영)
        CheckBox cb_aroma_modify = findViewById(R.id.aroma_cb_modify);

        setChecked(cb_aroma_modify.isChecked() ? false : true);
    }

    @Override
    public void setChecked(boolean checked) { // Checked 상태를 checked 변수대로 설정
        CheckBox cb_aroma_modify = findViewById(R.id.aroma_cb_modify);

        if (cb_aroma_modify.isChecked() != checked) {
            cb_aroma_modify.setChecked(checked);
        }
    }
}