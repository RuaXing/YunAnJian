package com.quanta.aj.yunanjian.constant;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.CookieStore;

/**
 * Created by lenovo on 2016/9/13.
 */
public class Constant {
    public static CookieStore cookieStore;
    public static String JSESSIONID;
    public static String ZW_SESSION;
    private static String HOST;

    public static String getHOST(Context context) {
        if (null==HOST || HOST.isEmpty()){
            SharedPreferences sp = context.getSharedPreferences("Cabin", Activity.MODE_PRIVATE);
            HOST ="http://"+ sp.getString("HostAddress","")+"/pssms";
            Log.i("URL", "getHost: "+HOST);
        }
        return HOST;
    }

    public static void setHOST(String HOST) {
        Constant.HOST = HOST;
    }
}
