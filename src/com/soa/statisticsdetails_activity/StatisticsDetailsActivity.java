package com.soa.statisticsdetails_activity;

import java.util.ArrayList;

import com.soa.barchart_fragmentactivity.BarChartFragment;
import com.soa.database.MyDBHelper;
import com.soa.linechart_fragmentactivity.LineChartFragment;
import com.soa.note.R;
import com.soa.piechart_fragmentactivity.PieChartFragment;
import com.soa.util.Constant;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

public class StatisticsDetailsActivity extends Activity {

	private Button btn_barChart, btn_lineChart, btn_pieChart, btn_back;
	// 声明fragment对象
	private BarChartFragment barChartActivity;
	private LineChartFragment lineChartActivity;
	private PieChartFragment pieChartActivity;

	private FragmentManager fragmentManage;
	private Cursor cursor;

	private ArrayList<String> arrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statisticsdetails_activity);

		cursor = new MyDBHelper(this).getWritableDatabase().query(Constant.TB_NAME, null,
				Constant._DATE + " between ?  and  ? ",
				new String[] { getIntent().getStringExtra("startDate"), getIntent().getStringExtra("endDate") }, null,
				null, Constant._DATE + " " + Constant.ORDER_BY_DESC);
		// 初始化X轴的数据组数
		initXlength();
		// 初始化支出和收入的金额
		initPayOrIncomeOfMoney();

		// 初始化界面布局
		findView();
		init();
		fragmentManage = getFragmentManager();
		// 初始化默认选择第一个
		setTabSelection(0);

	}

	/**
	 * 关联组件
	 */
	private void findView() {

		btn_barChart = (Button) findViewById(R.id.statisticsDetailsActivity_btn_barChart);
		btn_lineChart = (Button) findViewById(R.id.statisticsDetailsActivity_btn_lineChart);
		btn_pieChart = (Button) findViewById(R.id.statisticsDetailsActivity_btn_pieChart);
		btn_back = (Button) findViewById(R.id.statisticsDetailsActivity_btn_back);

	}

	/**
	 * 初始化组件
	 */
	private void init() {
		btn_barChart.setOnClickListener(new StatisticsDetailsActivityButtonListener(this));
		btn_lineChart.setOnClickListener(new StatisticsDetailsActivityButtonListener(this));
		btn_pieChart.setOnClickListener(new StatisticsDetailsActivityButtonListener(this));
		btn_back.setOnClickListener(new StatisticsDetailsActivityButtonListener(this));

	}

	/**
	 * 根据传入的下标判断点击的是哪一个标签
	 * 
	 * @param index
	 */
	public void setTabSelection(int index) {

		// 开启一个fragment事务
		FragmentTransaction fragmentTransaction = fragmentManage.beginTransaction();
		// 点击之前先清空之前的选择
		hideFragment(fragmentTransaction);
		switch (index) {
		case 0:

			// 每次点击都先还原按钮的背景设置
			clearButtonBackground();
			// 然后在设置按钮的背景色
			btn_barChart.setBackgroundColor(getResources().getColor(R.color.statisticsManage_activity));
			// 点击之后判断 如果为空 创建一个并添加到界面上
			if (barChartActivity == null) {
				barChartActivity = BarChartFragment.newInstance(xLength, payOfMoney, incomeOfMoney);
				fragmentTransaction.add(R.id.statisticsDetailsActivity_fl_content, barChartActivity);

			} else {
				// 如果不为空 则直接显示出来
				fragmentTransaction.show(barChartActivity);
			}
			break;
		case 1:

			// 每次点击都先还原按钮的背景设置
			clearButtonBackground();
			// 然后在设置按钮的背景色
			btn_lineChart.setBackgroundColor(getResources().getColor(R.color.statisticsManage_activity));
			// 点击之后判断 如果为空 创建一个并添加到界面上
			if (lineChartActivity == null) {
				lineChartActivity = new LineChartFragment();
				fragmentTransaction.add(R.id.statisticsDetailsActivity_fl_content, lineChartActivity);
			} else {
				// 如果不为空 则直接显示出来
				fragmentTransaction.show(lineChartActivity);
			}
			break;
		case 2:

			// 每次点击都先还原按钮的背景设置
			clearButtonBackground();
			// 然后在设置按钮的背景色
			btn_pieChart.setBackgroundColor(getResources().getColor(R.color.statisticsManage_activity));
			// 点击之后判断 如果为空 创建一个并添加到界面上
			if (pieChartActivity == null) {
				pieChartActivity = PieChartFragment.newInstance(getValues(),
						getResources().getStringArray(R.array.sort));
				fragmentTransaction.add(R.id.statisticsDetailsActivity_fl_content, pieChartActivity);

			} else {
				// 如果不为空 则直接显示出来
				fragmentTransaction.show(pieChartActivity);
			}
			break;

		}
		// 修改完成后记得提交
		fragmentTransaction.commit();
	}

	/**
	 * 还原按钮背景设置的方法
	 */
	private void clearButtonBackground() {

		btn_barChart.setBackgroundResource(R.drawable.statisticsmanageactivity_buttonselector_color);
		btn_lineChart.setBackgroundResource(R.drawable.statisticsmanageactivity_buttonselector_color);
		btn_pieChart.setBackgroundResource(R.drawable.statisticsmanageactivity_buttonselector_color);
		btn_back.setBackgroundResource(R.drawable.statisticsmanageactivity_buttonselector_color);
	}

	/**
	 * 判断不为空 隐藏所创建的fragment对象
	 * 
	 * @param fragmentTransaction
	 */
	private void hideFragment(FragmentTransaction fragmentTransaction) {

		if (barChartActivity != null) {
			fragmentTransaction.hide(barChartActivity);
		}
		if (lineChartActivity != null) {
			fragmentTransaction.hide(lineChartActivity);
		}
		if (pieChartActivity != null) {
			fragmentTransaction.hide(pieChartActivity);
		}

	}

	// 饼状图的填充数据集
	private double[] getValues() {
		// 创建一个跟字符串长度相同的数组 用于填充饼状图的数据
		double[] sort_info = new double[getResources().getStringArray(R.array.sort).length];

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			// 让对应位置的数据+1
			sort_info[cursor.getInt(cursor.getColumnIndex(Constant._SORT))]++;
		}

		// 返回这个填充数据数组
		return sort_info;

	}

	// 获取不重复的时间列的长度 就是X轴的数据组数
	private void initXlength() {

		arrayList = new ArrayList<String>();

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			if (!arrayList.contains(cursor.getString(cursor.getColumnIndex(Constant._DATE)))) {
				arrayList.add(cursor.getString(cursor.getColumnIndex(Constant._DATE)));
			}
		}

		xLength = arrayList.size();

	}

	private int xLength;
	private double[] payOfMoney, incomeOfMoney;

	// 初始化收入和支出的金额
	private void initPayOrIncomeOfMoney() {

		// 初始化两个数据组的长度 长度与记账的天数相等
		payOfMoney = new double[xLength];
		incomeOfMoney = new double[xLength];
		String[] strings = arrayList.toArray(new String[xLength]);
		int count = 0;

		for (cursor.moveToFirst(); !cursor.isAfterLast();) {

			while (!cursor.isAfterLast()
					&& cursor.getString(cursor.getColumnIndex(Constant._DATE)).equals(strings[count])) {

				if (0 == cursor.getInt(cursor.getColumnIndex(Constant._PAYORORINCOME))) {
					payOfMoney[count] += Double.valueOf(cursor.getString(cursor.getColumnIndex(Constant._MONEY)));

				} else {
					incomeOfMoney[count] += Double.valueOf(cursor.getString(cursor.getColumnIndex(Constant._MONEY)));
				}

				cursor.moveToNext();
			}
			count++;
		}

	}

}
