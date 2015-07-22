package com.soa.personmanage_activity;

import com.soa.note.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonManageActivityViewHolder {

	public ImageView imageView;
	public TextView textView;

	public PersonManageActivityViewHolder(View view) {

		// 关联组件
		imageView = (ImageView) view.findViewById(R.id.personManageActivity_lv_itemImage);
		textView = (TextView) view.findViewById(R.id.personManageActivity_lv_itemName);
	}

}
