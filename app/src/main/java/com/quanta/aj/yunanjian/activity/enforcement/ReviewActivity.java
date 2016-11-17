package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.LoginActivity;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfReview;
import com.quanta.aj.yunanjian.orm.KeyValue;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPage;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPageItem;
import com.quanta.aj.yunanjian.util.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewActivity extends MFragmentActivity {
    private static final String TAG = "ReviewActivity";
    public static Intent getIntent(Context ctx, String mefReview) {
        Intent intent = new Intent(ctx, ReviewActivity.class);
        intent.putExtra("mefReview", mefReview);
        return intent;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    corpInfo = getGson().fromJson(msg.obj.toString(),CorpInfo.class);
                    setCorpInfo();
                    break;
                case 2:
                    initDataY(docmentesY);
                    break;
                case 3:
                    finish();
                    break;
                default:break;
            }
        }
    };
    @ViewInject(R.id.qyxx)
    private ListView qyxx;
    @ViewInject(R.id.ef_list)
    private ListView ef_list;
    @ViewInject(R.id.immediateProblem)
    private TextView immediateProblem;
    @ViewInject(R.id.limitTime)
    private TextView limitTime;
    @ViewInject(R.id.limitProblem)
    private TextView limitProblem;
    @ViewInject(R.id.status)
    private TextView status;
    @ViewInject(R.id.comit)
    private TextView comit;
    @ViewInject(R.id.endef)
    private TextView endef;
    @ViewInject(R.id.doef)
    private TextView doef;
    @ViewInject(R.id.addefbtn)
    private LinearLayout addefbtn;

    private EfReview efReview;
    private MyListAdapter mylistAdapter;
    private Context mContext;
    private CorpInfo corpInfo;
    private ArrayList<KeyValue> mData;
    private MyAdapter corpinfAdapter;
    private List<EfDocumentPageItem> docmentesY = null;
    private int navId = 100;
    private Boolean isDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        x.view().inject(this);
        mContext = ReviewActivity.this;
        String mEfreviewJson = getIntent().getStringExtra("mefReview");
        efReview = getGson().fromJson(mEfreviewJson,EfReview.class);
        //如果已处理，隐藏相应按键
        if (null!=efReview&&efReview.getStatus().equals("1")){
            comit.setVisibility(View.GONE);
            endef.setVisibility(View.GONE);
            doef.setVisibility(View.GONE);
            addefbtn.setVisibility(View.GONE);
        }
        initData(efReview);
        getCorp();
    }

    private void initData(EfReview efReview) {
        if (null!=efReview){
            immediateProblem.setText(efReview.getImmediateProblem());
            limitTime.setText(getFmt().format(efReview.getLimitTime()));
            limitProblem.setText(efReview.getLimitProblem());
            status.setText(efReview.getStatus().equals("0")?"待复查":"已复查");
            isDone = efReview.getStatus().equals("0")?false:true;
            getDocYList();
        }else {
            showShortToast(3,"数据已丢失！");
        }
    }
    private void getCorp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETCORP_URL);
                params.addBodyParameter("corpId",efReview.getCorp());
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
    private void setCorpInfo() {
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
    private void getDocYList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfDocumentPage page = new EfDocumentPage();
                page.setReviewId(efReview.getId());
                page.setStatus("1");
                page.setBuildType("3");
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOMS_URL);
                params.addBodyParameter("reviewId",page.getReviewId());
                params.addBodyParameter("status","1");
                params.addBodyParameter("buildType","3");
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
                String type = efDocument.getType();
               // if (type.equals(DocumentType.d1.type))isD1 = true;
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
    private void toComit(final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                efReview.setStatus(s);
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEREVIW_URL);
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(efReview));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == 1){
                            showShortToast(4,"操作成功！");
                            //如果是结束复查，则关闭当前窗口！
                            if (s.equals("1")) {
                                handler.sendEmptyMessage(3);
                            }
                        }else {
                            showShortToast(3,"操作失败！");
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
    private void toendef(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                efReview.setStatus("1");
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVECASEREVIW_URL);
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(efReview));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == 1){
                            showShortToast(4,"操作成功！");
                            finish();
                        }else {
                            showShortToast(3,"操作失败！");
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
    @Event(value = {R.id.leftbtn,R.id.comit,R.id.limitTime,R.id.endef,R.id.doef,R.id.addefbtn})
    private void clink(View v){
        if (efReview.getStatus().equals("1")&&v.getId()!=R.id.leftbtn){
            showShortToast(4,"不能对已结束案件作此操作");
        }else {
            switch (v.getId()){
                case R.id.addefbtn:
                    startActivityForResult(ReviewDocnotdoActivity.getIntent(mContext,efReview),navId);
                    break;
                case R.id.leftbtn:
                    finish();
                    break;
                case R.id.comit:
                    startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"确定要提交么？"),1);
                    break;
                case R.id.limitTime:
                    SlideDateTimeListener limitTimelistener = new SlideDateTimeListener() {
                        @Override
                        public void onDateTimeSet(Date date) {
                            limitTime.setText(getFmt().format(date));
                        }
                    };
                    addTime(limitTimelistener);
                    break;
                case R.id.doef:
                    //toComit("1");
                    startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"是否确认本次整改复查办结？"),2);
                    break;
                case R.id.endef:
                    //toendef();
                    startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"结束本次整改复查并转入案件处理环节，是否确定本次操作？"),3);
                    break;
                default:break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK)toComit("0");
                break;
            case 2:
                if (resultCode == RESULT_OK)toComit("1");
                break;
            case 3:
                if (resultCode == RESULT_OK)toendef();
                break;
            case 100:
                getDocYList();
                break;
        }
    }
}
