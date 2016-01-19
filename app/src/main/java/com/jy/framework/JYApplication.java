package com.jy.framework;

import android.app.Application;

import com.jy.framework.utils.AndroidOSUtils;
import com.jy.framework.utils.PreferenceUtils;
import com.jy.framework.utils.ToastUtils;

public class JYApplication extends Application {

	private static JYApplication sInstance = null;

	public static JYApplication getInstance() {
		return sInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		initUtils();
		// 设置证书
		// OKHttpClientManager.getInstance().setCertificate();
		// OKHttpClientManager.getInstance().getOKHttpClient().setConnectTimeout(GlobalConstants.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS);
	}

	private void initUtils(){
		AndroidOSUtils.init(sInstance);
		PreferenceUtils.init(sInstance);
		ToastUtils.init(sInstance);
	}

}
