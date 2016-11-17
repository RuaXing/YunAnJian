package com.quanta.aj.yunanjian.mywegit;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.quanta.aj.yunanjian.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 罗鑫 on 2016/8/30.
 */
public class Title_Sm_Layout extends LinearLayout {

    @ViewInject(R.id.title2)
    private TextView title2;

    public Title_Sm_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title2,this);
        x.view().inject(this);

        findViewById(R.id.ttbtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }
    public void setTitle2(String title){
        title2.setText(title);
    }
    public void settitleColor(int color){
        title2.setTextColor(color);
    }
    public void setTitleSize(float size){
        title2.setTextSize(size);
    }
}
