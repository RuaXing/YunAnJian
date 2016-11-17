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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument13;
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

public class Document13Activity extends MFragmentActivity {
    private static final String TAG = "Document13Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document13Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document13Activity.class);
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
    @ViewInject(R.id.bxwr)
    private EditText bxwr;
    @ViewInject(R.id.ay)
    private EditText ay;
    @ViewInject(R.id.xwjzsj)
    private TextView xwjzsj;
    @ViewInject(R.id.xwdd)
    private EditText xwdd;
    @ViewInject(R.id.jdglbmdz)
    private EditText jdglbmdz;
    @ViewInject(R.id.lxr)
    private EditText lxr;
    @ViewInject(R.id.lxdh)
    private EditText lxdh;
    @ViewInject(R.id.sfz)
    private CheckBox sfz;
    @ViewInject(R.id.yyzz)
    private CheckBox yyzz;
    @ViewInject(R.id.fddbrsfzmhwts)
    private CheckBox fddbrsfzmhwts;
    @ViewInject(R.id.qtzj)
    private CheckBox qtzj;
    @ViewInject(R.id.qtzjtext)
    private EditText qtzjtext;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument13 document13 = new EfDocument13();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document13);
        x.view().inject(this);
        mContext = Document13Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d13.name);
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
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/13/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document13 = getGson().fromJson(s,EfDocument13.class);
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
        no.setText(document13.getNo());
        bxwr.setText(document13.getBxwr());
        ay.setText(document13.getAy());
        xwjzsj.setText(getFmt().format(document13.getXwjzsj()));
        xwdd.setText(document13.getXwdd());
        jdglbmdz.setText(document13.getJdglbmdz());
        lxr.setText(document13.getLxr());
        lxdh.setText(document13.getLxdh());
        if (document13.getSfz()!=null&&document13.getSfz().equals("1"))sfz.setChecked(true);
        if (document13.getYyzz()!=null&&document13.getYyzz().equals("1"))yyzz.setChecked(true);
        if (document13.getFddbrsfzmhwts()!=null&&document13.getFddbrsfzmhwts().equals("1"))fddbrsfzmhwts.setChecked(true);
        if(document13.getQtzj()!=null)qtzj.setChecked(true);
        qtzjtext.setText(document13.getQtzj());
    }
    private void toComit(final Boolean submit) {
        document13.setNo(no.getText().toString());
        document13.setBxwr(bxwr.getText().toString());
        document13.setAy(ay.getText().toString());
        try {
            document13.setXwjzsj(getFmt().parse(xwjzsj.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document13.setXwdd(xwdd.getText().toString());
        document13.setJdglbmdz(jdglbmdz.getText().toString());
        document13.setLxr(lxr.getText().toString());
        document13.setLxdh(lxdh.getText().toString());
        document13.setSfz(sfz.isChecked()?"1":"0");
        document13.setYyzz(yyzz.isChecked()?"1":"0");
        document13.setFddbrsfzmhwts(fddbrsfzmhwts.isChecked()?"1":"0");
        if (qtzj.isChecked())document13.setQtzj(qtzjtext.getText().toString());
        if (!qtzj.isChecked())document13.setQtzj(null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument13(document13);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/13/appsave.do");
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
    @Event(value = {R.id.comit,R.id.print,R.id.xwjzsj,R.id.comit2})
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
            case R.id.xwjzsj:
                SlideDateTimeListener xwjzsjlistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        xwjzsj.setText(getFmt().format(date));
                    }
                };
                addTime(xwjzsjlistener);
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
