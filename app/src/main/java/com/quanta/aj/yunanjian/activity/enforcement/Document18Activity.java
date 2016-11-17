package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument18;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Document18Activity extends MFragmentActivity {
    private static final String TAG = "Document18Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document18Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document18Activity.class);
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
                    break;
            }
        }
    };
    @ViewInject(R.id.domtt)
    private Title_Sm_Layout domtt;
    @ViewInject(R.id.ay)
    private EditText ay;
    @ViewInject(R.id.dsr)
    private EditText dsr;
    @ViewInject(R.id.sysjg)
    private EditText sysjg;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.ajjj)
    private EditText ajjj;
    @ViewInject(R.id.ysly)
    private EditText ysly;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument18 document18 = new EfDocument18();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document18);
        x.view().inject(this);
        mContext = Document18Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d18.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/18/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document18 = getGson().fromJson(s,EfDocument18.class);
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
        ay.setText(document18.getAy());
        dsr.setText(document18.getDsr());
        sysjg.setText(document18.getSysjg());
        address.setText(document18.getAddress());
        ajjj.setText(document18.getAjjj());
        ysly.setText(document18.getYsly());
    }
    private void toComit(final Boolean submit) {
        document18.setAy(ay.getText().toString());
        document18.setDsr(dsr.getText().toString());
        document18.setSysjg(sysjg.getText().toString());
        document18.setAddress(address.getText().toString());
        document18.setAjjj(ajjj.getText().toString());
        document18.setYsly(ysly.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument18(document18);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/18/appsave.do");
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
    @Event(value = {R.id.comit,R.id.print,R.id.comit2})
    private void clink(View v){
        switch (v.getId()){
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
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),2);
                break;
            default: break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 2:
                if (resultCode == RESULT_OK){
                    toComit(true);
                }
                break;
            default:break;
        }
    }
}
