package com.soa.billmanage_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.soa.changedata_activity.ChangeDataActivity;
import com.soa.note.R;
import com.soa.util.Constant;

/**
 * 账单管理界面的ListView的事件监听器
 * 
 * @author GuoDong
 *
 */
public class BillManageActivityListViewListener implements OnItemLongClickListener, OnItemClickListener {

	// 声明账单管理对象
	private Context context;

	/**
	 * 传入当前类的对象
	 * 
	 * @param billManageActivity
	 */
	public BillManageActivityListViewListener(Context context) {
		// 传入账单管理对象 并实例化
		this.context = context;
	}

	// 长按事件 长按删除
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// possion从0开始 是item的索引
		// id从1开始 id等于数据库id (数据库必须有个_id的字段 原来是干这个的 = =)

		// 父控件直接强转成ListView对象
		// ListView listView = (ListView) parent;
		// 根据position获取ListView的被点击项的内容 强转成Cursor对象 (里面本来装的就是Cursor对象)
		// Cursor cursor = (Cursor) listView.getItemAtPosition(position);
		// int idddddd = cursor.getInt(cursor.getColumnIndex(Constant._ID));
		// System.out.println("数据库id=" + idddddd);
		// System.out.println("参数id=" + id);
		// System.out.println("参数possion=" + position);

		new AlertDialog.Builder(context).setTitle(R.string.general_dialog_warning)
				.setMessage(R.string.billManageActivity_deleteAlertDialog_message)
				.setIcon(android.R.drawable.ic_menu_delete)
				.setPositiveButton(R.string.general_dialog_ok, new BillManageActivityDeleteDialogListener(context, id))
				.setNegativeButton(R.string.general_dialog_cancel, null).show().setCanceledOnTouchOutside(true);
		// 返回true带振动效果
		return true;
	}

	// 单击事件 单击进入修改界面
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		// int idddddd = cursor.getInt(cursor.getColumnIndex(Constant._ID));
		// System.out.println("数据库id=" + idddddd);
		// System.out.println("参数id=" + id);
		// System.out.println("参数position="+position);
		// 根据position获取ListView的被点击项的内容 强转成Cursor对象 (里面本来装的就是Cursor对象)
		// 父控件直接强转成ListView对象
		ListView listView = (ListView) parent;
		Cursor cursor = (Cursor) listView.getItemAtPosition(position);

		context.startActivity(creatIntent(cursor));

	}

	/**
	 * 创建intent对象
	 */
	private Intent creatIntent(Cursor cursor) {

		return new Intent(context, ChangeDataActivity.class).putExtras(getCursorData(cursor));

	}

	// 把对应Cursor里数据拿出来 装进一个bundle 通过Intent带值传递
	private Bundle getCursorData(Cursor cursor) {

		Bundle bundle = new Bundle();
		bundle.putInt("_id", cursor.getInt(cursor.getColumnIndex(Constant._ID)));
		bundle.putString("name", cursor.getString(cursor.getColumnIndex(Constant._NAME)));
		bundle.putString("date", cursor.getString(cursor.getColumnIndex(Constant._DATE)));
		bundle.putInt("sort", cursor.getInt(cursor.getColumnIndex(Constant._SORT)));
		bundle.putInt("mode", cursor.getInt(cursor.getColumnIndex(Constant._MODE)));
		bundle.putInt("payOrIncome", cursor.getInt(cursor.getColumnIndex(Constant._PAYORORINCOME)));
		bundle.putString("money", cursor.getString(cursor.getColumnIndex(Constant._MONEY)));
		bundle.putString("person", cursor.getString(cursor.getColumnIndex(Constant._PERSON)));
		bundle.putString("remark", cursor.getString(cursor.getColumnIndex(Constant._REMARK)));
		bundle.putByteArray("photo", cursor.getBlob(cursor.getColumnIndex(Constant._PHOTO)));

		return bundle;

	}

}
