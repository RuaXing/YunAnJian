package com.quanta.aj.yunanjian.constant;

/**
 * 企业基本类型
 * 
 * @author Fuzhicheng(iknoweth@yahoo.com)
 * 
 */
public enum CorpType {

	mk("0", "煤矿", "mk"), fm("1", "非煤", "fm"), whp("2", "危化品", "whp"), yhbz("3", "烟花爆竹", "yhbz"), zhjg("4", "综合监管",
			"zhjg");

	public String type;
	public String name;
	public String path;

	CorpType(String type, String name, String path) {
		this.type = type;
		this.name = name;
		this.path = path;
	}

	public static CorpType get(String type) {
		CorpType[] cts = CorpType.values();
		for (CorpType ct : cts) {
			if (ct.type.equals(type))
				return ct;
		}
		return null;
	}

	public static String getPath(String type) {
		CorpType[] cts = CorpType.values();
		for (CorpType ct : cts) {
			if (ct.type.equals(type))
				return ct.path;
		}
		return null;
	}

	/**
	 * <p>
	 * 获取类型名称
	 * </p>
	 * <p>
	 * author:许德建
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	public static String getName(String type) {
		if (type == null || type.isEmpty()) {
			return null;
		}
		for (CorpType ct : values()) {
			if (ct.type.equals(type))
				return ct.name;
		}
		return null;
	}
}
