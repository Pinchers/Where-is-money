package com.soa.personmanage_activity;

import com.soa.note.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class PersonManageActivityButtonListener implements OnClickListener {

	// 声明上下文对象
	private Context context;

	public PersonManageActivityButtonListener(Context context) {
		// 传入界面的对象并且实例化
		this.context = context;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.personManageActivity_btn_back:
			((PersonManageActivity) context).finish();
			break;
		case R.id.personManageActivity_btn_selectAddMode:
			((PersonManageActivity) context).createSelectAddModeDialog();
			break;
		case R.id.personManageActivity_btn_add:
			((PersonManageActivity) context).createAddNewPersonDialog();
			break;
		case R.id.personManageActivity_btn_addContacts:
			((PersonManageActivity) context).createAddContactsDialog();
			break;
		}

	}

}
