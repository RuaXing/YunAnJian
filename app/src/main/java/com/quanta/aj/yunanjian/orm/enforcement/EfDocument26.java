package com.quanta.aj.yunanjian.orm.enforcement;


/**
 * <p>执法文书-行政处罚决定书（个人）实体类</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfDocument26 {


	/** id **/
	private String id;

	/** 文书编号 **/
	private String no;

	/** 关联告知书 **/
	private String notice;

	/** 被处罚人 **/
	private String name;

	/** 性别 **/
	private String sex;

	/** 职务 **/
	private String post;

	/** 年龄 **/
	private Integer age;

	/** 身份证号码 **/
	private String number;

	/** 家庭住址 **/
	private String homeAddress;

	/** 所在单位 **/
	private String corpName;

	/** 单位地址 **/
	private String corpAddress;

	/** 联系电话 **/
	private String phone;
	
	/** 是否当场处罚 **/
	private String sfdccf;
	
	/** 违法事实 **/
	private String wfss;

	/** 罚款金额 **/
	private Double money;

	/** 违反规定 **/
	private String wfgd;

	/** 法律依据 **/
	private String flyj;

	/** 行政处罚 **/
	private String xzcf;

	/** 罚款收受单位 **/
	private String fkssdw;

	/** 罚款收受帐号 **/
	private String fksszh;

	/** 当事人委托代理人 **/
	private String dsrwtdlr;

	/** 复议机关（上级） **/
	private String fyjgsj;

	/** 复议机关（政府） **/
	private String fyjgzf;

	/** 诉讼法院 **/
	private String ssfy;

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

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getNotice() {
		 return this.notice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		 return this.name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		 return this.sex;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPost() {
		 return this.post;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		 return this.age;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		 return this.number;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomeAddress() {
		 return this.homeAddress;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpName() {
		 return this.corpName;
	}

	public void setCorpAddress(String corpAddress) {
		this.corpAddress = corpAddress;
	}

	public String getCorpAddress() {
		 return this.corpAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSfdccf() {
		return sfdccf;
	}

	public void setSfdccf(String sfdccf) {
		this.sfdccf = sfdccf;
	}

	public void setWfss(String wfss) {
		this.wfss = wfss;
	}

	public String getWfss() {
		 return this.wfss;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getMoney() {
		 return this.money;
	}

	public void setWfgd(String wfgd) {
		this.wfgd = wfgd;
	}

	public String getWfgd() {
		 return this.wfgd;
	}

	public void setFlyj(String flyj) {
		this.flyj = flyj;
	}

	public String getFlyj() {
		 return this.flyj;
	}

	public void setXzcf(String xzcf) {
		this.xzcf = xzcf;
	}

	public String getXzcf() {
		 return this.xzcf;
	}

	public void setFkssdw(String fkssdw) {
		this.fkssdw = fkssdw;
	}

	public String getFkssdw() {
		 return this.fkssdw;
	}

	public void setFksszh(String fksszh) {
		this.fksszh = fksszh;
	}

	public String getFksszh() {
		 return this.fksszh;
	}

	public void setDsrwtdlr(String dsrwtdlr) {
		this.dsrwtdlr = dsrwtdlr;
	}

	public String getDsrwtdlr() {
		 return this.dsrwtdlr;
	}

	public void setFyjgsj(String fyjgsj) {
		this.fyjgsj = fyjgsj;
	}

	public String getFyjgsj() {
		 return this.fyjgsj;
	}

	public void setFyjgzf(String fyjgzf) {
		this.fyjgzf = fyjgzf;
	}

	public String getFyjgzf() {
		 return this.fyjgzf;
	}

	public void setSsfy(String ssfy) {
		this.ssfy = ssfy;
	}

	public String getSsfy() {
		 return this.ssfy;
	}
}
