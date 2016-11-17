package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-先行登记保存证据处理决定书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument16 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被查单位 **/
	private String corp;

	/** 先保通知书文号 **/
	private String notice;

	/** 先行登记日期 **/
	private java.util.Date time;

	/** 登记物品 **/
	private String goods;

	/** 处理决定 **/
	private String decide;

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

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getNotice() {
		 return this.notice;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public java.util.Date getTime() {
		 return this.time;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getGoods() {
		 return this.goods;
	}

	public void setDecide(String decide) {
		this.decide = decide;
	}

	public String getDecide() {
		 return this.decide;
	}
}
