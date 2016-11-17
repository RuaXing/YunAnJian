package com.quanta.aj.yunanjian.constant;
/**
 * 文书参数定义
 * @author mei
 *
 */
public enum DocumentType {

	d1("现场检查记录","现场检查","笔录性文书",1),
	d2("责令改正指令书","现场处理","决定性文书",2),
	d3("行政（当场）处罚决定书（个人）","现场处理","决定性文书",3),
	d4("行政（当场）处罚决定书（单位）","现场处理","决定性文书",4),
	d5("整改复查意见书","复查处理","决定性文书",5),
	d6("立案审批表","立案处理","决定性文书",6),
	d7("询问笔录","立案处理","笔录性文书",7),
	d8("勘验笔录","立案处理","笔录性文书",8),
	d9("抽样取证凭证","立案处理","决定性文书",9),
	d10("鉴定委托书","立案处理","决定性文书",10),
	d11("先行登记保存证据审批表","立案处理","决定性文书",11),
	d12("案件移送书","立案处理","公函性文书",12),
	d13("询问通知书","立案处理","通知性文书",13),
	d14("先行登记保存证据通知书","立案处理","通知性文书",14),
	d15("先行登记保存证据处理审批表","立案处理","决定性文书",15),
	d16("先行登记保存证据处理决定书","立案处理","决定性文书",16),
	d17("案件处理呈批表","立案处理","决定性文书",17),
	d18("案件移送审批表","立案处理","公函性文书",18),
	d19("听证会报告书","复议听证","通知性文书",19),
	d20("听证会通知书","复议听证","通知性文书",20),
	d21("听证告知书","复议听证","通知性文书",21),
	d22("当事人陈述申辩笔录","复议听证","笔录性文书",22),
	d23("听证笔录","复议听证","笔录性文书",23),
	d24("行政处罚告知书","行政处罚","通知性文书",24),
	d25("行政处罚决定书（单位）","行政处罚","决定性文书",25),
	d26("行政处罚决定书（个人）","行政处罚","决定性文书",26),
	d27("文书送达回执","行政处罚","公函性文书",27),
	d28("行政处罚集体讨论记录","行政处罚","笔录性文书",28),
	d29("罚款催缴通知书","行政处罚","通知性文书",29),
	d30("延期（分期）缴纳罚款审批表","行政处罚","公函性文书",30),
	d31("延期（分期）缴纳罚款批准书","行政处罚","公函性文书",31),
	d32("强制措施决定书","强制执行","决定性文书",32),
	d33("强制执行申请书","强制执行","决定性文书",33),
	d34("结案审批表","结案处理","档案性文书",34),
	d35("案卷首页","归档处理","档案性文书",35),
	d36("卷内目录","归档处理","档案性文书",36);

	/** 文书名称 **/
	public String name;
	/** 文书分类 **/
	public String type;
	/** 文书性质 **/
	public String property;
	/** 顺序 **/
	public Integer seq;
	
	
	 DocumentType(String name, String type, String property, Integer seq) {
		this.name = name;
		this.type = type;
		this.property = property;
		this.seq = seq;
		
	}
}