package com.soa.billmanage_activity;

import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.FormatFactory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * 账单管理界面的数据库适配器
 * 
 * @author GuoDong
 *
 */
public class BillManageActivityDbAdapter extends CursorAdapter {

	// 构造方法
	public BillManageActivityDbAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		// 装载布局
		View view = LayoutInflater.from(context).inflate(R.layout.billmanage_lv_item, null);
		BillManageActivityViewHoledr billManageActivityViewHoledr = new BillManageActivityViewHoledr(view);
		// 设置Tag
		view.setTag(billManageActivityViewHoledr);

		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		// 拿到Tag
		BillManageActivityViewHoledr billManageActivityViewHoledr = (BillManageActivityViewHoledr) view.getTag();

		// 填充数据
		billManageActivityViewHoledr.tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant._NAME)));
		billManageActivityViewHoledr.tv_sort.setText(
				FormatFactory.getSortStringByIndex(context, cursor.getInt(cursor.getColumnIndex(Constant._SORT))));
		billManageActivityViewHoledr.tv_mode.setText(
				FormatFactory.getModeStringByIndex(context, cursor.getInt(cursor.getColumnIndex(Constant._MODE))));
		billManageActivityViewHoledr.tv_payOrIncome.setText(FormatFactory.getPayOrIncomeStringByIndex(context,
				cursor.getInt(cursor.getColumnIndex(Constant._PAYORORINCOME))));
		billManageActivityViewHoledr.tv_money.setText(cursor.getString(cursor.getColumnIndex(Constant._MONEY)));
		billManageActivityViewHoledr.tv_date.setText(cursor.getString(cursor.getColumnIndex(Constant._DATE)));
		billManageActivityViewHoledr.tv_person.setText(cursor.getString(cursor.getColumnIndex(Constant._PERSON)));

	}

}
