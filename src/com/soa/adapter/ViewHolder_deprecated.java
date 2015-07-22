package com.soa.adapter;

import android.util.SparseArray;
import android.view.View;
/**
 * 此类暂时被废弃  禁止使用
 * @author GuoDong
 *
 */
public class ViewHolder_deprecated {

	// 私有构造方法 不允许实例化此类实例
	private ViewHolder_deprecated() {
		super();
	}

	@SuppressWarnings("unchecked")
	public static <V extends View> V findView(View view, int viewId) {

		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(viewId);
		if (childView == null) {
			childView = view.findViewById(viewId);
			viewHolder.put(viewId, childView);
		}
		return (V) childView;

	}

}
