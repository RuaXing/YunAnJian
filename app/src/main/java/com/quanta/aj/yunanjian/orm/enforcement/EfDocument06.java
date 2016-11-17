package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>立案审批表实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument06 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 案件名称 **/
	private String caseName;

	/** 立案时间 **/
	private java.util.Date caseTime;

	/** 当事人 **/
	private String name;

	/** 电话 **/
	private String phone;

	/** 地址 **/
	private String address;
	
	/** 邮政编码 **/
	private String yzbm;

	/** 当事人基本情况 **/
	private String dsrjbqk;

	/** 案由 **/
	private String ay;

	/** 案件来源 **/
	private String ajly;

	/** 案件基本情况 **/
	private String ajjbqk;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		 return this.no;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseName() {
		 return this.caseName;
	}

	public void setCaseTime(java.util.Date caseTime) {
		this.caseTime = caseTime;
	}

	public java.util.Date getCaseTime() {
		 return this.caseTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		 return this.phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		 return this.address;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public void setDsrjbqk(String dsrjbqk) {
		this.dsrjbqk = dsrjbqk;
	}

	public String getDsrjbqk() {
		 return this.dsrjbqk;
	}

	public void setAy(String ay) {
		this.ay = ay;
	}

	public String getAy() {
		 return this.ay;
	}

	public void setAjly(String ajly) {
		this.ajly = ajly;
	}

	public String getAjly() {
		 return this.ajly;
	}

	public void setAjjbqk(String ajjbqk) {
		this.ajjbqk = ajjbqk;
	}

	public String getAjjbqk() {
		 return this.ajjbqk;
	}
}
