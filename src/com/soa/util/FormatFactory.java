package com.soa.util;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.soa.note.R;

/**
 * 负责各种格式件的转换
 * 
 * @author GuoDong
 *
 */
public class FormatFactory {

	/**
	 * 获得格式化之后的日期显示形式
	 * 
	 * @param year
	 *            要格式化时期的年
	 * @param mouth
	 *            要格式化日期的月
	 * @param day
	 *            要格式化日期的日
	 * @return 格式化之后的日期形式 yyyy-mm-dd
	 */
	public static String getDateByFormat(int year, int mouth, int day) {

		String space_mouth = "", space_day = "";
		if (mouth < 10) {
			space_mouth = "0";
		}
		if (day < 10) {
			space_day = "0";
		}
		return year + "-" + space_mouth + mouth + "-" + space_day + day;

	}

	/**
	 * 根据传入的位置信息 得到分类对应的文本
	 * 
	 * @param activity
	 *            当前activity对象
	 * @param Index
	 *            对应下拉列表的下标
	 * @return 对应下标的文本信息
	 */
	public static String getSortStringByIndex(Context context, int index) {

		return context.getResources().getStringArray(R.array.sort)[index];

	}

	/**
	 * 根据传入的位置信息 得到方式对应的文本
	 * 
	 * @param activity
	 *            当前activity对象
	 * @param index
	 *            对应下拉列表的下标
	 * @return 对应下标的文本信息
	 */
	public static String getModeStringByIndex(Context context, int index) {

		return context.getResources().getStringArray(R.array.mode)[index];

	}

	/**
	 * 根据传入的位置信息 得到支出还是收入对应的文本
	 * 
	 * @param activity
	 *            当前activity对象
	 * @param index
	 *            对应单选按钮的状态
	 * @return 对应单选按钮的文本信息
	 */
	public static String getPayOrIncomeStringByIndex(Context context, int index) {

		if (0 == index) {
			return context.getResources().getString(R.string.add_rd_pay);
		}
		return context.getResources().getString(R.string.add_rd_income);
	}

	/**
	 * 根据传入的分类的文本信息匹配其原来在数组中的位置
	 * 
	 * @param context
	 *            当前的上下文对象
	 * @param sortString
	 *            需要匹配的文本
	 * @return 此文本原来在数组中的索引 匹配失败返回-1
	 */
	public static int getSortIndexByString(Context context, String sortString) {
		// 获取数组资源
		String[] a = context.getResources().getStringArray(R.array.sort);
		for (int i = 0; i < a.length; i++) {
			// 如果匹配成功 返回索引
			if (sortString.equals(a[i])) {
				return i;
			}
		}
		// 失败返回-1
		return -1;

	}

	/**
	 * 根据传入的方式的文本信息匹配其原来在数组中的位置
	 * 
	 * @param context
	 *            当前的上下文对象
	 * @param modeString
	 *            需要匹配的文本
	 * @return 此文本原来在数组中的索引 匹配失败返回-1
	 */
	public static int getModeIndexByString(Context context, String modeString) {
		// 获取数组资源
		String[] a = context.getResources().getStringArray(R.array.mode);
		for (int i = 0; i < a.length; i++) {
			// 如果匹配成功 返回索引
			if (modeString.equals(a[i])) {
				return i;
			}
		}
		// 失败返回-1
		return -1;
	}

	/**
	 * 根据传入的支出还是收入的文本信息 返回其原来的索引
	 * 
	 * @param context
	 *            当前的上下文对象
	 * @param payOrIncomeString
	 *            需要匹配的文本
	 * @return 返回此文本原来在的索引
	 */
	public static int getPayOrIncomeIndexByString(Context context,
			String payOrIncomeString) {

		if (payOrIncomeString.equals(context.getResources().getString(
				R.string.add_rd_pay))) {
			return 0;
		}

		return 1;

	}

	/**
	 * 把字节数组转换成位图
	 * 
	 * @param data
	 *            要转换的字节数组数据源
	 * @return 转化之后得到的位图
	 */
	public static Bitmap getBitmapByByteArray(byte[] data) {

		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	/**
	 * 把位图转换为字节数组
	 * 
	 * @param bitmap
	 *            要转换的位图数据源
	 * @return 转化之后得到的字节数组
	 */
	public static byte[] getByteArrayByBitmap(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

}
