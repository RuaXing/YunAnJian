package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>执法文书-检查情况实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfCheck implements Serializable{


	/** id **/
	private String id;

	/** 详细文书id **/
	private String document;

	/** 自文书号 **/
	private String zwsh;

	/** 送达文书名称 **/
	private String name;

	/** 送达文书文号 **/
	private String no;

	/** 收件人 **/
	private String receiver;

	/** 送达地点 **/
	private String address;

	/** 送达日期 **/
	private java.util.Date time;

	/** 送达方式 **/
	private String type;

	/** 送达人 **/
	private String sender;

	/** 序号 **/
	private Integer seq;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocument() {
		 return this.document;
	}

	public void setZwsh(String zwsh) {
		this.zwsh = zwsh;
	}

	public String getZwsh() {
		 return this.zwsh;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		 return this.no;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver() {
		 return this.receiver;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		 return this.address;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public java.util.Date getTime() {
		 return this.time;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		 return this.type;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSender() {
		 return this.sender;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSeq() {
		 return this.seq;
	}
}
