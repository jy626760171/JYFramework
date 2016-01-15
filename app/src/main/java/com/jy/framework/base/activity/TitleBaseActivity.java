package com.jy.framework.base.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jy.framework.R;
import com.jy.framework.view.BaseTitleBar;
import com.jy.framework.view.BaseTitleBar.OnLeftRightClickListener;
import com.jy.framework.view.CustomLoading;

public abstract class TitleBaseActivity extends BaseActivity implements OnLeftRightClickListener{

	private BaseTitleBar mTitleBar;
	private FrameLayout mContentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}

	protected void initViews() {
		super.setContentView(R.layout.activity_base_frame_with_title_bar);
		mContentContainer = (FrameLayout) findViewById(R.id.activity_frame_container);
		mTitleBar = (BaseTitleBar) findViewById(R.id.activity_frame_title_bar);
		if (enableTitleBar()) {
			mTitleBar.setOnLeftRightClickListener(this);
			if(!enableDefaultBack()) mTitleBar.mLeftContainer.setVisibility(View.GONE);
		} else {
			mTitleBar.setVisibility(View.GONE);
		}
	}

	protected boolean enableTitleBar() {
		return true;
	}

	protected boolean enableDefaultBack() {
		return true;
	}

	@Override
	public void setContentView(int layoutResID) {
		View view = LayoutInflater.from(this).inflate(layoutResID, null);
		this.setContentView(view);
	}

	@Override
	public void setContentView(View view) {
		view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		mContentContainer.addView(view);
	}

	private boolean mEnableProgressbar = false;

	protected void showProgressBar() {
		View progressbar = mContentContainer.findViewById(R.id.progress);
		if (null == progressbar) {
			progressbar = createProgressbar();
			mContentContainer.addView(progressbar);
		}
		progressbarBringToFront(progressbar);
		mEnableProgressbar = true;
	}

	private void progressbarBringToFront(View progressbar) {
		try {
			// 注意， 这个方法在三星 GT-I9500上面报错，说找不到方法。
			progressbar.bringToFront();
		} catch (Exception e) {
		}
	}

	protected void hideProgressBar() {
		if (mEnableProgressbar) {
			View progressbar = mContentContainer.findViewById(R.id.progress);
			if (null != progressbar) mContentContainer.removeView(progressbar);
			mEnableProgressbar = false;
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (mEnableProgressbar) {
			return true;
		}
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_BACK:
					finish();
					return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private final View createProgressbar() {

		View progressBar = new CustomLoading(TitleBaseActivity.this);

		FrameLayout.LayoutParams locLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		locLayoutParams.gravity = Gravity.CENTER;

		progressBar.setLayoutParams(locLayoutParams);

		return progressBar;
	}

	@Override
	public void onLeftClick() {
		if (!processBackPressed()) {
			doReturnBack();
		}
	}

	@Override
	public void onRightClick() {

	}
}
