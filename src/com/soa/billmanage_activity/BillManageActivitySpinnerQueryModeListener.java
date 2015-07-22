package com.soa.billmanage_activity;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.Tools;

/**
 * 查询方式的监听器
 * 
 * @author GuoDong
 *
 */
public class BillManageActivitySpinnerQueryModeListener implements OnItemSelectedListener {

	private Context context;
	// 查询条件spinner的引用
	private Spinner spinner;

	public BillManageActivitySpinnerQueryModeListener(Context context, Spinner spinner) {
		this.context = context;
		this.spinner = spinner;
	}

	// 根据spinner1的选项给spinner2赋值
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		switch (position) {
		case 0:

			((BillManageActivity) context).dispalyData();
			spinner.setAdapter(null);
			break;

		case 1:

			initCondition(Tools.queryColumnUniquefData(context, Constant._DATE));
			break;

		case 2:

			initSortCondition();
			break;

		case 3:

			initModeCondition();
			break;

		case 4:

			initPayOrIncomeCondition();
			break;

		case 5:

			initCondition(orderMoney());
			break;

		}

	}

	/**
	 * 初始化分类条件的spinner
	 */
	private void initSortCondition() {
		// 获取此字段的唯一性数组
		String[] cursor = Tools.queryColumnUniquefData(context, Constant._SORT);
		// 载入包含分类信息的数组
		String[] resources = context.getResources()
				.getStringArray(R.array.sort);
		String[] data = new String[cursor.length];
		for (int i = 0; i < cursor.length; i++) {
			// 通过索引找到对应的文本
			data[i] = resources[Integer.valueOf(cursor[i])];
		}

		initCondition(data);

	}

	/**
	 * 初始化方式条件的spinner
	 */
	private void initModeCondition() {
		// 获取此字段的唯一性数组
		String[] cursor = Tools.queryColumnUniquefData(context, Constant._MODE);
		// 载入包含方式信息的数组
		String[] resources = context.getResources()
				.getStringArray(R.array.mode);
		String[] data = new String[cursor.length];
		for (int i = 0; i < cursor.length; i++) {
			// 通过索引找到对应的文本
			data[i] = resources[Integer.valueOf(cursor[i])];
		}

		initCondition(data);

	}

	/**
	 * 初始化收支条件的spinner
	 */
	private void initPayOrIncomeCondition() {
		// 获取此字段的唯一性数组
		String[] cursor = Tools.queryColumnUniquefData(context,
				Constant._PAYORORINCOME);
		String[] data = new String[cursor.length];
		for (int i = 0; i < data.length; i++) {
			if (0 == Integer.valueOf(cursor[i])) {
				data[i] = context.getResources().getString(R.string.add_rd_pay);
			} else {
				data[i] = context.getResources().getString(
						R.string.add_rd_income);
			}
		}

		initCondition(data);

	}

	/**
	 * 把数组绑定到适配器 在绑定到spinner
	 * 
	 * @param 为适配器提供数据的数组
	 */
	private void initCondition(String[] data) {
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, data);
		spinner.setAdapter(arrayAdapter);
	}

	/**
	 * 对获取的金额进行排序
	 * 
	 * @return
	 */
	private String[] orderMoney() {
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < Tools.queryColumnUniquefData(context,
				Constant._MONEY).length; i++) {
			arrayList.add(Tools
					.queryColumnUniquefData(context, Constant._MONEY)[i]);
		}
		// 先升序 在逆序 就变成降序了 = =
		Collections.sort(arrayList);
		Collections.reverse(arrayList);

		return arrayList.toArray(new String[arrayList.size()]);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
