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
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.mywegit.Dialog_adzfryActivity;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.Actionmenu;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument01;
import com.quanta.aj.yunanjian.orm.enforcement.EfUser;
import com.quanta.aj.yunanjian.page.enforcement.EfUserPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.DocumentForm;
import com.quanta.aj.yunanjian.vo.enforcement.MEFcaseInfo;
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

public class Document01Activity extends MFragmentActivity {
    private static final String TAG = "Document01Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document01Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document01Activity.class);
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
    @ViewInject(R.id.corpName)//被检查单位
    private EditText corpName;
    @ViewInject(R.id.corpAdress)//单位地址
    private EditText corpAdress;
    @ViewInject(R.id.fddbr)//法定代表人
    private EditText fddbr;
    @ViewInject(R.id.fddbrlxdh)//法定代表人电话
    private EditText fddbrlxdh;
    @ViewInject(R.id.zyfzr)//主要负责人
    private EditText zyfzr;
    @ViewInject(R.id.zyfzrlxdh)//主要负责人电话
    private EditText zyfzrlxdh;
    @ViewInject(R.id.place)//检查场所
    private EditText place;
    @ViewInject(R.id.flow)
    private Spinner flow;
    @ViewInject(R.id.beginTime)//检查开始时间
    private TextView beginTime;
    @ViewInject(R.id.endTime)//检查结束时间
    private TextView endTime;
    @ViewInject(R.id.note)//检查情况
    private EditText note;
    @ViewInject(R.id.zfrylist)
    private ListView zfrylist;
    /*@ViewInject(R.id.print)
    private LinearLayout print;*/

    private Context mContext;
    private MyListAdapter myListAdapter;
    private List<Actionmenu> mData;
    private int navId = 1;
    private EfUserPage efUserpage = new EfUserPage();
    private EfDocument efdcoment;
    private EfDocument01 document01 = new EfDocument01();
    private List<EfUser> efUsers = new LinkedList<>();
    private DocumentForm form = new DocumentForm();
    private List useritme = new ArrayList();
    private CorpInfo corpInfo;
    private MEFcaseInfo meFcaseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document01);
        x.view().inject(this);
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (null != corpInfo)initCorpData();
        //如果已结案或者文书已提交生效，则隐藏保存和提交按钮
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        mContext = Document01Activity.this;
        domtt.setTitle2(DocumentType.d1.name);
        domtt.setTitleSize(20);
        meFcaseInfo = (MEFcaseInfo) getIntent().getSerializableExtra("caseInfo");
        getData();
    }
    /**
     * 获取文书信息
     */
    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/01/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document01 = getGson().fromJson(s,EfDocument01.class);
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

    /**
     * 获取执法人员
     */
    private void getZfry(){
        if (null!=document01.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETZFRY_URL);
                    efUserpage.setDocument(document01.getId());
                    params.addParameter("page",efUserpage);
                    params.addBodyParameter("document",document01.getId());
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

    /**
     * 初始化界面
     */
    private void initData() {
        corpName.setText(document01.getCorpName());
        corpAdress.setText(document01.getCorpAdress());
        fddbr.setText(document01.getFddbr());
        fddbrlxdh.setText(document01.getFddbrlxdh());
        zyfzr.setText(document01.getZyfzr());
        zyfzrlxdh.setText(document01.getZyfzrlxdh());
        place.setText(document01.getPlace());
        flow.setSelection(Integer.valueOf(document01.getFlow()).intValue()-1,true);
        beginTime.setText(getFmt().format(document01.getBeginTime()));
        endTime.setText(getFmt().format(document01.getEndTime()));
        note.setText(document01.getNote());
    }
    private void initCorpData() {
        corpName.setText(corpInfo.getQymc());
        corpAdress.setText(corpInfo.getZcdz());
        fddbr.setText(corpInfo.getFrdb());
        fddbrlxdh.setText(corpInfo.getFrdblxdh());
        zyfzr.setText(corpInfo.getZyfzr());
        zyfzrlxdh.setText(corpInfo.getZyfzrlxdh());
    }

    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.beginTime,R.id.endTime,R.id.comit2})
    private void clink(View v){
        switch (v.getId()){
            case R.id.addzfry://添加执法人员
                startActivityForResult(Dialog_adzfryActivity.getIntent(mContext,Dialog_adzfryActivity.class),navId);
                break;
            case R.id.delzfry://删除执法人员
                delZfry();
                break;
            case R.id.comit://保存按钮
                toComit(false);
                break;
            case R.id.print://打印按钮
                if (null!=efdcoment) {
                    startActivity(DocPrintActivity.getIntent(mContext, efdcoment));
                }else {
                    showShortToast(4,"该文书不存在或尚未制作！");
                }
                break;
            case R.id.beginTime://获取开始时间
                 SlideDateTimeListener beginTimelistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        beginTime.setText(getFmt().format(date));
                    }
                };
                addTime(beginTimelistener);
                break;
            case R.id.endTime://获取结束时间
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
            default:break;
        }
    }

    /**
     * 保存
     */
    private void toComit(final Boolean submit) {
        if (efUsers == null||efUsers.size()<1){
            showShortToast(1,"执法人员不能为空！");
        }else {
            document01.setCorpName(corpName.getText().toString());
            document01.setCorpAdress(corpAdress.getText().toString());
            document01.setFddbr(fddbr.getText().toString());
            document01.setFddbrlxdh(fddbrlxdh.getText().toString());
            document01.setZyfzr(zyfzr.getText().toString());
            document01.setZyfzrlxdh(zyfzrlxdh.getText().toString());
            document01.setPlace(place.getText().toString());
            document01.setFlow(String.valueOf(flow.getSelectedItemPosition() + 1));
            try {
                document01.setBeginTime(getFmt().parse(beginTime.getText().toString()));
                document01.setEndTime(getFmt().parse(endTime.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            document01.setNote(note.getText().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    form.setDocument01(document01);
                    form.setSubmit(submit);
                    form.setEfUsers(efUsers);
                    if (null != efdcoment) {
                        form.setDocumentId(efdcoment.getId());
                    } else {
                        String docId = getIntent().getStringExtra("docId");
                        form.setDocumentId(docId);
                    }
                    MyApplication app = (MyApplication) Document01Activity.this.getApplication();
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL + "/01/appsave.do");
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
                                showShortToast(3, "操作失败！" + result.code);
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
            default:
        }
    }

    /**
     * 往执法人员列表中添加数据
     * @param efUser
     */
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
    /**
     * 删除执法人员
     */
    private void delZfry() {
        if (null!=useritme){
           for (int i=useritme.size()-1;i>=0;i--){
                efUsers.remove(Integer.parseInt(String.valueOf(useritme.get(i))));
            }
            useritme.clear();//清空以备下次使用
            addZfry(efUsers);
        }
    }
}
