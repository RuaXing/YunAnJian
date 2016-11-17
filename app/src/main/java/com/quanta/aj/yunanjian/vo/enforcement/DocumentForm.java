package com.quanta.aj.yunanjian.vo.enforcement;

import com.quanta.aj.yunanjian.orm.enforcement.Document36List;
import com.quanta.aj.yunanjian.orm.enforcement.EfAgent;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseDelict;
import com.quanta.aj.yunanjian.orm.enforcement.EfCheck;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument01;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument02;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument03;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument04;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument05;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument06;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument07;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument08;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument09;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument10;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument11;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument12;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument13;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument14;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument15;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument16;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument17;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument18;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument19;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument20;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument21;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument22;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument23;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument24;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument25;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument26;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument27;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument28;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument29;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument30;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument31;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument32;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument33;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument34;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument35;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument36;
import com.quanta.aj.yunanjian.orm.enforcement.EfEvidence;
import com.quanta.aj.yunanjian.orm.enforcement.EfInspector;
import com.quanta.aj.yunanjian.orm.enforcement.EfList;
import com.quanta.aj.yunanjian.orm.enforcement.EfUser;

import java.util.List;

public class DocumentForm{

	public List<EfUser> efUsers;
	
	public List<EfInspector> efInspectors;
	
	public List<EfEvidence> efEvidences;
	
	public List<EfList> efLists;
	
	public List<EfAgent> EfAgents;
	
	public List<EfCheck> EfChecks;

	public String documentId;
	
	public String corp;
	
	public boolean submit;
	
	public List<EfCaseDelict> delicts;
	
	public EfDocument01 document01;
	
	public EfDocument02 document02;
	
	public EfDocument03 document03;
	
	public EfDocument04 document04;
	
	public EfDocument05 document05;
	
	public EfDocument06 document06;
	
	public EfDocument07 document07;
	
	public EfDocument08 document08;
	
	public EfDocument09 document09;
	
	public EfDocument10 document10;
	
	public EfDocument11 document11;
	
	public EfDocument12 document12;
	
	public EfDocument13 document13;
	
	public EfDocument14 document14;
	
	public EfDocument15 document15;
	
	public EfDocument16 document16;
	
	public EfDocument17 document17;
	
	public EfDocument18 document18;
	
	public EfDocument19 document19;
	
	public EfDocument20 document20;
	
	public EfDocument21 document21;
	
	public EfDocument22 document22;
	
	public EfDocument23 document23;
	
	public EfDocument24 document24;
	
	public EfDocument25 document25;
	
	public EfDocument26 document26;
	
	public EfDocument27 document27;
	
	public EfDocument28 document28;
	
	public EfDocument29 document29;
	
	public EfDocument30 document30;
	
	public EfDocument31 document31;
	
	public EfDocument32 document32;
	
	public EfDocument33 document33;
	
	public EfDocument34 document34;
	
	public EfDocument35 document35;
	
	public EfDocument36 document36;
	
	public List<Document36List> document36List;
	
	public List<EfUser> getEfUsers() {
		return efUsers;
	}

	public void setEfUsers(List<EfUser> efUsers) {
		this.efUsers = efUsers;
	}

	public List<EfInspector> getEfInspectors() {
		return efInspectors;
	}

	public void setEfInspectors(List<EfInspector> efInspectors) {
		this.efInspectors = efInspectors;
	}

	public List<EfEvidence> getEfEvidences() {
		return efEvidences;
	}

	public void setEfEvidences(List<EfEvidence> efEvidences) {
		this.efEvidences = efEvidences;
	}

	public List<EfList> getEfLists() {
		return efLists;
	}

	public void setEfLists(List<EfList> efLists) {
		this.efLists = efLists;
	}

	public List<EfAgent> getEfAgents() {
		return EfAgents;
	}

	public void setEfAgents(List<EfAgent> efAgents) {
		EfAgents = efAgents;
	}

	public List<EfCheck> getEfChecks() {
		return EfChecks;
	}

	public void setEfChecks(List<EfCheck> efChecks) {
		EfChecks = efChecks;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public List<EfCaseDelict> getDelicts() {
		return delicts;
	}

	public void setDelicts(List<EfCaseDelict> delicts) {
		this.delicts = delicts;
	}

	public EfDocument01 getDocument01() {
		return document01;
	}

	public void setDocument01(EfDocument01 document01) {
		this.document01 = document01;
	}

	public EfDocument02 getDocument02() {
		return document02;
	}

	public void setDocument02(EfDocument02 document02) {
		this.document02 = document02;
	}

	public EfDocument03 getDocument03() {
		return document03;
	}

	public void setDocument03(EfDocument03 document03) {
		this.document03 = document03;
	}

	public EfDocument04 getDocument04() {
		return document04;
	}

	public void setDocument04(EfDocument04 document04) {
		this.document04 = document04;
	}

	public EfDocument05 getDocument05() {
		return document05;
	}

	public void setDocument05(EfDocument05 document05) {
		this.document05 = document05;
	}

	public EfDocument06 getDocument06() {
		return document06;
	}

	public void setDocument06(EfDocument06 document06) {
		this.document06 = document06;
	}

	public EfDocument07 getDocument07() {
		return document07;
	}

	public void setDocument07(EfDocument07 document07) {
		this.document07 = document07;
	}

	public EfDocument08 getDocument08() {
		return document08;
	}

	public void setDocument08(EfDocument08 document08) {
		this.document08 = document08;
	}

	public EfDocument09 getDocument09() {
		return document09;
	}

	public void setDocument09(EfDocument09 document09) {
		this.document09 = document09;
	}

	public EfDocument10 getDocument10() {
		return document10;
	}

	public void setDocument10(EfDocument10 document10) {
		this.document10 = document10;
	}
	
	public EfDocument11 getDocument11() {
		return document11;
	}

	public void setDocument11(EfDocument11 document11) {
		this.document11 = document11;
	}
	
	public EfDocument12 getDocument12() {
		return document12;
	}

	public void setDocument12(EfDocument12 document12) {
		this.document12 = document12;
	}

	public EfDocument13 getDocument13() {
		return document13;
	}

	public void setDocument13(EfDocument13 document13) {
		this.document13 = document13;
	}

	public EfDocument14 getDocument14() {
		return document14;
	}

	public void setDocument14(EfDocument14 document14) {
		this.document14 = document14;
	}

	public EfDocument15 getDocument15() {
		return document15;
	}

	public void setDocument15(EfDocument15 document15) {
		this.document15 = document15;
	}

	public EfDocument16 getDocument16() {
		return document16;
	}

	public void setDocument16(EfDocument16 document16) {
		this.document16 = document16;
	}

	public EfDocument17 getDocument17() {
		return document17;
	}

	public void setDocument17(EfDocument17 document17) {
		this.document17 = document17;
	}

	public EfDocument18 getDocument18() {
		return document18;
	}

	public void setDocument18(EfDocument18 document18) {
		this.document18 = document18;
	}

	public EfDocument19 getDocument19() {
		return document19;
	}

	public void setDocument19(EfDocument19 document19) {
		this.document19 = document19;
	}

	public EfDocument20 getDocument20() {
		return document20;
	}

	public void setDocument20(EfDocument20 document20) {
		this.document20 = document20;
	}

	public EfDocument21 getDocument21() {
		return document21;
	}

	public void setDocument21(EfDocument21 document21) {
		this.document21 = document21;
	}

	public EfDocument22 getDocument22() {
		return document22;
	}

	public void setDocument22(EfDocument22 document22) {
		this.document22 = document22;
	}

	public EfDocument23 getDocument23() {
		return document23;
	}

	public void setDocument23(EfDocument23 document23) {
		this.document23 = document23;
	}

	public EfDocument24 getDocument24() {
		return document24;
	}

	public void setDocument24(EfDocument24 document24) {
		this.document24 = document24;
	}

	public EfDocument25 getDocument25() {
		return document25;
	}

	public void setDocument25(EfDocument25 document25) {
		this.document25 = document25;
	}

	public EfDocument26 getDocument26() {
		return document26;
	}

	public void setDocument26(EfDocument26 document26) {
		this.document26 = document26;
	}

	public EfDocument27 getDocument27() {
		return document27;
	}

	public void setDocument27(EfDocument27 document27) {
		this.document27 = document27;
	}

	public EfDocument28 getDocument28() {
		return document28;
	}

	public void setDocument28(EfDocument28 document28) {
		this.document28 = document28;
	}
	
	public EfDocument29 getDocument29() {
		return document29;
	}

	public void setDocument29(EfDocument29 document29) {
		this.document29 = document29;
	}
	
	public EfDocument30 getDocument30() {
		return document30;
	}

	public void setDocument30(EfDocument30 document30) {
		this.document30 = document30;
	}
	
	public EfDocument31 getDocument31() {
		return document31;
	}

	public void setDocument31(EfDocument31 document31) {
		this.document31 = document31;
	}
	
	public EfDocument32 getDocument32() {
		return document32;
	}

	public void setDocument32(EfDocument32 document32) {
		this.document32 = document32;
	}
	
	public EfDocument33 getDocument33() {
		return document33;
	}

	public void setDocument33(EfDocument33 document33) {
		this.document33 = document33;
	}

	public EfDocument34 getDocument34() {
		return document34;
	}

	public void setDocument34(EfDocument34 document34) {
		this.document34 = document34;
	}
	
	public EfDocument35 getDocument35() {
		return document35;
	}

	public void setDocument35(EfDocument35 document35) {
		this.document35 = document35;
	}

	public EfDocument36 getDocument36() {
		return document36;
	}

	public void setDocument36(EfDocument36 document36) {
		this.document36 = document36;
	}

	public List<Document36List> getDocument36List() {
		return document36List;
	}

	public void setDocument36List(List<Document36List> document36List) {
		this.document36List = document36List;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

}
