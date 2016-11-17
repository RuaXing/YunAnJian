package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.activity.corpInf.QueryCPINFActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.mywegit.Title_Big_Layout;
import com.quanta.aj.yunanjian.orm.Actionmenu;
import com.quanta.aj.yunanjian.orm.Page;
import com.quanta.aj.yunanjian.page.enforcement.EfCaseInfoPage;
import com.quanta.aj.yunanjian.page.enforcement.EfInspectPage;
import com.quanta.aj.yunanjian.page.enforcement.EfReviewPage;
import com.quanta.aj.yunanjian.util.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_law_enforcing)
public class Menu_EnforcingActivity extends BaseActivity {
    private static final String TAG = "Menu_EnforcingActivity";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getReviwNum();
                    break;
                case 2:
                    getInsNum();
                    break;
                case 3:
                    initData();
                    break;
            }
        }
    };
    @ViewInject(R.id.ef_title)
    private Title_Big_Layout ef_title;
    @ViewInject(R.id.ef_menu)
    private GridView ef_menu;

    private Context mContext;
    private BaseAdapter mAdapter = null;
    private ArrayList<Actionmenu> mData = null;
    private String insNum;
    private String reViewNum;
    private String enfNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ef_title.setTitle("移动执法管理");
        //ef_title.setTextColor(R.color.hei);
        mContext = Menu_EnforcingActivity.this;
        mData = new ArrayList<Actionmenu>();
        mData.add(new Actionmenu(R.drawable.action_qyxxjg,"企业信息"));
        mData.add(new Actionmenu(R.drawable.action_ydzfgl,"安全检查"));
        mData.add(new Actionmenu(R.drawable.action_yhpczl,"整改复查"));
        mData.add(new Actionmenu(R.drawable.action_xzzf,"行政处罚"));
        getEnfNum();
    }

    private void getEnfNum(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfCaseInfoPage page = new EfCaseInfoPage();
                MyApplication app = (MyApplication) Menu_EnforcingActivity.this.getApplication();
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.CASE_URL);
                params.addParameter("page",page);
                params.addParameter("user",app.remoteUser);
                params.addBodyParameter("status","1");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> pages = JsonUtils.parseObject(s, Page.class);
                        enfNum = String.valueOf(pages.getRows().size());
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求出错！",throwable);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }

    private void getReviwNum() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfReviewPage page = new EfReviewPage();
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.REVIW_URL);
                params.addParameter("page",page);
                params.addBodyParameter("status","0");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> page = JsonUtils.parseObject(s,Page.class);
                        reViewNum = String.valueOf(page.getTotal());
                        handler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求出错！",throwable);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }

    private void getInsNum() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+ URLs.EFORCEMRNTPAGE_URL);
                EfInspectPage page = new EfInspectPage();
                params.addParameter("page",page);
                params.addBodyParameter("status","0");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> page = JsonUtils.parseObject(s,Page.class);
                        insNum = String.valueOf(page.getTotal());
                        handler.sendEmptyMessage(3);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"获取数据出错！",throwable);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }

    private void initData() {
        mAdapter = new MyAdapter<Actionmenu>(mData,R.layout.item_actions_withnum) {
            @Override
            public void bindView(ViewHolder holder, Actionmenu obj) {
                holder.setImageResource(R.id.action_icon,obj.getiId());
                holder.setText(R.id.action_name,obj.getActionName());
                if (obj.getActionName().equals("企业信息")){
                    holder.setVisibility(R.id.number, View.GONE);
                }
                if (obj.getActionName().equals("安全检查")){
                    holder.setText(R.id.number,insNum);
                }
                if (obj.getActionName().equals("整改复查")){
                    holder.setText(R.id.number,reViewNum);
                }
                if (obj.getActionName().equals("行政处罚")){
                    holder.setText(R.id.number,enfNum);
                }
            }
        };
        ef_menu.setAdapter(mAdapter);
        ef_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://企业信息
                        startActivityForResult(QueryCPINFActivity.getIntent(mContext,QueryCPINFActivity.class),100);
                        break;
                    case 1://现场检查
                        startActivityForResult(InspectionListActivity.getIntent(mContext,InspectionListActivity.class),100);
                        break;
                    case 2://整改复查
                        startActivityForResult(ReviewList.getIntent(mContext,ReviewList.class),100);
                        break;
                    case 3://行政处罚
                        startActivityForResult(CaseListActivity.getIntent(mContext,CaseListActivity.class),100);
                        break;
                    default:break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                getEnfNum();
                break;
            default:break;
        }
    }
}
