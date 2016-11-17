package com.quanta.aj.yunanjian.activity.gps;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.util.ActivityCollector;

import org.xutils.x;

public class TabHostTestActivity extends TabActivity {
    public static Intent getIntent(Context ctx) {
        Intent intent = new Intent(ctx,TabHostTestActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tab_host_test);
        x.view().inject(this);
        ActivityCollector.addActivity(this);
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_tab_host_test,tabHost.getTabContentView(),true);
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("当前位置")
                .setContent(new Intent(this,GpsMianActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("呼出电话",
                        getResources().getDrawable(R.drawable.action_gps))
                .setContent(R.id.tab02));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("未接电话")
                .setContent(R.id.tab03));
        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator("画廊")
                .setContent(new Intent(this,GalleryTestActivity.class)));
    }
}
