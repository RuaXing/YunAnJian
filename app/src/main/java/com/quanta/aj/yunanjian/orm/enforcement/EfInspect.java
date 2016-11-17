package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>行政执法-安全检查实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfInspect implements Serializable {


	/** id_ **/
	private String id;

	/** 检查单位 **/
	private String corp;

	/** 检查开始时间 **/
	private java.util.Date startTime;

	/** 检查结束时间 **/
	private java.util.Date endTime;

	/** 检查方式 **/
	private String type;

	/** 组长 **/
	private String leader;

	/** 组员 **/
	private String member;

	/** 状态：0-待检查；1-已检查 **/
	private String status;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
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

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getEndTime() {
		 return this.endTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		 return this.type;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getLeader() {
		 return this.leader;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getMember() {
		 return this.member;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		 return this.status;
	}
}
