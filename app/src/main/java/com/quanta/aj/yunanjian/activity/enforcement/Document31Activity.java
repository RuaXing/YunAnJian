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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument31;
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

public class Document31Activity extends MFragmentActivity {
    private static final String TAG = "Document31Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document31Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document31Activity.class);
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
    @ViewInject(R.id.corp)
    private EditText corp;
    @ViewInject(R.id.time)
    private TextView time;
    @ViewInject(R.id.money)
    private EditText money;
    @ViewInject(R.id.fromNo)
    private EditText fromNo;
    @ViewInject(R.id.yfqbj)
    private EditText yfqbj;
    @ViewInject(R.id.djq)
    private EditText djq;
    @ViewInject(R.id.yqjzrq)
    private TextView yqjzrq;
    @ViewInject(R.id.fqjzrq)
    private TextView fqjzrq;
    @ViewInject(R.id.fqjnfk)
    private EditText fqjnfk;
    @ViewInject(R.id.fqwjnfk)
    private EditText fqwjnfk;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument31 document31 = new EfDocument31();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document31);
        x.view().inject(this);
        mContext = Document31Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d31.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        corp.setText(corpInfo.getQymc());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/31/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document31 = getGson().fromJson(s,EfDocument31.class);
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
        no.setText(document31.getFromNo());
        corp.setText(document31.getCorp());
        time.setText(getFmt().format(document31.getTime()));
        money.setText(String.valueOf(document31.getMoney()));
        fromNo.setText(document31.getFromNo());
        yfqbj.setText(document31.getYfqbj());
        djq.setText(document31.getDjq());
        yqjzrq.setText(getFmt().format(document31.getYqjzrq()));
        fqjzrq.setText(getFmt().format(document31.getFqjzrq()));
        fqjnfk.setText(String.valueOf(document31.getFqjnfk()));
        fqwjnfk.setText(String.valueOf(document31.getFqwjnfk()));
    }
    private void toComit(final Boolean submit) {
        document31.setNo(no.getText().toString());
        document31.setCorp(corp.getText().toString());
        document31.setMoney(Double.parseDouble(money.getText().toString()));
        document31.setFromNo(fromNo.getText().toString());
        document31.setYfqbj(yfqbj.getText().toString());
        document31.setDjq(djq.getText().toString());
        document31.setFqjnfk(Double.parseDouble(fqjnfk.getText().toString()));
        document31.setFqwjnfk(Double.parseDouble(fqwjnfk.getText().toString()));
        try {
            document31.setTime(getFmt().parse(time.getText().toString()));
            document31.setYqjzrq(getFmt().parse(yqjzrq.getText().toString()));
            document31.setFqjzrq(getFmt().parse(yqjzrq.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument31(document31);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/31/appsave.do");
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
    @Event(value = {R.id.comit,R.id.print,R.id.time,R.id.yqjzrq,R.id.fqjzrq,R.id.comit2})
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
            case R.id.time:
                SlideDateTimeListener timelistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        time.setText(getFmt().format(date));
                    }
                };
                addTime(timelistener);
                break;
            case R.id.yqjzrq:
                SlideDateTimeListener yqjzrqlistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        yqjzrq.setText(getFmt().format(date));
                    }
                };
                addTime(yqjzrqlistener);
                break;
            case R.id.fqjzrq:
                SlideDateTimeListener fqjzrqlistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        fqjzrq.setText(getFmt().format(date));
                    }
                };
                addTime(fqjzrqlistener);
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
