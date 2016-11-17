package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-先行登记保存证据审批表实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument11 {

	/** id **/
	private String id;

	/** 当事人 **/
	private String personName;

	/** 案件名称 **/
	private String caseName;

	/** 当事人基本情况 **/
	private String dsrjbqk;

	/** 案件基本情况 **/
	private String ajjbqk;

	/** 证据名称 **/
	private String evidenceName;

	/** 证据数量 **/
	private String evidenceTimes;

	/** 提请理由 **/
	private String tqly;

	/** 提请依据 **/
	private String tqyj;

	/** 证据保存方式 **/
	private String zjbcfs;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonName() {
		 return this.personName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseName() {
		 return this.caseName;
	}

	public void setDsrjbqk(String dsrjbqk) {
		this.dsrjbqk = dsrjbqk;
	}

	public String getDsrjbqk() {
		 return this.dsrjbqk;
	}

	public void setAjjbqk(String ajjbqk) {
		this.ajjbqk = ajjbqk;
	}

	public String getAjjbqk() {
		 return this.ajjbqk;
	}

	public void setEvidenceName(String evidenceName) {
		this.evidenceName = evidenceName;
	}

	public String getEvidenceName() {
		 return this.evidenceName;
	}

	public void setEvidenceTimes(String evidenceTimes) {
		this.evidenceTimes = evidenceTimes;
	}

	public String getEvidenceTimes() {
		 return this.evidenceTimes;
	}

	public void setTqly(String tqly) {
		this.tqly = tqly;
	}

	public String getTqly() {
		 return this.tqly;
	}

	public void setTqyj(String tqyj) {
		this.tqyj = tqyj;
	}

	public String getTqyj() {
		 return this.tqyj;
	}

	public void setZjbcfs(String zjbcfs) {
		this.zjbcfs = zjbcfs;
	}

	public String getZjbcfs() {
		 return this.zjbcfs;
	}
}
