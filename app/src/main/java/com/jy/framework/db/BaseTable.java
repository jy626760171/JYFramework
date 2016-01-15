package com.jy.framework.db;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

/**
 * Created by jiangy on 2016/1/14.
 */
public abstract class BaseTable {

	private static final String CREATESENTENCE = "CREATE TABLE IF NOT EXISTS ";
	private static final String FIRSTBRACKETS = "(";
	private static final String LASTBRACKETS = ")";
	private static final String BLANK = " ";
	private static final String DIVIDER = "_";
	private static final String COMMA = ",";
	private static final String _ID = "_id";
	private static final String PRIMARYKEY = " INTEGER PRIMARY KEY" + COMMA;
	private static final String DROPSENTENCE = "DROP TABLE IF EXISTS ";

	protected BaseColumn mColumn;

	/**
	 * 实现BaseColumn的类的属性值必须是这种格式（字段名_类型）
	 */
	public interface BaseColumn {
	}

	public abstract String getTableName();

	/**
	 * 必须在构造方法中调用
	 */
	public abstract void initBaseColumn();

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(getCreateSql());
	}

	protected final String getCreateSql() {
		try {
			Field[] fields = mColumn.getClass().getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			sb.append(CREATESENTENCE);
			sb.append(getTableName());
			sb.append(FIRSTBRACKETS);
			sb.append(_ID);
			sb.append(PRIMARYKEY);
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String value = (String) field.get(mColumn);
				int index = value.indexOf(DIVIDER);
				String columnName = value.substring(0, index);
				String typeName = value.substring(index + 1);
				sb.append(columnName);
				sb.append(BLANK);
				sb.append(typeName);
				if (i < fields.length - 1) sb.append(COMMA);
			}
			sb.append(LASTBRACKETS);
			return sb.toString();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected final String getDelSql() {
		StringBuffer sb = new StringBuffer();
		sb.append(DROPSENTENCE);
		sb.append(getTableName());
		return sb.toString();
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
