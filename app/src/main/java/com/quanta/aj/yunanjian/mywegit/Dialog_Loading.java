package com.quanta.aj.yunanjian.mywegit;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;

/**
 * 加载中Dialog
 * 
 * @author james.li
 */
public class Dialog_Loading extends AlertDialog {

    private TextView tips_loading_msg;

    private String message = null;

    public Dialog_Loading(Context context) {
        super(context);
        message = getContext().getResources().getString(R.string.msg_load_ing);
    }

    public Dialog_Loading(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(false);
    }

    public Dialog_Loading(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

}

