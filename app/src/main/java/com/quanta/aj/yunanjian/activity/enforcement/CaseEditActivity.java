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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.activity.base.LoginActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.dao.EfCaseInfoDAO;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.KeyValue;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPage;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPageItem;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.MEFcaseInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CaseEditActivity extends BaseActivity {
    private static final String TAG = "CaseEditActivity";
    private Context mContext;
    private int navId = 100;
    private EfCaseInfoDAO caseDao;
    private MyListAdapter efListAdapter;
    private MyAdapter corpinfAdapter;
    //企业信息
    private CorpInfo corpInfo;
    //案件信息
    private MEFcaseInfo mcaseInfo;
    //是否有现场检查文书
    private Boolean isD1=false;
    private ArrayList<KeyValue> mData;
    private List<EfDocumentPageItem> docmentesY = null;
    private Boolean isDone;
    /**
     *
     * @param ctx
     * @param caseInfo 传过来的案件实现了序列化接口
     * @return
     */
    public static Intent getIntent(Context ctx, MEFcaseInfo caseInfo) {
        Intent intent = new Intent(ctx, CaseEditActivity.class);
        intent.putExtra("caseInfo", caseInfo);
        return intent;
    }

    @ViewInject(R.id.qyxx)
    private ListView qyxx;
    @ViewInject(R.id.unionable)
    private TextView unionable;
    @ViewInject(R.id.ounionable)
    private TextView ounionable;
    @ViewInject(R.id.unit)
    private TextView unit;
    @ViewInject(R.id.status)
    private TextView status;
    @ViewInject(R.id.ef_list)
    private ListView ef_list;
    @ViewInject(R.id.addefbtn)
    private LinearLayout addefbtn;
    @ViewInject(R.id.comit)
    private TextView comit;
    @ViewInject(R.id.endef)
    private TextView endef;



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
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_edit);
        x.view().inject(this);
        mContext = CaseEditActivity.this;
        mcaseInfo = (MEFcaseInfo) getIntent().getSerializableExtra("caseInfo");
        if (null!=mcaseInfo&&!mcaseInfo.getStatus().equals("1")){
            addefbtn.setVisibility(View.GONE);
            comit.setVisibility(View.GONE);
            endef.setVisibility(View.GONE);
        }
        getCorp();
        initCaseInfo();
        getDocYList();
    }

    private void getDocYList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfDocumentPage page = new EfDocumentPage();
                page.setCaseId(mcaseInfo.getId());
                page.setStatus("0");
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOMS_URL);
                params.addBodyParameter("caseId",page.getCaseId());
                params.addBodyParameter("status","1");
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

    /**
     * 初始化案件信息(执法信息)
     */
    private void initCaseInfo() {
        if (mcaseInfo == null){
            showShortToast(3,"没得到案件信息！");
        }else {
            unionable.setText(mcaseInfo.getUnionable().equals("Y")?"是":"否");
            ounionable.setText(mcaseInfo.getOunionable().equals("Y")?"是":"否");
            unit.setText(mcaseInfo.getUnitName());
            status.setText(getStatus(mcaseInfo.getStatus()));
            isDone = mcaseInfo.getStatus().equals("1")?false:true;
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
    /**
     * 获取关联企业信息
     */
    private void getCorp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETCORP_URL);
                params.addBodyParameter("corpId",mcaseInfo.getCorp());
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

    /**
     * 对页面中的信息赋值
     */
    private void setCorpInfo(String st){
        if (mcaseInfo == null){
            showLongToast(3,"未找到提供的参数");
            finish();
            return;
        }
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
        Gson gson = gsonBuilder.create();
        corpInfo = gson.fromJson(st,CorpInfo.class);
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
    /**
     * 获取已做成文书信息列表
     */
    private void initDataY(List<EfDocumentPageItem> items) {
            efListAdapter = new MyListAdapter<EfDocumentPageItem>(items,R.layout.item_ef_list) {
                @Override
                public void bindView(ViewHolder holder, EfDocumentPageItem obj) {
                    String docmentesJson = JsonUtils.toString(obj);
                    GsonBuilder gsonBuilder=new GsonBuilder();
                    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
                    Gson gson = gsonBuilder.create();
                    final EfDocument efDocument = gson.fromJson(docmentesJson,EfDocument.class);
                    String type = efDocument.getType();
                    if (type.equals(DocumentType.d1.type))isD1 = true;
                    if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                    if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                    holder.setText(R.id.ef_name,efDocument.getName());
                    holder.setText(R.id.status,efDocument.getStatus().equals("0")?"未做":(efDocument.getStatus().equals("1")?"未生效":"已生效"));
                    if (efDocument.getStatus().equals("1")){
                        holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.errtext));
                    }else {
                        holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.green));
                    }
                    if (efDocument.getCreateTime() != null){
                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        holder.setText(R.id.ef_time, fmt.format(efDocument.getCreateTime()));
                    }else {
                        holder.setText(R.id.ef_time, "");
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
            ef_list.setAdapter(efListAdapter);
        }

    /**
     * 根据文书基本信息判断调用相应的活动并传递参数过去
     * @param efDocument
     */
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

    /**
     * 界面上的按钮点击事件
     * @param view
     */
    @Event(value = {R.id.leftbtn,R.id.addefbtn,R.id.comit,R.id.endef})
    private void clink(View view){
        switch (view.getId()){
            case R.id.leftbtn:
                finish();
                break;
            case R.id.addefbtn:
                startActivityForResult(CaseDocNotDoActivity.getIntent(mContext,mcaseInfo,corpInfo,isD1),navId);
                break;
            case R.id.comit:
                finish();
                break;
            case R.id.endef:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"是否确定结束本次案件？"),2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 2:
                if (resultCode == RESULT_OK){toFinnish();}
                break;
            case 100:
                getDocYList();
                break;
        }
    }

    private void toFinnish() {
       if (docmentesY.size()<=0){
           showShortToast(3,"未制作任何文书，不可结案！");
       }else if (mcaseInfo.getStatus().equals("5")){
            showShortToast(3,"该案件已经结案！");
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.CLOSECASE_URL);
                    params.addBodyParameter("id",mcaseInfo.getId());
                    params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                            if (result.code == 1){
                                showShortToast(4,"操作成功！");
                                setResult(RESULT_OK);
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
    }
}
