package com.soa.add_activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
/**
 * 添加界面的添加人员的多选事件监听器
 * @author GuoDong
 *
 */
public class AddactivityPersonMultiChoiceListener implements OnMultiChoiceClickListener {
	
	
	private boolean[] personSelectState;	

	public AddactivityPersonMultiChoiceListener(boolean[] personSelectState) {
		
		this.personSelectState=personSelectState;
	}



	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		
		personSelectState[which]=isChecked;
		
	}

	
}
