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
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_AgentActivity;
import com.quanta.aj.yunanjian.mywegit.Dialog_addAgentActivity;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfAgent;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument23;
import com.quanta.aj.yunanjian.page.enforcement.EfAgentPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
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

public class Document23Activity extends MFragmentActivity {
    private static final String TAG = "Document23Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document23Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document23Activity.class);
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
                    addZfry(efAgents);
                    break;
            }
        }
    };
    @ViewInject(R.id.domtt)
    private Title_Sm_Layout domtt;
    @ViewInject(R.id.zfrylist)
    private ListView zfrylist;
    @ViewInject(R.id.ajmc)
    private EditText ajmc;
    @ViewInject(R.id.zctzjg)
    private EditText zctzjg;
    @ViewInject(R.id.startTime)
    private TextView startTime;
    @ViewInject(R.id.endTime)
    private TextView endTime;
    @ViewInject(R.id.tzjgdd)
    private EditText tzjgdd;
    @ViewInject(R.id.zcr)
    private EditText zcr;
    @ViewInject(R.id.tzr)
    private EditText tzr;
    @ViewInject(R.id.sjy)
    private EditText sjy;
    @ViewInject(R.id.fddbr)
    private EditText fddbr;
    @ViewInject(R.id.zw)
    private EditText zw;
    @ViewInject(R.id.sex)
    private Spinner sex;
    @ViewInject(R.id.age)
    private EditText age;
    @ViewInject(R.id.gzdw)
    private EditText gzdw;
    @ViewInject(R.id.dcry)
    private EditText dcry;
    @ViewInject(R.id.dcryzh)
    private EditText dcryzh;
    @ViewInject(R.id.dsr)
    private EditText dsr;
    @ViewInject(R.id.qtcyr)
    private EditText qtcyr;
    @ViewInject(R.id.tzjl)
    private EditText tzjl;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument23 document23 = new EfDocument23();
    private List<EfAgent> efAgents = new LinkedList<>();
    private MyListAdapter myListAdapter;
    private List efagentitem = new ArrayList();
    private DocumentForm form = new DocumentForm();
    private EfAgentPage efAgentPage = new EfAgentPage();
    private EfAgent efAgent;
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document23);
        x.view().inject(this);
        mContext = Document23Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d23.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        fddbr.setText(corpInfo.getFrdb());
        gzdw.setText(corpInfo.getQymc());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/23/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document23 = getGson().fromJson(s,EfDocument23.class);
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
        ajmc.setText(document23.getAjmc());
        zctzjg.setText(document23.getZctzjg());
        startTime.setText(getFmt().format(document23.getStartTime()));
        endTime.setText(getFmt().format(document23.getEndTime()));
        tzjgdd.setText(document23.getTzjgdd());
        zcr.setText(document23.getZcr());
        tzr.setText(document23.getTzr());
        sjy.setText(document23.getSjy());
        fddbr.setText(document23.getFddbr());
        zw.setText(document23.getZw());
        //sex.setDropDownWidth(Integer.parseInt(document23.getSex()));
        sex.setSelection(Integer.parseInt(document23.getSex()),true);
        age.setText(document23.getAge());
        gzdw.setText(document23.getGzdw());
        dcry.setText(document23.getDcry());
        dcryzh.setText(document23.getDcryzh());
        dsr.setText(document23.getDsr());
        qtcyr.setText(document23.getQtcyr());
        tzjl.setText(document23.getTzjl());
    }
    private void getZfry() {
        if (null!=document23.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETAGENT_URL);
                    efAgentPage.setDocument(document23.getId());
                    params.addParameter("page",efAgentPage);
                    params.addBodyParameter("document",document23.getId());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            EfAgentPage agentPage = JsonUtils.parseObject(s,EfAgentPage.class);
                            efAgents = agentPage.getRows();
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
    private void addZfry(List<EfAgent> efAgents) {
        myListAdapter = new MyListAdapter<EfAgent>(efAgents,R.layout.item_agent) {
            @Override
            public void bindView(final ViewHolder holder, final EfAgent obj) {
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.sex,obj.getSex().equals("0")?"男":"女");
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox)v;
                        if (box.isChecked()){
                            efagentitem.add(holder.getItemPosition()); //获得选中的列传出值去
                        }
                    }
                });
                holder.setOnClickListener(R.id.rightbtn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(Dialog_AgentActivity.getIntent(mContext,obj));
                    }
                });
            }
        };
        zfrylist.setAdapter(myListAdapter);
    }
    private void delZfry() {
        if (null!=efagentitem){
            for (int i=efagentitem.size()-1;i>=0;i--){
                efAgents.remove(Integer.parseInt(String.valueOf(efagentitem.get(i))));
            }
            efagentitem.clear();//清空以备下次使用
            addZfry(efAgents);
        }
    }
    private void toComit(final boolean submit) {
        document23.setAjmc(ajmc.getText().toString());
        document23.setZctzjg(zctzjg.getText().toString());
        try {
            document23.setStartTime(getFmt().parse(startTime.getText().toString()));
            document23.setEndTime(getFmt().parse(endTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document23.setTzjgdd(tzjgdd.getText().toString());
        document23.setZcr(zcr.getText().toString());
        document23.setTzr(tzr.getText().toString());
        document23.setSjy(sjy.getText().toString());
        document23.setFddbr(fddbr.getText().toString());
        document23.setZw(zw.getText().toString());
        document23.setSex(String.valueOf(sex.getSelectedItemPosition()));
        document23.setAge(age.getText().toString());
        document23.setGzdw(gzdw.getText().toString());
        document23.setDcry(dcry.getText().toString());
        document23.setDcryzh(dcryzh.getText().toString());
        document23.setDsr(dsr.getText().toString());
        document23.setQtcyr(qtcyr.getText().toString());
        document23.setTzjl(tzjl.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument23(document23);
                form.setSubmit(submit);
                form.setEfAgents(efAgents);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/23/appsave.do");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    efAgent = new EfAgent();
                    efAgent = (EfAgent) data.getSerializableExtra("efAgent");
                    efAgent.setDocument(efdcoment.getId());
                    efAgents.add(efAgent);
                    addZfry(efAgents);
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
    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.startTime,R.id.endTime,R.id.comit2})
    private void clink(View v){
        switch (v.getId()){
            case R.id.addzfry:
                startActivityForResult(Dialog_addAgentActivity.getIntent(mContext,Dialog_addAgentActivity.class),1);
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
            case R.id.startTime:
                SlideDateTimeListener startTimeistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        startTime.setText(getFmt().format(date));
                    }
                };
                addTime(startTimeistener);
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
}
