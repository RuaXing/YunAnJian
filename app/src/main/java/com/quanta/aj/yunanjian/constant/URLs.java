package com.quanta.aj.yunanjian.constant;

/**服务器端地址
 * Created by 罗鑫 on 2016/8/17.
 */
public class URLs {

    public static  String HOST = "192.168.15.155:8080";
    //服务器host地址

    //public static String BASIC_URL = "http://192.168.15.155:8080/Cabin";
    //public static final String BASIC_URL = "http://192.168.50.236:8082/pssms";
    public static final String BASIC_URL = "http://220.174.210.63:8082/pssms";
    //获取验证码地址
    public  static String VERIFCODE_URL = "/verifycode.do";
    //登录接口地址
    public static  String LOGIN_URL = "/app/android/login.do";
    //登出接口地址
    public  static String LOGOUT_URL = "/app/android/logout.do";
    public  static String test_get_info = "/app/android/testGetInfo.do";
    //获取企业列表接口http://192.168.50.236:8082/pssms/corp/info/pageChoise.do?dw
    public  static String CORPINF_URL = "/corp/info/pageChoise.do";
    //现场（安全）检查列表接口http://192.168.50.236:8082/pssms/enforcement/inspect/page.do
    public static  String EFORCEMRNTPAGE_URL = "/enforcement/inspect/page.do";
    //新增安全检查http://localhost:8080/Cabin/enforcement/inspect/save.do
    public static  String INSPECTSAVE_URL = "/enforcement/inspect/appsave.do";
    public static  String INSPECTSAVECASE_URL = "/enforcement/inspect/appsaveCase.do";
    //文书列表http://192.168.50.236:8082/pssms/enforcement/case/pageDocument.do?inspectId=05065971-690b-11e6-85fa-2c2f6b896e8e&status=1&buildType=2 (status=1是已做，=0是未做)
    public static  String DOMS_URL = "/enforcement/case/pageDocument.do";
    //整改复查列表http://192.168.50.236:8082/pssms/enforcement/review/page.do
    public static  String REVIW_URL = "/enforcement/review/page.do";
    //保存整改复查http://192.168.50.236:8082/pssms/enforcement/review/save.do
    public static  String SAVEREVIW_URL = "/enforcement/review/appsave.do";
    public  static String SAVECASEREVIW_URL = "/enforcement/review/appsaveCase.do";
    //行政处罚列表请求 URL: http://192.168.50.236:8082/pssms/enforcement/case/page.do
    public static  String CASE_URL = "/enforcement/case/page.do";
    //保存新增行政处罚
    public static  String SAVECASE_URL = "/enforcement/case/appsave.do";
    //行政处罚结案
    public static  String CLOSECASE_URL = "/enforcement/case/close.do";
    //查询码表的接口/app/code/findCodes.do?type='+type,async:false,type:'post',dataType:'json',
    public static  String CODE_URL = "/app/code/findCodes.do";
    //根据ID查找企业
    public static  String GETCORP_URL = "/corp/findcorp.do";
    //查看文书具体信息-->+/01/get.do
    public static  String DOCUMENT_URL = "/enforcement";
    //获取执法人员http://localhost:8080/Cabin/enforcement/case/pageUser.do?document=5fb1e29b-690b-11e6-85fa-2c2f6b896e8e
    public static  String GETZFRY_URL = "/enforcement/case/pageUser.do";
    //保存文书http://localhost:8080/Cabin/enforcement/01/appsave.do
    public static   String SAVEDOM_URL = "/enforcement";//+"/01/appsave.do"
    //打印文书接口 http://localhost:8080/Cabin/enforcement/01/print.do?id=aecd0f4e-8555-11e6-83f2-0a0027000005&documentId=0f9e6541-8533-11e6-83f2-0a0027000005&caseId=0f9e1865-8533-11e6-83f2-0a0027000005
    public static  String PRINTDOM_URL = "/enforcement";//+"/01/print.do"
    //获取用户列表
    public  static String GETUSERS_URL = "/app/user/page.do";//+"/01/print.do"
    //获取抽样物品列表http://localhost:8080/Cabin/enforcement/case/pageEvidence.do?document=70060272-809b-11e6-83f2-0a0027000005
    public static  String PEGEEVIDENCE_URL = "/enforcement/case/pageEvidence.do";
    //获取委托代理人http://localhost:8080/Cabin/enforcement/case/pageAgent.do?document=12d7f43a-809c-11e6-83f2-0a0027000005
    public static  String GETAGENT_URL = "/enforcement/case/pageAgent.do";
    //获取先保登物品列表http://localhost:8080/Cabin/enforcement/case/pageList.do?document=a0ba7151-809b-11e6-83f2-0a0027000005
    public static  String GETPAGELIST_URL = "/enforcement/case/pageList.do";
    //获取文书送达回执列表http://localhost:8080/Cabin/enforcement/case/pageCheck.do?document=28ac31f6-809c-11e6-83f2-0a0027000005
    public static  String GETCHECH_URL = "/enforcement/case/pageCheck.do";
    //获取卷内目录http://localhost:8080/Cabin/enforcement/36/page.do?documentList=41febee4-8df4-11e6-b03e-0a002700001a
    public static String GETDOM36_URL = "/enforcement/36/page.do";

}
