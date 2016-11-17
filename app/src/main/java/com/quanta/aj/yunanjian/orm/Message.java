package com.quanta.aj.yunanjian.orm;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {
	public int code;
	public int statusID;//消息标识
	public String msg;//消息内容
	public String data;//消息附带的数据

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}

