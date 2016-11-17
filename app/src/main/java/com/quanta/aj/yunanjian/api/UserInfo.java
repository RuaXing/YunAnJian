package com.quanta.aj.yunanjian.api;

import java.util.List;

/**
 * Created by lenovo on 2016/10/21.
 */
public class UserInfo extends AppUser {
    public String deptName;

    public String postName;

    public List<String> postIds;

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

    public List<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }
}
