package com.jy.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jiangy on 2016/1/14.
 */
public class DBHelper extends SQLiteOpenHelper {

	public final static String DBNAME = "jydb";

	public static final int DB_VERSION = 1;

	//用于承载需要创建的表对象
	private BaseTable[] tables = {};

	public DBHelper(Context context){
		super(context, DBNAME, null, DB_VERSION);
	}

	public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, DBNAME, factory, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (BaseTable table : tables) {
			try {
				table.onCreate(db);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (BaseTable table : tables) {
			table.onUpgrade(db, oldVersion, newVersion);
		}
	}
}
