package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-人物信息（勘验）实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfInspector {


	/** id **/
	private String id;

	/** 详细文书id **/
	private String document;

	/** 人员类别 **/
	private String type;

	/** 姓名 **/
	private String name;

	/** 工作单位 **/
	private String corp;

	/** 职务 **/
	private String post;

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

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		 return this.type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getCorp() {
		 return this.corp;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPost() {
		 return this.post;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getSeq() {
		 return this.seq;
	}
}
