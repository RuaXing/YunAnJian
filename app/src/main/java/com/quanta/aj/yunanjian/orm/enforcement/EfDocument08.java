package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-勘验笔录实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument08 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 勘验开始时间 **/
	private java.util.Date startTime;

	/** 勘验截止时间 **/
	private java.util.Date endTime;

	/** 勘验场所 **/
	private String place;

	/** 天气情况 **/
	private String weather;

	/** 勘验情况 **/
	private String inspection;

	/** 当事人联系方式 **/
	private String contact;

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

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPlace() {
		 return this.place;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWeather() {
		 return this.weather;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}

	public String getInspection() {
		 return this.inspection;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact() {
		 return this.contact;
	}
}
