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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument17;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Document17Activity extends MFragmentActivity {
    private static final String TAG = "Document17Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document17Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document17Activity.class);
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
    @ViewInject(R.id.caseName)
    private EditText caseName;
    @ViewInject(R.id.corpName)
    private EditText corpName;
    @ViewInject(R.id.corpAddress)
    private EditText corpAddress;
    @ViewInject(R.id.fddbr)
    private EditText fddbr;
    @ViewInject(R.id.fddbrzw)
    private EditText fddbrzw;
    @ViewInject(R.id.corpYzbm)
    private EditText corpYzbm;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.age)
    private EditText age;
    @ViewInject(R.id.sex)
    private Spinner sex;
    @ViewInject(R.id.corp)
    private EditText corp;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.homeAddress)
    private EditText homeAddress;
    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.yzbm)
    private EditText yzbm;
    @ViewInject(R.id.wfss)
    private EditText wfss;
    @ViewInject(R.id.cfyj)
    private EditText cfyj;
    @ViewInject(R.id.dsrsbyj)
    private EditText dsrsbyj;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument17 document17 = new EfDocument17();
    private DocumentForm form = new DocumentForm();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document17);
        x.view().inject(this);
        mContext = Document17Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d17.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        caseName.setText(corpInfo.getQymc());
        corpAddress.setText(corpInfo.getZcdz());
        fddbr.setText(corpInfo.getFrdb());
        fddbrzw.setText(corpInfo.getFrzw());
        yzbm.setText(corpInfo.getYzbm());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/17/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document17 = getGson().fromJson(s,EfDocument17.class);
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
        no.setText(document17.getNo());
        caseName.setText(document17.getCaseName());
        corpName.setText(document17.getCorpName());
        corpAddress.setText(document17.getCorpAddress());
        fddbr.setText(document17.getFddbr());
        fddbrzw.setText(document17.getFddbrzw());
        corpYzbm.setText(document17.getCorpYzbm());
        name.setText(document17.getName());
        age.setText(String.valueOf(document17.getAge()));
        //sex.setDropDownWidth(Integer.parseInt(document17.getSex().toString()));
        sex.setSelection(Integer.parseInt(document17.getSex().toString()),true);
        corp.setText(document17.getCorp());
        address.setText(document17.getAddress());
        homeAddress.setText(document17.getHomeAddress());
        phone.setText(document17.getPhone());
        yzbm.setText(document17.getYzbm());
        wfss.setText(document17.getWfss());
        cfyj.setText(document17.getCfyj());
        dsrsbyj.setText(document17.getDsrsbyj());
    }
    private void toComit(final Boolean submit) {
        document17.setNo(no.getText().toString());
        document17.setCaseName(caseName.getText().toString());
        document17.setCorpName(corpName.getText().toString());
        document17.setCorpAddress(corpAddress.getText().toString());
        document17.setFddbr(fddbr.getText().toString());
        document17.setFddbrzw(fddbrzw.getText().toString());
        document17.setCorpYzbm(corpYzbm.getText().toString());
        document17.setName(name.getText().toString());
        document17.setAge(Integer.parseInt(age.getText().toString()));
        document17.setSex(String.valueOf(sex.getSelectedItemPosition()));
        document17.setCorp(corp.getText().toString());
        document17.setAddress(address.getText().toString());
        document17.setHomeAddress(homeAddress.getText().toString());
        document17.setPhone(phone.getText().toString());
        document17.setYzbm(yzbm.getText().toString());
        document17.setWfss(wfss.getText().toString());
        document17.setCfyj(cfyj.getText().toString());
        document17.setDsrsbyj(dsrsbyj.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument17(document17);
                form.setSubmit(submit);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/17/appsave.do");
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
