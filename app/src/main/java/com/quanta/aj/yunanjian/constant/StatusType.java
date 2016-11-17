package com.quanta.aj.yunanjian.constant;

public enum StatusType {

	/** 状态：0-未作，1-未生效，2-已生效  **/
	wz("0", "未作"), wsx("1", "未生效"), ysx("2", "已生效");

	public String type;
	public String name;

	StatusType(String type, String name) {
		this.type = type;
		this.name = name;
	}
}
