package com.jy.framework.base.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jy.framework.R;
import com.jy.framework.base.fragment.FragmentParam;
import com.jy.framework.base.fragment.LazyFragment;
import com.jy.framework.constant.GlobalConstant;
import com.jy.framework.utils.LogUtil;
import com.jy.framework.utils.ToastUtils;

public abstract class SmartFragmentActivity extends FragmentActivity {

	private final static String LOG_TAG = "smart-fragment";

	public static boolean DEBUG = GlobalConstant.DEBUG;
	protected LazyFragment mCurrentFragment;

	protected abstract int getFragmentContainerId();

	public void pushFragmentToBackStack(Class<?> cls, Object data) {
		FragmentParam param = new FragmentParam();
		param.cls = cls;
		param.data = data;
		goToThisFragment(param);
	}

	protected String getFragmentTag(FragmentParam param) {
		StringBuilder sb = new StringBuilder(param.cls.toString());
		return sb.toString();
	}

	private void goToThisFragment(FragmentParam param) {
		int containerId = getFragmentContainerId();
		Class<?> cls = param.cls;
		if (cls == null) {
			return;
		}
		try {
			String fragmentTag = getFragmentTag(param);
			FragmentManager fm = getSupportFragmentManager();
			if (DEBUG) {
				LogUtil.d(LOG_TAG, "before operate, stack entry count: %s", fm.getBackStackEntryCount());
			}
			LazyFragment fragment = (LazyFragment) fm.findFragmentByTag(fragmentTag);
			if (fragment == null) {
				fragment = (LazyFragment) cls.newInstance();
			}
			if (mCurrentFragment != null && mCurrentFragment != fragment) {
				mCurrentFragment.onLeave();
			}
			fragment.onEnter(param.data);

			FragmentTransaction ft = fm.beginTransaction();
			if (fragment.isAdded()) {
				if (DEBUG) {
					LogUtil.d(LOG_TAG, "%s has been added, will be shown again.", fragmentTag);
				}
				ft.show(fragment);
			} else {
				if (DEBUG) {
					LogUtil.d(LOG_TAG, "%s is added.", fragmentTag);
				}
				ft.add(containerId, fragment, fragmentTag);
			}
			mCurrentFragment = fragment;

			ft.addToBackStack(fragmentTag);
			ft.commitAllowingStateLoss();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void goToFragment(Class<?> cls, Object data) {
		if (cls == null) {
			return;
		}
		LazyFragment fragment = (LazyFragment) getSupportFragmentManager().findFragmentByTag(cls.toString());
		if (fragment != null) {
			mCurrentFragment = fragment;
			fragment.onBackWithData(data);
		}
		getSupportFragmentManager().popBackStackImmediate(cls.toString(), 0);
	}

	public void popTopFragment(Object data) {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStackImmediate();
		if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
			mCurrentFragment.onBackWithData(data);
		}
	}

	public void popToRoot(Object data) {
		FragmentManager fm = getSupportFragmentManager();
		while (fm.getBackStackEntryCount() > 1) {
			fm.popBackStackImmediate();
		}
		popTopFragment(data);
	}

	/**
	 * process the return back logic return true if back pressed event has been processed and should stay in current view
	 *
	 * @return
	 */
	protected boolean processBackPressed() {
		return false;
	}

	protected boolean enableDefaultBack() {
		return true;
	}

	/**
	 * 用于记录第一次按下back键
	 */
	private long mLastTimemillions = 0;

	/**
	 * 当前页面是最终退出程序的界面，必须重写该方法
	 * @return
	 */
	protected boolean enableExitApp() {
		return false;
	}

	/**
	 * process back pressed
	 */
	@Override
	public void onBackPressed() {

		// process back for fragment
		if (processBackPressed()) {
			return;
		}

		// process back for fragment
		boolean enableBackPressed = enableDefaultBack();

		if (mCurrentFragment != null) {
			enableBackPressed = !mCurrentFragment.processBackPressed();
		}
		if (enableBackPressed) {
			if (enableExitApp()) {
				if (mLastTimemillions == 0) {
					mLastTimemillions = System.currentTimeMillis();
					ToastUtils.showLongToast(R.string.exit_app_tip);
				} else {
					long currentTimemillions = System.currentTimeMillis();
					if (currentTimemillions - mLastTimemillions <= 1000) {
						ToastUtils.cancleToast();
						doReturnBack();
					}else{
						mLastTimemillions = 0;
					}
				}
			} else {
				doReturnBack();
			}
		}
	}

	private boolean tryToUpdateCurrentAfterPop() {
		FragmentManager fm = getSupportFragmentManager();
		int cnt = fm.getBackStackEntryCount();
		if (cnt > 0) {
			String name = fm.getBackStackEntryAt(cnt - 1).getName();
			Fragment fragment = fm.findFragmentByTag(name);
			if (fragment != null && fragment instanceof LazyFragment) {
				mCurrentFragment = (LazyFragment) fragment;
			}
			return true;
		}
		return false;
	}

	protected void doReturnBack() {
		int count = getSupportFragmentManager().getBackStackEntryCount();
		if (count <= 1) {
			finish();
		} else {
			getSupportFragmentManager().popBackStackImmediate();
			if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
				mCurrentFragment.onBack();
			}
		}
	}

	public void hideKeyboardForCurrentFocus() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public void showKeyboardAtView(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	public void forceShowKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

	}

	protected void exitFullScreen() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	protected void showFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
