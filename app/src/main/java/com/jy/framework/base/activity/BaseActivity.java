package com.jy.framework.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jy.framework.utils.ToastUtils;

import java.util.ArrayList;

public abstract class BaseActivity extends MintsBaseActivity {

	private static final ArrayList<Activity> sActivityArray = new ArrayList<Activity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		sActivityArray.add(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			sActivityArray.remove(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private static void exitMemoryActivities() {
		Activity activity = null;
		while (!sActivityArray.isEmpty()) {
			activity = sActivityArray.remove(0);
			if (activity != null) {
				activity.finish();
			}
		}
	}

	protected void onExitApp() {
		exitMemoryActivities();
		ToastUtils.cancleToast();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		try {
			super.onSaveInstanceState(outState);
		} catch (Exception e) {
			outState = null;
		}
	}

//	private static final String TIPS_NETWORK_ERROR = "您的网络有问题!";
//	private static final String TIPS_JSON_PRASE_ERROR = "数据解析错误!";
//	private static final String TIPS_SERVER_ERROR = "服务器异常，请稍后再试!";
//	private static final String TIPS_OTHER = "抱歉，发生错误，请重试!";
//
//	protected void showErrorToast(Exception e) {
//		if (e instanceof IOException) {
//			ToastUtils.showLongToast(TIPS_NETWORK_ERROR);
//		} else if (e instanceof com.google.gson.JsonParseException) {
//			ToastUtils.showLongToast(TIPS_JSON_PRASE_ERROR);
//		} else if (e instanceof RuntimeException) {
//			ToastUtils.showLongToast(TIPS_SERVER_ERROR);
//		} else {
//			ToastUtils.showLongToast(TIPS_OTHER);
//		}
//	}
//
//	/**
//	 * 登录注册部分注销
//	 */
	// protected static final String KEY_NEXTACTION_FOR_USERCENTER_ACTIVITY = "key_next_action";
	// /**
	// * 登录后，结束掉自己
	// */
	// public static final int NEXT_ACTION_FINISH_SELF = 1;
	// /**
	// * 登录成功后，进入HomeActivity
	// */
	// public static final int NEXT_ACTION_GO_TO_HOME = 2;
	//
	// public void launchUserCenterActivity(int requestCode, int nextAction) {
	// Intent intent = new Intent(this, UserCenterActivity.class);
	// intent.putExtra(KEY_NEXTACTION_FOR_USERCENTER_ACTIVITY, nextAction);
	// startActivityForResult(intent, requestCode);
	// }
}
