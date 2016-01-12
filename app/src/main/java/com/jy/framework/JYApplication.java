package com.jy.framework;

import android.app.Application;

public class JYApplication extends Application {

	private static JYApplication sInstance = null;

	public static JYApplication getInstance() {
		return sInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		// 设置证书
		// OKHttpClientManager.getInstance().setCertificate();
		// OKHttpClientManager.getInstance().getOKHttpClient().setConnectTimeout(GlobalConstants.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS);
	}
}
