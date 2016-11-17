package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.dao.EfCaseInfoDAO;
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.KeyValue;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.CaseForm;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class CaseAddActivity extends BaseActivity {

    private static final String TAG = "CaseAddActivity";

    private Context mContext;
    private int navId;
    private EfCaseInfoDAO caseDao;
    private MyListAdapter efListAdapter;
    private MyAdapter corpinfAdapter;
    //企业信息
    private CorpInfo corpInfo;
    //案件信息
    private EfCaseInfo caseInfo;
    private ArrayList<KeyValue> mData;
    private CaseForm form = new CaseForm();
    private String lhzf = "N";
    private String wblhzf = "N";

    //执法信息

    /**
     *
     * @param ctx
     * @param corp
     * @return
     */
    public static Intent getIntent(Context ctx, CorpInfo corp) {
        Intent intent = new Intent(ctx, CaseAddActivity.class);
        intent.putExtra("corp", corp);
        return intent;

    }

    @ViewInject(R.id.qyxx)
    private ListView qyxx;
    @ViewInject(R.id.unionable)
    private RadioGroup unionable;
    @ViewInject(R.id.ounionable)
    private RadioGroup ounionable;
    @ViewInject(R.id.yes)
    private RadioButton yes;
    @ViewInject(R.id.no)
    private RadioButton no;
    @ViewInject(R.id.oyes)
    private RadioButton oyes;
    @ViewInject(R.id.ono)
    private RadioButton ono;
   /* @ViewInject(R.id.unit)
    private EditText unit;*/
    /*@ViewInject(R.id.status)
    private TextView status;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_no_edit);
        x.view().inject(this);
        //联合执法
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yes.isChecked())lhzf="Y";
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (no.isChecked())lhzf="N";
            }
        });
        oyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oyes.isChecked())wblhzf="Y";
            }
        });
        ono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ono.isChecked())wblhzf="N";
            }
        });
        setCorpInfo();
    }

    private void setCorpInfo() {
        //获取上个活动传来的数据
        corpInfo = (CorpInfo) getIntent().getSerializableExtra("corp");
        if (corpInfo == null) {
            showShortToast(3,"未找到提供的参数");
            finish();
            return;
        }
        //组装企业信息
        mData = new ArrayList<KeyValue>();
        mData.add(new KeyValue("被检查企业",corpInfo.getQymc()));
        mData.add(new KeyValue("企业地址",corpInfo.getZcdz()));
        mData.add(new KeyValue("法人代表",corpInfo.getFrdb()));
        mData.add(new KeyValue("联系电话",corpInfo.getFrdblxdh()));
        mData.add(new KeyValue("安全负责人",corpInfo.getAqfzr()!=null?corpInfo.getAqfzr():corpInfo.getZyfzr()));
        mData.add(new KeyValue("负责人电话",corpInfo.getAqfzrlxdh()!=null?corpInfo.getAqfzrlxdh():corpInfo.getZyfzrlxdh()));

        corpinfAdapter = new MyAdapter<KeyValue>(mData,R.layout.item_table_row) {
            @Override
            public void bindView(ViewHolder holder, KeyValue obj) {
                holder.setText(R.id.key,obj.getKey());
                holder.setText(R.id.value,obj.getValue());
            }
        };
        qyxx.setAdapter(corpinfAdapter);
    }
    /**
     * 界面上的按钮点击事件
     * @param view
     */
    @Event(value = {R.id.leftbtn,R.id.comit})
    private void clink(View view){
        switch (view.getId()){
            case R.id.leftbtn:
                finish();
                break;
            case R.id.comit:
                savecase();
                break;
            default:break;
        }
    }

    private void savecase() {
        Log.d(TAG,"savecaseIN");
        form.setCorp(corpInfo.getId());
        form.setUnionable(lhzf);
        form.setOunionable(wblhzf);
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVECASE_URL);
                MyApplication app = (MyApplication) CaseAddActivity.this.getApplication();
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(form));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG,"CASE :"+s);
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == 1){
                            showShortToast(1,"操作成功！");
                            startActivity(CaseListActivity.getIntent(CaseAddActivity.this,true));
                        }
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
}
