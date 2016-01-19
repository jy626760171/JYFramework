package com.jy.framework.utils;

import android.content.Context;

/**
 * Created by jiangy on 2016/1/19.
 */
public class BaseUtils {
	protected static Context sContext = null;

	/**
	 * 必须在application的onCreate方法中初始化，并且只能初始化一次
	 * @param context 必须是application实例
	 */
	public static void init(Context context){
		if (sContext == null) sContext = context;
	}

}
