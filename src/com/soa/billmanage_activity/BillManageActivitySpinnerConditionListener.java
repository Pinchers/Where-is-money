package com.soa.billmanage_activity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 查询条件的监听器
 * @author GuoDong
 *
 */
public class BillManageActivitySpinnerConditionListener implements OnItemSelectedListener {
	
	private Context context;
	
	
	public BillManageActivitySpinnerConditionListener(Context context) {
		this.context=context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		((BillManageActivity) context).dispalyData();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
