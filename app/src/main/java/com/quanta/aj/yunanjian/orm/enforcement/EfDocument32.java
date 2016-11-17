package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-强制措施决定书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument32 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被检查单位 **/
	private String corp;

	/** 存在问题 **/
	private String question;

	/** 法律依据 **/
	private String flyj;

	/** 强制措施 **/
	private String qzcs;

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

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		 return this.question;
	}

	public void setFlyj(String flyj) {
		this.flyj = flyj;
	}

	public String getFlyj() {
		 return this.flyj;
	}

	public void setQzcs(String qzcs) {
		this.qzcs = qzcs;
	}

	public String getQzcs() {
		 return this.qzcs;
	}
}
