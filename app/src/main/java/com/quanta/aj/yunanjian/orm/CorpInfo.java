package com.quanta.aj.yunanjian.orm;

import java.io.Serializable;

/**
 * <p>
 * 企业基础信息实体类
 * </p>
 * <p>
 * 代码自动生成插件
 * </p>
 * 
 * @author 许德建(xudejian_dev@126.com)
 */
public class CorpInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id **/
	private String id;

	/** 状态：0-待激活，1-已激活，3-提交状态，5-已注销，9-已禁用 **/
	private String zt;

	/** 工商注册号，唯一号码，作为登录帐号 **/
	private String no;

	/** 密码 **/
	private String password;

	/** 单位类型 **/
	private String dwlx;

	/** 行业类别1 **/
	private String hylb1;

	/** 行业类别2 **/
	private String hylb2;

	/** 行业类别3 **/
	private String hylb3;

	/** 行政区划1 **/
	private String xzqh1;

	/** 行政区划2 **/
	private String xzqh2;

	/** 行政区划3 **/
	private String xzqh3;
	
	/** 行政区划4 **/
	private String xzqh4;

	/** 行政区划5 **/
	private String xzqh5;

	/** 监管机关 **/
	private String jgjg;

	/** 主管部门 **/
	private String zgbm;

	/** 企业名称 **/
	private String qymc;

	/** 企业简称 **/
	private String qyjc;

	/** 企业代码 **/
	private String qydm;

	/** 机构代码 **/
	private String jgdm;

	/** 企业监管类型 **/
	private String qyjglx;

	/** 经济类型 **/
	private String jjlx;

	/** 隶属关系 **/
	private String lsgx;

	/** 上级单位 **/
	private String sjdw;

	/** 上级单位地址 **/
	private String sjdwdz;

	/** 单位所在环境功能区 **/
	private String dwszhjgnq;

	/** 企业规模 **/
	private String qygm;

	/** 职工人数 **/
	private Integer zgrs;

	/** 是否隶属市属集团公司 **/
	private String sflsssjtgs;

	/** 企业分类 **/
	private String qyfl;

	/** 企业分级 **/
	private String qyfj;

	/** 企业种类 **/
	private String qyzl;

	/** 注册地址 **/
	private String zcdz;

	/** 注册时间 **/
	private java.util.Date zcsj;

	/** 注册资金 **/
	private Double zczj;

	/** 固定资产总值（万元） **/
	private Double gdzczz;

	/** 上年销售收入（万元） **/
	private Double snxssr;

	/** 传真 **/
	private String cz;

	/** 邮政编码 **/
	private String yzbm;

	/** 应急咨询电话 **/
	private String yjzxdh;

	/** 法人代表 **/
	private String frdb;

	/** 法人代表联系电话 **/
	private String frdblxdh;

	/** 法人职务 **/
	private String frzw;

	/** 法人资格证号 **/
	private String frzgzh;

	/** 业务分类 **/
	private String ywfl;

	/** 联系人姓名 **/
	private String lxrxm;

	/** 联系人电话 **/
	private String lxrdh;

	/** 联系人所在部门 **/
	private String lxrszbm;

	/** 安全负责人 **/
	private String aqfzr;

	/** 安全负责人联系电话 **/
	private String aqfzrlxdh;

	/** 安全负责人手机 **/
	private String aqfzrsj;

	/** 主要负责人 **/
	private String zyfzr;

	/** 主要负责人联系电话 **/
	private String zyfzrlxdh;

	/** 主要负责人手机 **/
	private String zyfzrsj;

	/** 营业执照 **/
	private String yyzz;

	/** 网址 **/
	private String wz;

	/** 电子邮箱 **/
	private String email;

	/** 销售额年份 **/
	private java.util.Date xsenf;

	/** 年销售额(万元) **/
	private Long nxse;

	/** 初始化标记:1已初始化 0未初始化 **/
	private String inited;

	// 新注册用户为1 允许修改
	// 用户登陆后进入后填写业务表单 保存不错任何处理 提交后editable字段修改为0
	// 审批不通过后重新开放编辑 允许修改1
	// 审批通过后inited 1 editable 0
	// 申请修改 修改可编辑状态 editable 1
	// 修改后保存 和提交选项 提交后进入不可编辑状态
	/** 是否允许编辑 1允许 0不允许 **/
	private String editable;

	/** 审批状态 **/
	private String status;

	/** 流程id **/
	private String flow;
	
	/** 对应的网格id，与数据库无关 **/
	private String gridTreeId;
	
	/** 对应的网格名称 ，于数据库无关**/
	private String gridTreeName;
	
	/** 创建时间 **/
	private java.util.Date createTime;
	
	/**创建人id**/
	private String createPersonId;
	
	/**创建人**/
	private String createPerson;
	
	/** 修改时间 **/
	private java.util.Date modifyTime;
	
	/**修改人id**/
	private String modifyPersonId;
	
	/**修改人**/
	private String modifyPerson;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getHylb1() {
		return hylb1;
	}

	public void setHylb1(String hylb1) {
		this.hylb1 = hylb1;
	}

	public String getHylb2() {
		return hylb2;
	}

	public void setHylb2(String hylb2) {
		this.hylb2 = hylb2;
	}

	public String getHylb3() {
		return hylb3;
	}

	public void setHylb3(String hylb3) {
		this.hylb3 = hylb3;
	}

	public String getXzqh1() {
		return xzqh1;
	}

	public void setXzqh1(String xzqh1) {
		this.xzqh1 = xzqh1;
	}

	public String getXzqh2() {
		return xzqh2;
	}

	public void setXzqh2(String xzqh2) {
		this.xzqh2 = xzqh2;
	}

	public String getXzqh3() {
		return xzqh3;
	}

	public void setXzqh3(String xzqh3) {
		this.xzqh3 = xzqh3;
	}

	public String getJgjg() {
		return jgjg;
	}

	public void setJgjg(String jgjg) {
		this.jgjg = jgjg;
	}

	public String getZgbm() {
		return zgbm;
	}

	public void setZgbm(String zgbm) {
		this.zgbm = zgbm;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	public String getQyjc() {
		return qyjc;
	}

	public void setQyjc(String qyjc) {
		this.qyjc = qyjc;
	}

	public String getQydm() {
		return qydm;
	}

	public void setQydm(String qydm) {
		this.qydm = qydm;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getQyjglx() {
		return qyjglx;
	}

	public void setQyjglx(String qyjglx) {
		this.qyjglx = qyjglx;
	}

	public String getJjlx() {
		return jjlx;
	}

	public void setJjlx(String jjlx) {
		this.jjlx = jjlx;
	}

	public String getLsgx() {
		return lsgx;
	}

	public void setLsgx(String lsgx) {
		this.lsgx = lsgx;
	}

	public String getSjdw() {
		return sjdw;
	}

	public void setSjdw(String sjdw) {
		this.sjdw = sjdw;
	}

	public String getSjdwdz() {
		return sjdwdz;
	}

	public void setSjdwdz(String sjdwdz) {
		this.sjdwdz = sjdwdz;
	}

	public String getDwszhjgnq() {
		return dwszhjgnq;
	}

	public void setDwszhjgnq(String dwszhjgnq) {
		this.dwszhjgnq = dwszhjgnq;
	}

	public String getQygm() {
		return qygm;
	}

	public void setQygm(String qygm) {
		this.qygm = qygm;
	}

	public Integer getZgrs() {
		return zgrs;
	}

	public void setZgrs(Integer zgrs) {
		this.zgrs = zgrs;
	}

	public String getSflsssjtgs() {
		return sflsssjtgs;
	}

	public void setSflsssjtgs(String sflsssjtgs) {
		this.sflsssjtgs = sflsssjtgs;
	}

	public String getQyfl() {
		return qyfl;
	}

	public void setQyfl(String qyfl) {
		this.qyfl = qyfl;
	}

	public String getQyfj() {
		return qyfj;
	}

	public void setQyfj(String qyfj) {
		this.qyfj = qyfj;
	}

	public String getQyzl() {
		return qyzl;
	}

	public void setQyzl(String qyzl) {
		this.qyzl = qyzl;
	}

	public String getZcdz() {
		return zcdz;
	}

	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	public java.util.Date getZcsj() {
		return zcsj;
	}

	public void setZcsj(java.util.Date zcsj) {
		this.zcsj = zcsj;
	}

	public Double getZczj() {
		return zczj;
	}

	public void setZczj(Double zczj) {
		this.zczj = zczj;
	}

	public Double getGdzczz() {
		return gdzczz;
	}

	public void setGdzczz(Double gdzczz) {
		this.gdzczz = gdzczz;
	}

	public Double getSnxssr() {
		return snxssr;
	}

	public void setSnxssr(Double snxssr) {
		this.snxssr = snxssr;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getYjzxdh() {
		return yjzxdh;
	}

	public void setYjzxdh(String yjzxdh) {
		this.yjzxdh = yjzxdh;
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

	public String getFrzgzh() {
		return frzgzh;
	}

	public void setFrzgzh(String frzgzh) {
		this.frzgzh = frzgzh;
	}

	public String getYwfl() {
		return ywfl;
	}

	public void setYwfl(String ywfl) {
		this.ywfl = ywfl;
	}

	public String getLxrxm() {
		return lxrxm;
	}

	public void setLxrxm(String lxrxm) {
		this.lxrxm = lxrxm;
	}

	public String getLxrdh() {
		return lxrdh;
	}

	public void setLxrdh(String lxrdh) {
		this.lxrdh = lxrdh;
	}

	public String getLxrszbm() {
		return lxrszbm;
	}

	public void setLxrszbm(String lxrszbm) {
		this.lxrszbm = lxrszbm;
	}

	public String getAqfzr() {
		return aqfzr;
	}

	public void setAqfzr(String aqfzr) {
		this.aqfzr = aqfzr;
	}

	public String getAqfzrlxdh() {
		return aqfzrlxdh;
	}

	public void setAqfzrlxdh(String aqfzrlxdh) {
		this.aqfzrlxdh = aqfzrlxdh;
	}

	public String getAqfzrsj() {
		return aqfzrsj;
	}

	public void setAqfzrsj(String aqfzrsj) {
		this.aqfzrsj = aqfzrsj;
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

	public String getZyfzrsj() {
		return zyfzrsj;
	}

	public void setZyfzrsj(String zyfzrsj) {
		this.zyfzrsj = zyfzrsj;
	}

	public String getYyzz() {
		return yyzz;
	}

	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.util.Date getXsenf() {
		return xsenf;
	}

	public void setXsenf(java.util.Date xsenf) {
		this.xsenf = xsenf;
	}

	public Long getNxse() {
		return nxse;
	}

	public void setNxse(Long nxse) {
		this.nxse = nxse;
	}

	public String getInited() {
		return inited;
	}

	public void setInited(String inited) {
		this.inited = inited;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getXzqh4() {
		return xzqh4;
	}

	public void setXzqh4(String xzqh4) {
		this.xzqh4 = xzqh4;
	}

	public String getXzqh5() {
		return xzqh5;
	}

	public void setXzqh5(String xzqh5) {
		this.xzqh5 = xzqh5;
	}

	public String getGridTreeId() {
		return gridTreeId;
	}

	public void setGridTreeId(String gridTreeId) {
		this.gridTreeId = gridTreeId;
	}

	public String getGridTreeName() {
		return gridTreeName;
	}

	public void setGridTreeName(String gridTreeName) {
		this.gridTreeName = gridTreeName;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}


	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(String createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getModifyPersonId() {
		return modifyPersonId;
	}

	public void setModifyPersonId(String modifyPersonId) {
		this.modifyPersonId = modifyPersonId;
	}

	public String getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}
	

}
