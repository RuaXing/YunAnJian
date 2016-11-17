package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-责令改正指令书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument02 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被检查单位 **/
	private String corpName;

	/** 被查单位现场负责人 **/
	private String leaderName;

	/** 立即整改问题 **/
	private String immediateProblem;

	/** 限期整改时间 **/
	private java.util.Date limitTime;

	/** 限期整改问题 **/
	private String limitProblem;

	private String status;

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

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpName() {
		 return this.corpName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
