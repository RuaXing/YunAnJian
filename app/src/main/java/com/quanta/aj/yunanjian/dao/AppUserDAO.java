package com.quanta.aj.yunanjian.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.quanta.aj.yunanjian.helper.DBHelper;
import com.quanta.aj.yunanjian.orm.AppUser;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 账号信息数据访问接口
 * </p>
 * <p>
 * create: 2014年12月16日 下午16:03:36
 * </p>
 * 
 * @author 黄美
 * @version 1.0
 */
public class AppUserDAO {
	
	private DBHelper helper;
	
    private SQLiteDatabase db;
    /**
     * 构造方法
     * @param context
     */
    public AppUserDAO(Context context){
    	helper = new DBHelper(context);
    }
	/**
	 * <p>
	 * 插入账号信息列表
	 * </p>
	 * 
	 * @param user
	 */
	public void insert(AppUser user){
		db = helper.getReadableDatabase();
		db.execSQL("insert into app_user (ID_,ACCOUNT_ ,name_ ,password_ ,dept_ ,NO_ ,VALID_ ) values(?,?,?,?,?,?,?)", new Object[]
				{user.getId(),user.getAccount(),user.getName(),user.getPassword(),user.getDept(),user.getNo(),user.isValid()});
		//db.insert(table, nullColumnHack, values)
	}
	/**
	 * 修改账号信息
	 * @param user
	 */
	public void update(AppUser user){
		
	}

	/**
	 * <p>
	 * 获取账号信息
	 * </p>
	 * 
	 * @param account
	 * @return AppUser
	 */
	public AppUser findOne(String account, String password){
		AppUser item= null;
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select ID_,ACCOUNT_ ,name_ ,password_ ,dept_ ,NO_ ,VALID_  from app_user where account_=? and password_=?", new String[]
				{account,password});
		if(cursor.moveToNext()){
			item= new AppUser();
			item.setId(cursor.getString(0));
			item.setAccount(account);
			item.setName(cursor.getString(2));
			item.setPassword(cursor.getString(3));
			item.setDept(cursor.getString(4));
			item.setNo(cursor.getString(5));
			//item.isValid(cursor.getString(6);
			
		}
		return item;
	}

	/**
	 * <p>
	 * 删除账号信息
	 * </p>
	 * 
	 * @param account
	 */
	public void delete(String account){
		
	}
	
	public List<AppUser> findUser(){
		List<AppUser> userList=new ArrayList<AppUser>();
		AppUser item=null;
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select ID_,ACCOUNT_ ,name_ ,password_ ,dept_ ,NO_ ,VALID_ from app_user ", null);
		while(cursor.moveToNext()){
			userList.add(item= new AppUser());
			item.setId(cursor.getString(0));
			item.setAccount(cursor.getString(1));
			item.setName(cursor.getString(2));
			item.setPassword(cursor.getString(3));
			item.setDept(cursor.getString(4));
			item.setNo(cursor.getString(5));
			//item.isValid(cursor.getString(6);
		}
		return userList;
	}

}