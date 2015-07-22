package com.soa.util;

/**
 * 储存各种常量字段
 * 
 * @author GuoDong
 *
 */
public class Constant {

	// 定义数据库名称
	public static final String DB_NAME = "test.db";
	// 数据表名称
	public static final String TB_NAME = "table1";
	public static final String TB_PERSONMANAGE = "PersonManage";

	// 数据库版本号
	public static final int DB_VERSION = 1;
	// 数据表字段名
	public static final String _ID = "_id";
	public static final String _NAME = "name";
	public static final String _DATE = "calendar";
	public static final String _SORT = "sort";
	public static final String _MODE = "mode";
	public static final String _MONEY = "money";
	public static final String _PAYORORINCOME = "payOrIncome";
	public static final String _PERSON = "person";
	public static final String _REMARK = "remark";
	public static final String _PHOTO = "photo";

	/**
	 * 判断联系人是否被选中导入 的信息存储文件 防止重复导入
	 */
	public static final String CONTACTS_IS_SELECTED = "Contacts_is_selected";
	/**
	 * 联系人字段名
	 */
	public static final String CONTACTS_NAME = "Contacts_";

	/**
	 * 升序排列
	 */
	public static final String ORDER_BY_ASC = " asc ";
	/**
	 * 降序排列
	 */
	public static final String ORDER_BY_DESC = " desc ";

	/**
	 * 数据库备份命令
	 */
	public static final String COMMAND_BACKUP = "backupDatabase";
	/**
	 * 数据库还原命令
	 */
	public static final String COMMAND_RESTORE = "restoreDatabase";

	// 定义备份与还原的4种状态 备份成功 还原成功 备份出现错误 还原出现文件未找到错误
	/**
	 * 数据备份成功返回1
	 */
	public static final int BACKUP_SUCCESS = 1;
	/**
	 * 数据还原成功返回2
	 */
	public static final int RESTORE_SUCCESS = 2;
	/**
	 * 备份出现错误返回3
	 */
	public static final int BACKUP_ERROR = 3;
	/**
	 * 数据还原出现文件未找到错误返回4
	 */
	public static final int RESTORE_NOFILEERROR = 4;
	/**
	 * 此变量表示之前是否备份过
	 */
	public static boolean IS_RESTORE = false;

}
