package com.soa.add_activity;

import com.soa.camera_activity.CameraActivity;
import com.soa.note.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 添加界面的单击事件监听器
 * 
 * @author GuoDong
 *
 */
public class AddActivityButtonListener implements OnClickListener {

	// 声明添加界面的对象
	private Context context;

	public AddActivityButtonListener(Context context) {
		// 传入添加界面的对象并且实例化
		this.context = context;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.addActivity_tv_selectPerson:
			// 执行人员选择方法
			((AddActivity) context).createSelectPersoDialogn();
			break;

		case R.id.addActivity_btn_camera:
			// 启动拍照界面
			startCamera();
			break;
		case R.id.addActivity_btn_save:
			// 执行保存按钮的方法
			((AddActivity) context).saveToDB();
			break;
		case R.id.addActivity_tv_selectDate:
			// 执行日期选择方法
			((AddActivity) context).setDate();
			break;
		case R.id.addActivity_btn_back:
			// 执行返回按钮操作
			((AddActivity) context).finish();
			break;
		}
	}

	/**
	 * 启动相机的界面
	 */
	private void startCamera() {

		Intent intent = new Intent(context, CameraActivity.class);
		((AddActivity) context).startActivityForResult(intent, 1);
	}

}
