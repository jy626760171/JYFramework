package com.jy.framework.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jy.framework.R;
import com.jy.framework.utils.AndroidOSUtils;
import com.jy.framework.view.CustomLoading;
import com.jy.framework.view.TitleHeaderBar;

public abstract class TitleBaseActivity extends BaseActivity {

	private TitleHeaderBar mTitleHeaderBar;
	private FrameLayout mContentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}

	protected int getFrameLayoutId() {
		return R.layout.mints_base_content_frame_with_title_header;
	}

	protected TitleHeaderBar getTitleHeaderBar() {
		return (TitleHeaderBar) findViewById(R.id.mints_content_frame_title_header);
	}

	protected FrameLayout getContentContainer() {
		return (FrameLayout) findViewById(R.id.mints_content_frame_content);
	}

	protected void initViews() {
		super.setContentView(getFrameLayoutId());
		mContentContainer = getContentContainer();
		mTitleHeaderBar = getTitleHeaderBar();
		if (enableTitleBar()) {
			if (enableDefaultBack()) {
				mTitleHeaderBar.setLeftOnClickListener(mLeftOnClickListener);
			} else {
				mTitleHeaderBar.getLeftViewContainer().setVisibility(View.INVISIBLE);
			}
		} else {
			mTitleHeaderBar.setVisibility(View.GONE);
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

	public void setContentViewSupper(int layoutResID) {
		super.setContentView(layoutResID);
	}

	protected RelativeLayout getRightContainerView() {
		return mTitleHeaderBar.getRightViewContainer();
	}

	protected RelativeLayout getCenterContainerView() {
		return mTitleHeaderBar.getCenterViewContainer();
	}

	protected void setCustomizedRightView(View view, int index) {
		mTitleHeaderBar.setCustomizedRightView(view, index);
	}

	protected void setCustomizedRightView(View view) {
		mTitleHeaderBar.setCustomizedRightView(view);
	}

	protected void removeRightView(View view) {
		mTitleHeaderBar.getRightViewContainer().removeView(view);
	}

	protected void removeCenterView(View view) {
		mTitleHeaderBar.getCenterViewContainer().removeView(view);
	}

	protected void setHeaderBackground(int resid) {
		mTitleHeaderBar.setBackgroundResource(resid);
	}

	protected void setCustomizedCenterView(View view) {
		mTitleHeaderBar.setCustomizedCenterView(view);
	}

	protected void setCustomizedCenterView(int layoutResID) {
		mTitleHeaderBar.setCustomizedCenterView(layoutResID);
	}

	@SuppressWarnings("deprecation")
	protected void setHeaderBackground(Drawable drawable) {
		if (AndroidOSUtils.getCurrentAPILevel() < 16) {
			mTitleHeaderBar.setBackgroundDrawable(drawable);
		} else {
			if (AndroidOSUtils.getCurrentAPILevel() >= 16) {
				mTitleHeaderBar.setBackground(drawable);
			} else {
				mTitleHeaderBar.setBackgroundDrawable(drawable);
			}
		}
	}

	protected void setCustomizedLeftView(int layoutId) {
		mTitleHeaderBar.setCustomizedLeftView(layoutId);
	}

	protected void setCustomizedLeftView(View view) {
		mTitleHeaderBar.setCustomizedLeftView(view);
	}

	protected void setHeaderLeftTitle(int resId) {
		setHeaderLeftTitle(getString(resId));
	}

	protected void setHeaderLeftTitle(String title) {
		mTitleHeaderBar.setLeftTitle(title);
	}

	protected void setHeaderTitle(int id) {
		setHeaderTitle(getString(id));
	}

	protected void setHeaderTitle(String title) {
		mTitleHeaderBar.setCenterTitle(title);
	}

	private final OnClickListener mLeftOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!processBackPressed()) {
				doReturnBack();
			}
		}
	};

	private boolean mEnableProgressbar = false;

	protected void showProgressBar() {
		View progressbar = getContentContainer().findViewById(R.id.progress);
		if (null == progressbar) {
			progressbar = createProgressbar();
			getContentContainer().addView(progressbar);
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
			View progressbar = getContentContainer().findViewById(R.id.progress);
			if (null != progressbar) getContentContainer().removeView(progressbar);
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
}
