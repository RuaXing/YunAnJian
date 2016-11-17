package com.quanta.aj.yunanjian.constant;

public enum ValidType {

	/** 状态：0-无效，1-有效 **/
	wx("0", "无效"), yx("1", "有效");

	public String type;
	public String name;

	ValidType(String type, String name) {
		this.type = type;
		this.name = name;
	}
}
