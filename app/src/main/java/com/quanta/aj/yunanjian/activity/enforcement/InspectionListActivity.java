package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.activity.corpInf.QueryCPINFActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Title_Big_Layout;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.Page;
import com.quanta.aj.yunanjian.page.enforcement.EfInspectPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.MEfInspect;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class InspectionListActivity extends BaseActivity {
    private static final String TAG = "InspectionListActivity";
    public static Intent getIntent(Context ctx,Boolean isNew){
        Intent intent = new Intent(ctx, InspectionListActivity.class);
        intent.putExtra("isNew", isNew);//用来判断是不是从新建案件跳过来的，是的话默认打开第一项
        return intent;
    }
    @ViewInject(R.id.asitt)
    private Title_Big_Layout asitt;
    @ViewInject(R.id.xcjclist)
    ListView xcjclist;


    private Context mContext;
    private MyListAdapter myListAdapter;
    private ArrayList<EfCaseInfo> mData;
    private List<Object> inspects;
    private Boolean isNew;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData(inspects);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot__inspection);
        x.view().inject(this);
        asitt.setTitle("安全检查");
        //asitt.setTextColor(R.color.hei);
        mContext = InspectionListActivity.this;
        isNew = getIntent().getBooleanExtra("isNew",false);
        getData("0");
    }

    /**
     * 获取安全检查数据
     */
    private void getData(final String status) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.EFORCEMRNTPAGE_URL);
                EfInspectPage page = new EfInspectPage();
                params.addParameter("page",page);
                params.addBodyParameter("status",status);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> page = JsonUtils.parseObject(s,Page.class);
                        inspects = page.getRows();
                        handler.sendEmptyMessage(1);
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
    private void initData(List<Object> items) {
        myListAdapter = new MyListAdapter<Object>(items,R.layout.item_xcjc) {
            @Override
            public void bindView(ViewHolder holder, Object obj) {
                String efInspectJson= JsonUtils.toString(obj);
                final MEfInspect efInspect = getGson().fromJson(efInspectJson,MEfInspect.class);
                holder.setText(R.id.corp_name,efInspect.getCorpName());
                holder.setText(R.id.time,getFmt().format(efInspect.getStartTime())+"至"+getFmt().format(efInspect.getEndTime()));
                holder.setText(R.id.status,efInspect.getStatus().equals("0")?"待检查":"已检查");
                if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                if (efInspect.getStatus().equals("1")){
                    holder.setImageResource(R.id.img,R.drawable.jc_green);
                    holder.setText(R.id.title,"已检查");
                    holder.setTextColor(R.id.title,mContext.getResources().getColor(R.color.green));
                }else if(efInspect.getStatus().equals("0")){
                    holder.setImageResource(R.id.img,R.drawable.jc_red);
                    holder.setText(R.id.title,"待检查");
                    holder.setTextColor(R.id.title,mContext.getResources().getColor(R.color.errtext));
                }
                holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (efInspect.getStatus().equals("0")){
                            startActivityForResult(InspectInfoNewActivity.getIntent(mContext,efInspect),100);
                        }else {
                            startActivity(InspectInfoActivity.getIntent(mContext,efInspect));
                        }
                    }
                });
            }
        };
        xcjclist.setAdapter(myListAdapter);
        if (isNew){
            isNew = false;
            MEfInspect inspect = new MEfInspect();
            String ins = JsonUtils.toString(xcjclist.getItemAtPosition(0));
            inspect = getGson().fromJson(ins,MEfInspect.class);
            startActivityForResult(InspectInfoNewActivity.getIntent(mContext,inspect),100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                getData("0");
                break;
            default:break;
        }
    }

    @Event(value ={ R.id.xjjc,R.id.lookall,R.id.lookwz})
    private void clink(View v){
        switch (v.getId()){
            case R.id.xjjc:
                startActivity(QueryCPINFActivity.getIntent(mContext,"INS"));
                break;
            case R.id.lookall:
                getData(null);
                break;
            case R.id.lookwz:
                getData("0");
                break;
            default:break;
        }
    }
}
