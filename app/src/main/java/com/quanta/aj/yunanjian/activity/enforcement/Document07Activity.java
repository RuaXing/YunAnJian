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
import com.quanta.aj.yunanjian.mywegit.Dialog_adzfryActivity;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.mywegit.Title_Sm_Layout;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument07;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Document07Activity extends MFragmentActivity {
    private static final String TAG = "Document07Activity";

    public static Intent getIntent(Context ctx, EfDocument efDocument,Boolean isDone) {
        Intent intent = new Intent(ctx, Document07Activity.class);
        intent.putExtra("efDocument", efDocument);
        intent.putExtra("isDone", isDone);
        return intent;
    }
    public static Intent getIntent(Context ctx, CorpInfo corpInfo, String docId, EfCaseInfo caseInfo) {
        Intent intent = new Intent(ctx, Document07Activity.class);
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
    @ViewInject(R.id.startTime)
    private TextView startTime;
    @ViewInject(R.id.endTime)
    private TextView endTime;
    @ViewInject(R.id.persent)
    private EditText persent;
    @ViewInject(R.id.times)
    private EditText times;
    @ViewInject(R.id.place)
    private EditText place;
    @ViewInject(R.id.ajjbqk)
    private EditText ajjbqk;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.sex)
    private Spinner sex;
    @ViewInject(R.id.age)
    private EditText age;
    @ViewInject(R.id.number)
    private EditText number;
    @ViewInject(R.id.corpName)
    private EditText corpName;
    @ViewInject(R.id.post)
    private EditText post;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.questioning)
    private EditText questioning;
    @ViewInject(R.id.questioningCorp)
    private EditText questioningCorp;
    @ViewInject(R.id.questioningPost)
    private EditText questioningPost;
    @ViewInject(R.id.recorder)
    private EditText recorder;
    @ViewInject(R.id.recorderCorp)
    private EditText recorderCorp;
    @ViewInject(R.id.recorderPost)
    private EditText recorderPost;
    @ViewInject(R.id.question)
    private EditText question;
    @ViewInject(R.id.record)
    private EditText record;

    private Context mContext;
    private EfDocument efdcoment;
    private EfDocument07 document07 = new EfDocument07();
    private List<EfUser> efUsers = new LinkedList<>();
    private MyListAdapter myListAdapter;
    private List useritme = new ArrayList();
    private DocumentForm form = new DocumentForm();
    private EfUserPage efUserpage = new EfUserPage();
    private CorpInfo corpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document07);
        x.view().inject(this);
        mContext = Document07Activity.this;
        efdcoment = (EfDocument)getIntent().getSerializableExtra("efDocument");
        corpInfo = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        if (null != corpInfo)initCorpData();
        Boolean isDone = getIntent().getBooleanExtra("isDone",false);
        if (isDone||(null != efdcoment&&efdcoment.getStatus().equals("2"))){
            findViewById(R.id.co).setVisibility(View.GONE);
            findViewById(R.id.tj).setVisibility(View.GONE);
        }
        domtt.setTitle2(DocumentType.d7.name);
        domtt.setTitleSize(20);
        getData();
    }

    private void initCorpData() {
        corpName.setText(corpInfo.getQymc());
    }

    private void getData() {
        if (null != efdcoment){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOCUMENT_URL+"/07/get.do");
                    params.addBodyParameter("id",efdcoment.getDocument());
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            document07 = getGson().fromJson(s,EfDocument07.class);
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
        startTime.setText(getFmt().format(document07.getStartTime()));
        endTime.setText(getFmt().format(document07.getEndTime()));
        persent.setText(document07.getPersent());
        times.setText(document07.getTimes());
        place.setText(document07.getPlace());
        name.setText(document07.getName());
        //sex.setDropDownWidth(Integer.parseInt(document07.getSex()));
        sex.setSelection(Integer.parseInt(document07.getSex()),true);
        age.setText(String.valueOf(document07.getAge()));
        number.setText(document07.getNumber());
        corpName.setText(document07.getCorpName());
        post.setText(document07.getPost());
        address.setText(document07.getAddress());
        phone.setText(document07.getPhone());
        questioning.setText(document07.getQuestioning());
        questioningCorp.setText(document07.getQuestioningCorp());
        questioningPost.setText(document07.getQuestioningPost());
        recorder.setText(document07.getRecord());
        recorderCorp.setText(document07.getRecorderCorp());
        recorderPost.setText(document07.getRecorderPost());
        question.setText(document07.getQuestion());
        record.setText(document07.getRecord());
    }
    private void getZfry() {
        if (null!=document07.getId()){
            //获取执法人员
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETZFRY_URL);
                    efUserpage.setDocument(document07.getId());
                    params.addParameter("page",efUserpage);
                    params.addBodyParameter("document",document07.getId());
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
            try {
                document07.setStartTime(getFmt().parse(startTime.getText().toString()));
                document07.setEndTime(getFmt().parse(endTime.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            document07.setPersent(persent.getText().toString());
            document07.setTimes(times.getText().toString());
            document07.setPlace(place.getText().toString());
            document07.setName(name.getText().toString());
            document07.setSex(String.valueOf(sex.getSelectedItemPosition()));
            document07.setAge(Integer.parseInt(age.getText().toString()));
            document07.setNumber(number.getText().toString());
            document07.setCorpName(corpName.getText().toString());
            document07.setPost(post.getText().toString());
            document07.setAddress(address.getText().toString());
            document07.setPhone(phone.getText().toString());
            document07.setQuestioning(questioning.getText().toString());
            document07.setQuestioningCorp(questioningCorp.getText().toString());
            document07.setQuestioningPost(questioningPost.getText().toString());
            document07.setRecorder(recorder.getText().toString());
            document07.setRecorderCorp(recorderCorp.getText().toString());
            document07.setRecorderPost(recorderPost.getText().toString());
            document07.setQuestion(question.getText().toString());
            document07.setRecord(record.getText().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    form.setDocument07(document07);
                    form.setSubmit(submit);
                    form.setEfUsers(efUsers);
                    //form.setDocumentId(efdcoment.getId());
                    if (null != efdcoment) {
                        form.setDocumentId(efdcoment.getId());
                    } else {
                        String docId = getIntent().getStringExtra("docId");
                        form.setDocumentId(docId);
                    }
                    RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.SAVEDOM_URL + "/07/appsave.do");
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
    @Event(value = {R.id.addzfry,R.id.delzfry,R.id.comit,R.id.print,R.id.startTime,R.id.endTime,R.id.comit2})
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
            case R.id.comit2:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"提交后文书将不能更改，是否确定提交？"),2);
                break;
            default: break;
        }
    }
}
