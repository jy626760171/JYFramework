package com.jy.framework.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络相关工具类
 * 
 */
public class NetworkUtils {

	/**
	 * Returns whether the network is available
	 * 
	 * @param context
	 *            Context
	 * @return 网络是否可用
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isNetworkAvailable(Context context) {
		int type = getNetworkType(context);
		return type > -1;
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 *            Context
	 * @return 网络类型
	 * @see [类、类#方法、类#成员]
	 */
	public static int getNetworkType(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null && info.length != 0) {
				for (int i = 0; i < info.length; i++) {
					if (info[i] == null) continue;
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return info[i].getType();
					}
				}
			}
		}

		return -1;
	}

	/**
	 * 判断网络是不是手机网络，非wifi
	 * 
	 * @param context
	 *            Context
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isMobileNetwork(Context context) {
		return ConnectivityManager.TYPE_MOBILE == getNetworkType(context);
	}

	/**
	 * 判断网络是不是wifi
	 * 
	 * @param context
	 *            Context
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isWifiNetwork(Context context) {
		return ConnectivityManager.TYPE_WIFI == getNetworkType(context);
	}

	/**
	 * Returns whether the network is roaming
	 * 
	 * @param context
	 *            Context
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isNetworkRoaming(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) return false;
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (telephonyManager.isNetworkRoaming()) return true;
		}
		return false;
	}

	/** 判断端口是否被占用 */
	public static boolean isPortUsed(int port) {
		String[] cmds = { "netstat", "-an" };

		Process process = null;
		InputStream is = null;
		DataInputStream dis = null;
		String line = "";
		Runtime runtime = Runtime.getRuntime();
		try {
			process = runtime.exec(cmds);
			is = process.getInputStream();
			dis = new DataInputStream(is);
			while ((line = dis.readLine()) != null) {
				if (line.contains(":" + port)) {
					return true;
				}
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			try {
				if (dis != null) {
					dis.close();
				}
				if (is != null) {
					is.close();
				}
				if (process != null) {
					process.destroy();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
