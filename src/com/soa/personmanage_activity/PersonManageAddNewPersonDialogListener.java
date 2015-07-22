package com.soa.personmanage_activity;

import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

/**
 * 新添加联系人的对话框 确认按钮的监听器
 * 
 * @author GuoDong
 *
 */
public class PersonManageAddNewPersonDialogListener implements OnClickListener {

	private Context context;
	private SQLiteDatabase sqLiteDatabase;
	private EditText editText;

	public PersonManageAddNewPersonDialogListener(Context context,
			SQLiteDatabase sqLiteDatabase, EditText editText) {
		this.context = context;
		this.sqLiteDatabase = sqLiteDatabase;
		this.editText = editText;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		long result;

		if (!editText.getText().toString().equals("")) {

			ContentValues cv = new ContentValues();
			cv.put(Constant._NAME, editText.getText().toString());
			result = sqLiteDatabase.insert(Constant.TB_PERSONMANAGE, null, cv);

			if (result >0 ) {

				// 数据改变 调用刷新listview的方法
				((PersonManageActivity) context).refreshListView();
				

				Tools.showMsg(context, R.string.personManageActivity_toast_saveSuccess);
			} else {

				Tools.showMsg(context, R.string.personManageActivity_toast_saveFailExist);
			}
		} else {
			Tools.showMsg(context, R.string.personManageActivity_toast_saveFailNull);
		}

	}

}
