/*
 * Copyright (C) 2008 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.jy.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jy.framework.JYApplication;

public class PreferenceUtils {

	/**
	 * 用户登录成功后，服务器端返回的cookie，用来验证用户是否已经登录了.
	 */
	public static final String KEY_USER_COOKIE = "key_user_cookie";

	/**
	 * 用户登录成功后， 保存的PHP sessid
	 */
	public static final String KEY_PHP_SESSID = "key_php_sessid";
	/**
	 * 用户信息
	 */
	public static final String KEY__USERINFO = "key_userinfo";

	private static final String PREFERENCES_NAME = "com.ymdai2345.preferences";

	private static SharedPreferences sharedPreferences;

	private static SharedPreferences getSharedPreferences() {
		if (sharedPreferences == null) {
			synchronized (PreferenceUtils.class) {
				if (sharedPreferences == null) {
					sharedPreferences = JYApplication.getInstance().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
				}
			}
		}
		return sharedPreferences;
	}

	/**
	 * 清除掉跟用户相关的信息
	 */
	public static void clearUserInfo() {
		setString(KEY_USER_COOKIE, "");
		setString(KEY_PHP_SESSID, "");
		setString(KEY__USERINFO, "");
	}

	private static void setData(String key, Object value) {
		final Editor ed = getSharedPreferences().edit();
		if (value == null) {
			ed.remove(key);
		} else if (value instanceof Integer) {
			ed.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			ed.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			ed.putFloat(key, (Float) value);
		} else if (value instanceof Boolean) {
			ed.putBoolean(key, (Boolean) value);
		} else if (value instanceof String) {
			ed.putString(key, (String) value);
		}
		ed.commit();
	}

	public static void setInt(String key, int value) {
		setData(key, value);
	}

	public static void setBoolean(String key, boolean value) {
		setData(key, value);
	}

	public static void setLong(String key, long value) {
		setData(key, value);
	}

	public static void setFloat(String key, float value) {
		setData(key, value);
	}

	public static void setString(String key, String value) {
		setData(key, value);
	}

	public static void setNullStr(String key) {
		setData(key, (String) null);
	}

	public static int getInt(String key, int defaultValue) {
		return getSharedPreferences().getInt(key, defaultValue);
	}

	public static long getLong(String key, long defaultValue) {
		return getSharedPreferences().getLong(key, defaultValue);
	}

	public static float getFloat(String key, float defaultValue) {
		return getSharedPreferences().getFloat(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return getSharedPreferences().getBoolean(key, defaultValue);
	}

	public static String getString(String key, String defaultValue) {
		return getSharedPreferences().getString(key, defaultValue);
	}

}
