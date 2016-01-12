package com.jy.framework.view;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnCheckDoubleClickListener implements OnClickListener {
	private static int DEFAULT_WAIT_TIME = 500;

	private long mLastClickedTime = 0;
	private int mWaitTime = 0;

	public OnCheckDoubleClickListener() {
		initWaitTime(DEFAULT_WAIT_TIME);
	}

	public OnCheckDoubleClickListener(int paramWaitTime) {
		if (paramWaitTime <= 0) paramWaitTime = DEFAULT_WAIT_TIME;
		initWaitTime(paramWaitTime);
	}

	private void initWaitTime(int paramWaitTime) {
		mWaitTime = paramWaitTime;
	}

	@Override
	public final void onClick(View v) {
		long now = System.currentTimeMillis();
		if (now - mLastClickedTime < mWaitTime) {
			return;
		}
		mLastClickedTime = now;
		onClicked(v);
	}

	public abstract void onClicked(View v);
}
