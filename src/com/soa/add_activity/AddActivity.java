package com.soa.add_activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.soa.database.MyDBHelper;
import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.FormatFactory;
import com.soa.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class AddActivity extends Activity {

	private Button btn_back, btn_save, btn_camera;
	private ImageView iv_photo;
	private EditText et_name, et_money, et_remark;
	private TextView tv_selectDate, tv_addPerson;
	private Spinner sp_sort, sp_mode;
	private RadioButton rb_pay;
	private Cursor cursor;
	// 初始化系统时间 获得年月日
	private Calendar calendar = Calendar.getInstance();
	private int m_year = calendar.get(Calendar.YEAR);
	private int m_mounth = calendar.get(Calendar.MONTH);
	private int m_day = calendar.get(Calendar.DAY_OF_MONTH);
	// 人员多选对话框的数据源跟状态数组
	private String[] personDataList;
	private boolean[] personSelectState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);

		findView();

		init();

	}

	/**
	 * 
	 * 关联组件的方法
	 * 
	 */
	private void findView() {

		btn_back = (Button) findViewById(R.id.addActivity_btn_back);
		btn_save = (Button) findViewById(R.id.addActivity_btn_save);
		btn_camera = (Button) findViewById(R.id.addActivity_btn_camera);
		et_name = (EditText) findViewById(R.id.addActivity_et_name);
		et_money = (EditText) findViewById(R.id.addActivity_et_money);
		et_remark = (EditText) findViewById(R.id.addActivity_et_remark);
		iv_photo = (ImageView) findViewById(R.id.addActivity_iv_photo);
		tv_selectDate = (TextView) findViewById(R.id.addActivity_tv_selectDate);
		tv_addPerson = (TextView) findViewById(R.id.addActivity_tv_selectPerson);
		sp_sort = (Spinner) findViewById(R.id.addActivity_sp_sort);
		sp_mode = (Spinner) findViewById(R.id.addActivity_sp_mode);
		rb_pay = (RadioButton) findViewById(R.id.addActivity_rd_pay);
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void init() {

		btn_back.setOnClickListener(new AddActivityButtonListener(this));
		btn_camera.setOnClickListener(new AddActivityButtonListener(this));
		btn_save.setOnClickListener(new AddActivityButtonListener(this));
		tv_selectDate.setText(FormatFactory.getDateByFormat(m_year, m_mounth + 1, m_day));
		tv_selectDate.setOnClickListener(new AddActivityButtonListener(this));
		tv_addPerson.setOnClickListener(new AddActivityButtonListener(this));

		/*
		 * et_remark.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) {
		 * 
		 * if (null != s) { btn_clear.setVisibility(View.VISIBLE); }
		 * 
		 * if (null == s) {
		 * 
		 * btn_clear.setVisibility(View.GONE); }
		 * 
		 * }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) {
		 * 
		 * }
		 * 
		 * @Override public void afterTextChanged(Editable s) {
		 * 
		 * } });
		 */

	}

	public void createSelectPersoDialogn() {

		cursor = new MyDBHelper(this).getWritableDatabase().query(Constant.TB_PERSONMANAGE, null, null, null, null,
				null, null);
		if (cursor.getCount() > 0) {

			personDisplayData(cursor);

		} else {

			personNotDataDialog();

		}

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

		new AlertDialog.Builder(AddActivity.this).setTitle(R.string.addActivity_diapalyPersonDataDialog_title)
				.setIcon(android.R.drawable.btn_dialog)
				.setMultiChoiceItems(personDataList, personSelectState,
						new AddactivityPersonMultiChoiceListener(personSelectState))
				.setPositiveButton(R.string.general_dialog_ok, new AddActivitySelectPersonDialogListener(this))
				.setNegativeButton(R.string.addActivity_personNoDataDialog_new,
						new AddActivitySelectPersonDialogListener(this))
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

	// 对话框确认按钮调用此方法 把选择的人员显示出来
	public void displayPerson() {

		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < cursor.getCount(); i++) {
			if (personSelectState[i]) {
				arrayList.add(personDataList[i]);
			}
		}

		if (0 == arrayList.size()) {
			tv_addPerson.setText("");

		} else {

			tv_addPerson.setText(arrayList.toString());
		}

	}

	/**
	 * 如果person表没有数据 弹出需要添加第一条记录的对话框
	 */
	private void personNotDataDialog() {
		new AlertDialog.Builder(AddActivity.this).setTitle(R.string.addActivity_personNoDataDialog_title)
				.setMessage(R.string.addActivity_personNoDataDialog_message)
				.setPositiveButton(R.string.general_dialog_ok, null)
				.setNegativeButton(R.string.addActivity_personNoDataDialog_new,
						new AddActivitySelectPersonDialogListener(this))
				.show().setCanceledOnTouchOutside(true);
	}

	// 创建设置时间的方法
	public void setDate() {

		OnDateSetListener datelistener = new OnDateSetListener() {

			@Override
			// 创建datepickerdialog的回调函数
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				m_year = year;
				m_mounth = monthOfYear;
				m_day = dayOfMonth;
				// 设置完时间后更新edittext的显示日期
				tv_selectDate.setText(FormatFactory.getDateByFormat(m_year, m_mounth + 1, m_day));

			}
		};

		DatePickerDialog datedialog = new DatePickerDialog(AddActivity.this, datelistener, m_year, m_mounth, m_day);
		datedialog.setTitle(R.string.addActivity_selectDateDialog_title);
		datedialog.show();
	}

	/**
	 * 接收回传过来的Intent的方法
	 */
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

	public void saveToDB() {

		// 判断必填内容不能为空
		if (!(et_name.getText().toString().equals("") || et_money.getText().toString().equals("")
				|| tv_addPerson.getText().toString().equals(""))) {

			ContentValues cv = new ContentValues();
			// 要插入的字段名与值

			cv.put(Constant._NAME, et_name.getText().toString());
			cv.put(Constant._DATE, tv_selectDate.getText().toString());
			cv.put(Constant._SORT, sp_sort.getSelectedItemPosition());
			cv.put(Constant._MODE, sp_mode.getSelectedItemPosition());
			cv.put(Constant._MONEY, et_money.getText().toString());
			cv.put(Constant._PAYORORINCOME, getRadioButtonState());
			cv.put(Constant._PERSON, tv_addPerson.getText().toString());
			cv.put(Constant._REMARK, et_remark.getText().toString());
			cv.put(Constant._PHOTO, getByteArray());

			new MyDBHelper(this).getWritableDatabase().insert(Constant.TB_NAME, null, cv);
			Tools.showMsg(AddActivity.this, R.string.addActivity_toast_saveSuccess);
			// 信息保存成功后返回主界面
			finish();
		} else {

			// 弹出保存失败的对话框
			Tools.showMsg(this, R.string.addActivity_toast_saveFail);
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
