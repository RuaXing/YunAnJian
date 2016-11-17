package com.quanta.aj.yunanjian.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理类--用于实现关闭所有活动功能（强制下线）
 * Created by 罗鑫 on 2016/8/22.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAllButLast(){
        Activity activity = activities.get(activities.size()-1);
        removeActivity(activity);

        for (Activity activityItem: activities){
            if (!activityItem.isFinishing()){
                activityItem.finish();
            }
        }

        activities.clear();
        activities.add(activity);
    }
    public static void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
