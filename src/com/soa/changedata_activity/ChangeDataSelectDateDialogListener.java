package com.soa.changedata_activity;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.soa.util.FormatFactory;
/**
 * 修改界面的日期选择的单击事件监听器
 * @author GuoDong
 *
 */
public class ChangeDataSelectDateDialogListener implements OnDateSetListener {

	private TextView tv_selectDate;
	private int m_year;
	private int m_mounth;
	private int m_day;

	public ChangeDataSelectDateDialogListener(TextView textView, int year, int mouth,
			int day) {
		this.tv_selectDate = textView;
		this.m_year = year;
		this.m_mounth = mouth;
		this.m_day = day;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {

		m_year = year;
		m_mounth = monthOfYear;
		m_day = dayOfMonth;
		// 设置完时间后更新edittext的显示日期
		tv_selectDate.setText(FormatFactory.getDateByFormat(m_year, m_mounth+1, m_day));

	}

}
