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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument28;
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

public class Document28Activity extends MFragmentActivity {
    private static final String TAG = "Document28Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document28Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document28Activity.class);
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
    @ViewInject(R.id.ajmc)
    private EditText ajmc;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.startTime)
    private TextView startTime;
    @ViewInject(R.id.endTime)
    private TextView endTime;
    @ViewInject(R.id.zcr)
    private EditText zcr;
    @ViewInject(R.id.hbr)
    private EditText hbr;
    @ViewInject(R.id.jlr)
    private EditText jlr;
    @ViewInject(R.id.cxryxmjzw)
    private EditText cxryxmjzw;
    @ViewInject(R.id.tlnr)
    private EditText tlnr;
    @ViewInject(R.id.tljl)
    private EditText tljl;
    @ViewInject(R.id.jlxyj)
    private EditText jlxyj;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument28 document28 = new EfDocument28();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document28);
        x.view().inject(this);
        mContext = Document28Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d28.name);
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
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/28/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document28 = getGson().fromJson(s,EfDocument28.class);
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
        ajmc.setText(document28.getAjmc());
        address.setText(document28.getAddress());
        startTime.setText(getFmt().format(document28.getStartTime()));
        endTime.setText(getFmt().format(document28.getEndTime()));
        zcr.setText(document28.getZcr());
        hbr.setText(document28.getHbr());
        jlr.setText(document28.getJlr());
        cxryxmjzw.setText(document28.getCxryxmjzw());
        tlnr.setText(document28.getTlnr());
        tljl.setText(document28.getTljl());
        jlxyj.setText(document28.getJlxyj());
    }
    private void toComit(final Boolean submit) {
        document28.setAjmc(ajmc.getText().toString());
        document28.setAddress(address.getText().toString());
        try {
            document28.setStartTime(getFmt().parse(startTime.getText().toString()));
            document28.setEndTime(getFmt().parse(endTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document28.setZcr(zcr.getText().toString());
        document28.setHbr(hbr.getText().toString());
        document28.setJlr(jlr.getText().toString());
        document28.setCxryxmjzw(cxryxmjzw.getText().toString());
        document28.setTlnr(tlnr.getText().toString());
        document28.setTljl(tljl.getText().toString());
        document28.setJlxyj(jlxyj.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument28(document28);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/28/appsave.do");
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
    @Event(value = {R.id.comit,R.id.print,R.id.startTime,R.id.endTime,R.id.comit2})
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
            case R.id.startTime:
                SlideDateTimeListener startTimelistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        startTime.setText(getFmt().format(date));
                    }
                };
                addTime(startTimelistener);
                break;
            case R.id.endTime:
                SlideDateTimeListener endTimelistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        endTime.setText(getFmt().format(date));
                    }
                };
                addTime(endTimelistener);
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
