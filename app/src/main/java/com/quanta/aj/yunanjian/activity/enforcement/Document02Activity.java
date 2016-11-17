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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
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
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument02;
import com.quanta.aj.yunanjian.orm.enforcement.EfUser;
import com.quanta.aj.yunanjian.page.enforcement.EfUserPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.CaseVO;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Document02Activity extends MFragmentActivity {
    private static final String TAG = "Document02Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document02Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document02Activity.class);
        intent.putExtra("corpInfo", corpInfo);
        intent.putExtra("docId", docId);
        intent.putExtra("caseInfo", caseInfo);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo,String inspectId) {
        Intent intent = new Intent(ctx, Document02Activity.class);
        intent.putExtra("corpInfo", corpInfo);
        intent.putExtra("docId", docId);
        intent.putExtra("caseInfo", caseInfo);
        intent.putExtra("inspectId",inspectId);
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
                case 5:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 6:
                    initZhuan();
                    break;
            }
        }
    };

    @ViewInject(R.id.domtt)
    private Title_Sm_Layout domtt;//标题
    @ViewInject(R.id.corpName)
    private EditText corpName;//被检查单位
    @ViewInject(R.id.leaderName)
    private EditText leaderName;//现场负责人
    @ViewInject(R.id.no)
    private EditText no;//文书编号
    @ViewInject(R.id.limitTime)
    private TextView limitTime;//限期整改时间
    @ViewInject(R.id.limitProblem)
    private EditText limitProblem;//限期整改问题
    @ViewInject(R.id.immediateProblem)
    private EditText immediateProblem;//立即整改问题
    @ViewInject(R.id.zfrylist)
    private ListView zfrylist;
    @ViewInject(R.id.zhuan)
    private LinearLayout zhuan;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument02 document02 = new EfDocument02();
    private List<EfUser> efUsers = new LinkedList<>();
    private MyListAdapter myListAdapter;
    private List useritme = new ArrayList();
    private DocumentForm form = new DocumentForm();
    private EfUserPage efUserpage = new EfUserPage();
    private CorpInfo corpInfo;
    private CaseVO caseVo;
    private String inspectId;
    private Boolean isDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document02);
        x.view().inject(this);
        zhuan.setVisibility(View.GONE);
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        inspectId = getIntent().getStringExtra("inspectId");
        isDone = getIntent().getBooleanExtra("isDone",false);
        if(null!=efdcoment){
            getData();
            getCase();
        }else {
            if (null != inspectId){
                zhuan.setVisibility(View.VISIBLE);
            }
        }
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        mContext = Document02Activity.this;
        domtt.setTitle2(DocumentType.d2.name);
        domtt.setTitleSize(20);
    }

    private void initZhuan(){
        //转整改复查的逻辑：首先满足是从安全检查过来,而且对应的案件状态为0
            if (!efdcoment.getStatus().equals("2")&&(null!=document02.getStatus() && document02.getStatus().equals("0")) && (null != caseVo.getStatus() && caseVo.getStatus().equals("0"))){
                zhuan.setVisibility(View.VISIBLE);
        }
    }

    private void initCorpData() {
        corpName.setText(corpInfo.getQymc());
        leaderName.setText(corpInfo.getZyfzr());
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
                    toZgfc();
                }
                break;
            case 3:
                if (resultCode == RESULT_OK){
                    toComit(true);
                }
                break;
            default:break;
        }
    }

    private void getCase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+"/enforcement/case/appenforce.do");
                params.addBodyParameter("caseId",efdcoment.getCaseId());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        caseVo = getGson().fromJson(s,CaseVO.class);
                        handler.sendEmptyMessage(6);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"获取案件信息失败！",throwable);
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

    private void getData() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/02/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document02 = getGson().fromJson(s,EfDocument02.class);
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

    private void getZfry() {
        if (null!=document02.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETZFRY_URL);
                    efUserpage.setDocument(document02.getId());
                    params.addParameter("page",efUserpage);
                    params.addBodyParameter("document",document02.getId());
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

    private void initData() {
        corpName.setText(document02.getCorpName());
        no.setText(document02.getNo());
        leaderName.setText(document02.getLeaderName());
        limitTime.setText(getFmt().format(document02.getLimitTime()));
        limitProblem.setText(document02.getLimitProblem());
        immediateProblem.setText(document02.getImmediateProblem());
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
            document02.setCorpName(corpName.getText().toString());
            document02.setNo(no.getText().toString());
            document02.setImmediateProblem(immediateProblem.getText().toString());
            document02.setLeaderName(leaderName.getText().toString());
            document02.setLimitProblem(limitProblem.getText().toString());
            try {
                document02.setLimitTime(getFmt().parse(limitTime.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            document02.setStatus("0");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    form.setDocument02(document02);
                    form.setSubmit(submit);
                    form.setEfUsers(efUsers);
                    //form.setCorp(corpInfo.getId());
                    if (null != efdcoment) {
                        form.setDocumentId(efdcoment.getId());
                    } else {
                        String docId = getIntent().getStringExtra("docId");
                        form.setDocumentId(docId);
                    }RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL + "/02/appsave.do");
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

    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.tozgfc,R.id.limitTime,R.id.comit2})
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
            case R.id.tozgfc:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"是否将此隐患转整改复查？"),2);
                break;
            case R.id.limitTime:
                SlideDateTimeListener beginTimelistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        limitTime.setText(getFmt().format(date));
                    }
                };
                addTime(beginTimelistener);
                break;
            case R.id.comit2:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),3);
                break;
            default: break;
        }
    }

    private void toZgfc() {
        document02.setCorpName(corpName.getText().toString());
        document02.setNo(no.getText().toString());
        document02.setImmediateProblem(immediateProblem.getText().toString());
        document02.setLeaderName(leaderName.getText().toString());
        document02.setLimitProblem(limitProblem.getText().toString());
        try {
            document02.setLimitTime(getFmt().parse(limitTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document02.setStatus("1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument02(document02);
                form.setSubmit(false);
                form.setEfUsers(efUsers);
                if (null != corpInfo){
                    form.setCorp(corpInfo.getId());
                }else {
                    form.setCorp(caseVo.getCorp());
                }
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/02/appreview.do");
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.addBodyParameter("status","1");
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(form));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        RemoteLoginResult result = JsonUtils.parseObject(s, RemoteLoginResult.class);
                        if (result.code == 1){
                            showShortToast(4,"操作成功！");
                            handler.sendEmptyMessage(5);
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
}
