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
import android.widget.Spinner;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_adzfryActivity;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument26;
import com.quanta.aj.yunanjian.orm.enforcement.EfUser;
import com.quanta.aj.yunanjian.page.enforcement.EfUserPage;
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

public class Document26Activity extends MFragmentActivity {
    private static final String TAG = "Document26Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document26Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document26Activity.class);
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
                    addZfry(efUsers);
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
    @ViewInject(R.id.notice)
    private EditText notice;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.sex)
    private Spinner sex;
    @ViewInject(R.id.post)
    private EditText post;
    @ViewInject(R.id.age)
    private EditText age;
    @ViewInject(R.id.number)
    private EditText number;
    @ViewInject(R.id.homeAddress)
    private EditText homeAddress;
    @ViewInject(R.id.corpName)
    private EditText corpName;
    @ViewInject(R.id.corpAddress)
    private EditText corpAddress;
    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.sfdccf)
    private EditText sfdccf;
    @ViewInject(R.id.wfss)
    private EditText wfss;
    @ViewInject(R.id.money)
    private EditText money;
    @ViewInject(R.id.wfgd)
    private EditText wfgd;
    @ViewInject(R.id.flyj)
    private EditText flyj;
    @ViewInject(R.id.xzcf)
    private EditText xzcf;
    @ViewInject(R.id.fkssdw)
    private EditText fkssdw;
    @ViewInject(R.id.fksszh)
    private EditText fksszh;
    @ViewInject(R.id.dsrwtdlr)
    private EditText dsrwtdlr;
    @ViewInject(R.id.fyjgsj)
    private EditText fyjgsj;
    @ViewInject(R.id.fyjgzf)
    private EditText fyjgzf;
    @ViewInject(R.id.ssfy)
    private EditText ssfy;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument26 document26 = new EfDocument26();
    private List<EfUser> efUsers = new LinkedList<>();
    private MyListAdapter myListAdapter;
    private List useritme = new ArrayList();
    private DocumentForm form = new DocumentForm();
    private EfUserPage efUserpage = new EfUserPage();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document26);
        x.view().inject(this);
        mContext = Document26Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d26.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        name.setText(corpInfo.getFrdb());
        post.setText(corpInfo.getFrzw());
        corpName.setText(corpInfo.getQymc());
        corpAddress.setText(corpInfo.getZcdz());
        phone.setText(corpInfo.getFrdblxdh());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/26/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document26 = getGson().fromJson(s,EfDocument26.class);
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
        no.setText(document26.getNo());
        notice.setText(document26.getNotice());
        name.setText(document26.getName());
        //sex.setDropDownWidth(Integer.parseInt(document26.getSex()));
        sex.setSelection(Integer.parseInt(document26.getSex()),true);
        post.setText(document26.getPost());
        age.setText(String.valueOf(document26.getAge()));
        number.setText(document26.getNumber());
        homeAddress.setText(document26.getHomeAddress());
        corpName.setText(document26.getCorpName());
        corpAddress.setText(document26.getCorpAddress());
        phone.setText(document26.getPhone());
        sfdccf.setText(document26.getSfdccf());
        wfss.setText(document26.getWfss());
        money.setText(String.valueOf(document26.getMoney()));
        wfgd.setText(document26.getWfgd());
        flyj.setText(document26.getFlyj());
        xzcf.setText(document26.getXzcf());
        fkssdw.setText(document26.getFkssdw());
        fksszh.setText(document26.getFksszh());
        dsrwtdlr.setText(document26.getDsrwtdlr());
        fyjgsj.setText(document26.getFyjgsj());
        fyjgzf.setText(document26.getFyjgzf());
        ssfy.setText(document26.getSsfy());
    }
    private void getZfry() {
        if (null!=document26.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETZFRY_URL);
                    efUserpage.setDocument(document26.getId());
                    params.addParameter("page",efUserpage);
                    params.addBodyParameter("document",document26.getId());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            EfUserPage userPage = JsonUtils.parseObject(s,EfUserPage.class);
                            efUsers = userPage.getRows();
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
    private void addZfry(List<EfUser> efUser) {
        myListAdapter = new MyListAdapter<EfUser>(efUser,R.layout.item_addzfry) {
            @Override
            public void bindView(final ViewHolder holder, EfUser obj) {
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.cert,obj.getCert());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox)v;
                        if (box.isChecked()){
                            useritme.add(holder.getItemPosition()); //获得选中的列传出值去
                        }
                    }
                });
            }
        };
        zfrylist.setAdapter(myListAdapter);
    }
    private void delZfry() {
        if (null!=useritme){
            for (int i=useritme.size()-1;i>=0;i--){
                efUsers.remove(Integer.parseInt(String.valueOf(useritme.get(i))));
            }
            useritme.clear();//清空以备下次使用
            addZfry(efUsers);
        }
    }
    private void toComit(final Boolean submit) {
        if (efUsers == null||efUsers.size()<1){
            showShortToast(1,"执法人员不能为空！");
        }else {
            document26.setNo(no.getText().toString());
            document26.setNotice(notice.getText().toString());
            document26.setName(name.getText().toString());
            document26.setSex(String.valueOf(sex.getSelectedItemPosition()));
            document26.setPost(post.getText().toString());
            document26.setAge(Integer.parseInt(age.getText().toString()));
            document26.setNumber(number.getText().toString());
            document26.setHomeAddress(homeAddress.getText().toString());
            document26.setCorpName(corpName.getText().toString());
            document26.setCorpAddress(corpAddress.getText().toString());
            document26.setPhone(phone.getText().toString());
            document26.setSfdccf(sfdccf.getText().toString());
            document26.setWfss(wfss.getText().toString());
            document26.setMoney(Double.parseDouble(money.getText().toString()));
            document26.setWfgd(wfgd.getText().toString());
            document26.setFlyj(flyj.getText().toString());
            document26.setXzcf(xzcf.getText().toString());
            document26.setFkssdw(fkssdw.getText().toString());
            document26.setFksszh(fksszh.getText().toString());
            document26.setDsrwtdlr(dsrwtdlr.getText().toString());
            document26.setFyjgsj(fyjgsj.getText().toString());
            document26.setFyjgzf(fyjgzf.getText().toString());
            document26.setSsfy(ssfy.getText().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    form.setDocument26(document26);
                    form.setSubmit(submit);
                    form.setEfUsers(efUsers);
                    //form.setDocumentId(efdcoment.getId());
                    if (null != efdcoment) {
                        form.setDocumentId(efdcoment.getId());
                    } else {
                        String docId = getIntent().getStringExtra("docId");
                        form.setDocumentId(docId);
                    }
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL + "/26/appsave.do");
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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    String cert = data.getStringExtra("cert");
                    Log.d(TAG,"name: "+name+"   cert: "+cert);
                    EfUser efUser = new EfUser();
                    efUser.setName(name);
                    efUser.setCert(cert);
                    efUsers.add(efUser);
                    addZfry(efUsers);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    toComit(true);
                }
                break;
            default:
        }
    }
    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.comit2})
    private void clink(View v){
        switch (v.getId()){
            case R.id.addzfry:
                startActivityForResult(Dialog_adzfryActivity.getIntent(mContext,Dialog_adzfryActivity.class),1);
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
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),2);
                break;
            default: break;
        }
    }
}
