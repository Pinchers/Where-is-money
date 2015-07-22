package com.soa.add_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 保存到数据库对话框的监听器
 * 
 * @author GuoDong
 *
 */
public class AddActivitySaveDialogListener implements OnClickListener {

	private Context context;

	public AddActivitySaveDialogListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		((AddActivity) context).saveToDB();
	}

}
