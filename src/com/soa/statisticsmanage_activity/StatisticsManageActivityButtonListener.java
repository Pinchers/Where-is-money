package com.soa.statisticsmanage_activity;

import com.soa.note.R;
import com.soa.util.Tools;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 统计管理界面的单击事件监听器
 * 
 * @author GuoDong
 *
 */
public class StatisticsManageActivityButtonListener implements OnClickListener {

	private Context context;

	public StatisticsManageActivityButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 单击返回按钮 销毁当前界面
		case R.id.statisticsManageActivity_ib_back:
			((StatisticsManageActivity) context).finish();
			break;

		case R.id.statisticsManageActivity_btn_enter:

			// 启动统计明细界面
			if (((StatisticsManageActivity) context).dateIsQualify()) {

				((StatisticsManageActivity) context).startStatisticsDetailsActivity();
			} else {
				Tools.showMsg(context, R.string.statisticsManageActivity_toast_dateFormat);
			}
			break;
		}
	}

}
