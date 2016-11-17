package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>执法文书-委托代理人信息实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfAgent implements Serializable{


	/** id **/
	private String id;

	/** 详细文书id **/
	private String document;

	/** 姓名 **/
	private String name;

	/** 性别 **/
	private String sex;

	/** 年龄 **/
	private String age;

	/** 工作单位 **/
	private String corp;

	/** 职务 **/
	private String zw;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		 return this.sex;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAge() {
		 return this.age;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getCorp() {
		 return this.corp;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public String getZw() {
		 return this.zw;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSeq() {
		 return this.seq;
	}
}
