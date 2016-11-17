package com.quanta.aj.yunanjian.page.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.EfCheck;
import com.ziwu.core.module.Page;

/**
 * <p>执法文书-检查情况数据分页</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfCheckPage extends Page<EfCheck> {
	private String document;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
}
