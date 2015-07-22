package com.soa.personmanage_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.soa.database.MyDBHelper;
import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.Tools;
/**
 * 人员管理界面的长按事件  删除对话框的确认按钮监听器
 * @author GuoDong
 *
 */
public class PersonManageDeleteDialogListener implements OnClickListener {

	private Context context;
	private long id;

	public PersonManageDeleteDialogListener(Context context, long id) {
		this.context = context;
		this.id = id;

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		int result = new MyDBHelper(context).deleteById(Constant.TB_PERSONMANAGE,id);

		//返回成功删除的行数  失败返回0
		if (0 != result) {
			// 删除成功
			Tools.showMsg(context,R.string.billManageActivity_toast_deleteSuccess);
			((PersonManageActivity) context).refreshListView();
		} else {
			// 失败
			Tools.showMsg(context, R.string.billManageActivity_toast_deleteFail);
		}
	}

}
