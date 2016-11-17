package com.quanta.aj.yunanjian.constant;

/**
 * Created by 许德建 on 2014/12/17.
 */
public enum PowerLevel {
    low(100),
    mid(200),
    high(300);
    public final int level;

    PowerLevel(int level) {
        this.level = level;
    }
}
