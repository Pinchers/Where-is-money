package com.soa.camera_activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.soa.note.R;
import com.soa.util.Tools;

public class CameraActivity extends Activity {

	private View layout;
	private Button btn_photograph, btn_back;

	private Camera camera;
	private Camera.Parameters parameters;
	// bundle对象 用来存储数据
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_activity);

		findView();

		init();

	}

	/**
	 * 关联组件
	 */
	private void findView() {
		btn_photograph = (Button) findViewById(R.id.cameraActivity_btn_photograph);
		btn_back = (Button) findViewById(R.id.cameraActivity_btn_back);
		layout = findViewById(R.id.cameraActivity_layoutForButton);
		
	}

	/**
	 * 初始化组件设置
	 */
	private void init() {

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.cameraActivity_surFaceView);
		surfaceView.getHolder()
				.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 设置分辨率
		surfaceView.getHolder().setFixedSize(
				getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight());
		// 设置屏幕常亮
		surfaceView.getHolder().setKeepScreenOn(true);
		// 添加回调函数
		surfaceView.getHolder().addCallback(new SurfaceCallback());
		btn_photograph.setOnClickListener(new CameraActivityButtonListener(this));
		btn_back.setOnClickListener(new CameraActivityButtonListener(this));

	}

	/**
	 * 拍照按钮点击事件
	 * 
	 * @param v
	 *            按钮本身
	 */
	public void photoGraph() {

		// 判断相机不为空
		if (camera != null) {

			// 调用相机的拍照方法 第三个参数为一个callback回调接口
			// 回调接口负责获取所拍摄图片之后对图片进行一系列处理 例如 保存指定路径 填充bundle对象
			camera.takePicture(null, null, new MyPictureCallback());
		}
	}


	/**
	 * 实现拍照回调接口
	 * 
	 * @author admin
	 *
	 */
	private class MyPictureCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			//实例化一个Bundle对象
			bundle = new Bundle();
			// 把图片字节放到Bundle对象中 参数data就是拍照所获取照片的字节数组
			bundle.putByteArray("data", data);
			// 拍照完成后重新开启预览
//			camera.startPreview();
			
			//获得传递过来的intent对象
			Intent intent=getIntent();
			
			//对intent添加bundle
			intent.putExtras(bundle);
			//设置结果码
			setResult(188, intent);
			//关闭当前界面
			finish();
		}
	}

	/**
	 * 实现surfaceviewd回调接口
	 * 
	 * @author admin
	 *
	 */
	private class SurfaceCallback implements Callback {

		/**
		 * 开始拍照之前调用该方法 创建相机预览窗口
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				// 打开摄像头
				camera = Camera.open();
				// 设置设置用于拍照的holder对象
				camera.setPreviewDisplay(holder);
				// 设置显示方向 根据屏幕旋转判断
				camera.setDisplayOrientation(getPreviewDegree(CameraActivity.this));
				// 开启预览
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 开始拍照调用此方法 设置照片的一系列属性
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

			parameters = camera.getParameters();
			parameters.setPreviewFormat(PixelFormat.JPEG);
			parameters.setPreviewSize(width, height);
			// 设置预览图片每秒10-20帧刷新
			// 其实这个方法没啥作用 帧数是不可控的 = =
			parameters.setPreviewFpsRange(10, 20);
			// 设置照片质量
			parameters.setJpegQuality(100);

		}

		/**
		 * 停止拍照调用此方法
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

			// 如果相机不为空
			if (camera != null) {
				// 释放相机在资源 不在占用相机
				//否则其他程序调用会报错
				camera.release();
				// 把相机重新设为空值
				camera = null;
			}
		}
	}

	/**
	 * activity的触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		// 捕获状态为按下
		case MotionEvent.ACTION_DOWN:
			
			Tools.showMsg(this, R.string.cameraActivity_toast_click);
			// 把隐藏的按钮设为可见状态
			layout.setVisibility(View.VISIBLE);
			break;
			
		}
		// 返回true表示事件已经被处理 不会向下传递
		return true;
	}

	/**
	 * 拍照键的按键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		// 捕获按下拍照键
		case KeyEvent.KEYCODE_CAMERA:
			// 判断相机不为空
			if (camera != null && event.getRepeatCount() == 0) {
				// 调用相机拍照的方法
				camera.takePicture(null, null, new MyPictureCallback());
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// 根据手机的方向获取 预览画面的角度
	public static int getPreviewDegree(Activity activity) {

		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degree = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degree = 90;
			break;
		case Surface.ROTATION_90:
			degree = 0;
			break;
		case Surface.ROTATION_180:
			degree = 270;
			break;
		case Surface.ROTATION_270:
			degree = 180;
			break;
		}
		return degree;
	}

}
