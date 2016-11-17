package com.quanta.aj.yunanjian.page.enforcement;

import com.ziwu.core.module.Page;

public class EfCaseInfoPage extends Page<EfCaseInfoPageItem> {

	public String userId;
	public String corpName;
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
