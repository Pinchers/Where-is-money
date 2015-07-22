package com.soa.welcome_activity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.soa.main_activity.MainActivity;
import com.soa.note.R;
import com.soa.util.Tools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE); 隐藏系统标题栏 只适用于单个Activity
		// 设置隐藏系统状态栏 一定要放在setContentView之前执行
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome_activity);

		// 创建一个随机生成延迟时间的类
		Random random = new Random();
		// 创建一个timer定时器
		Timer timer = new Timer();
		// 创建一个定时任务
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {

				SharedPreferences sharedPreferences = getSharedPreferences("isFirstUse", MODE_PRIVATE);

				if (sharedPreferences.getBoolean("isFirstUse", true)) {

					// 实例化Editor对象
					Editor editor = sharedPreferences.edit();
					// 存入数据
					editor.putBoolean("isFirstUse", false);
					// 提交修改
					editor.commit();
					// 跳转到引导界面
					Tools.skip(WelcomeActivity.this, GuideActivity.class);
					// 销毁掉当前界面
					finish();

				} else {

					// 跳转到主界面
					Tools.skip(WelcomeActivity.this, MainActivity.class);
					// 销毁掉当前界面
					finish();

				}

			}
		};
		// 延迟0-3秒启动
		timer.schedule(timerTask, random.nextInt(3000));

	}

}
