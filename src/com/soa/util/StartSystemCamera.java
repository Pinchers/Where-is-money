package com.soa.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.soa.note.R;

public class StartSystemCamera extends Activity {
	private static final int CAMERA_REQUEST = 1888;
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_IMAGE_GET_CONTENT = 2;
	Button take_Button, back_Button;
	ImageView imageView;
	File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		take_Button = (Button) findViewById(R.id.button1);
		back_Button = (Button) findViewById(R.id.button2);
		imageView = (ImageView) findViewById(R.id.imageView1);
		take_Button.setOnClickListener(new mClick());
		back_Button.setOnClickListener(new mClick());
	}

	class mClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == take_Button) {
				// 调用相机拍照
				capturePictureAndReturn();
			} else {
				//
				imageView.setImageResource(0);
			}
		}

	}

	// 调用相机拍照方法
	private void capturePictureAndReturn() {
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		file = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/DCIM/Camera/tu.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(Intent.createChooser(intent,
					getString(R.string.choose_app)), REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			imageView.setImageURI(Uri.fromFile(file));
		} else if (requestCode == REQUEST_IMAGE_GET_CONTENT
				&& resultCode == RESULT_OK) {
			imageView.setImageURI(data.getData());
		}
	}
}
