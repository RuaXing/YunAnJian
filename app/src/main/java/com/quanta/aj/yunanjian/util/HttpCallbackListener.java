package com.quanta.aj.yunanjian.util;

/**
 * Created by 罗鑫 on 2016/8/25.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
