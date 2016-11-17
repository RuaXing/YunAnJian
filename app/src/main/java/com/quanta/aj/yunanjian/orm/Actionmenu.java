package com.quanta.aj.yunanjian.orm;

/**
 * Created by 罗鑫 on 2016/8/25.
 */
public class Actionmenu {
    private int iId;
    private String actionName;

    public Actionmenu() {
    }

    public Actionmenu(int iId, String actionName) {
        this.iId = iId;
        this.actionName = actionName;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
