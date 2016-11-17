package com.quanta.aj.yunanjian.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * 创建SQLite数据库
 * Created by 黄美  OrmLiteSqliteOpenHelper
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "sqlitePass.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 创建SQLite数据库表
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    	//用户表
    	db.execSQL("create table app_user (ID_ varchar(40) primary key,ACCOUNT_ varchar(64),name_ varchar(64),password_ varchar(128),dept_ varchar(40),NO_ varchar(20),VALID_ varchar(1))");
    	//行政执法-案件基本信息
    	db.execSQL("create table EF_CASE_INFO (ID_ varchar(40) primary key,CORP_ varchar(40),UNIONABLE_ varchar(1),OUNIONABLE_ varchar(1),UNIT_ varchar(40),TIME_ timestamp,NO_ varchar(20),CREATE_USER_ varchar(40),CREATE_TIME_ timestamp,CLOSE_USER_ varchar(40),CLOSE_TIME_ timestamp,STATUS_ varchar(1))");
    	//行政执法-违法行为
    	db.execSQL("create table EF_CASE_DELICT (ID_ varchar(40) primary key,CASE_ID_ varchar(40),TYPE_ varchar(1),EF_DELICT_ varchar(40),CONTENT_ varchar(512),DEADLINE_ timestamp,HAZARD_ varchar(1),HAZARD_TYPE_ varchar(2),HAZARD_LEVEL_ varchar(2),HAZARD_CATEGORY_ varchar(2),HAZARD_CLASSIFY_ varchar(2),HAZARD_SOURCE_ varchar(256),SEQ_ int)");
    	//行政执法-执法依据基础数据
    	db.execSQL("create table EF_BASIS (ID_ varchar(40) primary key,CODE_ varchar(20),TYPE_ varchar(6),VALID_ varchar(1),NAME_ varchar(256),CONTENT_ varchar(1024))");
    	//行政执法-违法行为基础数据
    	db.execSQL("create table EF_DELICT (ID_ varchar(40) primary key,CODE_ varchar(20),INDUSTRY_ varchar(6),VALID_ varchar(1),TYPE_ varchar(6),NAME_ varchar(512),DISPOSE_ varchar(1024),PUNISH_ varchar(1024))");
    	//行政执法-违法行为依据
    	db.execSQL("create table EF_DELICT_BASIS (DELICT_ varchar(40) primary key,BASIS_ varchar(40),TYPE_ varchar(1))");
    	//行政执法-执法文书信息
    	db.execSQL("create table ef_document (ID_ integer primary key,CASE_ID_ varchar(40) ,DOCUMENT_ varchar(40),NAME_ varchar(64),TYPE_ varchar(64),PROPERTY_ varchar(64),SEQ_ int,STATUS_ varchar(1),CREATE_TIME_ timestamp,CREATE_USER_ varchar(40))");
    	//执法文书-现场检查笔录
    	db.execSQL("create table EF_DOCUMENT01 (ID_ integer primary key,CORP_NAME_ varchar(40) ,CORP_ADRESS_ varchar(128),PLACE_ varchar(128),FDDBR_ varchar(40),FDDBRLXDH_ varchar(40),ZYFZR_ varchar(40),ZYFZRLXDH_ varchar(40),BEGIN_TIME_ timestamp,END_TIME_ timestamp,NOTE_ varchar(2000),FLOW_ varchar(1))");
    	//企业信息
    	db.execSQL("create table corp_info (ID_ varchar(40) primary key,QYMC_ varchar(128) ,QYDM_ varchar(32),QYJGLX_ varchar(2),NO_ varchar(32),ZT_ varchar(1),ZCDZ_ varchar(200),FRDB_ varchar(20),FRDBLXDH_ varchar(20),ZYFZR_ varchar(20),ZYFZRLXDH_ varchar(20))");
       
    }

    /**
     * 更新SQLite数据库表
     */
   @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
    }

/*	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table app_user (account_ varchar(40) primary key,name_ varchar(20),password_ varchar(10),old_Passw_ varchar(10),status_ varchar(1),flag_ varchar(1))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}*/


}
