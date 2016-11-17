package com.quanta.aj.yunanjian.vo.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/9/21.
 */
public class MEFcaseInfo extends EfCaseInfo implements Serializable{
    private String corpName;
    private String unitName;
    private String days;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
