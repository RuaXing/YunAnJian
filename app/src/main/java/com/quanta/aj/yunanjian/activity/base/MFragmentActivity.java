package com.quanta.aj.yunanjian.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.util.ActivityCollector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MFragmentActivity extends FragmentActivity {
    private static TipsToast tipsToast;
    public static Intent getIntent(Context ctx, Class cls) {
        Intent intent = new Intent(ctx,cls);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), " onCreate() invoked!!");
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onStart() {
        Log.d(this.getClass().getSimpleName() ," onStart() invoked!!");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(this.getClass().getSimpleName()
                ," onRestart() invoked!!");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(this.getClass().getSimpleName()
                , " onResume() invoked!!");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(this.getClass().getSimpleName(), " onPause() invoked!!");
        super.onPause();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        Log.d(this.getClass().getSimpleName() ," onStop() invoked!!");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName()
                ," onDestroy() invoked!!");
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    //[start] 仿造微信TIPS

    /**
     * 长时间显示提示框
     * @param MsgType：消息类型。1：提醒；2：成功；3：失败；4：笑脸
     * @param text ： 消息内容
     */
    protected void showLongToast(int MsgType, CharSequence text) {
        int typeid = 0;
        switch (MsgType) {
            case 1:
                typeid = R.drawable.tips_warning;
                break;
            case 2:
                typeid = R.drawable.tips_success;
                break;
            case 3:
                typeid = R.drawable.tips_error;
                break;
            case 4:
                typeid = R.drawable.tips_smile;
                break;
            default:
                break;
        }
        showTips(typeid, text, 1);
    }

    /**
     * 长时间显示提示框
     * @param MsgType：消息类型。1：提醒；2：成功；3：失败；4：笑脸
     * @param text ： 消息内容
     */
    protected void showShortToast(int MsgType, CharSequence text) {
        int typeid = 0;
        switch (MsgType) {
            case 1:
                typeid = R.drawable.tips_warning;
                break;
            case 2:
                typeid = R.drawable.tips_success;
                break;
            case 3:
                typeid = R.drawable.tips_error;
                break;
            case 4:
                typeid = R.drawable.tips_smile;
                break;
            default:
                break;
        }
        showTips(typeid, text, 0);
    }

    private void showTips(int iconResId, CharSequence text, int duartion) {
        if (tipsToast != null) {

        }else {
            if (duartion == 0) {
                tipsToast = TipsToast.makeText(getApplication()
                        .getBaseContext(), text, TipsToast.LENGTH_SHORT);
            } else {
                tipsToast = TipsToast.makeText(getApplication()
                        .getBaseContext(), text, TipsToast.LENGTH_LONG);
            }
        }
        tipsToast.show();
        tipsToast.setIcon(iconResId);
        tipsToast.setText(text);
    }
    /**
     * 用于因时间格式转换问题的gson
     * @return
     */
    protected Gson getGson(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
        Gson gson = gsonBuilder.create();
        return gson;
    }

    /**
     * 时间转换器
     * @return
     */
    protected SimpleDateFormat getFmt(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt;
    }
    protected void addTime(SlideDateTimeListener listener){
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                //.setMinDate(minDate)
                //.setMaxDate(maxDate)
                //.setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }
    //[end]
}
