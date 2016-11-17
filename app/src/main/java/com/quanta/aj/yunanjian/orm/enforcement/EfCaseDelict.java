package com.quanta.aj.yunanjian.orm.enforcement;

/**
 * <p>
 * 行政执法-违法行为实体类
 * </p>
 * <p>
 * 代码自动生成插件
 * </p>
 * 
 * @author 许德建(xudejian_dev@126.com)
 */
public class EfCaseDelict {

	/** id **/
	private String id;

	/** 关联案件 **/
	private String caseId;

	/** 违法行为类型：1-一般违法行为、2-特殊违法行为 **/
	private String type;
	
	/** 关联违法行为 **/
	private String efDelict;

	/** 违法行为 **/
	private String content;

	/** 整改期限 **/
	private java.util.Date deadline;

	/** 是否隐患：n-否，y-是 **/
	private String hazard;

	/** 字典：hazard_type **/
	private String hazardType;

	/** 字典：hazard_level **/
	private String hazardLevel;

	/** 字典：hazard_category **/
	private String hazardCategory;

	/** 字典：hazard_classify **/
	private String hazardClassify;

	/** 隐患来源 **/
	private String hazardSource;

	/** 序号 **/
	private Integer seq;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setDeadline(java.util.Date deadline) {
		this.deadline = deadline;
	}

	public java.util.Date getDeadline() {
		return this.deadline;
	}

	public void setHazard(String hazard) {
		this.hazard = hazard;
	}

	public String getHazard() {
		return this.hazard;
	}

	public void setHazardType(String hazardType) {
		this.hazardType = hazardType;
	}

	public String getHazardType() {
		return this.hazardType;
	}

	public void setHazardLevel(String hazardLevel) {
		this.hazardLevel = hazardLevel;
	}

	public String getHazardLevel() {
		return this.hazardLevel;
	}

	public void setHazardCategory(String hazardCategory) {
		this.hazardCategory = hazardCategory;
	}

	public String getHazardCategory() {
		return this.hazardCategory;
	}

	public void setHazardClassify(String hazardClassify) {
		this.hazardClassify = hazardClassify;
	}

	public String getHazardClassify() {
		return this.hazardClassify;
	}

	public void setHazardSource(String hazardSource) {
		this.hazardSource = hazardSource;
	}

	public String getHazardSource() {
		return this.hazardSource;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public String getEfDelict() {
		return efDelict;
	}

	public void setEfDelict(String efDelict) {
		this.efDelict = efDelict;
	}
	
}
