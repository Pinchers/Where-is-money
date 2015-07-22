package com.soa.changedata_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.soa.personmanage_activity.PersonManageActivity;
import com.soa.util.Tools;

public class ChangeDataActivitySelectPersonDialogListener implements OnClickListener {
	
	private Context context;
	
	/**
	 *  人员选择对话框的监听器
	 * @param context 传入上下文对象  当前类名.this
	 */
	public ChangeDataActivitySelectPersonDialogListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		switch (which) {
		//判断点击取消按钮
		case DialogInterface.BUTTON_NEGATIVE:
			// 销毁当前的对话框
			// dialog.dismiss();
			Tools.skip(context, PersonManageActivity.class);
			break;
			
		//判断点击确认按钮	
		case DialogInterface.BUTTON_POSITIVE:
			((ChangeDataActivity) context).displayPerson();
			break;
		}
	}

	 

}
