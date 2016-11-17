package com.quanta.aj.yunanjian.vo;


import com.quanta.aj.yunanjian.constant.CorpType;

/**
 * 
 * <p>
 * 企业基本信息选择对象
 * </p>
 * 
 * @author 许德建(xudejian@126.com)
 *
 */
public class CorpChoiseItem {

	/**企业id **/
	public String id;
	/** 工商注册号 **/
	public String no;
	/** 单位类型 **/
	public String dwlx;
	/** 行业类别1 **/
	public String hylb1;
	/** 行业类别2 **/
	public String hylb2;
	/** 行业类别3 **/
	public String hylb3;
	/** 监管机关 **/
	public String jgjg;
	/** 主管部门 **/
	public String zgbm;
	/** 企业代码 **/
	public String qydm;
	/** 企业名称 **/
	public String qymc;
	/** 机构代码 **/
	public String jgdm;
	/** 企业监管类型 **/
	public String qyjglx;
	/** 经济类型 **/
	public String jjlx;
	/** 企业规模 **/
	public String qygm;
	/** 职工人数 **/
	public Integer zgrs;
	/** 企业分类 **/
	public String qyfl;
	/** 企业分级 **/
	public String qyfj;
	/** 企业种类 **/
	public String qyzl;
	/** 注册地址 **/
	public String zcdz;
	/** 注册时间 **/
	public java.util.Date zcsj;
	/** 注册资金 **/
	public Double zczj;
	/** 固定资产总值（万元） **/
	public Double gdzczz;
	/** 上年销售收入（万元） **/
	public Double snxssr;
	/** 传真 **/
	public String cz;
	/** 邮政编码 **/
	public String yzbm;
	/** 联系人姓名 **/
	public String lxrxm;
	/** 联系人电话 **/
	public String lxrdh;
	/** 主要负责人 **/
	public String zyfzr;
	/** 主要负责人联系电话 **/
	public String zyfzrlxdh;
	/** 法人代表 **/
	public String frdb;
	/** 法人代表联系电话 **/
	public String frdblxdh;
	/** 电子邮箱 **/
	public String email;
	/** 年销售额(万元) **/
	public Long nxse;
	/** 营业执照 **/
	public String yyzz;
	
	protected String dwlxName;
	protected String yhlb;

	public String getDwlxName() {
		return dwlxName = CorpType.getName(dwlx);
	}

	public String getYhlb() {
		return yhlb = String.format("%s %s %s", hylb1 != null ? hylb1 : "", hylb2 != null ? hylb2 : "", hylb3 != null ? hylb3 : "");
	}

}
