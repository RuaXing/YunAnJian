package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-抽样取证凭证实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument09 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被抽样取证单位 **/
	private String corp;

	/** 现场负责人 **/
	private String name;

	/** 联系电话 **/
	private String phone;

	/** 单位地址 **/
	private String corpAddress;

	/** 邮政编码 **/
	private String yzbm;

	/** 抽样地点 **/
	private String address;

	/** 抽样取证开始时间 **/
	private java.util.Date startTime;

	/** 抽样取证截止时间 **/
	private java.util.Date endTime;

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

	public void setCorpAddress(String corpAddress) {
		this.corpAddress = corpAddress;
	}

	public String getCorpAddress() {
		 return this.corpAddress;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getYzbm() {
		 return this.yzbm;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		 return this.address;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getStartTime() {
		 return this.startTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getEndTime() {
		 return this.endTime;
	}
}
