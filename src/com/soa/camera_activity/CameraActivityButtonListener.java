package com.soa.camera_activity;

import com.soa.note.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 相机界面的按钮单击事件监听器
 * 
 * @author GuoDong
 *
 */
public class CameraActivityButtonListener implements OnClickListener {

	// 声明一个上下文对象
	private Context context;

	/**
	 * 相机界面单击事件监听器的构造方法
	 * 
	 * @param context
	 *            当前类的实例
	 */
	public CameraActivityButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cameraActivity_btn_photograph:

			// 调用相机界面的拍照方法 并带值返回
			((CameraActivity) context).photoGraph();
			break;

		case R.id.cameraActivity_btn_back:

			// 直接关闭当前界面
			((CameraActivity) context).finish();
			break;

		}

	}

}
