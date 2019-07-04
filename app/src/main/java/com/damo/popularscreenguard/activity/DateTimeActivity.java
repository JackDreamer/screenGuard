package com.damo.popularscreenguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;


import com.damo.popularscreenguard.R;

import static android.view.WindowManager.LayoutParams;

public class DateTimeActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_date_time);
        //取消标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 取消状态栏
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | LayoutParams.FLAG_TURN_SCREEN_ON);

    }

}
