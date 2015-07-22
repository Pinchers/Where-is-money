package com.soa.statisticsmanage_activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.soa.note.R;
import com.soa.statisticsdetails_activity.StatisticsDetailsActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatisticsManageActivity extends Activity {

	private Button btn_back, btn_enter;
	private TextView tv_startDate, tv_endDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statisticsmanage_activity);

		findView();
		init();

	}

	/**
	 * 关联组件
	 */
	private void findView() {
		btn_back = (Button) findViewById(R.id.statisticsManageActivity_ib_back);
		tv_startDate = (TextView) findViewById(R.id.statisticsManage_et_startDate);
		tv_endDate = (TextView) findViewById(R.id.statisticsManage_et_endDate);
		btn_enter = (Button) findViewById(R.id.statisticsManageActivity_btn_enter);
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		btn_back.setOnClickListener(new StatisticsManageActivityButtonListener(this));
		tv_startDate.setOnClickListener(new StatisticsManageTextViewListener(this, tv_startDate));
		tv_endDate.setOnClickListener(new StatisticsManageTextViewListener(this, tv_endDate));
		btn_enter.setOnClickListener(new StatisticsManageActivityButtonListener(this));
	}

	public void selectDate(TextView textView) {

		// 初始化系统时间 获得年月日
		Calendar calendar = Calendar.getInstance();
		int m_year = calendar.get(Calendar.YEAR);
		int m_mounth = calendar.get(Calendar.MONTH);
		int m_day = calendar.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				new StatisticsManageSelectDateDialogListener(textView), m_year, m_mounth, m_day);
		datePickerDialog.setTitle(R.string.addActivity_selectDateDialog_title);
		datePickerDialog.show();
	}

	// 启动统计详细的界面
	public void startStatisticsDetailsActivity() {

		startActivity(new Intent(this, StatisticsDetailsActivity.class)
				.putExtra("startDate", tv_startDate.getText().toString())
				.putExtra("endDate", tv_endDate.getText().toString()));

	}

	/**
	 * 格式化时间之后比较时间的大小
	 * 
	 * @return
	 */
	public boolean dateIsQualify() {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date startDate = simpleDateFormat.parse(tv_startDate.getText().toString());
			Date endDate = simpleDateFormat.parse(tv_endDate.getText().toString());

			if (1 != startDate.compareTo(endDate)) {
				return true;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;

	}

}
