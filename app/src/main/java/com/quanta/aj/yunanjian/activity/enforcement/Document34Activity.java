package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument34;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Document34Activity extends MFragmentActivity {
    private static final String TAG = "Document34Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document34Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document34Activity.class);
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
    @ViewInject(R.id.ajmc)
    private EditText ajmc;
    @ViewInject(R.id.bcfdw)
    private EditText bcfdw;
    @ViewInject(R.id.dz)
    private EditText dz;
    @ViewInject(R.id.fddbr)
    private EditText fddbr;
    @ViewInject(R.id.frzw)
    private EditText frzw;
    @ViewInject(R.id.dwyb)
    private EditText dwyb;
    @ViewInject(R.id.bcfr)
    private EditText bcfr;
    @ViewInject(R.id.bcfrnl)
    private EditText bcfrnl;
    @ViewInject(R.id.bcfrxb)
    private Spinner bcfrxb;
    @ViewInject(R.id.bcfrszdw)
    private EditText bcfrszdw;
    @ViewInject(R.id.bcfrszdwdz)
    private EditText bcfrszdwdz;
    @ViewInject(R.id.bcfrjtzz)
    private EditText bcfrjtzz;
    @ViewInject(R.id.bcfrlxdh)
    private EditText bcfrlxdh;
    @ViewInject(R.id.bcfrzzyb)
    private EditText bcfrzzyb;
    @ViewInject(R.id.cfjg)
    private EditText cfjg;
    @ViewInject(R.id.zxjg)
    private EditText zxjg;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument34 document34 = new EfDocument34();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document34);
        x.view().inject(this);
        mContext = Document34Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d34.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        bcfdw.setText(corpInfo.getQymc());
        dz.setText(corpInfo.getZcdz());
        fddbr.setText(corpInfo.getFrdb());
        frzw.setText(corpInfo.getFrzw());
        dwyb.setText(corpInfo.getYzbm());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/34/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document34 = getGson().fromJson(s,EfDocument34.class);
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
        no.setText(document34.getNo());
        ajmc.setText(document34.getAjmc());
        bcfdw.setText(document34.getBcfdw());
        dz.setText(document34.getDz());
        fddbr.setText(document34.getFddbr());
        frzw.setText(document34.getFrzw());
        dwyb.setText(document34.getDwyb());
        bcfr.setText(document34.getBcfr());
        bcfrnl.setText(document34.getBcfrnl());
        //bcfrxb.setDropDownWidth(Integer.parseInt(document34.getBcfrxb()));
        bcfrxb.setSelection(Integer.parseInt(document34.getBcfrxb()),true);
        bcfrszdw.setText(document34.getBcfrszdw());
        bcfrszdwdz.setText(document34.getBcfrszdwdz());
        bcfrjtzz.setText(document34.getBcfrjtzz());
        bcfrlxdh.setText(document34.getBcfrlxdh());
        bcfrzzyb.setText(document34.getBcfrzzyb());
        cfjg.setText(document34.getCfjg());
        zxjg.setText(document34.getZxjg());
    }
    private void toComit(final Boolean submit) {
        document34.setNo(no.getText().toString());
        document34.setAjmc(ajmc.getText().toString());
        document34.setBcfdw(bcfdw.getText().toString());
        document34.setDz(dz.getText().toString());
        document34.setFddbr(fddbr.getText().toString());
        document34.setFrzw(frzw.getText().toString());
        document34.setDwyb(dwyb.getText().toString());
        document34.setBcfr(bcfr.getText().toString());
        document34.setBcfrnl(bcfrnl.getText().toString());
        document34.setBcfrxb(String.valueOf(bcfrxb.getSelectedItemPosition()));
        document34.setBcfrszdw(bcfrszdw.getText().toString());
        document34.setBcfrszdwdz(bcfrszdwdz.getText().toString());
        document34.setBcfrjtzz(bcfrjtzz.getText().toString());
        document34.setBcfrlxdh(bcfrlxdh.getText().toString());
        document34.setBcfrzzyb(bcfrzzyb.getText().toString());
        document34.setCfjg(cfjg.getText().toString());
        document34.setZxjg(zxjg.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument34(document34);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/34/appsave.do");
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
