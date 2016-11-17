package com.quanta.aj.yunanjian.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.quanta.aj.yunanjian.orm.AppUser;

import org.xutils.x;


/**
 * Created by 许德建 on 2014/12/13.
 */
public class MyApplication extends Application {

    private String host;

    //退出的密码
    public String password = "123";

    public AppUser remoteUser;

    public AppUser localUser;

    public String getHost() {
        if(host == null || host.isEmpty()) {
            SharedPreferences spf = getSharedPreferences("Cabin", Application.MODE_PRIVATE);
            host = spf.getString("HostAddress", "");
        }
        Log.i("URL", "getHost: "+host);
        return host;
    }

    public void setHost(String host) {
        if(host == null) host = "";
        SharedPreferences spf = getSharedPreferences("Cabin", Application.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("CabinHost", host);
        editor.commit();
        this.host = host;
    }

    public static MyApplication getApplication(Context context) {
        return (MyApplication)context.getApplicationContext();
    }

    public static String getUrl(Context context, String uri) {
        MyApplication app = getApplication(context);
        Log.i("URL", "getUrl: "+context);
        Log.i("URL", "getUrl: "+app.getHost());
        return String.format("http://%s%s",app.getHost(), uri);
    }

    public static AppUser getRemoteUser(Context context) {
        return getApplication(context).remoteUser;
    }

    public static AppUser getLocalUser(Context context) {
        return getApplication(context).localUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        x.Ext.init(this);
        x.Ext.setDebug(false);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        closeBar();
    }

    @Override
    public void onTerminate() {
        showBar();
        super.onTerminate();
    }

    public void showBar() {
        try {
            Process proc = Runtime.getRuntime().exec(
                    new String[]{"am", "startservice", "-n",
                            "com.android.systemui/.SystemUIService"});
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeBar() {
        try {
            // 需要root 权限
            Build.VERSION_CODES vc = new Build.VERSION_CODES();
            Build.VERSION vr = new Build.VERSION();
            String ProcID = "79";

            if (vr.SDK_INT >= vc.ICE_CREAM_SANDWICH) {
                ProcID = "42"; // ICS AND NEWER
            }

            // 需要root 权限
            Process proc = Runtime.getRuntime().exec(
                    new String[]{
                            "su",
                            "-c",
                            "service call activity " + ProcID
                                    + " s16 com.android.systemui"}); // WAS 79
            proc.waitFor();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
