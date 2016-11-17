package com.quanta.aj.yunanjian.activity.corpInf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.dao.EfCaseInfoDAO;
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.mywegit.Title_Big_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.Page;
import com.quanta.aj.yunanjian.page.CorpChoisePage;
import com.quanta.aj.yunanjian.util.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
@ContentView(R.layout.activity_query_cpinf)
public class QueryCPINFActivity extends BaseActivity {
    private static final String TAG = "QueryCPINFActivity";
    public static Intent getIntent(Context ctx, String who) {
        Intent intent = new Intent(ctx, QueryCPINFActivity.class);
        intent.putExtra("who", who);
        return intent;
    }
    @ViewInject(R.id.queryCPINFTT)
    private Title_Big_Layout queryCPINFTT;
    @ViewInject(R.id.corpname)//输入的公司名称
    private EditText qymc;
    @ViewInject(R.id.searchcorp)//查询按钮
    private Button searchcorp;
    @ViewInject(R.id.comit)//确定按钮
    private Button comit;
    @ViewInject(R.id.corplist)//公司列表
    private ListView corplist;

    private int navId;
    private Context mContext;
    private MyListAdapter mAdapter;
    //private List<Actionmenu> mData = null;
    private ProgressDialog prgDlg;
    private ProgressDialog progressDialog;
    private EfCaseInfoDAO caseDao;
    private String corpId;//用来保存选择的企业的ID
    private CorpInfo corp;

    private int pageSize= 30;// 每页显示的条数
    private int curPage= 1;
    private int rowCount= 0;
    private int pageCount= 0;// 总页数
    private String who;

    private boolean isBottom=false;// 判断是否滚动到数据最后一条
    private List<Object> corps = null;//企业信息列表



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData(null,corps);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        queryCPINFTT.setTitle("企业信息");
        //queryCPINFTT.setTextColor(R.color.hei);
        mContext = QueryCPINFActivity.this;
        who = getIntent().getStringExtra("who");
        getcorps(null);
    }

    private void getcorps(final String qymc) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final CorpChoisePage page = new CorpChoisePage();
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.CORPINF_URL);
                params.addHeader("Cookie","JSESSIONID="+Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.addParameter("page",page);
                params.addBodyParameter("qymc",qymc);
                params.addBodyParameter("dwlx","");
                params.addParameter("user", MyApplication.getRemoteUser(mContext));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> pages = JsonUtils.parseObject(s, Page.class);
                        corps =pages.rows;
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"连接失败",throwable);
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

    /**
     * 获取执法案件列表自身库表数据
     * @param qymc 检索内容（企业名称）
     */
    private void initData(String qymc,List<Object> items) {
        //对数据内容进行循环处理
        mAdapter = new MyListAdapter<Object>(items,R.layout.item_corp) {
            @Override
            public void bindView(ViewHolder holder, final Object obj) {
                String corpInfoJson= JsonUtils.toString(obj);
                GsonBuilder gsonBuilder=new GsonBuilder();
                gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
                Gson gson = gsonBuilder.create();
                final CorpInfo corpInfo= gson.fromJson(corpInfoJson,CorpInfo.class);
                holder.setText(R.id.corp_name,corpInfo.getQymc());
                if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(CorpInfActivity.getIntent(mContext,corpInfo,who));
                    }
                });
            }
        };
        //赋值到页面中显示
        corplist.setAdapter(mAdapter);
    }

    @Event(value = {R.id.searchcorp})
    private void cilnk(View v){
        switch ( v.getId()){
            case R.id.searchcorp://点击了查询按钮
                toSearch();
                break;
            default:break;
        }
    }

    private void toSearch() {
        String search = qymc.getText().toString().trim();
        if (search.isEmpty()) {
            search = null;
        }
        //进入查询显示操作
        getcorps(search);
    }
}
