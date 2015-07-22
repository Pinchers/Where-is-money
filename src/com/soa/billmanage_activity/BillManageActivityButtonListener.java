package com.soa.billmanage_activity;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class BillManageActivityButtonListener implements OnClickListener {

	// 声明账单管理界面变量
	private Context context;

	// 传入账单管理界面实例 实例化账单管理界面
	public BillManageActivityButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {

			((BillManageActivity) context).finish();

	}

}
