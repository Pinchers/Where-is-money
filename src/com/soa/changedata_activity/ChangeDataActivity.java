package com.soa.changedata_activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.soa.add_activity.AddactivityPersonMultiChoiceListener;
import com.soa.database.MyDBHelper;
import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.FormatFactory;
import com.soa.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ChangeDataActivity extends Activity {

	private Button btn_back, btn_amend, btn_camera;
	private ImageView iv_photo;
	private EditText et_name, et_money, et_remark;
	private TextView tv_selectDate, tv_selectPerson;
	private Spinner sp_sort, sp_mode;
	private RadioButton rb_pay, rb_income;
	// 判断要修改那条记录的条件
	private int id;
	private String[] personDataList;
	private boolean[] personSelectState;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changebillactivity_activity);

		findView();

		dispalyIntentData();

		init();
	}

	/**
	 * 关联组件
	 */
	private void findView() {

		btn_back = (Button) findViewById(R.id.changeBillActivity_btn_back);
		btn_amend = (Button) findViewById(R.id.changeBillActivity_btn_save);
		btn_camera = (Button) findViewById(R.id.changeBillActivity_btn_camera);
		et_name = (EditText) findViewById(R.id.changeBillActivity_et_name);
		et_money = (EditText) findViewById(R.id.changeBillActivity_et_money);
		et_remark = (EditText) findViewById(R.id.changeBillActivity_et_remark);
		iv_photo = (ImageView) findViewById(R.id.changeBillActivity_iv_photo);
		tv_selectDate = (TextView) findViewById(R.id.changeBillActivity_tv_selectDate);
		tv_selectPerson = (TextView) findViewById(R.id.changeBillActivity_tv_selectPerson);
		sp_sort = (Spinner) findViewById(R.id.changeBillActivity_sp_sort);
		sp_mode = (Spinner) findViewById(R.id.changeBillActivity_sp_mode);
		rb_pay = (RadioButton) findViewById(R.id.changeBillActivity_rd_pay);
		rb_income = (RadioButton) findViewById(R.id.changeBillActivity_rd_income);

	}

	private void dispalyIntentData() {

		Bundle bundle = getIntent().getExtras();
		id = bundle.getInt("_id");
		et_name.setText(bundle.getString("name"));
		tv_selectDate.setText(bundle.getString("date"));
		sp_sort.setSelection(bundle.getInt("sort"));
		sp_mode.setSelection(bundle.getInt("mode"));

		if (0 == bundle.getInt("payOrIncome")) {
			rb_pay.setChecked(true);
		} else {
			rb_income.setChecked(true);
		}
		et_money.setText(bundle.getString("money"));
		tv_selectPerson.setText(bundle.getString("person"));
		et_remark.setText(bundle.getString("remark"));

		// 先判断传过来的数据里有图片数据
		if (null != bundle.getByteArray("photo")) {
			// 转换图像数据并显示
			iv_photo.setImageBitmap(FormatFactory.getBitmapByByteArray(bundle.getByteArray("photo")));
			// 设置成可见状态
			iv_photo.setVisibility(View.VISIBLE);
			// 隐藏编辑区域
			et_remark.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * 初始化组件 设置监听事件
	 */
	private void init() {
		btn_back.setOnClickListener(new ChangeDataActivityButtonListener(this));
		btn_camera.setOnClickListener(new ChangeDataActivityButtonListener(this));
		btn_amend.setOnClickListener(new ChangeDataActivityButtonListener(this));
		tv_selectDate.setOnClickListener(new ChangeDataActivityButtonListener(this));
		tv_selectPerson.setOnClickListener(new ChangeDataActivityButtonListener(this));
		/*et_remark.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (null != s) {
					btn_clear.setVisibility(View.VISIBLE);
				}

				if (null == s) {

					btn_clear.setVisibility(View.GONE);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});*/

	}

	// 设置时间的方法
	public void setDate() {

		// 初始化系统时间 获得年月日
		Calendar calendar = Calendar.getInstance();
		int m_year = calendar.get(Calendar.YEAR);
		int m_mounth = calendar.get(Calendar.MONTH);
		int m_day = calendar.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datedialog = new DatePickerDialog(this,
				new ChangeDataSelectDateDialogListener(tv_selectDate, m_year, m_mounth, m_day), m_year, m_mounth,
				m_day);
		datedialog.setTitle(R.string.addActivity_selectDateDialog_title);
		datedialog.show();
	}

	public void selectPersoDialogn() {

		cursor = new MyDBHelper(this).getWritableDatabase().query(Constant.TB_PERSONMANAGE, null, null, null, null,
				null, null);
		// personSelectState = new boolean[cursor.getCount()];
		personDisplayData(cursor);
	}

	/**
	 * @param cursor
	 *            包含人员记录的数据库Cursor结果集
	 */
	private void personDisplayData(Cursor cursor) {

		// 此处创建添加人员对话框的时候初始化两个数组
		// 后面的显示数据会调用这两个数组
		personDataList = getPersonDBList(cursor);
		personSelectState = new boolean[cursor.getCount()];

		new AlertDialog.Builder(ChangeDataActivity.this).setTitle(R.string.addActivity_diapalyPersonDataDialog_title)
				.setIcon(android.R.drawable.btn_dialog)
				.setMultiChoiceItems(personDataList, personSelectState,
						new AddactivityPersonMultiChoiceListener(personSelectState))
				.setPositiveButton(R.string.general_dialog_ok, new ChangeDataActivitySelectPersonDialogListener(this))
				.setNegativeButton(R.string.addActivity_personNoDataDialog_new,
						new ChangeDataActivitySelectPersonDialogListener(this))
				.show().setCanceledOnTouchOutside(true);

	}

	/**
	 * 获得人员管理联系人数组的方法
	 * 
	 * @param cursor
	 *            包含联系人姓名信息的cursor对象
	 * @return 包含联系人信息的String数组
	 */
	private String[] getPersonDBList(Cursor cursor) {
		// 根据获取的联系人cursor创建 联系人列表数组 传入dialog第一个参数
		ArrayList<String> arrayList = new ArrayList<String>(cursor.getCount());

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			arrayList.add(cursor.getString(cursor.getColumnIndex(Constant._NAME)));

		}
		// 把最后获取的含有联系人姓名在arrayList转换成数组 传入第一个参数

		return arrayList.toArray(new String[cursor.getCount()]);
	}

	public void displayPerson() {

		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < cursor.getCount(); i++) {
			if (personSelectState[i]) {
				arrayList.add(personDataList[i]);
			}
		}

		if (0 == arrayList.size()) {
			tv_selectPerson.setText("");

		} else {

			tv_selectPerson.setText(arrayList.toString());
		}
	}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 验证请求码与结果码
		if (1 == requestCode && resultCode == 188) {

			// 转换字节数组到位图 并显示
			iv_photo.setImageBitmap(FormatFactory.getBitmapByByteArray((byte[]) data.getExtras().get("data")));
			// 清空之前的备注栏内容
			et_remark.setText("");
			// 隐藏备注栏
			et_remark.setVisibility(View.GONE);
			// 显示图片
			iv_photo.setVisibility(View.VISIBLE);

		}

	}

	public void amendData() {

		// 判断必填内容不能为空
		if (!(et_name.getText().toString().equals("") || et_money.getText().toString().equals("")
				|| tv_selectPerson.getText().toString().equals(""))) {

			SQLiteDatabase myDB = new MyDBHelper(this).getWritableDatabase();
			ContentValues cv = new ContentValues();
			// 要插入的字段名与值
			cv.put(Constant._NAME, et_name.getText().toString());
			cv.put(Constant._DATE, tv_selectDate.getText().toString());
			cv.put(Constant._SORT, sp_sort.getSelectedItemPosition());
			cv.put(Constant._MODE, sp_mode.getSelectedItemPosition());
			cv.put(Constant._MONEY, et_money.getText().toString());
			cv.put(Constant._PAYORORINCOME, getRadioButtonState());
			cv.put(Constant._PERSON, tv_selectPerson.getText().toString());
			cv.put(Constant._REMARK, et_remark.getText().toString());
			cv.put(Constant._PHOTO, getByteArray());

			myDB.update(Constant.TB_NAME, cv, Constant._ID + " = ? ", new String[] { String.valueOf(id) });
			// 保存后销毁当前对话框
			finish();
			Tools.showMsg(this, R.string.addActivity_toast_saveSuccess);
		} else {
			new AlertDialog.Builder(this).setTitle(R.string.general_dialog_warning)
					.setIcon(android.R.drawable.ic_dialog_info).setMessage(R.string.addActivity_toast_saveFail)
					.setPositiveButton(R.string.general_dialog_ok, null).show();
		}

	}

	// 获取备注栏图片字节数组的方法
	private byte[] getByteArray() {

		// 打开绘图缓冲区 否则 无法从ImageView对象中获取图像
		iv_photo.setDrawingCacheEnabled(true);

		if (iv_photo.getDrawingCache() != null) {
			// 拿到图片的背景中的bitmap对象 转换成字节数组
			byte[] data = FormatFactory.getByteArrayByBitmap(iv_photo.getDrawingCache());

			// 以清空画图缓冲区 否则 下一次从ImageView对象中获取的图像 还是原来的图像
			iv_photo.setDrawingCacheEnabled(false);
			return data;

		}
		// 以清空画图缓冲区 否则 下一次从ImageView对象中获取的图像 还是原来的图像
		iv_photo.setDrawingCacheEnabled(false);
		return null;
	}

	// 获取单选按钮的文本信息
	private int getRadioButtonState() {

		if (rb_pay.isChecked()) {
			return 0;
		}
		return 1;
	}

	 

}
