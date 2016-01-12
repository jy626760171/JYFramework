package com.jy.framework.constant;

import com.jy.framework.JYApplication;

public class GlobalConstant {

	public static final boolean DEBUG = true;
	// 设置网络请求超时时间为1分之
	public static final int NETWORK_CONNECT_TIMEOUT = 1 * 60 * 1000;
	/**
	 * 网络请求里面，set-cookie字段
	 */
	public static final String HTTP_GET_HEADER_SET_COOKIE = "Set-Cookie";

	public static final String HTTP_POST_HEADER_COOKIE = "Cookie";

	public static final String PHP_SESSID_PREFIX = "PHPSESSID";

	private static final String INTENT_ACTION_BASE = JYApplication.getInstance().getPackageName() + ".";

}
