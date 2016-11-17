package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-执法人员实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfUser {


	/** id **/
	private String id;

	/** 文书id **/
	private String document;

	/** 执法人员姓名 **/
	private String name;

	/** 执法人员证件 **/
	private String cert;

	/** 顺序 **/
	private int seq;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getCert() {
		 return this.cert;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getSeq() {
		 return this.seq;
	}
}
