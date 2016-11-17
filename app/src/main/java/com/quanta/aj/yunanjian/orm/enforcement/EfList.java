package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>执法文书-保存证据清单实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfList implements Serializable{


	/** id **/
	private String id;

	/** 详细文书id **/
	private String document;

	/** 证据物品名称 **/
	private String name;

	/** 规格型号 **/
	private String type;

	/** 产地 **/
	private String address;

	/** 成色（品级） **/
	private String quality;

	/** 单位 **/
	private String corp;

	/** 价格 **/
	private Double price;

	/** 数量 **/
	private Integer quantity;

	/** 物品所有人 **/
	private String owner;

	/** 承办人1 **/
	private String personOne;

	/** 承办人2 **/
	private String personTwo;

	/** 备注 **/
	private String remark;

	/** 序号 **/
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

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		 return this.address;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getQuality() {
		 return this.quality;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getCorp() {
		 return this.corp;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		 return this.price;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		 return this.quantity;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		 return this.owner;
	}

	public void setPersonOne(String personOne) {
		this.personOne = personOne;
	}

	public String getPersonOne() {
		 return this.personOne;
	}

	public void setPersonTwo(String personTwo) {
		this.personTwo = personTwo;
	}

	public String getPersonTwo() {
		 return this.personTwo;
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
