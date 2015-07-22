package com.soa.personmanage_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;

/**
 * 添加联系人多选项的单击事件
 * 
 * @author GuoDong
 *
 */
public class PersonManageAddContactsMultiChoiceListener implements OnMultiChoiceClickListener {

	// 传入参数 上下文对象 联系人列表 包含选择状态的boolean类型的数组
	private boolean[] contactsSelectState;

	// 构造方法 实例化声明的对象
	public PersonManageAddContactsMultiChoiceListener(Context context, boolean[] contactsSelectState) {
		this.contactsSelectState = contactsSelectState;

	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {

		// 首先改变数组中的选择状态
		contactsSelectState[which] = isChecked;

	}

}
