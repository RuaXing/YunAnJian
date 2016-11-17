package com.quanta.aj.yunanjian.vo.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.EfCaseDelict;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;

import java.util.List;


public class CaseForm extends EfCaseInfo {

	public List<EfCaseDelict> delicts;

	public EfCaseDelict delict;
	
	public List<EfDocument> docs;
	
	public List<EfCaseDelict> getDelicts() {
		return delicts;
	}

	public void setDelicts(List<EfCaseDelict> delicts) {
		this.delicts = delicts;
	}

	public EfCaseDelict getDelict() {
		return delict;
	}

	public void setDelict(EfCaseDelict delict) {
		this.delict = delict;
	}

}
