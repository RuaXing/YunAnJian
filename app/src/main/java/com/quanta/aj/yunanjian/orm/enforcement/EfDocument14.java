package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-先行登记保存证据通知书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument14 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被查单位 **/
	private String bcdw;

	/** 截止时间 **/
	private java.util.Date jzsj;

	/** 受理单位 **/
	private String sldw;

	/** 被通知人或单位负责人 **/
	private String dwfzr;

	/** 涉嫌行为 **/
	private String sxxw;

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

	public void setBcdw(String bcdw) {
		this.bcdw = bcdw;
	}

	public String getBcdw() {
		 return this.bcdw;
	}

	public void setJzsj(java.util.Date jzsj) {
		this.jzsj = jzsj;
	}

	public java.util.Date getJzsj() {
		 return this.jzsj;
	}

	public void setSldw(String sldw) {
		this.sldw = sldw;
	}

	public String getSldw() {
		 return this.sldw;
	}

	public void setDwfzr(String dwfzr) {
		this.dwfzr = dwfzr;
	}

	public String getDwfzr() {
		 return this.dwfzr;
	}

	public void setSxxw(String sxxw) {
		this.sxxw = sxxw;
	}

	public String getSxxw() {
		 return this.sxxw;
	}
}
