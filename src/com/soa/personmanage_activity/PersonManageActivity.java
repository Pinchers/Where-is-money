package com.soa.personmanage_activity;

import java.util.ArrayList;
import java.util.HashSet;

import com.soa.database.MyDBHelper;
import com.soa.note.R;
import com.soa.util.Constant;

import android.R.animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class PersonManageActivity extends Activity {

	private Button btn_back, btn_selectAddMode;
	private ListView lv_dispaly;
	private SQLiteDatabase myPersonDB;
	private PersonManageActivityDbAdapter personManageActivityDbAdapter;
	private EditText et_name;
	private Cursor cursor;
	private AlertDialog selectAddModeDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personmanage_activity);

		findView();

		// 获得人员管理的数据表
		myPersonDB = new MyDBHelper(this).getWritableDatabase();
		init();

		displayData();

	}

	/**
	 * 初始化组件
	 */
	private void init() {
		btn_back.setOnClickListener(new PersonManageActivityButtonListener(this));
		btn_selectAddMode.setOnClickListener(new PersonManageActivityButtonListener(this));
		lv_dispaly.setOnItemLongClickListener(new PersonManageActivityListViewListener(this));
	}

	/**
	 * 把数据库里的数据显示到lisiview
	 */
	private void displayData() {

		cursor = myPersonDB.query(Constant.TB_PERSONMANAGE, null, null, null, null, null, null);

		personManageActivityDbAdapter = new PersonManageActivityDbAdapter(this, cursor);

		lv_dispaly.setAdapter(personManageActivityDbAdapter);
	}

	/**
	 * 关联组件
	 */
	private void findView() {
		btn_back = (Button) findViewById(R.id.personManageActivity_btn_back);
		btn_selectAddMode = (Button) findViewById(R.id.personManageActivity_btn_selectAddMode);
		lv_dispaly = (ListView) findViewById(R.id.personManageActivity_lv_displayData);
	}

	/**
	 * 创建添加模式的对话框 在联系人导入 还是手动添加
	 */
	public void createSelectAddModeDialog() {

		View view = getLayoutInflater().inflate(R.layout.personmanage_dialog_selectaddmode, null);
		view.findViewById(R.id.personManageActivity_btn_add)
				.setOnClickListener(new PersonManageActivityButtonListener(this));
		view.findViewById(R.id.personManageActivity_btn_addContacts)
				.setOnClickListener(new PersonManageActivityButtonListener(this));

		selectAddModeDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.personManageActivity_selectAddModeDialog_title).setView(view).show();
		selectAddModeDialog.setCanceledOnTouchOutside(true);

	}

	public void createAddContactsDialog() {

		// 创建对话框之前先把前面的选择添加方式的对话框取消掉
		selectAddModeDialog.dismiss();

		// 获得系统联系人的名字
		Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
				new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME }, null, null, null);

		String[] contactsList = getContactsList(cursor);
		boolean[] contactsSelectState = new boolean[contactsList.length];

		// 创建导入联系人的对话框
		new AlertDialog.Builder(this).setTitle(R.string.personManageActivity_addContactsDialog_title)
				.setMultiChoiceItems(contactsList, null,
						new PersonManageAddContactsMultiChoiceListener(this, contactsSelectState))
				.setPositiveButton(R.string.general_dialog_ok,
						new PersonManageAddContatcsDialogListener(this, contactsList, contactsSelectState, myPersonDB))
				.show().setCanceledOnTouchOutside(true);

	}

	/**
	 * 获得联系人选择状态的方法
	 * 
	 * @param cursor
	 *            包含联系人信息的cursor对象
	 * @return 包含联系人选择状态的Boolean数组
	 */
	/*
	 * private boolean[] getContactsSelectState(Cursor cursor) {
	 * 
	 * // 根据获取的联系人cursor创建 联系人选择状态的boolean类型数组 传入dialog第二个参数
	 * 
	 * SharedPreferences sharedPreferences = getSharedPreferences(
	 * Constant.CONTACTS_IS_SELECTED, MODE_PRIVATE); ArrayList<Boolean>
	 * arrayList1 = new ArrayList<Boolean>( cursor.getCount());
	 * 
	 * for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	 * 
	 * arrayList1 .add(sharedPreferences.getBoolean( cursor.getString(cursor
	 * .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
	 * false)); } Boolean[] transition = arrayList1 .toArray(new
	 * Boolean[cursor.getCount()]);
	 * 
	 * // 手动解包 = =.. boolean[] contactsIsSelected = new
	 * boolean[cursor.getCount()];
	 * 
	 * for (int i = 0; i < transition.length; i++) { contactsIsSelected[i] =
	 * transition[i]; }
	 * 
	 * return contactsIsSelected; }
	 */

	/**
	 * 获得电话本联系人数组的方法
	 * 
	 * @param cursor
	 *            包含联系人姓名信息的cursor对象
	 * @return 包含联系人信息的String数组
	 */
	private String[] getContactsList(Cursor cursor) {

		// 获得包含现有联系人的集合 用于筛选
		HashSet<String> existPerson = getExistPerson();

		// 根据获取的联系人cursor创建 联系人列表数组 传入dialog第一个参数
		ArrayList<String> arrayList = new ArrayList<String>(cursor.getCount());

		String contactsName;
		// for循环 遍历cursor对象
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			// 拿到现有联系人对象的名字
			contactsName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			// 避免显示重复的联系人
			// 如果现有人员管理中没有此联系人
			if (existPerson.add(contactsName)) {

				arrayList.add(contactsName);
			}

		}
		// 把最后获取的含有联系人姓名在arrayList转换成数组 传入第一个参数

		return arrayList.toArray(new String[arrayList.size()]);
	}

	/**
	 * 
	 * 获得现有的人员列表 用于筛选不重复对象
	 * 
	 * @return 一个包含所有人员记录的列表hashSet集合
	 */
	private HashSet<String> getExistPerson() {
		Cursor c = myPersonDB.query(Constant.TB_PERSONMANAGE, null, null, null, null, null, null);
		HashSet<String> hashSet = new HashSet<String>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			hashSet.add(c.getString(c.getColumnIndex(Constant._NAME)));
		}
		return hashSet;
	}

	// 创建新建参与人员的对话框
	public void createAddNewPersonDialog() {

		// 创建对话框之前先把前面的选择添加方式的对话框取消掉
		selectAddModeDialog.dismiss();

		et_name = new EditText(this);
		et_name.setHint(R.string.personManageActivity_addNewPersonDialog_textHint);
		et_name.requestFocus();

		new AlertDialog.Builder(this).setTitle(R.string.personManageActivity_addNewPersonDialog_title)
				.setIcon(android.R.drawable.ic_input_add)
				.setMessage(R.string.personManageActivity_addNewPersonDialog_message).setView(et_name)
				.setPositiveButton(R.string.general_dialog_ok,
						new PersonManageAddNewPersonDialogListener(this, myPersonDB, et_name))
				.setNegativeButton(R.string.general_dialog_cancel, null).show().setCanceledOnTouchOutside(true);
	}

	/**
	 * 
	 * 刷新ListView以更新显示数据
	 */
	public void refreshListView() {
		// 重新查询数据库
		cursor.requery();
		// 通知适配器数据已更改
		personManageActivityDbAdapter.notifyDataSetChanged();
	}

}
