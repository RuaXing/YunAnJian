package com.quanta.aj.yunanjian.page.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.EfAgent;
import com.ziwu.core.module.Page;

/**
 * <p>执法文书-委托代理人信息数据分页</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfAgentPage extends Page<EfAgent> {
	private String document;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
}
