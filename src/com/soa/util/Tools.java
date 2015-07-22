package com.soa.util;

import java.util.ArrayList;

import com.soa.database.MyDBHelper;
import com.soa.main_activity.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class Tools {

 

	/**
	 * 跳转界面的方法 注:跳转完成后 不销毁当前界面
	 * 
	 * @param context
	 *            当前的上下文对象 当前类名.this
	 * @param cls
	 *            要跳转的对象
	 */
	public static void skip(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}

 

	/**
	 * 显示提示信息的方法(默认提示时长)
	 * 
	 * @param context
	 *            当前的上下文对象 当前类名.this
	 * @param str
	 *            要显示的文本
	 */
	public static void showMsg(Context context, int strId) {
		
		Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示提示信息的方法(指定提示时长)
	 * 
	 * @param context
	 *            当前的上下文对象 当前类名.this
	 * @param str
	 *            要显示的文本
	 * @param duration
	 *            要显示的时长 单位 毫秒
	 */
	public static void showMsg(Context context, String str, int duration) {
		Toast.makeText(context, str, duration).show();
	}



	

	/**
	 * 根据传入的上下文 明 获得操作账单管理数据表的SqliteDatabase对象
	 * 
	 * @param context
	 *            当前的上下文对象 当前类名.this
	 * 
	 * @return 操作指定数据表的SqliteDatabase对象
	 */
	public static SQLiteDatabase getBillDatabase(Context context) {
		SQLiteDatabase myDB = new MyDBHelper(context).getWritableDatabase();
		return myDB;
	}

	/**
	 * 查询指定列的字段 并且字段唯一 不重复
	 * 
	 * @param context
	 *            当前的上下文对象
	 * @param tableName
	 *            要查询的表明
	 * @param columns
	 *            要查询的列名
	 * 
	 * @return 包含所查询列的唯一值的数组
	 */
	public static String[] queryColumnUniquefData(Context context,
			String columns) {

		SQLiteDatabase myDB = new MyDBHelper(context).getWritableDatabase();
		Cursor cursor = myDB.query(Constant.TB_NAME, new String[] { columns },
				null, null, null, null, columns + " desc");

		ArrayList<String> arrayList = new ArrayList<String>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			String data = cursor.getString(0);
			if (!arrayList.contains(data)) {
				arrayList.add(data);
			}
		}

		return arrayList.toArray(new String[arrayList.size()]);

	}

	/**
	 * 查询账单管理数据表的所有数据
	 * 
	 * @param context
	 *            当前的上下文对象 当前类名.this
	 * @param tableName
	 *            要查询的数据表名
	 * @return 查询后得到的Cursor结果集
	 */

	public static Cursor queryAllData(Context context) {

		SQLiteDatabase myDB = new MyDBHelper(context).getWritableDatabase();
		Cursor cursor = myDB.query(Constant.TB_NAME, null, null, null, null,
				null, null);
		return cursor;

	}

	/**
	 * 查询账单管理表的 符合指定字段和条件的数据
	 * 
	 * @param context
	 *            当前上下文对象
	 
	 * @param columns
	 *            要查询那一列
	 * @param selectionArgs
	 *            符合这一列的条件是什么
	 * @return 包含查询结果的Cursor结果集
	 */
	public static Cursor queryDataByCondition(Context context, String columns,
			Object selectionArgs) {

		SQLiteDatabase myDB = new MyDBHelper(context).getWritableDatabase();
		Cursor cursor = myDB.query(Constant.TB_NAME, null, columns + " = ? ",
				new String[] { selectionArgs.toString() }, null, null, null);
		return cursor;

	}

	/**
	 * 刷新ListView显示内容
	 * 
	 * @param baseAdapter
	 *            ListView所设置的适配器
	 * @param cursor
	 *            为适配器提供数据的数据库Cursor
	 */
	public static void refreshListView(BaseAdapter baseAdapter, Cursor cursor) {

		cursor.requery();
		baseAdapter.notifyDataSetChanged();

	}
	
	
	
	
	public static void getPayMoney() {
		
	}
	

	// public static void query() {
	//
	// String[] from = { Constant._NAME };
	// int[] to = { R.id.personManage_lv_name };
	//
	// Cursor cursor = mydb.query(Constant.TB_PERSONMANAGE, null, null, null,
	// null, null, null);
	// simpleCursorAdapter = new SimpleCursorAdapter(this,
	// R.layout.persronmanage_lv_item, cursor, from, to, 0);
	// lv_dispaly.setAdapter(simpleCursorAdapter);
	// }

	// public static Cursor seekAll(Context context,String tbname) {
	// SQLiteDatabase db=new MyDbHelper(context, tbname).getWritableDatabase();
	// Cursor cursor=db.query(tbname, null, null, null, null, null, null);
	// return cursor;
	// }

}
