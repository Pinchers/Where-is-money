package com.soa.statisticsmanage_activity;

import com.soa.util.FormatFactory;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * 统计管理界面的日期选择对话框
 * 
 * @author GuoDong
 *
 */
public class StatisticsManageSelectDateDialogListener implements OnDateSetListener {

	private TextView textView;

	public StatisticsManageSelectDateDialogListener(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {

		textView.setText(FormatFactory.getDateByFormat(year, monthOfYear + 1,
				dayOfMonth));

	}

}
