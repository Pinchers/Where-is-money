package com.soa.billmanage_activity;

import com.soa.add_activity.AddActivity;
import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.FormatFactory;
import com.soa.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class BillManageActivity extends Activity {

	 
	private Spinner sp_mode, sp_condition;
	private Button btn_back;
	private ListView lv_display;
	private Cursor cursor;
	private BillManageActivityDbAdapter myDbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.billmanage_activity);

		findView();

		init();
	}

	@Override
	protected void onStart() {
		super.onStart();

		dispalyData();

	}

	/**
	 * 
	 */
	private void init() {

		btn_back.setOnClickListener(new BillManageActivityButtonListener(this));
		sp_mode.setOnItemSelectedListener(new BillManageActivitySpinnerQueryModeListener(
				this, sp_condition));
		sp_condition
				.setOnItemSelectedListener(new BillManageActivitySpinnerConditionListener(
						this));

		// 设置item的单击事件
		lv_display.setOnItemClickListener(new BillManageActivityListViewListener(this));
		// 设置item的长按事件
		lv_display.setOnItemLongClickListener(new BillManageActivityListViewListener(
				this));

	}

	/**
	 * 关联组件
	 */
	private void findView() {
		sp_mode = (Spinner) findViewById(R.id.billManageActivity_sp_mode);
		sp_condition = (Spinner) findViewById(R.id.billManageActivity_sp_condition);
		btn_back = (Button) findViewById(R.id.billManageActivity_btn_back);
		lv_display = (ListView) findViewById(R.id.billManageActivity_lv_display);

	}

	// 根据查询方式跟查询条件的不同 动态更新数据
	private void getData() {

		switch (sp_mode.getSelectedItemPosition()) {
		case 0:
			cursor = Tools.queryAllData(this);

			break;
		case 1:
			cursor = Tools.queryDataByCondition(this, Constant._DATE,
					sp_condition.getSelectedItem().toString());

			break;
		case 2:
			cursor = Tools.queryDataByCondition(this, Constant._SORT,
					FormatFactory.getSortIndexByString(this, sp_condition
							.getSelectedItem().toString()));

			break;
		case 3:
			cursor = Tools.queryDataByCondition(this, Constant._MODE,
					FormatFactory.getModeIndexByString(this, sp_condition
							.getSelectedItem().toString()));

			break;
		case 4:

			cursor = Tools.queryDataByCondition(this, Constant._PAYORORINCOME,
					FormatFactory.getPayOrIncomeIndexByString(this,
							sp_condition.getSelectedItem().toString()));

			break;
		case 5:
			cursor = Tools.queryDataByCondition(this, Constant._MONEY,
					sp_condition.getSelectedItem().toString());

			break;

		}

	}

	/**
	 * 显示数据的方法 内部调用得到cursor有标的方法
	 */
	public void dispalyData() {

		getData();

		// 如果数据库里有数据 才进行加载
		if (cursor.getCount() > 0) {

			myDbAdapter = new BillManageActivityDbAdapter(this, cursor);
			lv_display.setAdapter(myDbAdapter);

		} else {
			// 如果开始没有数据 弹出需要创建第一条数据的对话框
			creatAddDialog();
		}

	}

	// 数据更改后调用此方法通知ListView刷新数据
	public void refreshListView() {
		// 如果当前选项不是第一个 则设置成第一个刷新全部
		if (0 != sp_mode.getSelectedItemPosition()) {
			// 查询模式设置成全部 自动刷新查询结果
			sp_mode.setSelection(0);

		} else {
			//否则刷新当前数据
			cursor.requery();
			// 以下两个方法都可以实现列表的刷新 效果一样
			myDbAdapter.notifyDataSetChanged();
			// lv_display.setAdapter(myDbAdapter);
		}

	}

	// 询问用户是否创建第一条内容的对话框
	private void creatAddDialog() {
		
		new AlertDialog.Builder(this).setTitle(R.string.billManageActivity_addDialog_title)
				.setMessage(R.string.billManageActivity_addDialog_message)
				.setPositiveButton(R.string.general_dialog_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Tools.skip(BillManageActivity.this, AddActivity.class);
						finish();
					}
				}).setNegativeButton(R.string.general_dialog_cancel, null).show();
	}

}
