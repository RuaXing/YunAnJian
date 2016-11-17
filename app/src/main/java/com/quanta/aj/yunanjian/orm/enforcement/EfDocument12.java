package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-案件移送书实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument12 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 受移送机关 **/
	private String sysjg;

	/** 调查日期 **/
	private java.util.Date dcrq;

	/** 份数 **/
	private String fs;

	/** 页数 **/
	private String ys;

	/** 发现问题 **/
	private String fxwt;

	/** 法律依据 **/
	private String flyj;

	/** 附送材料 **/
	private String fscl;

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

	public void setSysjg(String sysjg) {
		this.sysjg = sysjg;
	}

	public String getSysjg() {
		 return this.sysjg;
	}

	public void setDcrq(java.util.Date dcrq) {
		this.dcrq = dcrq;
	}

	public java.util.Date getDcrq() {
		 return this.dcrq;
	}

	public void setFs(String fs) {
		this.fs = fs;
	}

	public String getFs() {
		 return this.fs;
	}

	public void setYs(String ys) {
		this.ys = ys;
	}

	public String getYs() {
		 return this.ys;
	}

	public void setFxwt(String fxwt) {
		this.fxwt = fxwt;
	}

	public String getFxwt() {
		 return this.fxwt;
	}

	public void setFlyj(String flyj) {
		this.flyj = flyj;
	}

	public String getFlyj() {
		 return this.flyj;
	}

	public void setFscl(String fscl) {
		this.fscl = fscl;
	}

	public String getFscl() {
		 return this.fscl;
	}
}
