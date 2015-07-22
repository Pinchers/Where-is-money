package com.soa.statisticsdetails_activity;

import com.soa.note.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * 统计明细界面的单击事件监听器
 * @author GuoDong
 *
 */
public class StatisticsDetailsActivityButtonListener implements OnClickListener {

	
	private Context context;

	
	public StatisticsDetailsActivityButtonListener(Context context) {
		this.context = context;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.statisticsDetailsActivity_btn_barChart:
			
			((StatisticsDetailsActivity) context).setTabSelection(0);
			break;
			
		case R.id.statisticsDetailsActivity_btn_lineChart:
			
			((StatisticsDetailsActivity) context).setTabSelection(1);
			break;
			
		case R.id.statisticsDetailsActivity_btn_pieChart:
			
			((StatisticsDetailsActivity) context).setTabSelection(2);
			break;
			
		case R.id.statisticsDetailsActivity_btn_back:

			((StatisticsDetailsActivity) context).finish();
			break;
		}
	}

}
