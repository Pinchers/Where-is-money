package com.soa.personmanage_activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;

import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.Tools;

/**
 * 添加联系人多选对话框的确定按钮的监听器
 * 
 * @author GuoDong
 *
 */
public class PersonManageAddContatcsDialogListener implements OnClickListener {

	// 传入参数 上下文对象 联系人列表 包含选择状态的boolean类型的数组
	private Context context;
	private String[] contactsList;
	private boolean[] contactsSelectState;
	private SQLiteDatabase myPersonDB;

	// 构造方法 实例化声明的对象
	public PersonManageAddContatcsDialogListener(Context context, String[] contactsList, boolean[] contactsSelectState,
			SQLiteDatabase sqLiteDatabase) {
		this.context = context;
		this.contactsList = contactsList;
		this.contactsSelectState = contactsSelectState;
		this.myPersonDB = sqLiteDatabase;

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		int count = 0;
		long result = 0, isSucceed;
		for (int i = 0; i < contactsSelectState.length; i++) {
			if (contactsSelectState[i]) {

				ContentValues cv = new ContentValues();
				cv.put(Constant._NAME, contactsList[i]);
				isSucceed = myPersonDB.insert(Constant.TB_PERSONMANAGE, null, cv);
				// 如果返回-1 表示插入失败
				if (isSucceed != -1) {
					result++;
				}
				count++;
			}

		}

		// 如果插入的记录数的次数跟 插入成功的次数相等 并且都不等于初始值0
		// 那么才标示数值插入成功 弹出提示信息 并且刷新数据
		if (result != 0 && count != 0 && count == result) {

			((PersonManageActivity) context).refreshListView();
			Tools.showMsg(context, R.string.personManageActivity_toast_saveSuccess);

		} else {

			Tools.showMsg(context, R.string.personManageActivity_toast_saveFail);

		}

	}

}
