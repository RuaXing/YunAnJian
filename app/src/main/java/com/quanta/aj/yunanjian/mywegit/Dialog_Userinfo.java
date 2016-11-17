package com.quanta.aj.yunanjian.mywegit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.activity.base.LoginActivity;
import com.quanta.aj.yunanjian.api.UserInfo;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Dialog_Userinfo extends BaseActivity {
    private static final String TAG = "Dialog_Userinfo";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData();
                    break;
            }
        }
    };
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.sex)
    private Spinner sex;
    @ViewInject(R.id.mobile)
    private EditText mobile;
    @ViewInject(R.id.email)
    private EditText email;
    @ViewInject(R.id.no)
    private TextView no;
    @ViewInject(R.id.account)
    private TextView account;
    @ViewInject(R.id.dept)
    private TextView dept;
    @ViewInject(R.id.post)
    private TextView post;

    private UserInfo userInfo;
    private MyApplication application;
    private Context mContext;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__userinfo);
        x.view().inject(this);
        mContext = Dialog_Userinfo.this;
        application = (MyApplication)Dialog_Userinfo.this.getApplication();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        getAppUser();
    }
    @Event(value = {R.id.btn_left,R.id.btn_qhyh,R.id.comit,R.id.sctx})
    private void clink(View v){
        switch (v.getId()){
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_qhyh:
                toQhyh();
                break;
            case R.id.comit:
                toComit();
                break;
            case R.id.sctx:
                showShortToast(4,"暂不支持该功能！");
                break;
            default:break;
        }
    }

    private void getAppUser() {
        RequestParams params = new RequestParams(Constant.getHOST(mContext)+"/app/user/getInfo.do");
        params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String s) {
                userInfo = JsonUtils.parseObject(s,UserInfo.class);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e(TAG,"失败",throwable);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void initData(){
        name.setText(userInfo.getName());
        sex.setSelection(Integer.parseInt(userInfo.getGender())-1);
        mobile.setText(userInfo.getMobile());
        email.setText(userInfo.getEmail());
        no.setText(userInfo.getNo());
        account.setText(userInfo.getAccount());
        dept.setText(userInfo.getDeptName());
        post.setText(userInfo.getPostName());
    }
    private void toComit(){
        RequestParams params = new RequestParams(Constant.getHOST(mContext)+"/app/user/updateMine.do");
        params.addHeader("Cookie","JSESSIONID="+ Constant.JSESSIONID+";ZW_SESSION="+Constant.JSESSIONID);
        params.addBodyParameter("email",email.getText().toString());
        params.addBodyParameter("name",name.getText().toString());
        params.addBodyParameter("gender",String.valueOf(sex.getSelectedItemPosition()+1));
        params.addBodyParameter("mobile",mobile.getText().toString());
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
    private void toQhyh(){
        editor = sp.edit();
        editor.putBoolean("remember_pass", false);
        editor.putBoolean("zddl", false);
        editor.commit();
        startActivity(LoginActivity.getIntent(mContext,LoginActivity.class));
    }
}
