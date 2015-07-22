package com.soa.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.FormatFactory;
/**
 * 
 * 此类暂时被废弃 禁止使用
 * 
 * @author GuoDong
 *
 */
public class MyAdapter_deprecated extends BaseAdapter {

	private Cursor cursor;
	private Context context;
	private TextView tv_name, tv_date, tv_sort, tv_mode, tv_money,
			tv_payOrIncome, tv_person, tv_remark;

	public MyAdapter_deprecated(Context context, Cursor cursor) {
		this.cursor = cursor;
		this.context = context;
	}

	/**
	 * 此方法返回多少 执行多少次getView方法
	 */
	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		
		cursor.moveToPosition(position);
		return cursor;
		
	}

	/**
	 * 获得对应位置cursor数据所对应的数据库id
	 */
	@Override
	public long getItemId(int position) {
		  cursor.moveToPosition(position);
		 return cursor.getLong(cursor.getColumnIndex(Constant._ID));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.billmanage_lv_item, null);
		}

		// cursor.moveToFirst();
		// System.out.println("cursor.moveToFirst()");
		// 显示之前把游标移动到下一个 因为一开始游标是在外面
		cursor.moveToNext();
		dispalyData(convertView);
		// System.out.println("cursor.moveToNext();");

		return convertView;
	}

	private void dispalyData(View convertView) {

		findView(convertView);

		tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant._NAME)));
		tv_date.setText(cursor.getString(cursor.getColumnIndex(Constant._DATE)));
		tv_money.setText(cursor.getString(cursor
				.getColumnIndex(Constant._MONEY)));
		tv_person.setText(cursor.getString(cursor
				.getColumnIndex(Constant._PERSON)));
		tv_sort.setText(FormatFactory.getSortStringByIndex(context,
				cursor.getInt(cursor.getColumnIndex(Constant._SORT))));
		tv_mode.setText(FormatFactory.getModeStringByIndex(context,
				cursor.getInt(cursor.getColumnIndex(Constant._MODE))));
		tv_payOrIncome.setText(FormatFactory.getPayOrIncomeStringByIndex(context,
				cursor.getInt(cursor.getColumnIndex(Constant._PAYORORINCOME))));

	}

	/**
	 * 关联组件
	 * 
	 * @param convertView
	 */
	private void findView(View convertView) {
		tv_name = ViewHolder_deprecated.findView(convertView, R.id.billManageActivity_lv_name);
		tv_date = ViewHolder_deprecated.findView(convertView, R.id.billManageActivity_lv_date);
		tv_sort = ViewHolder_deprecated.findView(convertView, R.id.billManageActivity_lv_sort);
		tv_mode = ViewHolder_deprecated.findView(convertView, R.id.billManageActivity_lv_mode);
		tv_money = ViewHolder_deprecated.findView(convertView, R.id.billManageActivity_lv_money);
		tv_payOrIncome = ViewHolder_deprecated.findView(convertView,
				R.id.billManageActivity_lv_payOrIncome);
//		tv_person = ViewHolder.findView(convertView, R.id.billManage_lv_person);

	}

}
