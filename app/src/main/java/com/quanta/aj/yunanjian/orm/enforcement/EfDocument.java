package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>执法文书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument implements Serializable{


	/** id **/
	private String id;

	/** 案件id **/
	private String caseId;
	
	/** 安全检查ID **/
	private String inspectId;
	
	/** 整改复查ID **/
	private String reviewId;

	/** 详细文书id **/
	private String document;

	/** 文书名称 **/
	private String name;

	/** 文书分类 **/
	private String type;

	/** 文书性质 **/
	private String property;

	/** 顺序 **/
	private Integer seq;

	/** 状态：0-未做，1-已做，2-生效 **/
	private String status;

	/** 创建时间 **/
	private java.util.Date createTime;

	/** 创建人 **/
	private String createUser;
	
	/** 创建人 **/
	private String buildType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getInspectId() {
		return inspectId;
	}

	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}
	
}
