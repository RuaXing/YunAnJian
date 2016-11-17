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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument24;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Document24Activity extends MFragmentActivity {
    private static final String TAG = "Document24Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document24Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document24Activity.class);
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
    @ViewInject(R.id.no)
    private EditText no;
    @ViewInject(R.id.corpName)
    private EditText corpName;
    @ViewInject(R.id.ctjg)
    private EditText ctjg;
    @ViewInject(R.id.wfxw)
    private EditText wfxw;
    @ViewInject(R.id.wfgd)
    private EditText wfgd;
    @ViewInject(R.id.flyj)
    private EditText flyj;
    @ViewInject(R.id.xzcf)
    private EditText xzcf;
    @ViewInject(R.id.jdglbmdz)
    private EditText jdglbmdz;
    @ViewInject(R.id.lxr)
    private EditText lxr;
    @ViewInject(R.id.yzbm)
    private EditText yzbm;
    @ViewInject(R.id.lxdh)
    private EditText lxdh;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument24 document24 = new EfDocument24();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document24);
        x.view().inject(this);
        mContext = Document24Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d24.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        corpName.setText(corpInfo.getQymc());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/24/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document24 = getGson().fromJson(s,EfDocument24.class);
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
        no.setText(document24.getNo());
        corpName.setText(document24.getCorpName());
        ctjg.setText(document24.getCtjg());
        wfxw.setText(document24.getWfxw());
        wfgd.setText(document24.getWfgd());
        flyj.setText(document24.getFlyj());
        xzcf.setText(document24.getXzcf());
        jdglbmdz.setText(document24.getJdglbmdz());
        lxr.setText(document24.getLxr());
        yzbm.setText(document24.getYzbm());
        lxdh.setText(document24.getLxdh());
    }
    private void toComit(final Boolean submit) {
        document24.setNo(no.getText().toString());
        document24.setCorpName(corpName.getText().toString());
        document24.setCtjg(ctjg.getText().toString());
        document24.setWfxw(wfxw.getText().toString());
        document24.setWfgd(wfgd.getText().toString());
        document24.setFlyj(flyj.getText().toString());
        document24.setXzcf(xzcf.getText().toString());
        document24.setJdglbmdz(jdglbmdz.getText().toString());
        document24.setLxr(lxr.getText().toString());
        document24.setYzbm(yzbm.getText().toString());
        document24.setLxdh(lxdh.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument24(document24);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/24/appsave.do");
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
