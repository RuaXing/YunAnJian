package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.activity.corpInf.QueryCPINFActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.mywegit.Title_Big_Layout;
import com.quanta.aj.yunanjian.orm.Page;
import com.quanta.aj.yunanjian.page.enforcement.EfCaseInfoPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.MEFcaseInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class CaseListActivity extends BaseActivity {
    private static final String TAG = "CaseListActivity";
    public static Intent getIntent(Context ctx,Boolean isNew){
        Intent intent = new Intent(ctx, CaseListActivity.class);
        intent.putExtra("isNew", isNew);//用来判断是不是从新建案件跳过来的，是的话默认打开第一项
        return intent;
    }
    private int navId = 100;

    @ViewInject(R.id.enftt)
    private Title_Big_Layout enftt;
    @ViewInject(R.id.enfcaselist)
    private ListView enfcaselist;

    private Context mContext;
    private MyListAdapter myListAdapter;
    private List<Object> efCases = null;//案件列表
    private Boolean isNew;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData(efCases);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enforce);
        x.view().inject(this);
        mContext = CaseListActivity.this;
        //enftt.setTextColor(R.color.hei);
        enftt.setTitle("行政处罚");
        isNew = getIntent().getBooleanExtra("isNew",false);
        getData("1");
    }

    @Event(value = {R.id.xjzf,R.id.lookall,R.id.lookwz})
    private void clink(View v){
        switch (v.getId()){
            case R.id.xjzf:
                startActivityForResult(QueryCPINFActivity.getIntent(mContext,"EFC"),navId);
                break;
            case R.id.lookall:
                getData(null);
                break;
            case R.id.lookwz:
                getData("1");
                break;
            default:break;
        }
    }

    private void getData(final String status) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfCaseInfoPage page = new EfCaseInfoPage();
                MyApplication app = (MyApplication) CaseListActivity.this.getApplication();
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.CASE_URL);
                params.addParameter("page",page);
                params.addParameter("user",app.remoteUser);
                params.addBodyParameter("status",status);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> pages = JsonUtils.parseObject(s, Page.class);
                        efCases = pages.rows;
                        Log.d(TAG,"NUM: "+pages.getTotal());
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

    private void initData(List<Object> items) {
       myListAdapter = new MyListAdapter<Object>(items,R.layout.item_efcase) {
           @Override
           public void bindView(ViewHolder holder, final Object obj) {
               String efCasesJson = JsonUtils.toString(obj);
               GsonBuilder gsonBuilder=new GsonBuilder();
               gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
               Gson gson = gsonBuilder.create();
               final MEFcaseInfo efCaseInfo = gson.fromJson(efCasesJson,MEFcaseInfo.class);
               holder.setText(R.id.corp_name,efCaseInfo.getCorpName());
               //时间不为空判断
               if(efCaseInfo.getTime()!=null){
                   holder.setText(R.id.time, JsonUtils.toString(efCaseInfo.getTime()));
               }else holder.setText(R.id.time,"");
               holder.setText(R.id.status,getStatus(efCaseInfo.getStatus()));
               holder.setText(R.id.unit,efCaseInfo.getUnitName());
               if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
               if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
               if (efCaseInfo.getStatus().equals("5")){
                   holder.setImageResource(R.id.igm,R.drawable.flag_green);
                   holder.setText(R.id.title,"已结案");
                   holder.setTextColor(R.id.title,mContext.getResources().getColor(R.color.green));
               }else if (efCaseInfo.getStatus().equals("1")){
                   holder.setImageResource(R.id.igm,R.drawable.flag_red);
                   holder.setText(R.id.title,"在执行");
                   holder.setTextColor(R.id.title,mContext.getResources().getColor(R.color.errtext));
               }
               holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                        //点击跳转页面
                      startActivityForResult(CaseEditActivity.getIntent(mContext,efCaseInfo),navId);
                   }
               });
           }
       };
        //赋值到页面中显示
        enfcaselist.setAdapter(myListAdapter);
        //这里实现打开第一项（用于刚新建了案件）
        if (isNew){
            isNew = false;//重置为false,防止乱跳
            MEFcaseInfo info = new MEFcaseInfo();
            String efc = JsonUtils.toString(enfcaselist.getItemAtPosition(0));
            info = getGson().fromJson(efc,MEFcaseInfo.class);
            startActivityForResult(CaseEditActivity.getIntent(mContext,info),navId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                getData("1");
                break;
        }
    }

    private String getStatus(String status) {
        /** 状态：1-在执行，5-已结案，9-已作废 **/
        String st = null;
        switch (status){
            case "1":
                st = "在执行";
                break;
            case "5":
                st = "已结案";
                break;
            case "9":
                st = "已作废";
                break;
            default:break;
        }
        return st;
    }
}
