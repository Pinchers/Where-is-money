package com.soa.main_activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import com.soa.note.R;

public class MainActivity extends Activity {

	private Button btn_add, btn_billManage, btn_personManage,
			btn_statisticsManage, btn_system, btn_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		findView();

		init();

	}

	/**
	 * 初始化组件
	 */
	private void init() {
		btn_add.setOnClickListener(new MainActivityButtonListener(this));
		btn_billManage.setOnClickListener(new MainActivityButtonListener(this));
		btn_personManage
				.setOnClickListener(new MainActivityButtonListener(this));
		btn_statisticsManage.setOnClickListener(new MainActivityButtonListener(
				this));
		btn_system.setOnClickListener(new MainActivityButtonListener(this));
		btn_exit.setOnClickListener(new MainActivityButtonListener(this));
	}

	/**
	 * 关联组件
	 */
	private void findView() {
		btn_add = (Button) findViewById(R.id.mainActivity_btn_add);
		btn_billManage = (Button) findViewById(R.id.mainActivity_btn_billManage);
		btn_personManage = (Button) findViewById(R.id.mainActivity_btn_personManage);
		btn_statisticsManage = (Button) findViewById(R.id.mainActivity_btn_statisticsManage);
		btn_system = (Button) findViewById(R.id.mainActivity_btn_system);
		btn_exit = (Button) findViewById(R.id.mainActivity_btn_exit);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitDialog();
		}

		return true;
	}

	//退出时的确认对话框
	public void exitDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.mainActivity_exitDialog_title)
				.setMessage(R.string.mainActivity_exitDialog_message)
				
				.setPositiveButton(R.string.mainActivity_exitDialog_btnOk,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 关闭当前主界面
								finish();
							}
						}).setNegativeButton(R.string.mainActivity_exitDialog_btnCancel, null).show().setCanceledOnTouchOutside(true);
	}

}
