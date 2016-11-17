package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-行政处罚集体讨论记录实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument28 {


	/** id **/
	private String id;

	/** 案件名称 **/
	private String ajmc;

	/** 地点 **/
	private String address;

	/** 讨论开始时间 **/
	private java.util.Date startTime;

	/** 讨论结束时间 **/
	private java.util.Date endTime;

	/** 主持人 **/
	private String zcr;

	/** 汇报人 **/
	private String hbr;

	/** 记录人 **/
	private String jlr;

	/** 出席人员姓名及职务 **/
	private String cxryxmjzw;

	/** 讨论内容 **/
	private String tlnr;

	/** 讨论记录 **/
	private String tljl;

	/** 结论性意见 **/
	private String jlxyj;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getAjmc() {
		 return this.ajmc;
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

	public void setZcr(String zcr) {
		this.zcr = zcr;
	}

	public String getZcr() {
		 return this.zcr;
	}

	public void setHbr(String hbr) {
		this.hbr = hbr;
	}

	public String getHbr() {
		 return this.hbr;
	}

	public void setJlr(String jlr) {
		this.jlr = jlr;
	}

	public String getJlr() {
		 return this.jlr;
	}

	public void setCxryxmjzw(String cxryxmjzw) {
		this.cxryxmjzw = cxryxmjzw;
	}

	public String getCxryxmjzw() {
		 return this.cxryxmjzw;
	}

	public void setTlnr(String tlnr) {
		this.tlnr = tlnr;
	}

	public String getTlnr() {
		 return this.tlnr;
	}

	public void setTljl(String tljl) {
		this.tljl = tljl;
	}

	public String getTljl() {
		 return this.tljl;
	}

	public void setJlxyj(String jlxyj) {
		this.jlxyj = jlxyj;
	}

	public String getJlxyj() {
		 return this.jlxyj;
	}
}
