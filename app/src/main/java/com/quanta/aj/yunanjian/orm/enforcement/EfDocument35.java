package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-案卷首页实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument35 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 案件名称 **/
	private String ay;

	/** 处理结果 **/
	private String cljg;

	/** 立案日期 **/
	private java.util.Date startTime;

	/** 结案日期 **/
	private java.util.Date endTime;

	/** 承办人1 **/
	private String cbrOne;

	/** 承办人2 **/
	private String cbrTwo;

	/** 归档日期 **/
	private java.util.Date gdrq;

	/** 归档号 **/
	private String gdh;

	/** 保存期限（年） **/
	private String bcnx;

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

	public void setAy(String ay) {
		this.ay = ay;
	}

	public String getAy() {
		 return this.ay;
	}

	public void setCljg(String cljg) {
		this.cljg = cljg;
	}

	public String getCljg() {
		 return this.cljg;
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

	public void setCbrOne(String cbrOne) {
		this.cbrOne = cbrOne;
	}

	public String getCbrOne() {
		 return this.cbrOne;
	}

	public void setCbrTwo(String cbrTwo) {
		this.cbrTwo = cbrTwo;
	}

	public String getCbrTwo() {
		 return this.cbrTwo;
	}

	public void setGdrq(java.util.Date gdrq) {
		this.gdrq = gdrq;
	}

	public java.util.Date getGdrq() {
		 return this.gdrq;
	}

	public void setGdh(String gdh) {
		this.gdh = gdh;
	}

	public String getGdh() {
		 return this.gdh;
	}

	public void setBcnx(String bcnx) {
		this.bcnx = bcnx;
	}

	public String getBcnx() {
		 return this.bcnx;
	}
}
