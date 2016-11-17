package com.quanta.aj.yunanjian.page;


import com.quanta.aj.yunanjian.vo.CorpChoiseItem;
import com.ziwu.core.module.Page;

/**
 * 
 * <p>
 * 企业选择对象分页
 * </p>
 * 
 * @author 许德建(xudejian@126.com)
 * 
 */
public class CorpChoisePage extends Page<CorpChoiseItem> {

	/** 用户id **/
	public String userId;
	
	/** 企业类型 **/
	public String dwlx;

	/** 企业名称 **/
	public String qymc;

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
