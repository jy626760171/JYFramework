package com.jy.framework.activity;

import android.os.Bundle;
import android.view.View;

import com.jy.framework.R;
import com.jy.framework.base.activity.TitleBaseActivity;

public class MainActivity extends TitleBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitleBar.mCenterText.setText("主页");
		mTitleBar.mLeftContainer.setVisibility(View.GONE);
		mTitleBar.mRightContainer.setVisibility(View.GONE);
	}

	@Override
	protected boolean enableExitApp() {
		return true;
	}
}
