package com.soa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.soa.util.Constant;
/**
 * 数据库的助手类
 * @author GuoDong
 *
 */
public class MyDBHelper extends SQLiteOpenHelper {
	
	

	/**
	 * 创建或打开数据库
	 * 
	 * @param context
	 *            当前的上下文对象 当前类名.this
	 */

	public MyDBHelper(Context context) {
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = " create table if not exists " + Constant.TB_NAME + " ( "
				+ Constant._ID + " integer primary key autoincrement, "
				+ Constant._NAME + " text not null, " + Constant._DATE
				+ " text not null, " + Constant._SORT + " integer not null, "
				+ Constant._MODE + " integer not null, "
				+ Constant._PAYORORINCOME + " integer not null, "
				+ Constant._MONEY + " text not null, " + Constant._PERSON
				+ " text not null ," + Constant._REMARK + " text, "
				+ Constant._PHOTO + " blob ) ";
		
		String personSql = " create table if not exists " + Constant.TB_PERSONMANAGE
				+ " ( " + Constant._ID + " integer primary key autoincrement, "
				+ Constant._NAME + " text not null unique ) ";

		db.execSQL(sql);
		db.execSQL(personSql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 根据传入的id删除对应的记录
	 * 
	 * @param tableName 要删除记录的表名
	 * 
	 * @param id
	 *            要删除数据的ID号
	 * @return 返回成功删除的行数 失败返回0
	 */
	public int deleteById(String tableName,long id) {

		int result = getWritableDatabase().delete(tableName,
				Constant._ID + " = ? ", new String[] { String.valueOf(id) });
		return result;

	}

}
