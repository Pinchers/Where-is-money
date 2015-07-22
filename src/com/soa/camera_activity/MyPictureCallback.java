package com.soa.camera_activity;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
/**
 * 相机拍照的回调方法
 * @author GuoDong
 *
 */
public class MyPictureCallback implements PictureCallback {

	private Bundle bundle;
	
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		
		bundle = new Bundle();
		// 把图片字节放到Bundle对象中
		bundle.putByteArray("data", data);
		// 拍照完成后重新开启预览
//		camera.startPreview();
	}

}
