package com.quanta.aj.yunanjian.activity.base;

import android.os.Bundle;

import com.quanta.aj.yunanjian.R;

public class QuitApp extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_app);
    }

    @Override
    protected void onStart() {
        super.onStart();
        finish();
    }
}
