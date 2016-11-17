package com.quanta.aj.yunanjian.page;

import com.quanta.aj.yunanjian.orm.NAppUser;

/**
 * Created by lenovo on 2016/9/30.
 */
public class UserPageItem extends NAppUser {

    public String deptName;

    public String postName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
