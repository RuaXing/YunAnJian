package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-延期（分期）缴纳罚款批准书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument31 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被罚单位 **/
	private String corp;

	/** 被罚决定日期 **/
	private java.util.Date time;

	/** 被罚金额（元） **/
	private Double money;

	/** 处罚决定书文号 **/
	private String fromNo;

	/** 延分期标记 **/
	private String yfqbj;

	/** 第几期 **/
	private String djq;

	/** 延期截止日期 **/
	private java.util.Date yqjzrq;

	/** 分期截止日期 **/
	private java.util.Date fqjzrq;

	/** 分期缴纳罚款（元） **/
	private Double fqjnfk;

	/** 分期未缴纳罚款（元） **/
	private Double fqwjnfk;

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

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public java.util.Date getTime() {
		 return this.time;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getMoney() {
		 return this.money;
	}

	public void setFromNo(String fromNo) {
		this.fromNo = fromNo;
	}

	public String getFromNo() {
		 return this.fromNo;
	}

	public void setYfqbj(String yfqbj) {
		this.yfqbj = yfqbj;
	}

	public String getYfqbj() {
		 return this.yfqbj;
	}

	public void setDjq(String djq) {
		this.djq = djq;
	}

	public String getDjq() {
		 return this.djq;
	}

	public void setYqjzrq(java.util.Date yqjzrq) {
		this.yqjzrq = yqjzrq;
	}

	public java.util.Date getYqjzrq() {
		 return this.yqjzrq;
	}

	public void setFqjzrq(java.util.Date fqjzrq) {
		this.fqjzrq = fqjzrq;
	}

	public java.util.Date getFqjzrq() {
		 return this.fqjzrq;
	}

	public void setFqjnfk(Double fqjnfk) {
		this.fqjnfk = fqjnfk;
	}

	public Double getFqjnfk() {
		 return this.fqjnfk;
	}

	public void setFqwjnfk(Double fqwjnfk) {
		this.fqwjnfk = fqwjnfk;
	}

	public Double getFqwjnfk() {
		 return this.fqwjnfk;
	}
}
