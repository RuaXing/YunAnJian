package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-询问通知书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument13 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 被询问人 **/
	private String bxwr;

	/** 案由 **/
	private String ay;

	/** 询问截止时间 **/
	private java.util.Date xwjzsj;

	/** 询问地点 **/
	private String xwdd;

	/** 监督管理部门地址 **/
	private String jdglbmdz;

	/** 联系人 **/
	private String lxr;

	/** 联系电话 **/
	private String lxdh;

	/** 身份证：0-无，1有 **/
	private String sfz;

	/** 营业执照：0-无，1有 **/
	private String yyzz;

	/** 法定代表人身份证明或委托书：0-无，1有 **/
	private String fddbrsfzmhwts;

	/** 其他证件 **/
	private String qtzj;

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

	public void setBxwr(String bxwr) {
		this.bxwr = bxwr;
	}

	public String getBxwr() {
		 return this.bxwr;
	}

	public void setAy(String ay) {
		this.ay = ay;
	}

	public String getAy() {
		 return this.ay;
	}

	public void setXwjzsj(java.util.Date xwjzsj) {
		this.xwjzsj = xwjzsj;
	}

	public java.util.Date getXwjzsj() {
		 return this.xwjzsj;
	}

	public void setXwdd(String xwdd) {
		this.xwdd = xwdd;
	}

	public String getXwdd() {
		 return this.xwdd;
	}

	public void setJdglbmdz(String jdglbmdz) {
		this.jdglbmdz = jdglbmdz;
	}

	public String getJdglbmdz() {
		 return this.jdglbmdz;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getLxr() {
		 return this.lxr;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getLxdh() {
		 return this.lxdh;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getSfz() {
		 return this.sfz;
	}

	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}

	public String getYyzz() {
		 return this.yyzz;
	}

	public void setFddbrsfzmhwts(String fddbrsfzmhwts) {
		this.fddbrsfzmhwts = fddbrsfzmhwts;
	}

	public String getFddbrsfzmhwts() {
		 return this.fddbrsfzmhwts;
	}

	public void setQtzj(String qtzj) {
		this.qtzj = qtzj;
	}

	public String getQtzj() {
		 return this.qtzj;
	}
}
