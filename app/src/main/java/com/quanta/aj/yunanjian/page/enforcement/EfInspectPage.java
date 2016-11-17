package com.quanta.aj.yunanjian.page.enforcement;


import com.quanta.aj.yunanjian.orm.enforcement.EfInspect;
import com.ziwu.core.module.Page;

/**
 * <p>行政执法-安全检查数据分页</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfInspectPage extends Page<EfInspect> {
	private String qymc;
	private String type;
	private String status;
	public String getQymc() {
		return qymc;
	}
	public void setQymc(String qymc) {
		this.qymc = qymc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
