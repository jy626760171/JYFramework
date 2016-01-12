package com.jy.framework.utils;

import android.text.TextUtils;
import android.util.Log;

import com.jy.framework.BuildConfig;

public class LogUtil {

	private static final boolean DEBUG = BuildConfig.DEBUG;

	private static final String TAG = "LogUtil";

	public static void e(String tag, String msg) {
		if (DEBUG) Log.e(getTag(tag), msg);
	}

	public static void e(String tag, String label, Object... args) {
		if (DEBUG) Log.e(getTag(tag), formatLable(label, args));
	}

	public static void i(String tag, String msg) {
		if (DEBUG) Log.i(getTag(tag), msg);
	}

	public static void i(String tag, String label, Object... args) {
		if (DEBUG) Log.i(getTag(tag), formatLable(label, args));
	}

	public static void v(String tag, String msg) {
		if (DEBUG) Log.v(getTag(tag), msg);
	}

	public static void v(String tag, String label, Object... args) {
		if (DEBUG) Log.v(getTag(tag), formatLable(label, args));
	}

	public static void d(String tag, String msg) {
		if (DEBUG) Log.d(getTag(tag), msg);
	}

	public static void d(String tag, String label, Object... args) {
		if (DEBUG) Log.d(getTag(tag), formatLable(label, args));
	}

	public static void w(String tag, String msg) {
		if (DEBUG) Log.w(getTag(tag), msg);
	}

	public static void w(String tag, String label, Object... args) {
		if (DEBUG) Log.w(getTag(tag), formatLable(label, args));
	}

	private static String getTag(String tag) {
		if (!TextUtils.isEmpty(tag)) return tag;
		return TAG;
	}

	private static String formatLable(String label, Object... args) {

		if (null != args && args.length > 0) {
			label = String.format(label, args);
		}

		return label;
	}
}
