package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-抽取物品列表实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfEvidence {


	/** id **/
	private String id;

	/** 详细文书id **/
	private String document;

	/** 证据物品名称 **/
	private String name;

	/** 规格及批号 **/
	private String type;

	/** 数量 **/
	private String number;

	/** 备注 **/
	private String remark;

	/** 顺序 **/
	private Integer seq;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocument() {
		 return this.document;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		 return this.type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		 return this.remark;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSeq() {
		 return this.seq;
	}
}
