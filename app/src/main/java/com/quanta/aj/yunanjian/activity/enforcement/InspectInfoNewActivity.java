package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.LoginActivity;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.activity.base.SelectUserActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.MCode;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.orm.AppCode;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfInspect;
import com.quanta.aj.yunanjian.orm.KeyValue;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPage;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPageItem;
import com.quanta.aj.yunanjian.page.UserPageItem;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.MEfInspect;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspectInfoNewActivity extends MFragmentActivity {
    private static final String TAG = "InspectInfoNewActivity";
    public static Intent getIntent(Context ctx, CorpInfo corpInfo,boolean isNew) {
        Intent intent = new Intent(ctx, InspectInfoNewActivity.class);
        intent.putExtra("corpInfo", corpInfo);
        intent.putExtra("isNew", isNew);
        return intent;
    }
    public static Intent getIntent(Context ctx, MEfInspect efInspect) {
        Intent intent = new Intent(ctx, InspectInfoNewActivity.class);
        intent.putExtra("efInspect", efInspect);
        return intent;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    typelist = JsonUtils.parseList(msg.obj.toString(),AppCode.class);
                    getType();
                    break;
                case 2:
                    corpInfo = getGson().fromJson(msg.obj.toString(),CorpInfo.class);
                    setCorpInfo();
                    initInspect();
                    break;
                case 3:
                    initDataY(docmentesY);
                    break;
                case 4:
                    //判断是不是新建的，新建的要跳到列表，否则关闭当前页即可回到列表
                    if (isNew){
                        startActivity(InspectionListActivity.getIntent(mContext,true));
                    }else {
                        setResult(RESULT_OK);
                        finish();
                    }
                    break;
                default:break;
            }
        }
    };
    @ViewInject(R.id.qyxx)
    private ListView qyxx;
    @ViewInject(R.id.type)
    private Spinner type;
    @ViewInject(R.id.startTime)
    private TextView startTime;
    @ViewInject(R.id.endTime)
    private TextView endTime;
    @ViewInject(R.id.leader)
    private EditText leader;
    @ViewInject(R.id.member)
    private EditText member;
    @ViewInject(R.id.status)
    private Spinner status;
    @ViewInject(R.id.ef_list)
    private ListView ef_list;

    private CorpInfo corpInfo;
    private ArrayList<KeyValue> mData;
    private MyAdapter corpinfAdapter;
    private MyListAdapter mylistAdapter;
    private EfInspect efInspect = new EfInspect();
    private MEfInspect mEfInspect;
    private MCode mcode = new MCode();
    private List<AppCode> typelist;
    private Context mContext;
    private List<EfDocumentPageItem> docmentesY = null;
    private int navId = 100;
    private Boolean isDone;
    private Boolean isNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inspect_info);
        x.view().inject(this);
        mContext = InspectInfoNewActivity.this;
        findViewById(R.id.cancel).setVisibility(View.GONE);
        mcode.getCodeList("JCFS",1,handler,mContext);
        mEfInspect = (MEfInspect)getIntent().getSerializableExtra("efInspect");
        corpInfo = (CorpInfo) getIntent().getSerializableExtra("corpInfo");
        isNew = getIntent().getBooleanExtra("isNew",false);
        if (null!=corpInfo){
            setCorpInfo();
            findViewById(R.id.addefbtn).setVisibility(View.GONE);//控制添加文书在新建检查是隐藏
            findViewById(R.id.endef).setVisibility(View.GONE);
            findViewById(R.id.ajcl).setVisibility(View.GONE);
            findViewById(R.id.cancel).setVisibility(View.VISIBLE);
        }else {
            if (null!=mEfInspect)getCorp();
        }
    }

    @Event(value = {R.id.leftbtn,R.id.comit,R.id.addleader,R.id.addmember,R.id.startTime,R.id.endTime,R.id.endef,R.id.addefbtn,R.id.ajcl,R.id.cancel})
    private void clink(View v){
        switch (v.getId()){
            case R.id.leftbtn:
                finish();
                break;
            case R.id.comit:
                toComint(String.valueOf(status.getSelectedItemPosition()));
                break;
            case R.id.addleader:
                startActivityForResult(SelectUserActivity.getIntent(mContext,SelectUserActivity.class),1);
                break;
            case R.id.addmember:
                startActivityForResult(SelectUserActivity.getIntent(mContext,SelectUserActivity.class),2);
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
            case R.id.endef:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"请确认检查前完成相关文书操作，是否结束本次检查？"),3);
                break;
            case R.id.addefbtn:
                startActivityForResult(InsDocnotdoActivity.getIntent(mContext,mEfInspect),100);
                break;
            case R.id.ajcl:
                startActivityForResult(Dialog_confirmActivity.getIntent(mContext,"结束检查并进入案件处理环节，是否确定本次操作？"),4);
                break;
            case R.id.cancel:
                finish();
                break;
            default:break;
        }
    }

    private void getType() {
        mylistAdapter = new MyListAdapter<AppCode>(typelist,R.layout.item_spinner) {
            @Override
            public void bindView(ViewHolder holder, AppCode obj) {
                holder.setText(R.id.value,obj.getName());
            }
        };
        type.setAdapter(mylistAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                efInspect.setType(String.valueOf(position+1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCorp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETCORP_URL);
                params.addBodyParameter("corpId",mEfInspect.getCorp());
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == -10000){
                            showShortToast(3,"登录超时！");
                            startActivity(LoginActivity.getIntent(mContext,LoginActivity.class));
                        }
                        Message msg = new Message();
                        msg.obj = s;
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"数据出错！",throwable);
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

    private void setCorpInfo() {
        //组装企业信息
        mData = new ArrayList<KeyValue>();
        mData.add(new KeyValue("被检查企业",corpInfo.getQymc()));
        mData.add(new KeyValue("企业地址",corpInfo.getZcdz()));
        mData.add(new KeyValue("法人代表",corpInfo.getFrdb()));
        mData.add(new KeyValue("联系电话",corpInfo.getFrdblxdh()));
        mData.add(new KeyValue("安全负责人",corpInfo.getAqfzr()!=null?corpInfo.getAqfzr():corpInfo.getZyfzr()));
        mData.add(new KeyValue("负责人电话",corpInfo.getAqfzrlxdh()!=null?corpInfo.getAqfzrlxdh():corpInfo.getZyfzrlxdh()));

        corpinfAdapter = new MyAdapter<KeyValue>(mData,R.layout.item_table_row) {
            @Override
            public void bindView(ViewHolder holder, KeyValue obj) {
                holder.setText(R.id.key,obj.getKey());
                holder.setText(R.id.value,obj.getValue());
            }
        };
        qyxx.setAdapter(corpinfAdapter);
    }

    private void initInspect() {
        if (null!=mEfInspect.getType()){type.setSelection(Integer.parseInt(mEfInspect.getType())-1,true);}
            startTime.setText(getFmt().format(mEfInspect.getStartTime()));
            endTime.setText(getFmt().format(mEfInspect.getEndTime()));
            leader.setText(mEfInspect.getLeader());
            member.setText(mEfInspect.getMember());
            status.setDropDownWidth(Integer.parseInt(mEfInspect.getStatus()));
        isDone = mEfInspect.getStatus().equals("0")?false:true;
        getDocYList();
    }

    private void getDocYList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfDocumentPage page = new EfDocumentPage();
                page.setInspectId(mEfInspect.getId());
                page.setStatus("1");
                page.setBuildType("2");
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOMS_URL);
                params.addBodyParameter("inspectId",page.getInspectId());
                params.addBodyParameter("status","1");
                params.addBodyParameter("buildType","2");
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        EfDocumentPage page = getGson().fromJson(s,EfDocumentPage.class);
                        docmentesY = page.getRows();
                        handler.sendEmptyMessage(3);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求出错",throwable);
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

    private void initDataY(List<EfDocumentPageItem> items) {
        mylistAdapter = new MyListAdapter<EfDocumentPageItem>(items,R.layout.item_ef_list) {
            @Override
            public void bindView(ViewHolder holder, EfDocumentPageItem obj) {
                String docmentesJson = JsonUtils.toString(obj);
                final EfDocument efDocument = getGson().fromJson(docmentesJson,EfDocument.class);
                String type = efDocument.getType();
                // if (type.equals(DocumentType.d1.type))isD1 = true;
                holder.setText(R.id.ef_name,efDocument.getName());
                if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                if (efDocument.getCreateTime() != null){
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    holder.setText(R.id.ef_time, fmt.format(efDocument.getCreateTime()));
                }else {
                    holder.setText(R.id.ef_time, "");
                }
                holder.setText(R.id.status,efDocument.getStatus().equals("0")?"未做":(efDocument.getStatus().equals("1")?"未生效":"已生效"));
                if (efDocument.getStatus().equals("1")){
                    holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.errtext));
                }else {
                    holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.green));
                }
                holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击图标打开相应文书信息界面
                        toItemClink(efDocument);
                    }
                });
            }
        };
        ef_list.setAdapter(mylistAdapter);
    }

    private void toItemClink(EfDocument efDocument) {
        //由于这里是已做文书，只要判断文书种类并传值即可
        String docName = efDocument.getName();
        if (DocumentType.d1.name.equals(docName)){
            startActivityForResult(Document01Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d2.name.equals(docName)){
            startActivityForResult(Document02Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d3.name.equals(docName)){
            startActivityForResult(Document03Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d4.name.equals(docName)){
            startActivityForResult(Document04Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d5.name.equals(docName)){
            startActivityForResult(Document05Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d6.name.equals(docName)){
            startActivityForResult(Document06Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d7.name.equals(docName)){
            startActivityForResult(Document07Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d8.name.equals(docName)){
            startActivityForResult(Document08Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d9.name.equals(docName)){
            startActivityForResult(Document09Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d10.name.equals(docName)){
            startActivityForResult(Document10Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d11.name.equals(docName)){
            startActivityForResult(Document11Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d12.name.equals(docName)){
            startActivityForResult(Document12Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d13.name.equals(docName)){
            startActivityForResult(Document13Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d14.name.equals(docName)){
            startActivityForResult(Document14Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d15.name.equals(docName)){
            startActivityForResult(Document15Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d16.name.equals(docName)){
            startActivityForResult(Document16Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d17.name.equals(docName)){
            startActivityForResult(Document17Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d18.name.equals(docName)){
            startActivityForResult(Document18Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d19.name.equals(docName)){
            startActivityForResult(Document19Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d20.name.equals(docName)){
            startActivityForResult(Document20Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d21.name.equals(docName)){
            startActivityForResult(Document21Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d22.name.equals(docName)){
            startActivityForResult(Document22Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d23.name.equals(docName)){
            startActivityForResult(Document23Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d24.name.equals(docName)){
            startActivityForResult(Document24Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d25.name.equals(docName)){
            startActivityForResult(Document25Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d26.name.equals(docName)){
            startActivityForResult(Document26Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d27.name.equals(docName)){
            startActivityForResult(Document27Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d28.name.equals(docName)){
            startActivityForResult(Document28Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d29.name.equals(docName)){
            startActivityForResult(Document29Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d30.name.equals(docName)){
            startActivityForResult(Document30Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d31.name.equals(docName)){
            startActivityForResult(Document31Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d32.name.equals(docName)){
            startActivityForResult(Document32Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d33.name.equals(docName)){
            startActivityForResult(Document33Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d34.name.equals(docName)){
            startActivityForResult(Document34Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d35.name.equals(docName)){
            startActivityForResult(Document35Activity.getIntent(mContext,efDocument,isDone),navId);
        }else if (DocumentType.d36.name.equals(docName)){
            startActivityForResult(Document36Activity.getIntent(mContext,efDocument,isDone),navId);
        }
    }

    private void toComint(String statu) {
        efInspect.setId(null!=mEfInspect?mEfInspect.getId():null);
        efInspect.setCorp(null!=mEfInspect?mEfInspect.getCorp():corpInfo.getId());
        try {
            efInspect.setStartTime(getFmt().parse(startTime.getText().toString()));
            efInspect.setEndTime(getFmt().parse(endTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        efInspect.setLeader(leader.getText().toString());
        efInspect.setMember(member.getText().toString());
        efInspect.setStatus(statu);
        efInspect.setType(String.valueOf(type.getSelectedItemPosition()+1));
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.INSPECTSAVE_URL);
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(efInspect));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == 1){
                            showShortToast(4,"操作成功！");
                            handler.sendEmptyMessage(4);
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

    private void toAjcl(){
        efInspect.setId(null!=mEfInspect?mEfInspect.getId():null);
        efInspect.setCorp(null!=mEfInspect?mEfInspect.getCorp():corpInfo.getId());
        try {
            efInspect.setStartTime(getFmt().parse(startTime.getText().toString()));
            efInspect.setEndTime(getFmt().parse(endTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        efInspect.setLeader(leader.getText().toString());
        efInspect.setMember(member.getText().toString());
        efInspect.setStatus("1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.INSPECTSAVECASE_URL);
                params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
                params.setAsJsonContent(true);
                params.setBodyContent(JsonUtils.toString(efInspect));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        com.quanta.aj.yunanjian.orm.Message result = JsonUtils.parseObject(s, com.quanta.aj.yunanjian.orm.Message.class);
                        if (result.code == 1){
                            showShortToast(4,"操作成功！");
                            handler.sendEmptyMessage(4);
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
                    String rs = data.getStringExtra("users");
                    String ld = "";
                    List<UserPageItem> user = JsonUtils.parseList(rs,UserPageItem.class);
                   for (int i = 0;i<user.size();i++){
                      ld += user.get(i).getName()+" ";
                   }
                   leader.setText(ld);
               }
               break;
           case 2:
               if (resultCode == RESULT_OK){
                   String rs = data.getStringExtra("users");
                   String ld = "";
                   List<UserPageItem> user = JsonUtils.parseList(rs,UserPageItem.class);
                   for (int i = 0;i<user.size();i++){
                       ld += user.get(i).getName()+" ";
                   }
                   member.setText(ld);
               }
               break;
           case 3:
               if (resultCode == RESULT_OK)toComint("1");
               break;
           case 4:
               if (resultCode == RESULT_OK)toAjcl();
               break;
           case 100:
               getDocYList();
               break;
           default:break;
       }
    }
}
