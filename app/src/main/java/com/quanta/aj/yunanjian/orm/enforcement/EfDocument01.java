package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-现场检查笔录实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument01 {


	/** id **/
	private String id;

	/** 被检查单位 **/
	private String corpName;

	/** 单位地址 **/
	private String corpAdress;

	/** 检查场所 **/
	private String place;

	/** 法定代表人 **/
	private String fddbr;

	/** 法定代表人电话 **/
	private String fddbrlxdh;

	/** 主要负责人 **/
	private String zyfzr;

	/** 主要负责人电话 **/
	private String zyfzrlxdh;

	/** 检查开始时间 **/
	private java.util.Date beginTime;

	/** 检查结束时间 **/
	private java.util.Date endTime;

	/** 检查情况 **/
	private String note;

	/** 执法流程：1-一般流程，2-特殊流程 **/
	private String flow;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpName() {
		 return this.corpName;
	}

	public void setCorpAdress(String corpAdress) {
		this.corpAdress = corpAdress;
	}

	public String getCorpAdress() {
		 return this.corpAdress;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPlace() {
		 return this.place;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getFddbr() {
		 return this.fddbr;
	}

	public void setFddbrlxdh(String fddbrlxdh) {
		this.fddbrlxdh = fddbrlxdh;
	}

	public String getFddbrlxdh() {
		 return this.fddbrlxdh;
	}

	public void setZyfzr(String zyfzr) {
		this.zyfzr = zyfzr;
	}

	public String getZyfzr() {
		 return this.zyfzr;
	}

	public String getZyfzrlxdh() {
		return zyfzrlxdh;
	}

	public void setZyfzrlxdh(String zyfzrlxdh) {
		this.zyfzrlxdh = zyfzrlxdh;
	}

	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getBeginTime() {
		 return this.beginTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getEndTime() {
		 return this.endTime;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		 return this.note;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getFlow() {
		 return this.flow;
	}
}
