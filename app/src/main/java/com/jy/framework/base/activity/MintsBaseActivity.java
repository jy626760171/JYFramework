package com.jy.framework.base.activity;

import com.jy.framework.R;

public abstract class MintsBaseActivity extends SmartActivity {
	@Override
	protected String getCloseWarning() {
		return getResources().getString(R.string.mints_exit_tip);
	}

	@Override
	protected int getFragmentContainerId() {
		return 0;
	}
}
