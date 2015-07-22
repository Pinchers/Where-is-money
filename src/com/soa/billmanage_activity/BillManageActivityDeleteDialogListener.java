package com.soa.billmanage_activity;

import com.soa.database.MyDBHelper;
import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.Tools;

import android.content.Context;
import android.content.DialogInterface;

public class BillManageActivityDeleteDialogListener implements DialogInterface.OnClickListener {

	// 声明id和账单管理对象
	private long id;
	private Context context;

	// 构造参数 传入id跟账单管理对象 并且实例化
	public BillManageActivityDeleteDialogListener(Context context, long id) {
		this.context = context;
		this.id = id;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		int result = new MyDBHelper(context).deleteById(Constant.TB_NAME,id);

		if (0 != result) {
			// 删除成功
			Tools.showMsg(context, R.string.billManageActivity_toast_deleteSuccess);
			((BillManageActivity) context).refreshListView();
		} else {
			// 失败
			Tools.showMsg(context, R.string.billManageActivity_toast_deleteFail);
		}
	}

}
