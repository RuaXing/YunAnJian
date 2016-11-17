package com.quanta.aj.yunanjian.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.enforcement.Menu_EnforcingActivity;
import com.quanta.aj.yunanjian.activity.gps.GpsActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.mywegit.Dialog_Userinfo;
import com.quanta.aj.yunanjian.orm.Actionmenu;
import com.quanta.aj.yunanjian.util.ActivityCollector;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_action_menu)
public class Index_Menu_Activity extends BaseActivity {

    private int navId;

    private Context mContext;
    private BaseAdapter mAdapter = null;
    private ArrayList<Actionmenu> mData = null;

    @ViewInject(R.id.action_menu)
    GridView action_menu;
    @ViewInject(R.id.quit_app)
    TextView quit_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        mContext = Index_Menu_Activity.this;
        mData = new ArrayList<Actionmenu>();
        mData.add(new Actionmenu(R.drawable.action_qyxxjg,getString(R.string.mn1)));
        mData.add(new Actionmenu(R.drawable.action_ydzfgl,getString(R.string.mn2)));
        mData.add(new Actionmenu(R.drawable.action_msdscx,getString(R.string.mn3)));
        mData.add(new Actionmenu(R.drawable.action_aqzsk,getString(R.string.mn4)));
        mData.add(new Actionmenu(R.drawable.action_yhpczl,getString(R.string.mn5)));
        mData.add(new Actionmenu(R.drawable.action_wxyjg,getString(R.string.mn6)));
        mData.add(new Actionmenu(R.drawable.action_aqsjfx,getString(R.string.mn7)));
        mData.add(new Actionmenu(R.drawable.action_gps,getString(R.string.mn8)));
        mData.add(new Actionmenu(R.drawable.action_aqyj,getString(R.string.mn9)));

        mAdapter = new MyAdapter<Actionmenu>(mData,R.layout.item_actions) {
            @Override
            public void bindView(MyAdapter.ViewHolder holder, Actionmenu obj) {
                holder.setImageResource(R.id.action_icon,obj.getiId());
                holder.setText(R.id.action_name,obj.getActionName());
            }
        };

        action_menu.setAdapter(mAdapter);

        action_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://企业信息监管
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    case 1://移动执法管理
                        startActivity(Menu_EnforcingActivity.getIntent(mContext,Menu_EnforcingActivity.class));
                        break;
                    case 2://MSDS查询
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    case 3://安全知识库
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    case 4://隐患排查管理
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    case 5://危险源监管
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    case 6://安全数据分析
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    case 7://GPS服务
                        //showShortToast(4,"该功能尚未上线，敬请期待！");
                        //startActivity(GpsMianActivity.getIntent(mContext,GpsMianActivity.class));
                        //startActivity(TabHostTestActivity.getIntent(mContext));
                        //startActivity(GalleryTestActivity.getIntent(mContext,GalleryTestActivity.class));
                        startActivity(new Intent(mContext, GpsActivity.class));
                        break;
                    case 8://监测预警
                        showShortToast(4,getString(R.string.noaction));
                        break;
                    default:break;
                }
            }
        });
    }
    @Event(value = {R.id.quit_app,R.id.app_user})
    private void clink(View v){
        switch (v.getId()){
            case R.id.quit_app:
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
               /* Intent intent = new Intent(mContext,QuitApp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                break;
            case R.id.app_user:
                startActivity(Dialog_Userinfo.getIntent(mContext,Dialog_Userinfo.class));
                break;
            default:break;
        }
    }
}
