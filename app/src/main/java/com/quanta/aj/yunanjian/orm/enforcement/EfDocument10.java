package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-鉴定委托书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument10 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 抽样取证凭证文书号 **/
	private String fromNo;

	/** 鉴定截止日期 **/
	private java.util.Date endTime;

	/** 鉴定单位 **/
	private String corp;

	/** 鉴定要求 **/
	private String requirement;

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

	public void setFromNo(String fromNo) {
		this.fromNo = fromNo;
	}

	public String getFromNo() {
		 return this.fromNo;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getEndTime() {
		 return this.endTime;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getCorp() {
		 return this.corp;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

}
