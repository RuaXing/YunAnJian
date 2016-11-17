package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.LoginActivity;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.MCode;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.KeyValue;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPage;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPageItem;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.MEfInspect;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InspectInfoActivity extends MFragmentActivity {
    private static final String TAG = "InspectInfoActivity";
   public static Intent getIntent(Context ctx, MEfInspect efInspect) {
        Intent intent = new Intent(ctx, InspectInfoActivity.class);
        intent.putExtra("efInspect", efInspect);
        return intent;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setCorpInfo(msg.obj.toString());
                    break;
                case 2:
                    initDataY(docmentesY);
                    break;
                case 3:
                    type.setText(msg.obj.toString());
                    break;
            }
        }
    };
    @ViewInject(R.id.qyxx)
    private ListView qyxx;
    @ViewInject(R.id.type)
    private TextView type;
    @ViewInject(R.id.startTime)
    private TextView startTime;
    @ViewInject(R.id.endTime)
    private TextView endTime;
    @ViewInject(R.id.leader)
    private TextView leader;
    @ViewInject(R.id.member)
    private TextView member;
    @ViewInject(R.id.status)
    private TextView status;
    @ViewInject(R.id.ef_list)
    private ListView ef_list;

    private MEfInspect mEfInspect;
    private Context mContext;
    private MyAdapter corpinfAdapter;
    private CorpInfo corpInfo;
    private ArrayList<KeyValue> mData;
    private MCode mcode = new MCode();
    private List<EfDocumentPageItem> docmentesY = null;
    private MyListAdapter mylistAdapter;
    private int navId = 100;
    private Boolean isDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_info);
        x.view().inject(this);
        mContext = InspectInfoActivity.this;
        mEfInspect = (MEfInspect)getIntent().getSerializableExtra("efInspect");
        getCorp();
        initInspect();
    }
    private void getCorp() {
        //mEfInspect =(MEfInspect) getIntent().getSerializableExtra("inspect");
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETCORP_URL);
                params.addBodyParameter("corpId",mEfInspect.getCorp());
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == -10000){
                            showShortToast(3,"登录超时！");
                            startActivity(LoginActivity.getIntent(mContext,LoginActivity.class));
                        }
                        Message msg = new Message();
                        msg.obj = s;
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"数据出错！",throwable);
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
    private void setCorpInfo(String s) {
        if (mEfInspect == null){
            showLongToast(3,"未找到提供的参数");
            finish();
            return;
        }
        corpInfo = getGson().fromJson(s,CorpInfo.class);
        //企业信息适配器
        //组装企业信息
        mData = new ArrayList<KeyValue>();
        mData.add(new KeyValue("被检查企业",corpInfo.getQymc()));
        mData.add(new KeyValue("企业地址",corpInfo.getZcdz()));
        mData.add(new KeyValue("法人代表",corpInfo.getFrdb()));
        mData.add(new KeyValue("联系电话",corpInfo.getFrdblxdh()));
        mData.add(new KeyValue("安全负责人",corpInfo.getAqfzr()));
        mData.add(new KeyValue("负责人电话",corpInfo.getAqfzrlxdh()));

        corpinfAdapter = new MyAdapter<KeyValue>(mData,R.layout.item_table_row) {
            @Override
            public void bindView(ViewHolder holder, KeyValue obj) {
                holder.setText(R.id.key,obj.getKey());
                holder.setText(R.id.value,obj.getValue());
            }
        };
        qyxx.setAdapter(corpinfAdapter);
    }
    private void initInspect() {
        if (mEfInspect == null){
            showShortToast(3,"没得到案件信息！");
        }else {
            Log.d(TAG,"mEfInspect"+JsonUtils.toString(mEfInspect));
            int code = Integer.parseInt(null!=mEfInspect.getType()?mEfInspect.getType():"1");
            mcode.getCode("JCFS",code-1,3,handler,mContext);
            startTime.setText(getFmt().format(mEfInspect.getStartTime()));
            endTime.setText(getFmt().format(mEfInspect.getEndTime()));
            leader.setText(mEfInspect.getLeader());
            member.setText(mEfInspect.getMember());
            status.setText(mEfInspect.getStatus().equals("0")?"待检查":"已检查");
            isDone = mEfInspect.getStatus().equals("0")?false:true;
            getDocYList();
        }
    }
    private void getDocYList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfDocumentPage page = new EfDocumentPage();
                page.setInspectId(mEfInspect.getId());
                page.setStatus("1");
                page.setBuildType("2");
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOMS_URL);
                params.addBodyParameter("inspectId",page.getInspectId());
                params.addBodyParameter("status","1");
                params.addBodyParameter("buildType","2");
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        EfDocumentPage page = getGson().fromJson(s,EfDocumentPage.class);
                        docmentesY = page.getRows();
                        handler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求出错",throwable);
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
    private void initDataY(List<EfDocumentPageItem> items) {
        mylistAdapter = new MyListAdapter<EfDocumentPageItem>(items,R.layout.item_ef_list) {
            @Override
            public void bindView(ViewHolder holder, EfDocumentPageItem obj) {
                String docmentesJson = JsonUtils.toString(obj);
                final EfDocument efDocument = getGson().fromJson(docmentesJson,EfDocument.class);
                holder.setText(R.id.ef_name,efDocument.getName());
                if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                if (efDocument.getCreateTime() != null){
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    holder.setText(R.id.ef_time, fmt.format(efDocument.getCreateTime()));
                }else {
                    holder.setText(R.id.ef_time, "");
                }
                holder.setText(R.id.status,efDocument.getStatus().equals("0")?"未做":(efDocument.getStatus().equals("1")?"未生效":"已生效"));
                if (efDocument.getStatus().equals("1")){
                    holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.errtext));
                }else {
                    holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.green));
                }
                holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击图标打开相应文书信息界面
                        toItemClink(efDocument);
                    }
                });
            }
        };
        ef_list.setAdapter(mylistAdapter);
    }
    private void toItemClink(EfDocument efDocument) {
        //由于这里是已做文书，只要判断文书种类并传值即可
        String docName = efDocument.getName();
        if (DocumentType.d1.name.equals(docName)){
            startActivityForResult(Document01Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d2.name.equals(docName)){
            startActivityForResult(Document02Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d3.name.equals(docName)){
            startActivityForResult(Document03Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d4.name.equals(docName)){
            startActivityForResult(Document04Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d5.name.equals(docName)){
            startActivityForResult(Document05Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d6.name.equals(docName)){
            startActivityForResult(Document06Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d7.name.equals(docName)){
            startActivityForResult(Document07Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d8.name.equals(docName)){
            startActivityForResult(Document08Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d9.name.equals(docName)){
            startActivityForResult(Document09Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d10.name.equals(docName)){
            startActivityForResult(Document10Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d11.name.equals(docName)){
            startActivityForResult(Document11Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d12.name.equals(docName)){
            startActivityForResult(Document12Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d13.name.equals(docName)){
            startActivityForResult(Document13Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d14.name.equals(docName)){
            startActivityForResult(Document14Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d15.name.equals(docName)){
            startActivityForResult(Document15Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d16.name.equals(docName)){
            startActivityForResult(Document16Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d17.name.equals(docName)){
            startActivityForResult(Document17Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d18.name.equals(docName)){
            startActivityForResult(Document18Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d19.name.equals(docName)){
            startActivityForResult(Document19Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d20.name.equals(docName)){
            startActivityForResult(Document20Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d21.name.equals(docName)){
            startActivityForResult(Document21Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d22.name.equals(docName)){
            startActivityForResult(Document22Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d23.name.equals(docName)){
            startActivityForResult(Document23Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d24.name.equals(docName)){
            startActivityForResult(Document24Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d25.name.equals(docName)){
            startActivityForResult(Document25Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d26.name.equals(docName)){
            startActivityForResult(Document26Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d27.name.equals(docName)){
            startActivityForResult(Document27Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d28.name.equals(docName)){
            startActivityForResult(Document28Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d29.name.equals(docName)){
            startActivityForResult(Document29Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d30.name.equals(docName)){
            startActivityForResult(Document30Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d31.name.equals(docName)){
            startActivityForResult(Document31Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d32.name.equals(docName)){
            startActivityForResult(Document32Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d33.name.equals(docName)){
            startActivityForResult(Document33Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d34.name.equals(docName)){
            startActivityForResult(Document34Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d35.name.equals(docName)){
            startActivityForResult(Document35Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d36.name.equals(docName)){
            startActivityForResult(Document36Activity.getIntent(mContext,efDocument,isDone),navId);
        }
    }
    @Event(value = {R.id.leftbtn,R.id.addefbtn})
    private void clink(View v){
        switch (v.getId()){
            case R.id.leftbtn:
                finish();
                break;
            case R.id.addefbtn:
                showShortToast(3,"检查已结束！");
                break;
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                getDocYList();
                break;
            default:break;
        }
    }
}
