package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument20;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.util.Date;

public class Document20Activity extends MFragmentActivity {
    private static final String TAG = "Document20Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document20Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document20Activity.class);
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
    @ViewInject(R.id.sqtzdw)
    private EditText sqtzdw;
    @ViewInject(R.id.tzdd)
    private EditText tzdd;
    @ViewInject(R.id.tzsj)
    private TextView tzsj;
    @ViewInject(R.id.zcr)
    private EditText zcr;
    @ViewInject(R.id.zcrzw)
    private EditText zcrzw;
    @ViewInject(R.id.sjy)
    private EditText sjy;
    @ViewInject(R.id.sjyzw)
    private EditText sjyzw;
    @ViewInject(R.id.tzy)
    private EditText tzy;
    @ViewInject(R.id.tzyzw)
    private EditText tzyzw;
    @ViewInject(R.id.jdglbmdz)
    private EditText jdglbmdz;
    @ViewInject(R.id.jdglbmlxr)
    private EditText jdglbmlxr;
    @ViewInject(R.id.jdglbmyzbm)
    private EditText jdglbmyzbm;
    @ViewInject(R.id.jdglbmlxdh)
    private EditText jdglbmlxdh;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument20 document20 = new EfDocument20();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document20);
        x.view().inject(this);
        mContext = Document20Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d20.name);
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
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/20/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document20 = getGson().fromJson(s,EfDocument20.class);
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
        no.setText(document20.getNo());
        ajmc.setText(document20.getAjmc());
        sqtzdw.setText(document20.getSqtzdw());
        tzdd.setText(document20.getTzdd());
        tzsj.setText(getFmt().format(document20.getTzsj()));
        zcr.setText(document20.getZcr());
        zcrzw.setText(document20.getZcrzw());
        sjy.setText(document20.getSjy());
        sjyzw.setText(document20.getSjyzw());
        tzy.setText(document20.getTzy());
        tzyzw.setText(document20.getTzyzw());
        jdglbmdz.setText(document20.getJdglbmdz());
        jdglbmlxr.setText(document20.getJdglbmlxr());
        jdglbmyzbm.setText(document20.getJdglbmyzbm());
        jdglbmlxdh.setText(document20.getJdglbmlxdh());
    }
    private void toComit(final Boolean submit) {
        document20.setNo(no.getText().toString());
        document20.setAjmc(ajmc.getText().toString());
        document20.setSqtzdw(sqtzdw.getText().toString());
        document20.setTzdd(tzdd.getText().toString());
        try {
            document20.setTzsj(getFmt().parse(tzsj.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document20.setZcr(zcr.getText().toString());
        document20.setZcrzw(zcrzw.getText().toString());
        document20.setSjy(sjy.getText().toString());
        document20.setSjyzw(sjyzw.getText().toString());
        document20.setTzy(tzy.getText().toString());
        document20.setTzyzw(tzyzw.getText().toString());
        document20.setJdglbmdz(jdglbmdz.getText().toString());
        document20.setJdglbmlxr(jdglbmlxr.getText().toString());
        document20.setJdglbmyzbm(jdglbmyzbm.getText().toString());
        document20.setJdglbmlxdh(jdglbmlxdh.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument20(document20);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/20/appsave.do");
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
    @Event(value = {R.id.comit,R.id.print,R.id.tzsj,R.id.comit2})
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
            case R.id.tzsj:
                SlideDateTimeListener tzsjlistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        tzsj.setText(getFmt().format(date));
                    }
                };
                addTime(tzsjlistener);
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
