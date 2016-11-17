package com.quanta.aj.yunanjian.activity.enforcement;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_EfCheck;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCheck;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument27;
import com.quanta.aj.yunanjian.page.enforcement.EfCheckPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Document27Activity extends MFragmentActivity {
    private static final String TAG = "Document27Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document27Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document27Activity.class);
        intent.putExtra("corpInfo", corpInfo);
        intent.putExtra("docId", docId);
        intent.putExtra("caseInfo", caseInfo);
        return intent;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData();
                    getZfry();
                    break;
                case 2:
                    addZfry(efChecks);
                    break;
            }
        }
    };
    @ViewInject(R.id.domtt)
    private Title_Sm_Layout domtt;
    @ViewInject(R.id.zfrylist)
    private ListView zfrylist;
    @ViewInject(R.id.no)
    private EditText no;
    @ViewInject(R.id.ajmc)
    private EditText ajmc;
    @ViewInject(R.id.ssddwhgr)
    private EditText ssddwhgr;
    @ViewInject(R.id.bz)
    private EditText bz;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument27 document27 = new EfDocument27();
    private List<EfCheck> efChecks = new LinkedList<>();
    private MyListAdapter myListAdapter;
    private List checkitem = new ArrayList();
    private DocumentForm form = new DocumentForm();
    private EfCheckPage efCheckPage = new EfCheckPage();
    private EfCheck efCheck;
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document27);
        x.view().inject(this);
        mContext = Document27Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d27.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void  initCorpData() {
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/27/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document27 = getGson().fromJson(s,EfDocument27.class);
                            handler.sendEmptyMessage(1);
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            Log.e(TAG,"连接失败！",throwable);
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
    private void initData() {
        no.setText(document27.getNo());
        ajmc.setText(document27.getAjmc());
        ssddwhgr.setText(document27.getSsddwhgr());
        bz.setText(document27.getBz());
    }
    private void getZfry() {
        if (null!=document27.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETCHECH_URL);
                    efCheckPage.setDocument(document27.getId());
                    params.addParameter("page",efCheckPage);
                    params.addBodyParameter("document",document27.getId());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            EfCheckPage efCheckPage = getGson().fromJson(s,EfCheckPage.class);
                            efChecks = efCheckPage.getRows();
                            handler.sendEmptyMessage(2);
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            Log.e(TAG,"请求出错！"+throwable);
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
    private void addZfry(List<EfCheck> efChecks) {
        myListAdapter = new MyListAdapter<EfCheck>(efChecks,R.layout.item_efcheck) {
            @Override
            public void bindView(final ViewHolder holder, final EfCheck obj) {
                holder.setText(R.id.zwsh,obj.getZwsh());
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.no,obj.getNo());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox)v;
                        if (box.isChecked()){
                            checkitem.add(holder.getItemPosition()); //获得选中的列传出值去
                        }
                    }
                });
                holder.setOnClickListener(R.id.rightbtn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        efCheck = obj;
                        myListAdapter.remove(holder.getItemPosition());
                        startActivityForResult(Dialog_EfCheck.getIntent(mContext,efCheck),2);
                    }
                });
            }
        };
        zfrylist.setAdapter(myListAdapter);
    }
    private void delZfry() {
        if (null!=checkitem){
            for (int i=checkitem.size()-1;i>=0;i--){
                efChecks.remove(Integer.parseInt(String.valueOf(checkitem.get(i))));
            }
            checkitem.clear();//清空以备下次使用
            addZfry(efChecks);
        }
    }
    private void toComit(final Boolean submit) {
        document27.setNo(no.getText().toString());
        document27.setAjmc(ajmc.getText().toString());
        document27.setSsddwhgr(ssddwhgr.getText().toString());
        document27.setBz(bz.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument27(document27);
                form.setSubmit(submit);
                form.setEfChecks(efChecks);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/27/appsave.do");
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(form));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        RemoteLoginResult result = JsonUtils.parseObject(s, RemoteLoginResult.class);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    efCheck = (EfCheck)data.getSerializableExtra("efCheck") ;
                    efChecks.add(efCheck);
                    addZfry(efChecks);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    efCheck = (EfCheck)data.getSerializableExtra("efCheck") ;
                    efChecks.add(efCheck);
                    addZfry(efChecks);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK){
                    toComit(true);
                }
                break;
            default:break;
        }
    }
    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.comit2})
    private void clink(View v){
        switch (v.getId()){
            case R.id.addzfry:
                startActivityForResult(Dialog_EfCheck.getIntent(mContext,Dialog_EfCheck.class),1);
                break;
            case R.id.delzfry:
                delZfry();
                break;
            case R.id.comit:
                toComit(false);
                break;
            case R.id.print:
                if (null!=efdcoment) {
                    startActivity(DocPrintActivity.getIntent(mContext, efdcoment));
                }else {
                    showShortToast(4,"该文书不存在或尚未制作！");
                }
                break;
            case R.id.comit2:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),3);
                break;
            default: break;
        }
    }
}
