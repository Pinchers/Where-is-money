package com.soa.welcome_activity;

import java.util.ArrayList;

import com.soa.main_activity.MainActivity;
import com.soa.note.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 引导界面activity类
 * 
 * @author yangyu
 */
public class GuideActivity extends Activity   {
	// 定义ViewPager对象
	private ViewPager viewPager;

	// 定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;

	// 定义一个ArrayList来存放View
	private ArrayList<View> views;

	// 定义各个界面View对象
	private View view1, view2, view3;

	// 定义开始按钮对象
	private Button startBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		findView();
		
		init();
	}

	/**
	 * 关联组件组件
	 */
	private void findView() {

		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 实例化各个界面的布局对象
		LayoutInflater mLi = LayoutInflater.from(this);
		view1 = mLi.inflate(R.layout.guideactivity_view01, null);
		view2 = mLi.inflate(R.layout.guideactivity_view02, null);
		view3 = mLi.inflate(R.layout.guideactivity_view03, null);
		// 实例化开始按钮
		startBt = (Button) view3.findViewById(R.id.guideActivity_btn_enter);

	}

	/**
	 * 初始化数据
	 */
	private void init() {

		// 实例化ArrayList对象
		views = new ArrayList<View>();
		// 将要分页显示的View装入数组中
		views.add(view1);
		views.add(view2);
		views.add(view3);
		// 实例化ViewPager适配器
		vpAdapter = new ViewPagerAdapter(views);

		// 设置适配器数据
		viewPager.setAdapter(vpAdapter);

		// 给开始按钮设置监听
		startBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				entertbutton();
			}
		});
	}

	/**
	 * 进入按钮点击事件
	 */
	private void entertbutton() {

		// 创建intent对象
		Intent intent = new Intent();
		// 跳转主界面
		intent.setClass(GuideActivity.this, MainActivity.class);
		startActivity(intent);
		// 销毁当前界面
		this.finish();
	}
}
