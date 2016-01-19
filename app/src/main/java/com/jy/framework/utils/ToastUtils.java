package com.jy.framework.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.framework.R;

public class ToastUtils extends BaseUtils{

	private static final int DISTANCE_BOTTOM = AndroidOSUtils.dip2Pix(80);
	private static final Handler sHandler = new Handler(Looper.getMainLooper());
	private static Toast sToast = null;
	private static ShowRunnable showRunnable = new ShowRunnable();
	private static Object sLock = new Object();
	private static CancleRunnable sCancleRunnable = new CancleRunnable();

	public static void showLongToast(int resId) {
		showToast(resId, Toast.LENGTH_LONG);
	}

	public static void showShortToast(int resId) {
		showToast(resId, Toast.LENGTH_SHORT);
	}

	public static void showLongToast(CharSequence text) {
		showToast(text, Toast.LENGTH_LONG);
	}

	public static void showShortToast(CharSequence text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	private static void showToast(int resId, int duration) {
		String text = sContext.getResources().getString(resId);
		showToast(text, duration);
	}

	private static void showToast(CharSequence text, int duration) {
		synchronized (sLock) {
			sHandler.removeCallbacks(showRunnable);
			sHandler.removeCallbacks(sCancleRunnable);
			showRunnable.message = text;
			showRunnable.duration = duration;
			sHandler.post(showRunnable);
		}
	}

	public static void cancleToast() {
		synchronized (sLock) {
			sHandler.removeCallbacks(showRunnable);
			sHandler.removeCallbacks(sCancleRunnable);
			sHandler.post(sCancleRunnable);
		}
	}

	private static void cancle() {
		if (sToast != null) {
			sToast.cancel();
			sToast = null;
		}
	}

	private static class ShowRunnable implements Runnable {
		private CharSequence message;
		private int duration;

		@Override
		public void run() {
			synchronized (sLock) {
				cancle();
				show();
			}
		}

		private void show() {
			sToast = new Toast(sContext);
			TextView tv = (TextView) LayoutInflater.from(sContext).inflate(R.layout.layout_toast, null);
			tv.setText(message);
			sToast.setView(tv);
			sToast.setGravity(Gravity.BOTTOM, 0, DISTANCE_BOTTOM);
			sToast.setDuration(duration);
			sToast.show();
		}
	}

	private static class CancleRunnable implements Runnable{
		@Override
		public void run() {
			synchronized (sLock) {
				cancle();
			}
		}
	}
}