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
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_addEflist;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument14;
import com.quanta.aj.yunanjian.orm.enforcement.EfList;
import com.quanta.aj.yunanjian.page.enforcement.EfListPage;
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

public class Document14Activity extends MFragmentActivity {
    private static final String TAG = "Document14Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document14Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document14Activity.class);
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
                    addZfry(efLists);
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
    @ViewInject(R.id.bcdw)
    private EditText bcdw;
    @ViewInject(R.id.jzsj)
    private TextView jzsj;
    @ViewInject(R.id.sldw)
    private EditText sldw;
    @ViewInject(R.id.dwfzr)
    private EditText dwfzr;
    @ViewInject(R.id.sxxw)
    private EditText sxxw;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument14 document14 = new EfDocument14();
    private DocumentForm form = new DocumentForm();
    private EfList efList;
    private EfListPage efListPage = new EfListPage();
    private List eflistitem = new ArrayList();
    private List<EfList> efLists = new LinkedList<>();
    private MyListAdapter myListAdapter;
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document14);
        x.view().inject(this);
        mContext = Document14Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d14.name);
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
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/14/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document14 = getGson().fromJson(s,EfDocument14.class);
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
        no.setText(document14.getNo());
        bcdw.setText(document14.getBcdw());
        jzsj.setText(getFmt().format(document14.getJzsj()));
        sldw.setText(document14.getSldw());
        dwfzr.setText(document14.getDwfzr());
        sxxw.setText(document14.getSxxw());
    }
    private void getZfry() {
        if (null!=document14.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETPAGELIST_URL);
                    efListPage.setDocument(document14.getId());
                    params.addParameter("page",efListPage);
                    params.addBodyParameter("document",document14.getId());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            EfListPage listPage = JsonUtils.parseObject(s,EfListPage.class);
                            efLists = listPage.getRows();
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
    private void addZfry(List<EfList> efLists) {
        myListAdapter = new MyListAdapter<EfList>(efLists,R.layout.item_eflist) {
            @Override
            public void bindView(final ViewHolder holder, final EfList obj) {
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.type,obj.getType());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox)v;
                        if (box.isChecked()){
                            eflistitem.add(holder.getItemPosition()); //获得选中的列传出值去
                        }
                    }
                });
                holder.setOnClickListener(R.id.rightbtn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        efList = obj;
                        myListAdapter.remove(holder.getItemPosition());
                        startActivityForResult(Dialog_addEflist.getIntent(mContext,efList),2);
                    }
                });
            }
        };
        zfrylist.setAdapter(myListAdapter);
    }
    private void delZfry() {
        if (null!=eflistitem){
            for (int i=eflistitem.size()-1;i>=0;i--){
                efLists.remove(Integer.parseInt(String.valueOf(eflistitem.get(i))));
            }
            eflistitem.clear();//清空以备下次使用
            addZfry(efLists);
        }
    }
    private void toComit(final Boolean submit) {
        document14.setNo(no.getText().toString());
        document14.setBcdw(bcdw.getText().toString());
        try {
            document14.setJzsj(getFmt().parse(jzsj.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document14.setSldw(sldw.getText().toString());
        document14.setDwfzr(dwfzr.getText().toString());
        document14.setSxxw(sxxw.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument14(document14);
                form.setSubmit(submit);
                form.setEfLists(efLists);
                //form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/14/appsave.do");
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
                    efList = (EfList)data.getSerializableExtra("efList");
                    efLists.add(efList);
                    addZfry(efLists);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    efList = (EfList)data.getSerializableExtra("efList");
                    efLists.add(efList);
                    addZfry(efLists);
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
    @Event(value = {R.id.comit,R.id.print,R.id.jzsj,R.id.addzj,R.id.delzj,R.id.comit2})
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
            case R.id.jzsj:
                SlideDateTimeListener jzsjlistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        jzsj.setText(getFmt().format(date));
                    }
                };
                addTime(jzsjlistener);
                break;
            case R.id.addzj:
                startActivityForResult(Dialog_addEflist.getIntent(mContext,Dialog_addEflist.class),1);
                break;
            case R.id.delzj:
                delZfry();
                break;
            case R.id.comit2:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),3);
                break;
            default: break;
        }
    }
}
