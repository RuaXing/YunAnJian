package com.quanta.aj.yunanjian.page;

import com.ziwu.core.module.Page;

/**
 * Created by luoxin on 2016/9/30.
 */
public class AppUserPage extends Page<UserPageItem> {

    public String name;

    public String mobile;

    public String deptName;

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
