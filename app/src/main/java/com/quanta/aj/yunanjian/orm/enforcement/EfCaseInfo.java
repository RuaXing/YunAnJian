package com.quanta.aj.yunanjian.orm.enforcement;

import java.io.Serializable;

/**
 * <p>
 * 行政执法-案件基本信息实体类
 * </p>
 * <p>
 * 代码自动生成插件
 * </p>
 * 
 * @author 许德建(xudejian_dev@126.com)
 */
public class EfCaseInfo implements Serializable {

	/** id **/
	private String id;

	/** 关联企业 **/
	private String corp;

	/** 是否联合执法：n-否，y-是 **/
	private String unionable;

	/** 是否外部门联合执法：n-否，y-是 **/
	private String ounionable;

	/** 检查单位 */
	private String unit;

	/** 检查时间 **/
	private java.util.Date time;

	/** 检查编号 */
	private String no;

	/** 创建人 **/
	private String createUser;

	/** 创建时间 **/
	private java.util.Date createTime;

	/** 结案人 **/
	private String closeUser;

	/** 结案时间 **/
	private java.util.Date closeTime;

	/** 状态：1-在执行，5-已结案，9-已作废 **/
	private String status;
	/**
	 * 企业名称
	 */
	private String qymc;

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

	public void setUnionable(String unionable) {
		this.unionable = unionable;
	}

	public String getUnionable() {
		return this.unionable;
	}

	public void setOunionable(String ounionable) {
		this.ounionable = ounionable;
	}

	public String getOunionable() {
		return this.ounionable;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public java.util.Date getTime() {
		return this.time;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCloseUser(String closeUser) {
		this.closeUser = closeUser;
	}

	public String getCloseUser() {
		return this.closeUser;
	}

	public void setCloseTime(java.util.Date closeTime) {
		this.closeTime = closeTime;
	}

	public java.util.Date getCloseTime() {
		return this.closeTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}
	
}
