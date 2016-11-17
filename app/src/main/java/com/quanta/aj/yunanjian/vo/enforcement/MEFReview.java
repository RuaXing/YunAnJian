package com.quanta.aj.yunanjian.vo.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.EfReview;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/9/21.
 */
public class MEFReview extends EfReview implements Serializable{
    private String corpName;
    private String statusName;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    @Override
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
