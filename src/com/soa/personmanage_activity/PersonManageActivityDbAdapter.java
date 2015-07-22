package com.soa.personmanage_activity;

import java.util.Random;

import com.soa.note.R;
import com.soa.util.Constant;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * 人员管理界面的数据库适配器
 * 
 * @author GuoDong
 *
 */
public class PersonManageActivityDbAdapter extends CursorAdapter {

	private Random random;
	private TypedArray typedArray;
	

	// 构造方法
	public PersonManageActivityDbAdapter(Context context, Cursor c) {
		super(context, c, true);
		random = new Random();
		typedArray = context.getResources().obtainTypedArray(R.array.person_icon);

		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		// 装载布局
		View view = LayoutInflater.from(context).inflate(R.layout.persronmanage_lv_item, null);
		PersonManageActivityViewHolder personManageActivityViewHolder = new PersonManageActivityViewHolder(view);
		// 设置Tag
		view.setTag(personManageActivityViewHolder);

		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		// 拿到Tag
		PersonManageActivityViewHolder personManageActivityViewHolder = (PersonManageActivityViewHolder) view.getTag();

		// 填充数据
		personManageActivityViewHolder.imageView.setBackgroundResource(typedArray.getResourceId(random.nextInt(7), 0));
		personManageActivityViewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(Constant._NAME)));

	}

}
