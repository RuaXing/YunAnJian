package com.quanta.aj.yunanjian.vo.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.EfInspect;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/9/28.
 */
public class MEfInspect extends EfInspect implements Serializable{
    private String corpName;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }
}
