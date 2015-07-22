package com.soa.billmanage_activity;

import android.view.View;
import android.widget.TextView;

import com.soa.note.R;

public class BillManageActivityViewHoledr {

	public TextView tv_name, tv_date, tv_sort, tv_mode, tv_money, tv_payOrIncome, tv_person;

	public BillManageActivityViewHoledr(View view) {

		// 关联组件
		tv_name = (TextView) view.findViewById(R.id.billManageActivity_lv_name);
		tv_sort = (TextView) view.findViewById(R.id.billManageActivity_lv_sort);
		tv_mode = (TextView) view.findViewById(R.id.billManageActivity_lv_mode);
		tv_payOrIncome = (TextView) view.findViewById(R.id.billManageActivity_lv_payOrIncome);
		tv_money = (TextView) view.findViewById(R.id.billManageActivity_lv_money);
		tv_date = (TextView) view.findViewById(R.id.billManageActivity_lv_date);
		tv_person = (TextView) view.findViewById(R.id.billManageActivity_lv_person);

	}

}
