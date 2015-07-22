package com.soa.system_activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.soa.util.Constant;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

/**
 * 执行备份恢复操作的异步任务类
 * 
 * @author GuoDong
 *
 */
public class BackupTask extends AsyncTask<String, Void, Integer> {

	// 声明一个上下文对象
	private Context context;
	// 声明一个内部接口对象
	private CompletionListener completionListener;

	// 公开一个内部接口 负责异步任务与主线程之间的通信
	public interface CompletionListener {
		void onBackupComplete();

		void onRestoreComplete();

		void onError(int errorCode);
	}

	/**
	 * 设置当前的listener接口
	 * 
	 * @param listener
	 */
	public void setCompletionListener(CompletionListener listener) {
		completionListener = listener;
	}

	/**
	 * 构造方法 实例化上下文对象
	 * 
	 * @param context
	 */
	public BackupTask(Context mContext) {
		context = mContext;
	}

	@Override
	protected Integer doInBackground(String... params) {

		// 得到当前数据库的所在路径
		File dbFile = context.getDatabasePath(Constant.DB_NAME);
		// 创建备份数据库文件的所在路径
		File exportDir = new File(Environment.getExternalStorageDirectory(), "myAppBackup");
		// 如果备份路径不存在 则创建备份路径
		if (!exportDir.exists()) {
			exportDir.mkdirs();
		}

		// 声明备份数据库的路径名与文件
		File backup = new File(exportDir, dbFile.getName());
		String command = params[0];

		// 如果收到备份数据库命令 执行备份操作
		if (command.equals(Constant.COMMAND_BACKUP)) {
			try {
				// 创建备份数据库的路径名与文件
				backup.createNewFile();
				// 执行复制文件命令
				fileCopy(dbFile, backup);
				// 返回数据库备份成功参数
				return Constant.BACKUP_SUCCESS;
			} catch (IOException e) {
				// 如有异常则返回数据库备份执行失败参数
				return Constant.BACKUP_ERROR;
			}
		}
		// 如果收到恢复数据的命令 执行恢复数据操作
		else if (command.equals(Constant.COMMAND_RESTORE)) {
			try {
				// 如果之前没有备份过数据
				if (!backup.exists()) {
					// 返回恢复文件未找到错误
					return Constant.RESTORE_NOFILEERROR;
				}
				// 创建恢复数据的文件 其实就是覆盖原数据库文件
				dbFile.createNewFile();
				// 执行复制文件命令
				fileCopy(backup, dbFile);
				// 返回数据库恢复成功参数
				return Constant.RESTORE_SUCCESS;
			} catch (IOException e) {
				// 有异常 返回数据库错误参数
				return Constant.BACKUP_ERROR;
			}
		} else {
			// 如果既不是备份数据库命令又不是恢复数据库命令 则返回数据库错误参数
			return Constant.BACKUP_ERROR;
		}
	}

	/**
	 * 当后台操作结束时 此方法将会被调用 计算结果将做为参数传递到此方法中 用来向主UI发送消息
	 */
	@Override
	protected void onPostExecute(Integer result) {

		switch (result) {
		case Constant.BACKUP_SUCCESS:
			if (completionListener != null) {
				completionListener.onBackupComplete();
			}
			break;
		case Constant.RESTORE_SUCCESS:
			if (completionListener != null) {
				completionListener.onRestoreComplete();
			}
			break;
		case Constant.RESTORE_NOFILEERROR:
			if (completionListener != null) {
				completionListener.onError(Constant.RESTORE_NOFILEERROR);
			}
			break;
		default:
			if (completionListener != null) {
				completionListener.onError(Constant.BACKUP_ERROR);
			}
		}
	}

	/**
	 * 复制文件的方法
	 * 
	 * @param source
	 *            从哪来
	 * @param dest
	 *            到哪去
	 */
	private void fileCopy(File source, File dest) {
		// 声明两个文件管道对象 一个输入 一个输出
		FileChannel inChannel = null, outChannel = null;

		try {
			// 从源文件得到输入管道
			inChannel = new FileInputStream(source).getChannel();
			// 从目标文件得到输出管道
			outChannel = new FileOutputStream(dest).getChannel();
			try {
				// 把输入管道的数据写到输出管道
				inChannel.transferTo(0, inChannel.size(), outChannel);
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {

			// 最终要关闭两个输入输出管道流
			if (inChannel != null)
				try {
					inChannel.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			if (outChannel != null)
				try {
					outChannel.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
	}

}
