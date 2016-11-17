package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-整改复查意见书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument05 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 处罚决定书文号 **/
	private String cfjdswh;

	/** 监督管理部门 **/
	private String jdglbm;

	/** 被复查单位 **/
	private String bfcdw;

	/** 被复查单位负责人 **/
	private String bfcdwfzr;

	/** 处罚决定日期 **/
	private java.util.Date cfjdrq;

	/** 复查意见 **/
	private String fcyj;

	/** 隐患情况 **/
	private String yhqk;

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

	public void setCfjdswh(String cfjdswh) {
		this.cfjdswh = cfjdswh;
	}

	public String getCfjdswh() {
		 return this.cfjdswh;
	}

	public void setJdglbm(String jdglbm) {
		this.jdglbm = jdglbm;
	}

	public String getJdglbm() {
		 return this.jdglbm;
	}

	public void setBfcdw(String bfcdw) {
		this.bfcdw = bfcdw;
	}

	public String getBfcdw() {
		 return this.bfcdw;
	}

	public void setBfcdwfzr(String bfcdwfzr) {
		this.bfcdwfzr = bfcdwfzr;
	}

	public String getBfcdwfzr() {
		 return this.bfcdwfzr;
	}

	public void setCfjdrq(java.util.Date cfjdrq) {
		this.cfjdrq = cfjdrq;
	}

	public java.util.Date getCfjdrq() {
		 return this.cfjdrq;
	}

	public void setfcyj(String fcyj) {
		this.fcyj = fcyj;
	}

	public String getfcyj() {
		 return this.fcyj;
	}

	public void setYhqk(String yhqk) {
		this.yhqk = yhqk;
	}

	public String getYhqk() {
		 return this.yhqk;
	}
}
