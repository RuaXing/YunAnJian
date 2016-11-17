package com.quanta.aj.yunanjian.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.quanta.aj.yunanjian.helper.DBHelper;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument01;
import com.quanta.aj.yunanjian.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 行政执法案件数据库处理类
 * @author mei
 *
 */
public class EfCaseInfoDAO {
	
	private DBHelper helper;
	
	SQLiteDatabase db;
	
	 public EfCaseInfoDAO(Context context){
	    	helper = new DBHelper(context);
	    }
	 /**
	  * 插入案件数据
	  * @param item
	  */
	public String insert(EfCaseInfo item){
		db = helper.getReadableDatabase();
		db.execSQL("insert into EF_CASE_INFO (ID_ ,CORP_ ,UNIONABLE_,OUNIONABLE_ ,UNIT_ ,TIME_ ,NO_ ,CREATE_USER_ ,CREATE_TIME_ ,CLOSE_USER_ ,CLOSE_TIME_ ,STATUS_  ) values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
				{item.getId(),item.getCorp(),item.getUnionable(),item.getOunionable(),item.getUnit(),item.getTime(),item.getNo(),item.getCreateUser(),item.getCreateTime(),item.getCloseUser(),item.getCloseTime(),item.getStatus()});
		/**
		 * 获取新插入的自增id
		 */
		String id="";
		Cursor cursor = db.rawQuery("select last_insert_rowid() from EF_CASE_INFO",null);
		if(cursor.moveToFirst())   id = cursor.getString(0);
		return id;
	}
	/**
	 * 修改案件信息为结案
	 * @param id
	 */
    public void updateCase(String id){
    	db = helper.getReadableDatabase(); 
    	db.execSQL("update EF_CASE_INFO set STATUS_=? where ID_=?", new Object[]{"5",id});
	 }
	 /**
	  * 获取案件信息集合
	  * @return
	  */
	 public List<EfCaseInfo> findCaseInfo(){
		 
		 List<EfCaseInfo> items = new ArrayList<EfCaseInfo>();
		 EfCaseInfo item = null;
		 //SQL语句
		 String sql ="select a.ID_ ,a.CORP_ ,a.UNIONABLE_,a.OUNIONABLE_ ,a.UNIT_ ,a.TIME_ ,a.NO_ ,a.CREATE_USER_ ,a.CREATE_TIME_ ,a.CLOSE_USER_ ,a.CLOSE_TIME_ ,a.STATUS_,b.qymc_ from EF_CASE_INFO a join CORP_INFO b on a.CORP_ = b.ID_ ";
		 db = helper.getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, null);
		 String str="";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = null;
		 while(cursor.moveToNext()) {
			 items.add(item = new EfCaseInfo());
			 item.setId(cursor.getString(0));
			 item.setCorp(cursor.getString(1));
			 item.setUnionable(cursor.getString(2));
			 item.setOunionable(cursor.getString(3));
			 item.setUnit(cursor.getString(4));
			 //item.setTime(cursor.get);
			 item.setNo(cursor.getString(6));
			 item.setCreateUser(cursor.getString(7));
			 
			 try {
				  str = cursor.getString(8);
					 if(str!=null){
							date =  format.parse(str);
					 }
				  item.setCreateTime(date);
				  str="";
				  date = null;
				  str = cursor.getString(10);
					 if(str!=null){
							date =  format.parse(str);
					 }
				  item.setCloseTime(date);
			  
			   } catch (ParseException e) {
					e.printStackTrace();
			 }
			
			 item.setCloseUser(cursor.getString(9));
			 item.setStatus(cursor.getString(11));
			 item.setQymc(cursor.getString(12));
		 }
		// cursor.close();
		 return items;
		 
	 }
	
	 /**
	  * 插入企业信息数据
	  * @param item
	  */
	public void insertCorp(CorpInfo item){
		db = helper.getReadableDatabase();
		db.execSQL("insert into corp_info (ID_ ,QYMC_ ,QYDM_ ,QYJGLX_,NO_ ,ZT_,ZCDZ_,FRDB_,FRDBLXDH_,ZYFZR_,ZYFZRLXDH_ ) values(?,?,?,?,?,?,?,?,?,?,?)", new Object[]
				{item.getId(),item.getQymc(),item.getQydm(),item.getQyjglx(),item.getNo(),item.getZt(),item.getZcdz(),item.getFrdb(),item.getFrdblxdh(),item.getZyfzr(),item.getZyfzrlxdh()});
		
	}
	
	/**
	 * 获取企业信息
	 * @return
	 */
	 public List<CorpInfo> findCorpInfo(String qymc){
		 List<CorpInfo> items = new ArrayList<CorpInfo>();
		 CorpInfo item = null;
		 String sql ="select ID_ ,QYMC_ ,QYDM_ ,QYJGLX_,NO_ ,ZT_,ZCDZ_,FRDB_,FRDBLXDH_,ZYFZR_,ZYFZRLXDH_  from corp_info ";
		 if(qymc!=null && !"".equals(qymc)){
			 sql +=" where QYMC_ like '%"+qymc+"%'";
		 }
		 db = helper.getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, null);
		// Log.i("dd", "CorpInfo======:"+cursor.getCount());
		 while(cursor.moveToNext()) {
			 items.add(item = new CorpInfo()); 
			 item.setId(cursor.getString(0));
			 item.setQymc(cursor.getString(1));
			 item.setQydm(cursor.getString(2));
			 item.setQyjglx(cursor.getString(3));
			 item.setNo(cursor.getString(4));
			 item.setZt(cursor.getString(5));
			 item.setZcdz(cursor.getString(6));
			 item.setFrdb(cursor.getString(7));
			 item.setFrdblxdh(cursor.getString(8));
			 item.setZyfzr(cursor.getString(9));
			 item.setZyfzrlxdh(cursor.getString(10));
		 }
		 return items;
	 }
	 /**
	  * 根据id获取企业信息
	  * @param id 企业id
	  * @return
	  */
	 public CorpInfo getCorpInfo(String id){
		 CorpInfo item = new CorpInfo();
		 String sql ="select ID_ ,QYMC_ ,QYDM_ ,QYJGLX_,NO_ ,ZT_,ZCDZ_,FRDB_,FRDBLXDH_,ZYFZR_,ZYFZRLXDH_ from corp_info where ID_=? ";
		 db = helper.getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, new String[]{id});
		 if(cursor.moveToNext()){
			 item.setId(cursor.getString(0));
			 item.setQymc(cursor.getString(1));
			 item.setQydm(cursor.getString(2));
			 item.setQyjglx(cursor.getString(3));
			 item.setNo(cursor.getString(4));
			 item.setZt(cursor.getString(5));
			 item.setZcdz(cursor.getString(6));
			 item.setFrdb(cursor.getString(7));
			 item.setFrdblxdh(cursor.getString(8));
			 item.setZyfzr(cursor.getString(9));
			 item.setZyfzrlxdh(cursor.getString(10));
		 }
		 return item; 
	 }
	 /**
	  * 插入案件对应的文书
	  * @param item
	  */
	 public void insertEfDoc(EfDocument item){
		db = helper.getReadableDatabase();
		String sql = "insert into EF_DOCUMENT(ID_,CASE_ID_,DOCUMENT_,NAME_,TYPE_,PROPERTY_,SEQ_,STATUS_,CREATE_TIME_,CREATE_USER_)values(?,?,?,?,?,?,?,?,?,?)";
		db.execSQL(sql,  new Object[]
				{item.getId(),item.getCaseId(),item.getDocument(),item.getName(),item.getType(),item.getProperty(),item.getSeq(),item.getStatus(),item.getCreateTime(),item.getCreateUser()});
	 }
	 /**
	  * 修改文书状态
	  * @param
	  * @param
	  */
    public void updateEfDoc(EfDocument item){
    	db = helper.getReadableDatabase(); 
    	db.execSQL("update EF_DOCUMENT set CASE_ID_=?,DOCUMENT_=?,NAME_=?,TYPE_=?,PROPERTY_=?,SEQ_=?,STATUS_=?,CREATE_TIME_=?,CREATE_USER_=?  where ID_=?", new Object[]{
    			item.getCaseId(),item.getDocument(),item.getName(),item.getType(),item.getProperty(),item.getSeq(),item.getStatus(),item.getCreateTime(),item.getCreateUser(),item.getId()});
	 }
    /**
     * 专门准对文书中修改状态及对应的具体文书id
     * @param document
     * @param status
     * @param id
     */
    public void updateEfDoc1(String document, String status, String id){
    	db = helper.getReadableDatabase(); 
    	db.execSQL("update EF_DOCUMENT set DOCUMENT_=?,STATUS_=? where ID_=?", new Object[]{
    			document,status,id});
	 }
    /**
     * 获取单笔文书信息
     * @param id
     * @return
     */
    public EfDocument getEfDoc(String id){
    	EfDocument item = new EfDocument();
		 String sql ="select  ID_,CASE_ID_,DOCUMENT_,NAME_,TYPE_,PROPERTY_,SEQ_,STATUS_,CREATE_TIME_,CREATE_USER_ from EF_DOCUMENT  where ID_=? ";
		 db = helper.getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, new String[]{id});
		 String str="";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = null;
		 if(cursor.moveToNext()){
			 item.setId(cursor.getString(0));
			 item.setCaseId(cursor.getString(1));
			 item.setDocument(cursor.getString(2));
			 item.setName(cursor.getString(3));
			 item.setType(cursor.getString(4));
			 item.setProperty(cursor.getString(5));
			 item.setSeq(cursor.getInt(6));
			 item.setStatus(cursor.getString(7));
			 str = cursor.getString(8);
			 try {
				 if(str!=null){
						date =  format.parse(str);
				 }
			 } catch (ParseException e) {
					e.printStackTrace();
			 }
			 item.setCreateTime(date);
			 item.setCreateUser(cursor.getString(9));
		 }
		 return item; 
    }
	 /**
	  * 获取案件对应d文书
	  * @param caseId 案件id
	  * @param status 状态
	  * @return
	  */
	 public List<EfDocument> findEfDoc(String caseId, String status){
		 List<EfDocument> items = new ArrayList<EfDocument>();
		 EfDocument item = null;
		 String sql ="select ID_,CASE_ID_,DOCUMENT_,NAME_,TYPE_,PROPERTY_,SEQ_,STATUS_,CREATE_TIME_,CREATE_USER_ from EF_DOCUMENT where CASE_ID_ =?";
		 if("0".equals(status)){
			sql +=" and STATUS_='0'";
		 }else if("1".equals(status)){
			 sql +=" and STATUS_ in('1','2')";
		 }
		 db = helper.getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, new String[]{caseId});
		 String str="";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = null;
		 while(cursor.moveToNext()) {
			 items.add(item = new EfDocument()); 
			 item.setId(cursor.getString(0));
			 item.setCaseId(cursor.getString(1));
			 item.setDocument(cursor.getString(2));
			 item.setName(cursor.getString(3));
			 item.setType(cursor.getString(4));
			 item.setProperty(cursor.getString(5));
			 item.setSeq(cursor.getInt(6));
			 item.setStatus(cursor.getString(7));
			 str = cursor.getString(8);
			 try {
				 if(str!=null){
						date =  format.parse(str);
				 }
			 } catch (ParseException e) {
					e.printStackTrace();
			 }
			 item.setCreateTime(date);
			 item.setCreateUser(cursor.getString(9));
		 }
		 return items; 
	 }
	 /**
	  * 现场检查文书保存
	  * @param item
	  */
	public String insertEfD1(EfDocument01 item){
		db = helper.getReadableDatabase();
		db.execSQL("insert into EF_DOCUMENT01(ID_,CORP_NAME_,CORP_ADRESS_,PLACE_,FDDBR_,FDDBRLXDH_,ZYFZR_,ZYFZRLXDH_,BEGIN_TIME_,END_TIME_,NOTE_,FLOW_) values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
				{item.getId(),item.getCorpName(),item.getCorpAdress(),item.getPlace(),item.getFddbr(),item.getFddbrlxdh(),item.getZyfzr(),item.getZyfzrlxdh(),item.getBeginTime(),item.getEndTime(),item.getNote(),item.getFlow()});
		/**
		 * 获取新插入的自增id
		 */
		String id="";
		Cursor cursor = db.rawQuery("select last_insert_rowid() from EF_DOCUMENT01",null);
		if(cursor.moveToFirst())   id = cursor.getString(0);
		return id;
	}
	/**
	 * 修改现场检查文书
	 * @param item
	 */
	public void updateEfD1(EfDocument01 item){
		db = helper.getReadableDatabase();
		String beginTime = "";
		String endTime = "";
		if(item.getBeginTime()!=null) beginTime = StringUtils.dateToStr("yyyy-MM-dd hh:mm:ss", item.getBeginTime());
		if(item.getEndTime()!=null) endTime = StringUtils.dateToStr("yyyy-MM-dd hh:mm:ss", item.getEndTime());
		
		db.execSQL("update EF_DOCUMENT01 set CORP_NAME_=?,CORP_ADRESS_=?,PLACE_=?,FDDBR_=?,FDDBRLXDH_=?,ZYFZR_=?,ZYFZRLXDH_=?,BEGIN_TIME_=?,END_TIME_=?,NOTE_=?,FLOW_=? where id_=? ", new Object[]
				{item.getCorpName(),item.getCorpAdress(),item.getPlace(),item.getFddbr(),item.getFddbrlxdh(),item.getZyfzr(),item.getZyfzrlxdh(),beginTime,endTime,item.getNote(),item.getFlow(),item.getId()});
		
	}
	/**
	 * 获取现场检查文书信息
	 * @param id
	 * @return
	 * @throws ParseException
	 */
	 public EfDocument01 getEfD1(String id) {
		 EfDocument01 item = new EfDocument01();
		 String sql ="select ID_,CORP_NAME_,CORP_ADRESS_,PLACE_,FDDBR_,FDDBRLXDH_,ZYFZR_,ZYFZRLXDH_,BEGIN_TIME_,END_TIME_,NOTE_,FLOW_ from EF_DOCUMENT01 where ID_=? ";
		 db = helper.getReadableDatabase();
		 Cursor cursor = db.rawQuery(sql, new String[]{id});
		 String str="";
		 if(cursor.moveToNext()){
			 item.setId(cursor.getString(0));
			 item.setCorpName(cursor.getString(1));
			 item.setCorpAdress(cursor.getString(2));
			 item.setPlace(cursor.getString(3));
			 item.setFddbr(cursor.getString(4));
			 item.setFddbrlxdh(cursor.getString(5));
			 item.setZyfzr(cursor.getString(6));
			 item.setZyfzrlxdh(cursor.getString(7));
			 str = cursor.getString(8);
			 Log.i("getEfD1", "getEfD1 BeginTime successed："+str);
			 if(str!=null ){
				item.setBeginTime(StringUtils.strToDate("yyyy-MM-dd", str));
				str ="";
			 }
			 str = cursor.getString(9);
			 if(str!=null ){
				item.setEndTime(StringUtils.strToDate("yyyy-MM-dd", str));
				str ="";
			  }
			 item.setNote(cursor.getString(10));
			 item.setFlow(cursor.getString(11));
		 }
		 return item; 
	 }
	
}
