package com.soa.statisticsmanage_activity;

import com.soa.note.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 统计管理界面的文本(日期选择)单击事件
 * 
 * @author GuoDong
 *
 */
public class StatisticsManageTextViewListener implements OnClickListener {

	private Context context;
	// 指定对应的文本
	private TextView textView;

	public StatisticsManageTextViewListener(Context context, TextView textView) {
		this.context = context;
		this.textView = textView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.statisticsManage_et_startDate:

			// 执行选择日期的方法
			((StatisticsManageActivity) context).selectDate(textView);
			break;

		case R.id.statisticsManage_et_endDate:

			// 执行选择日期的方法
			((StatisticsManageActivity) context).selectDate(textView);
			break;
		}
	}

}
