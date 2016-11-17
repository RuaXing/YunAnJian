package com.quanta.aj.yunanjian.page.enforcement;


import com.quanta.aj.yunanjian.orm.enforcement.EfReview;
import com.ziwu.core.module.Page;

/**
 * <p>行政执法-整改复查管理数据分页</p>
 * <p>代码自动生成插件</p>
 * @author 许德建(xudejian_dev@126.com) 
*/
public class EfReviewPage extends Page<EfReview> {
	private String qymc;
	private String status;
	private String limitTimeBegin;
	private String limitTimeEnd;
	public String getQymc() {
		return qymc;
	}
	public void setQymc(String qymc) {
		this.qymc = qymc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLimitTimeBegin() {
		return limitTimeBegin;
	}
	public void setLimitTimeBegin(String limitTimeBegin) {
		this.limitTimeBegin = limitTimeBegin;
	}
	public String getLimitTimeEnd() {
		return limitTimeEnd;
	}
	public void setLimitTimeEnd(String limitTimeEnd) {
		this.limitTimeEnd = limitTimeEnd;
	}
	
}
