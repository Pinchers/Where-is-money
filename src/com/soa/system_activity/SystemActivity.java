package com.soa.system_activity;

import java.util.Random;

import com.soa.note.R;
import com.soa.util.Constant;
import com.soa.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class SystemActivity extends Activity implements BackupTask.CompletionListener {

	private Button btn_back, btn_backupAndRestore, btn_help, btn_feedback, btn_about;
	private AlertDialog backupAndRestoreDialog;
	private Message message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_activity);

		findView();

		init();

	}

	/**
	 * 关联组件
	 */
	private void findView() {
		btn_back = (Button) findViewById(R.id.systemActivity_btn_back);
		btn_backupAndRestore = (Button) findViewById(R.id.systemActivity_btn_backupAndRestore);
		btn_help = (Button) findViewById(R.id.systemActivity_btn_help);
		btn_feedback = (Button) findViewById(R.id.systemActivity_btn_feedback);
		btn_about = (Button) findViewById(R.id.systemActivity_btn_about);
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		btn_back.setOnClickListener(new SystemActivityButtonListener(this));
		btn_backupAndRestore.setOnClickListener(new SystemActivityButtonListener(this));
		btn_help.setOnClickListener(new SystemActivityButtonListener(this));
		btn_feedback.setOnClickListener(new SystemActivityButtonListener(this));
		btn_about.setOnClickListener(new SystemActivityButtonListener(this));
	}

	// 创建选择备份还是还原的对话框
	public void creatBackupAndRestoreDialog() {

		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			creatNotFindSDCardDialog();
		}

		// 实例化Message对象
		message = new Message();
		message.arg1 = 0;

		View view = getLayoutInflater().inflate(R.layout.systemactivity_dialog_backupandrestore, null);

		view.findViewById(R.id.systemActivity_btn_backupToSDCard)
				.setOnClickListener(new SystemActivityButtonListener(this));
		view.findViewById(R.id.systemActivity_btn_restoreFromSDCard)
				.setOnClickListener(new SystemActivityButtonListener(this));

		backupAndRestoreDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.systemActivity_backupAndRestoreDialog_title).setView(view).show();

		backupAndRestoreDialog.setCanceledOnTouchOutside(true);

	}

	/**
	 * 当检测不到SD卡的时候弹出此对话框
	 */
	private void creatNotFindSDCardDialog() {

		// 销毁选择备份还是还原的对话框
		backupAndRestoreDialog.dismiss();

		// 创建找不到SD卡的警告对话框
		new AlertDialog.Builder(this).setTitle(R.string.general_dialog_warning)
				.setMessage(R.string.systemActivity_notFindSDCardDialog_title)
				.setPositiveButton(R.string.general_dialog_ok, null).show().setCanceledOnTouchOutside(true);

	}

	/**
	 * 创建备份数据的进度条
	 */
	private void creatBackupProgressDialog() {

		// 销毁选择备份还是还原的对话框
		backupAndRestoreDialog.dismiss();

		final ProgressDialog progressDialog = new ProgressDialog(this);
		// 设置进度条为水平进度条
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setTitle(R.string.systemActivity_backupProgressDialog_title);
		// 设置进度最大值为100
		progressDialog.setMax(100);
		// 禁止对话框按back键销毁
		progressDialog.setCancelable(false);
		// 禁止点击别的位置取消对话框
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		// 进度条由线程控制
		new Thread() {

			Random random = new Random();
			int count = 0;

			@Override
			public void run() {
				try {
					// 执行之前进度先清零
					while (count <= 100) {
						progressDialog.setProgress(count++);
						// 每1-50毫秒走1%
						Thread.sleep(random.nextInt(50));
					}
					// 执行完进度后销毁此对话框
					progressDialog.dismiss();
					// 通知主线程已经备份成功 弹出消息提示
					handler.sendMessage(message);
				} catch (InterruptedException e) {
					// 遇到异常销毁当前对话框
					progressDialog.dismiss();
					handler.sendMessage(message);

				}
			}
		}.start();

	}

	/**
	 * 创建恢复数据的进度条
	 */
	private void creatRestoreProgressDialog() {

		// 如果没有找到备份的数据文件 则直接返回
		if (!Constant.IS_RESTORE) {
			return;
		}

		// 销毁选择备份还是还原的对话框
		backupAndRestoreDialog.dismiss();

		final ProgressDialog progressDialog = new ProgressDialog(this);
		// 设置进度条为水平进度条
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setTitle(R.string.systemActivity_restoreProgressDialog_title);
		// 设置进度最大值为100
		progressDialog.setMax(100);
		// 禁止对话框按back键销毁
		progressDialog.setCancelable(false);
		// 禁止点击别的位置取消对话框
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		// 进度条由线程控制
		new Thread() {

			Random random = new Random();
			int count = 0;

			@Override
			public void run() {
				try {
					// 执行之前进度先清零
					while (count <= 100) {
						progressDialog.setProgress(count++);
						// 每随机1-50毫秒走1%
						Thread.sleep(random.nextInt(50));
					}
					// 执行完进度后销毁此对话框
					progressDialog.dismiss();
					// 通知主线程已经恢复成功 弹出消息提示
					handler.sendMessage(message);
				} catch (InterruptedException e) {
					// 遇到异常销毁当前对话框
					progressDialog.dismiss();
					handler.sendMessage(message);

				}
			}

		}.start();

	}

	// 创建一个Handler对象
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.arg1 == Constant.BACKUP_SUCCESS) {

				Tools.showMsg(SystemActivity.this, R.string.systemActivity_toast_backupSuccess);
			} else if (msg.arg1 == Constant.RESTORE_SUCCESS) {

				Tools.showMsg(SystemActivity.this, R.string.systemActivity_toast_restoreSuccess);
			} else if (0 == msg.arg1) {

				Tools.showMsg(SystemActivity.this, R.string.systemActivity_toast_exception);
			}

		}
	};

	/**
	 * 执行备份数据库的命令
	 */
	public void dataBackup() {

		// 弹出备份数据库执行进度条的对话框
		creatBackupProgressDialog();

		// 创建异步备份数据库对象
		BackupTask backupTask = new BackupTask(this);
		// 设置当前Activity为BackupTask内部接口的实现类
		backupTask.setCompletionListener(this);
		// 执行备份数据库的命令
		backupTask.execute(Constant.COMMAND_BACKUP);

	}

	/**
	 * 执行数据库恢复命令
	 */
	public void dataRecover() {

		// 弹出恢复数据库执行进度条的对话框
		creatRestoreProgressDialog();

		// 创建异步备份数据库对象
		BackupTask backupTask = new BackupTask(this);
		// 设置当前Activity为BackupTask内部接口的实现类
		backupTask.setCompletionListener(this);
		// 执行恢复数据库的命令
		backupTask.execute(Constant.COMMAND_RESTORE);

	}

	@Override
	public void onBackupComplete() {

		// 备份成功 把标记设为备份成功
		message.arg1 = Constant.BACKUP_SUCCESS;
		Constant.IS_RESTORE = true;
	}

	@Override
	public void onRestoreComplete() {

		// 恢复成功 把标记设为恢复成功
		message.arg1 = Constant.RESTORE_SUCCESS;
	}

	@Override
	public void onError(int errorCode) {

		if (errorCode == Constant.RESTORE_NOFILEERROR) {

			Tools.showMsg(this, R.string.systemActivity_toast_notFindBackup);
		} else {
			Tools.showMsg(this, R.string.systemActivity_toast_unknownError);
		}
	}

	/**
	 * 创建帮助信息对话框
	 */
	public void creatHelpInfoDialog() {

		new AlertDialog.Builder(this).setTitle(R.string.systemActivity_helpInfoDialog_title)
				.setView(getLayoutInflater().inflate(R.layout.systemactivity_dialog_help, null))
				.setPositiveButton(R.string.general_dialog_ok, null).show().setCanceledOnTouchOutside(true);

	}

	/**
	 * 创建意见反馈对话框
	 */
	public void createFeedbackDialog() {

		View view = getLayoutInflater().inflate(R.layout.systemactivity_dialog_feedback, null);
		view.findViewById(R.id.systemActivity_btn_email).setOnClickListener(new SystemActivityButtonListener(this));
		view.findViewById(R.id.systemActivity_btn_openHomepage)
				.setOnClickListener(new SystemActivityButtonListener(this));

		new AlertDialog.Builder(this).setTitle(R.string.systemActivity_feedbackDialog_title).setView(view).show()
				.setCanceledOnTouchOutside(true);
	}

	/**
	 * 创建关于软件对话框
	 */
	public void creatAboutAPP() {

		new AlertDialog.Builder(this).setTitle(R.string.systemActivity_aboutAPPDialog_title)
				.setView(getLayoutInflater().inflate(R.layout.systemactivity_dialog_about, null))
				.setPositiveButton(R.string.general_dialog_ok, null).show().setCanceledOnTouchOutside(true);

	}

}
