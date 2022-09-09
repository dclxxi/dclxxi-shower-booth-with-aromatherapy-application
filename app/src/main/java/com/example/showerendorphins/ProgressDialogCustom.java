package com.example.showerendorphins;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

public class ProgressDialogCustom extends Dialog {
    public ProgressDialogCustom(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //title 없이
        setContentView(R.layout.loading);

//        //라이브러리 로딩이미지 사용
//        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);

    }
}