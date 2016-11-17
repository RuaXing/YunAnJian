package com.quanta.aj.yunanjian.constant;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.quanta.aj.yunanjian.orm.AppCode;
import com.quanta.aj.yunanjian.util.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by 罗鑫 on 2016/9/18.
 */
public class MCode {
    private static final String TAG = "MCode";
    /**
     *
     * @param type 查询的类型
     * @param code  返回的代码
     * @param i     用来区别不同类型以便返回hander的what
     * @param handler
     */
    public void getCode(final String type, final int code,final int i,Handler handler,Context context){
       new Thread(new RunnableCode(type,code,i,handler,context)).start();
    }
   class RunnableCode implements Runnable{

       Handler handler;
       String type;
       int code;
       int i;
       Context context;
       public RunnableCode(String type, int code, int i,Handler handler,Context context){
            this.type=type;
            this.code=code;
            this.handler=handler;
            this.i=i;
            this.context = context;
        }
        @Override
        public void run() {

            RequestParams params = new RequestParams(Constant.getHOST(context)+URLs.CODE_URL);
            params.addHeader("Cookie", "JSESSIONID=" + Constant.JSESSIONID + ";ZW_SESSION=" + Constant.JSESSIONID);
            params.addBodyParameter("type", type);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    List<AppCode> appCodes = JsonUtils.parseList(result, AppCode.class);
                    AppCode appcode = appCodes.get(code);
                    String st = appcode.getName();
                    Message msg=new Message();
                    msg.obj=st;
                    msg.what=i;
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    Log.e(TAG,"意外错误！",throwable);
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
    public void getCodeList(final String type, final int i, final Handler handler, final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(context)+URLs.CODE_URL);
                params.addHeader("Cookie", "JSESSIONID=" + Constant.JSESSIONID + ";ZW_SESSION=" + Constant.JSESSIONID);
                params.addBodyParameter("type", type);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg=new Message();
                        msg.obj=result;
                        msg.what=i;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"意外错误！",throwable);
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
