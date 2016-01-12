package com.jy.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jy.framework.JYApplication;

public class AndroidOSUtils {
	// private static final String TAG = "AndroidOSUtils";

	private static int sOsWidth = -1;

	private static int sOsHeight = -1;

	private static DisplayMetrics sDisplayMetrics;

	private static DisplayMetrics getDisplayMetrics() {
		if (sDisplayMetrics == null) {
			synchronized (AndroidOSUtils.class) {
				if (sDisplayMetrics == null) {
					sDisplayMetrics = JYApplication.getInstance().getResources().getDisplayMetrics();
				}
			}
		}
		return sDisplayMetrics;
	}

	private static void initOsHeight() {
		if (sOsHeight <= 0) {
			synchronized (AndroidOSUtils.class) {
				if (sOsHeight <= 0) {
					sOsHeight = getDisplayMetrics().heightPixels;
				}
			}
		}
	}

	private static void initOSWidth() {
		if (sOsWidth <= 0) {
			synchronized (AndroidOSUtils.class) {
				if (sOsWidth <= 0) {
					sOsWidth = getDisplayMetrics().widthPixels;
				}
			}
		}
	}

	public static int getDisplayHeight() {
		initOsHeight();
		initOSWidth();
		return Math.max(sOsWidth, sOsHeight);
	}

	public static int getDisplayWidth() {
		initOSWidth();
		initOsHeight();
		return Math.min(sOsWidth, sOsHeight);
	}

	public static int dip2Pix(float dip) {
		return ((int) (dip * getDisplayMetrics().density + 0.5f));
	}

	public static int calculateByRatio(int width, float radio) {
		return (int) (width * radio);
	}

	public static void clear() {
		sDisplayMetrics = null;
	}

	public static String getMEID() {
		return ((TelephonyManager) JYApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public static int getAppVersion() {
		PackageInfo pi = getPackageInfo();
		if (pi == null) return -1;
		return pi.versionCode;

	}

	public static int getDispalyDensityDpi() {
		return getDisplayMetrics().densityDpi;
	}

	public static final PackageInfo getPackageInfo() {
		try {
			PackageManager pm = JYApplication.getInstance().getPackageManager();
			String pn = JYApplication.getInstance().getPackageName();
			return pm.getPackageInfo(pn, 0);
		} catch (NameNotFoundException e) {
		}
		return null;
	}

	public static String getString(int resId, Object... args) {
		try {
			return getResources().getString(resId, args);
		} catch (NotFoundException e) {
		}
		return null;
	}

	public static final Resources getResources() {
		return JYApplication.getInstance().getResources();
	}

	public static Drawable getResourceDrawable(Context context, int id) {
		if (id < 0) {
			return null;
		}
		return context.getResources().getDrawable(id);
	}

	/**
	 * Note: use BitmapFactory.decodeStream() more efficiency than BitmapFactory.decodeResource() take less time and less native momery.
	 *
	 * @param id
	 *            Resource id
	 * @return Bitmap
	 */
	public static Bitmap getResourceBitmap(Context context, int id) {
		if (id < 0) {
			return null;
		}
		// return BitmapFactory.decodeStream(SLApplication.getContext().getResources().openRawResource(id));
		return BitmapFactory.decodeResource(context.getResources(), id);
	}

	public static Bitmap getOriginalSizeOneRes(int id) {
		if (id < 0) {
			return null;
		}
		Options options = new Options();
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inDither = false;
		options.inPreferredConfig = null;
		options.inScreenDensity = AndroidOSUtils.getDispalyDensityDpi();
		options.inTargetDensity = AndroidOSUtils.getDispalyDensityDpi();
		InputStream is = null;
		try {
			is = JYApplication.getInstance().getResources().openRawResource(id);
			return BitmapFactory.decodeStream(is, null, options);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
				is = null;
			}
		}
	}

	/**
	 * @return null may be returned if the specified process not found
	 */
	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		for (RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}

	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * 获取当前系统sdk版本号
	 * 
	 * @return
	 */
	public static int getCurrentAPILevel() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 获取手机号码，不一定能获取到
	 * 
	 * @param context
	 * @return
	 */
	public static String getLine1Number(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}

	/**
	 * 隐藏输入法
	 * 
	 * @param activity
	 */
	public static void hideIme(Activity activity) {
		if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null
				|| activity.getWindow().getDecorView().getWindowToken() == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
	}

	public static String strMD5(String string) {

		byte[] hash;

		try {

			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

		} catch (NoSuchAlgorithmException e) {

			throw new RuntimeException("Huh, MD5 should be supported?", e);

		} catch (UnsupportedEncodingException e) {

			throw new RuntimeException("Huh, UTF-8 should be supported?", e);

		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {

			if ((b & 0xFF) < 0x10) hex.append("0");

			hex.append(Integer.toHexString(b & 0xFF));

		}
		return hex.toString();
	}

	public static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
	public static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";

	/**
	 * 获取系统状态栏和导航栏高度
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInternalDimensionSize(Context context, String key) {
		Resources res = context.getResources();
		int result = 0;
		int resourceId = res.getIdentifier(key, "dimen", "android");
		if (resourceId > 0) {
			result = res.getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 设置透明状态栏和导航栏
	 *
	 * @param activity
	 *            需要全屏的activity
	 * @param topView
	 *            activity布局中最顶部的view
	 * @param bottomView
	 *            activity布局中最底部的view
	 */
	public static void setImmiveMode(Activity activity, View topView, View bottomView) {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || activity == null || activity.isFinishing() || activity.isDestroyed()) return;

		Window window = activity.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
			window.setNavigationBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		int navbarHeight = getInternalDimensionSize(activity, NAV_BAR_HEIGHT_RES_NAME);
		int statusbarHeight = getInternalDimensionSize(activity, STATUS_BAR_HEIGHT_RES_NAME);

		try {
			if (topView.getId() == bottomView.getId()) {
				topView.setPadding(topView.getPaddingLeft(), topView.getPaddingTop() + statusbarHeight, topView.getPaddingRight(), topView.getPaddingBottom()
						+ navbarHeight);
			} else {
				topView.setPadding(topView.getPaddingLeft(), topView.getPaddingTop() + statusbarHeight, topView.getPaddingRight(), topView.getPaddingBottom());
				if (isNavBarEnabled(activity)) {
					bottomView.setPadding(bottomView.getPaddingLeft(), bottomView.getPaddingTop(), bottomView.getPaddingRight(), bottomView.getPaddingBottom()
							+ navbarHeight);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 判断虚拟键是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNavBarEnabled(Context context) {
		Resources res = context.getResources();
		int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
		if (resourceId != 0) {
			boolean hasNav = res.getBoolean(resourceId);
			String sNavBarOverride = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				try {
					Class c = Class.forName("android.os.SystemProperties");
					Method m = c.getDeclaredMethod("get", String.class);
					m.setAccessible(true);
					sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
				} catch (Throwable e) {
					sNavBarOverride = null;
				}
			}

			// check override flag (see static block)
			if ("1".equals(sNavBarOverride)) {
				hasNav = false;
			} else if ("0".equals(sNavBarOverride)) {
				hasNav = true;
			}
			return hasNav;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // fallback
			return !ViewConfiguration.get(context).hasPermanentMenuKey();
		}
		return false;
	}
}
