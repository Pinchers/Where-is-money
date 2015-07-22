package com.soa.personmanage_activity;

import com.soa.note.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * 
 * 人员管理界面的ListView长按事件监听器
 * 
 * @author GuoDong
 *
 */
public class PersonManageActivityListViewListener implements OnItemLongClickListener {

	private Context context;

	public PersonManageActivityListViewListener(Context context) {
		this.context = context;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		new AlertDialog.Builder(context).setTitle(R.string.general_dialog_warning)
				.setIcon(android.R.drawable.ic_menu_delete)
				.setMessage(R.string.billManageActivity_deleteAlertDialog_message)
				.setPositiveButton(R.string.general_dialog_ok, new PersonManageDeleteDialogListener(context, id))
				.setNegativeButton(R.string.general_dialog_cancel, null).show().setCanceledOnTouchOutside(true);

		// 返回true带振动效果
		return true;
	}

}
