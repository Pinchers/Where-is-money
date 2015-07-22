package com.soa.changedata_activity;

import com.soa.camera_activity.CameraActivity;
import com.soa.note.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 修改界面的单击事件监听器
 * 
 * @author GuoDong
 *
 */
public class ChangeDataActivityButtonListener implements OnClickListener {

	// 声明修改界面对象
	private Context context;

	// 传入修改界面实例 实例化之前声明的对象
	public ChangeDataActivityButtonListener(Context context) {

		this.context = context;

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.changeBillActivity_btn_back:

			// 返回上一页
			((ChangeDataActivity) context).finish();
			break;

		case R.id.changeBillActivity_tv_selectPerson:
			// 执行人员选择方法
			((ChangeDataActivity) context).selectPersoDialogn();
			break;
		case R.id.changeBillActivity_btn_camera:
			// 启动拍照界面
			startCamera();
			break;
		case R.id.changeBillActivity_btn_save:
			// 执行修改按钮的方法
			((ChangeDataActivity) context).amendData();
			break;
		 
		case R.id.changeBillActivity_tv_selectDate:
			// 执行日期选择方法
			((ChangeDataActivity) context).setDate();
			break;

		}

	}

	/**
	 * 启动相机的界面
	 */
	private void startCamera() {

		Intent intent = new Intent(context, CameraActivity.class);
		((ChangeDataActivity) context).startActivityForResult(intent, 1);
	}

}
