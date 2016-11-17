package com.quanta.aj.yunanjian.activity.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.helper.MyApplication;
import com.quanta.aj.yunanjian.mywegit.Dialog_Loading;
import com.quanta.aj.yunanjian.util.ActivityCollector;
import com.quanta.aj.yunanjian.util.CryptUtils;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.RemoteLoginResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.List;


@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    public static String JSESSIONID;
    public static Intent getIntent(Context ctx, boolean isLocal) {
        Intent intent = new Intent(ctx, LoginActivity.class);
        intent.putExtra("isLocal", isLocal);
        return intent;
    }
    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.remember_pass)
    private CheckBox remember_pass;
    @ViewInject(R.id.zddl)
    private CheckBox zddl;

    private boolean isLocal;
    private Context mContext;
    private Dialog_Loading dialog;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dialog.show();
                    break;
                case 2:
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                        showShortToast(3,getString(R.string.connection_fail));
                    }
                    break;
                case 3:
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                        showShortToast(4,"欢迎"+msg.obj+"用户!");
                        startActivity(Index_Menu_Activity.getIntent(mContext, Index_Menu_Activity.class));
                    }
                    break;
                case 4:
                    if(dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        showShortToast(3,msg.obj+"!");
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        isLocal = getIntent().getBooleanExtra("isLocal", true);
        x.view().inject(this);
        mContext = LoginActivity.this;
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        dialog = new Dialog_Loading(mContext,"登录中...");

        if(sp.getBoolean("remember_pass", false))
        {
            //设置默认是记录密码状态
            remember_pass.setChecked(true);
            account.setText(sp.getString("account", ""));
            password.setText(sp.getString("password", ""));
            //判断自动登陆多选框状态
            if(sp.getBoolean("zddl", false))
            {
                zddl.setChecked(true);
                toLogin();
            }
        }
    }

    @Event(value = {R.id.button_submit, R.id.button_cancel, R.id.remember_pass,R.id.zddl,R.id.setting})
    private void clink(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                toLogin();
                break;
            case R.id.button_cancel:
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.remember_pass:
                editor = sp.edit();
                if (remember_pass.isChecked()){
                    editor.putBoolean("remember_pass", true);
                    editor.putString("account",account.getText().toString());
                    editor.putString("password",password.getText().toString());
                }else {
                    editor.clear();
                }
                editor.commit();
                break;
            case R.id.zddl:
                editor = sp.edit();
                if (zddl.isChecked()){
                    editor.putBoolean("zddl", true);
                }else {
                    editor.putBoolean("zddl", false);
                }
                editor.commit();
                break;
            case R.id.setting:
                startActivity(Host_Setting_Activity.getIntent(mContext,Host_Setting_Activity.class));
            default:
                break;
        }
    }

    private void toLogin() {
        final String sa = account.getText().toString().trim();
        if (sa.isEmpty()) {
            account.setError("帐号不能为空");
            return;
        }
        final String sp =CryptUtils.encode( password.getText().toString());//加密操作
        Log.i(TAG,"sp"+sp);
        if (sp.isEmpty()) {
            password.setError("密码不能为空");
            return;
        }
        handler.sendEmptyMessage(1);
        RequestParams params = new RequestParams(Constant.getHOST(mContext)+ URLs.LOGIN_URL);
        Log.d(TAG,"HOST: "+Constant.getHOST(mContext));
        String m_szDevIDShort = "66" +
                Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 位
        String  device= m_szDevIDShort;
        String org="android_ORG";
        params.addBodyParameter("device",device);
        params.addBodyParameter("org",org);
        params.addBodyParameter("account",sa);
        params.addBodyParameter("password",sp);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                //处理cookie
                DbCookieStore instance = DbCookieStore.INSTANCE;
                Constant.cookieStore = instance;
                List<HttpCookie> cookies = instance.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    HttpCookie cookie = cookies.get(i);
                    if (cookie.getName() != null  && cookie.getName().equals("JSESSIONID")) {
                        Constant.JSESSIONID = cookie.getValue();
                    }
                    if (cookie.getName() != null  && cookie.getName().equals("ZW_SESSION")) {
                        Constant.ZW_SESSION = cookie.getValue();
                    }
                }
                //处理数据
                RemoteLoginResult result = JsonUtils.parseObject(s, RemoteLoginResult.class);
                if (result.code == 1){
                    MyApplication app = (MyApplication) LoginActivity.this.getApplication();
                    app.remoteUser = result.data;
                    app.localUser = app.remoteUser;
                    Message message = new Message();
                    message.what = 3;
                    message.obj = app.remoteUser.getName();
                    handler.sendMessage(message);
                }
                if (result.code == -1){
                    Message message = new Message();
                    message.what = 4;
                    message.obj = result.msg;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

