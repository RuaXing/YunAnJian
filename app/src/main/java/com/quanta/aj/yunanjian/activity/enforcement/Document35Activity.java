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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument35;
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

public class Document35Activity extends MFragmentActivity {
    private static final String TAG = "Document35Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document35Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }

    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document35Activity.class);
        intent.putExtra("corpInfo", corpInfo);
        intent.putExtra("docId", docId);
        intent.putExtra("caseInfo", caseInfo);
        return intent;
    }

    Handler handler = new Handler() {
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
    @ViewInject(R.id.ay)
    private EditText ay;
    @ViewInject(R.id.cljg)
    private EditText cljg;
    @ViewInject(R.id.startTime)
    private TextView startTime;
    @ViewInject(R.id.endTime)
    private TextView endTime;
    @ViewInject(R.id.cbrOne)
    private EditText cbrOne;
    @ViewInject(R.id.cbrTwo)
    private EditText cbrTwo;
    @ViewInject(R.id.gdrq)
    private TextView gdrq;
    @ViewInject(R.id.gdh)
    private EditText gdh;
    @ViewInject(R.id.bcnx)
    private EditText bcnx;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument35 document35 = new EfDocument35();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document35);
        x.view().inject(this);
        mContext = Document35Activity.this;
        efdcoment = (EfDocument) getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d35.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {

    }

    private void getData() {
        if (null != efdcoment) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL + "/35/get.do");
                    params.addBodyParameter("id", efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document35 = getGson().fromJson(s, EfDocument35.class);
                            handler.sendEmptyMessage(1);
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            Log.e(TAG, "连接失败！", throwable);
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
        no.setText(document35.getNo());
        ay.setText(document35.getAy());
        cljg.setText(document35.getCljg());
        startTime.setText(getFmt().format(document35.getStartTime()));
        endTime.setText(getFmt().format(document35.getEndTime()));
        cbrOne.setText(document35.getCbrOne());
        cbrTwo.setText(document35.getCbrTwo());
        gdrq.setText(getFmt().format(document35.getGdrq()));
        gdh.setText(document35.getGdh());
        bcnx.setText(document35.getBcnx());
    }

    private void toComit(final Boolean submit) {
        document35.setNo(no.getText().toString());
        document35.setAy(ay.getText().toString());
        document35.setCljg(cljg.getText().toString());
        document35.setCbrOne(cbrOne.getText().toString());
        document35.setCbrTwo(cbrTwo.getText().toString());
        document35.setGdh(gdh.getText().toString());
        document35.setBcnx(bcnx.getText().toString());
        try {
            document35.setStartTime(getFmt().parse(startTime.getText().toString()));
            document35.setEndTime(getFmt().parse(endTime.getText().toString()));
            document35.setGdrq(getFmt().parse(gdrq.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument35(document35);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL + "/35/appsave.do");
                params.addHeader("Cookie", "JSESSIONID=" + Constant.JSESSIONID + ";ZW_SESSION=" + Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(form));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        RemoteLoginResult result = JsonUtils.parseObject(s, RemoteLoginResult.class);
                        if (result.code == 1) {
                            showShortToast(4, "操作成功！");
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            showShortToast(3, "操作失败！");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG, "请求出错！", throwable);
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

    @Event(value = {R.id.comit, R.id.print, R.id.startTime, R.id.endTime, R.id.gdrq,R.id.comit2})
    private void clink(View v) {
        switch (v.getId()) {
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
            case R.id.gdrq:
                SlideDateTimeListener gdrqlistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        gdrq.setText(getFmt().format(date));
                    }
                };
                addTime(gdrqlistener);
                break;
            case R.id.comit2:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),2);
                break;
            default:
                break;
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
