package com.quanta.aj.yunanjian.vo.enforcement;


import com.quanta.aj.yunanjian.constant.CorpType;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;

public class CaseVO extends EfCaseInfo {
	/**
	 * 企业编码
	 */
	private String qybm;
	/**
	 * 企业名称
	 */
	//private String qymc;
	/**
	 * 注册地址
	 */
	private String zcdz;
	/**
	 * 工商注册号
	 */
	private String gsno;
	/**
	 * 营业执照
	 */
	private String yyzz;
	/**
	 * 行业类别
	 */
	private String hylbName1;
	private String hylbName2;
	private String hylbName3;

	protected String dwlx;
	
	private String zyfzr;
	private String zyfzrlxdh;
	private String frdb;
	private String frdblxdh;
	private String frzw;
	/** 邮政编码 **/
	private String yzbm;
	public String getDwlxName() {
		return CorpType.getName(dwlx);
	}

	public String getHylbName() {
		return String.format("%s %s %s", hylbName1 != null ? hylbName1 : "", hylbName2 != null ? hylbName2 : "", hylbName3 != null ? hylbName3 : "");
	}

	public String getQybm() {
		return qybm;
	}

	public void setQybm(String qybm) {
		this.qybm = qybm;
	}


	public String getZcdz() {
		return zcdz;
	}

	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	public String getGsno() {
		return gsno;
	}

	public void setGsno(String gsno) {
		this.gsno = gsno;
	}

	public String getYyzz() {
		return yyzz;
	}

	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getHylbName1() {
		return hylbName1;
	}

	public void setHylbName1(String hylbName1) {
		this.hylbName1 = hylbName1;
	}

	public String getHylbName2() {
		return hylbName2;
	}

	public void setHylbName2(String hylbName2) {
		this.hylbName2 = hylbName2;
	}

	public String getHylbName3() {
		return hylbName3;
	}

	public void setHylbName3(String hylbName3) {
		this.hylbName3 = hylbName3;
	}

	public String getZyfzr() {
		return zyfzr;
	}

	public void setZyfzr(String zyfzr) {
		this.zyfzr = zyfzr;
	}

	public String getZyfzrlxdh() {
		return zyfzrlxdh;
	}

	public void setZyfzrlxdh(String zyfzrlxdh) {
		this.zyfzrlxdh = zyfzrlxdh;
	}

	public String getFrdb() {
		return frdb;
	}

	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}

	public String getFrdblxdh() {
		return frdblxdh;
	}

	public void setFrdblxdh(String frdblxdh) {
		this.frdblxdh = frdblxdh;
	}

	public String getFrzw() {
		return frzw;
	}

	public void setFrzw(String frzw) {
		this.frzw = frzw;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

}
