package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-罚款催缴通知书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument29 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被罚单位 **/
	private String corp;

	/** 罚款日期 **/
	private java.util.Date startTime;

	/** 行政处罚决定书文号 **/
	private String fromNo;

	/** 罚款截止日期 **/
	private java.util.Date endTime;

	/** 受收款单位 **/
	private String receiveCorp;

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

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getCorp() {
		 return this.corp;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getStartTime() {
		 return this.startTime;
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

	public void setReceiveCorp(String receiveCorp) {
		this.receiveCorp = receiveCorp;
	}

	public String getReceiveCorp() {
		 return this.receiveCorp;
	}
}
