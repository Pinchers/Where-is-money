package com.soa.main_activity;

import com.soa.add_activity.AddActivity;
import com.soa.billmanage_activity.BillManageActivity;
import com.soa.note.R;
import com.soa.personmanage_activity.PersonManageActivity;
import com.soa.statisticsmanage_activity.StatisticsManageActivity;
import com.soa.system_activity.SystemActivity;
import com.soa.util.Tools;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 主界面的按钮单击事件监听器
 * 
 * @author GuoDong
 */
public class MainActivityButtonListener implements OnClickListener {

	private Context context;

	public MainActivityButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 添加按钮执行操作
		case R.id.mainActivity_btn_add:
			Tools.skip(context, AddActivity.class);
			break;

		// 账单管理按钮执行的操作
		case R.id.mainActivity_btn_billManage:
			Tools.skip(context, BillManageActivity.class);

			break;
		// 人员管理按钮执行的操作
		case R.id.mainActivity_btn_personManage:
			Tools.skip(context, PersonManageActivity.class);

			break;
		// 统计管理按钮执行的操作
		case R.id.mainActivity_btn_statisticsManage:

			Tools.skip(context, StatisticsManageActivity.class);
			break;

		// 系统设置按钮执行的操作
		case R.id.mainActivity_btn_system:

			Tools.skip(context, SystemActivity.class);
			break;
		// 退出按钮的执行从操作
		case R.id.mainActivity_btn_exit:
			((MainActivity) context).exitDialog();
			break;

		}

	}

}
