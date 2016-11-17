package com.quanta.aj.yunanjian.orm;

/**
 * Created by lenovo on 2016/9/30.
 */
public class NAppUser {
    /** ID **/
    private String id;

    /** 帐号 **/
    private String account;

    /** 密码 **/
    private String password;

    /** 姓名 **/
    private String name;

    /** 部门 **/
    private String dept;

    /** 性别：1-男，2-女 */
    private String gender;

    /** 工号 **/
    private String no;

    /** 手机 **/
    private String mobile;

    /** Email **/
    private String email;

    /** 相片 **/
    private String photo;

    /** 有效标志 **/
    private boolean valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
