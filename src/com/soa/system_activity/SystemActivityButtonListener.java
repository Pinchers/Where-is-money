package com.soa.system_activity;

import com.soa.note.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 系统设置界面的按钮单击事件监听器
 * 
 * @author GuoDong
 *
 */
public class SystemActivityButtonListener implements OnClickListener {

	private Context context;

	/**
	 * 构造方法 初始化上下文对象
	 * 
	 * @param context
	 *            当前的Activity
	 */
	public SystemActivityButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		// 单击返回按钮执行的操作
		case R.id.systemActivity_btn_back:
			((SystemActivity) context).finish();
			break;

		// 单击备份与还原按钮执行的操作
		case R.id.systemActivity_btn_backupAndRestore:

			((SystemActivity) context).creatBackupAndRestoreDialog();
			break;

		// 单击帮助信息按钮执行的操作
		case R.id.systemActivity_btn_help:

			((SystemActivity) context).creatHelpInfoDialog();
			break;

		// 单击意见反馈按钮执行的操作
		case R.id.systemActivity_btn_feedback:

			((SystemActivity) context).createFeedbackDialog();
			break;

		// 单击关于软件按钮执行的操作
		case R.id.systemActivity_btn_about:

			((SystemActivity) context).creatAboutAPP();
			break;

		// 单击备份数据到SD卡按钮执行的操作
		case R.id.systemActivity_btn_backupToSDCard:

			((SystemActivity) context).dataBackup();
			break;

		// 单击从SD卡恢复数据按钮执行的操作
		case R.id.systemActivity_btn_restoreFromSDCard:

			((SystemActivity) context).dataRecover();
			break;

		// 单击发送邮件按钮执行的操作
		case R.id.systemActivity_btn_email:

			sendEmail();
			break;

		// 单击打开主页后执行的操作
		case R.id.systemActivity_btn_openHomepage:

			openHomepage();
			break;
		}

	}

	/**
	 * 打开邮箱发送邮件的方法
	 */
	private void sendEmail() {

		Intent intent = new Intent(Intent.ACTION_SENDTO);
		// 设置邮件的收件人
		intent.setData(Uri.parse("mailto:guo727193176@qq.com"));
		// 给邮件设置标题
		intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.systemActivity_email_title));
		context.startActivity(intent);

	}

	/**
	 * 打开主页的方法
	 */
	private void openHomepage() {

		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sdlvtc.cn")));
	}

}
