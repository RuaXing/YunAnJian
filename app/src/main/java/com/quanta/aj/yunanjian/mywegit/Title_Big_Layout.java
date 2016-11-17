package com.quanta.aj.yunanjian.mywegit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.Index_Menu_Activity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 自定义控件--标题栏
 * Created by 罗鑫 on 2016/8/19.
 */
public class Title_Big_Layout extends LinearLayout {
    @ViewInject(R.id.mtitlytext)
    TextView mtitlytext;
    @ViewInject(R.id.pass_backText)
    TextView pass_backText;
    @ViewInject(R.id.pass_meunText)
    TextView pass_meunText;
    public Title_Big_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titel,this);
        x.view().inject(this);
        //设置返回上一页面按钮
        findViewById(R.id.pass_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });

        //设置返回首页菜单按钮
        findViewById(R.id.pass_meun).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Activity)getContext(), Index_Menu_Activity.class);
                ((Activity)getContext()).startActivity(intent);
            }
        });
    }
    public void setTitle(String title){
       mtitlytext.setText(title);
    }
    public void setTextColor(int color) { mtitlytext.setTextColor(color);pass_backText.setTextColor(color);pass_meunText.setTextColor(color);}
    public void setTextSize(float size) {mtitlytext.setTextSize(size);}

}
