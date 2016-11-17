package com.quanta.aj.yunanjian.orm;

/**
 * Created by lenovo on 2016/9/19.
 */
public class AppCode {
    /** id **/
    private String id;

    /** 类别 **/
    private String type;

    /** 代码 **/
    private String code;

    /** 名称 **/
    private String name;

    /** 备注 **/
    private String memo;

    private int index;

    /** 有效标志 **/
    private boolean valid = true;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public boolean getValid() {
        return this.valid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
