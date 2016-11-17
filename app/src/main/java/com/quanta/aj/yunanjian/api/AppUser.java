package com.quanta.aj.yunanjian.api;

/**
 * <p>
 * 系统-用户信息实体类
 * </p>
 * <p>
 * 代码自动生成插件
 * </p>
 * <p>
 * 系统-用户信息
 * </p>
 *
 * @author 许德建(xudejian_dev@126.com)
 */
public class AppUser {

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return this.dept;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return this.no;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return this.valid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

