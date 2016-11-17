package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Dialog_jnmu;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.Document36List;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument36;
import com.quanta.aj.yunanjian.page.enforcement.Document36ListPage;
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

public class Document36Activity extends MFragmentActivity {
    private static final String TAG = "Document36Activity";
    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document36Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document36Activity.class);
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
                    addD36(document36Lists);
                    break;
                case 2:

                    break;
            }
        }
    };


    @ViewInject(R.id.domtt)
    private Title_Sm_Layout domtt;
    @ViewInject(R.id.zfrylist)
    private ListView zfrylist;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument36 document36 = new EfDocument36();
    private Document36List document36List;
    private List item = new ArrayList();
    private List<Document36List> document36Lists = new LinkedList<>();
    private DocumentForm form = new DocumentForm();
    private MyListAdapter myListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document36);
        x.view().inject(this);
        mContext = Document36Activity.this;
        efdcoment = (EfDocument) getIntent().getSerializableExtra("efDocument");
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d36.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void getData() {
        if (null!=efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETDOM36_URL);
                    params.addBodyParameter("documentList",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Document36ListPage page = getGson().fromJson(s,Document36ListPage.class);
                            document36Lists = page.getRows();
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

    private void addD36(List<Document36List> document36Lists) {
        myListAdapter = new MyListAdapter<Document36List>(document36Lists,R.layout.item_jumn) {
            @Override
            public void bindView(final ViewHolder holder, final Document36List obj) {
                holder.setText(R.id.title,obj.getTitle());
                holder.setText(R.id.no,obj.getNo());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox)v;
                        if (box.isChecked()){
                            item.add(holder.getItemPosition());
                        }
                    }
                });
                holder.setOnClickListener(R.id.rightbtn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        document36List = obj;
                        myListAdapter.remove(holder.getItemPosition());
                        startActivityForResult(Dialog_jnmu.getIntent(mContext,document36List),2);
                    }
                });
            }
        };
        zfrylist.setAdapter(myListAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    document36List = (Document36List)data.getSerializableExtra("document36List");
                    document36Lists.add(document36List);
                    addD36(document36Lists);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    document36List = (Document36List) data.getSerializableExtra("document36List") ;
                    document36Lists.add(document36List);
                    addD36(document36Lists);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK){
                    toComit(true);
                    break;
                }
            default:break;
        }
    }
    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.beginTime,R.id.endTime,R.id.comit2})
    private void clink(View v){
        switch (v.getId()){
            case R.id.addzfry://添加执法人员
                startActivityForResult(Dialog_jnmu.getIntent(mContext,Dialog_jnmu.class),1);
                break;
            case R.id.delzfry://删除执法人员
                deljnmu();
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
            case R.id.comit2:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),3);
                break;
            default:break;
        }
    }

    private void toComit(final Boolean submit) {
        document36.setId(efdcoment.getDocument());
        document36.setEp("ep");
        new Thread(new Runnable() {
            @Override
            public void run() {
                form.setDocument36(document36);
                form.setSubmit(submit);
                form.setDocument36List(document36Lists);
               // form.setDocumentId(efdcoment.getId());
                if (null!=efdcoment){form.setDocumentId(efdcoment.getId());}else {
                    String docId = getIntent().getStringExtra("docId");
                    form.setDocumentId(docId);
                }
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL+"/36/appsave.do");
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

    private void deljnmu() {
        if (null!=item){
            for (int i=item.size()-1;i>=0;i--){
                document36Lists.remove(Integer.parseInt(String.valueOf(item.get(i))));
            }
            item.clear();//清空以备下次使用
            addD36(document36Lists);
        }
    }
}
