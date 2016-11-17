package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>行政执法-整改复查管理实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfReview implements Serializable{


	/** id_ **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被检查单位 **/
	private String corp;

	/** 被查单位现场负责人 **/
	private String leaderName;

	/** 立即整改问题 **/
	private String immediateProblem;

	/** 限期整改时间 **/
	private java.util.Date limitTime;

	/** 限期整改问题 **/
	private String limitProblem;

	/** 状态：0-待复查，1-已复查 **/
	private String status;
	
	public String getStatusName() {
		String statusName = "待复查";
		if(status.equals("1")){
			statusName ="已复查";
		}
		return statusName;
	}

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

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getLeaderName() {
		 return this.leaderName;
	}

	public void setImmediateProblem(String immediateProblem) {
		this.immediateProblem = immediateProblem;
	}

	public String getImmediateProblem() {
		 return this.immediateProblem;
	}

	public void setLimitTime(java.util.Date limitTime) {
		this.limitTime = limitTime;
	}

	public java.util.Date getLimitTime() {
		 return this.limitTime;
	}

	public void setLimitProblem(String limitProblem) {
		this.limitProblem = limitProblem;
	}

	public String getLimitProblem() {
		 return this.limitProblem;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		 return this.status;
	}
}
