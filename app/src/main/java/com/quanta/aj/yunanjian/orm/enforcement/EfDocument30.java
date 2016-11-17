package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-延期（分期）缴纳罚款审批表实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument30 {


	/** id **/
	private String id;

	/** 案由 **/
	private String ay;

	/** 处罚决定书文号 **/
	private String fromNo;

	/** 当事人 **/
	private String dsr;

	/** 地址 **/
	private String address;

	/** 违法事实 **/
	private String wfss;

	/** 违法处罚决定 **/
	private String wfcfjd;

	/** 理由 **/
	private String ly;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		 return this.id;
	}

	public void setAy(String ay) {
		this.ay = ay;
	}

	public String getAy() {
		 return this.ay;
	}

	public void setFromNo(String fromNo) {
		this.fromNo = fromNo;
	}

	public String getFromNo() {
		 return this.fromNo;
	}

	public void setDsr(String dsr) {
		this.dsr = dsr;
	}

	public String getDsr() {
		 return this.dsr;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		 return this.address;
	}

	public void setWfss(String wfss) {
		this.wfss = wfss;
	}

	public String getWfss() {
		 return this.wfss;
	}

	public void setWfcfjd(String wfcfjd) {
		this.wfcfjd = wfcfjd;
	}

	public String getWfcfjd() {
		 return this.wfcfjd;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getLy() {
		 return this.ly;
	}
}
