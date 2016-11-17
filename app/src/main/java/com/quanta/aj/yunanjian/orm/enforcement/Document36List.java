package com.quanta.aj.yunanjian.orm.enforcement;


import java.io.Serializable;

/**
 * <p>卷内目录详情实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class Document36List implements Serializable{


	/** id **/
	private String id;

	/** 卷内目录id **/
	private String documentList;

	/** 标题 **/
	private String title;

	/** 文件原编号 **/
	private String no;

	/** 文件日期 **/
	private java.util.Date time;

	/** 文件所在页号 **/
	private String page;

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

	public void setDocumentList(String documentList) {
		this.documentList = documentList;
	}

	public String getDocumentList() {
		 return this.documentList;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		 return this.title;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		 return this.no;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public java.util.Date getTime() {
		 return this.time;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPage() {
		 return this.page;
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
