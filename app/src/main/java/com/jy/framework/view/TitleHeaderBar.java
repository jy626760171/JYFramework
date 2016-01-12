package com.jy.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jy.framework.R;

public class TitleHeaderBar extends RelativeLayout {

	private static final int ID_CUSTOMIZED_LEFT_VIEW = 1;
	private static final int ID_CUSTOMIZED_CENTER_VIEW = 2;
	private static final int ID_CUSTOMIZED_RIGHT_VIEW = 3;

	/**
	 * titlebar center textview.
	 */
	private TextView mCenterTitleTextView;

	/**
	 * titlebar left textview
	 */
	private TextView mLeftTitleTextView;

	/**
	 * titlebar left return imageview
	 */
	private ImageView mLeftReturnImageView;
	/**
	 * titlebar left view container.
	 */
	private RelativeLayout mLeftViewContainer;
	/**
	 * titlebar right view container
	 */
	private RelativeLayout mRightViewContainer;
	/**
	 * titlebar center view container
	 */
	private RelativeLayout mCenterViewContainer;

	// private String mTitle;

	public TitleHeaderBar(Context context) {
		this(context, null);
	}

	public TitleHeaderBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleHeaderBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(getHeaderViewLayoutId(), this);
		mLeftViewContainer = (RelativeLayout) findViewById(R.id.ly_title_bar_left);
		mCenterViewContainer = (RelativeLayout) findViewById(R.id.ly_title_bar_center);
		mRightViewContainer = (RelativeLayout) findViewById(R.id.ly_title_bar_right);
		mLeftReturnImageView = (ImageView) findViewById(R.id.iv_title_bar_left);
		mCenterTitleTextView = (TextView) findViewById(R.id.tv_title_bar_title);
		mLeftTitleTextView = (TextView) findViewById(R.id.tv_title_left_bar);
	}

	protected int getHeaderViewLayoutId() {
		return R.layout.mints_base_header_bar_title;
	}

	public ImageView getLeftImageView() {
		return mLeftReturnImageView;
	}

	public TextView getTitleTextView() {
		return mCenterTitleTextView;
	}

	public void setLeftTitle(String title) {
		mLeftTitleTextView.setText(title);
	}

	public void setCenterTitle(String title) {
		mCenterTitleTextView.setText(title);
	}

	private LayoutParams makeLayoutParams(View view) {
		ViewGroup.LayoutParams lpOld = view.getLayoutParams();
		LayoutParams lp = null;
		if (lpOld == null) {
			lp = new LayoutParams(-2, -1);
		} else {
			lp = new LayoutParams(lpOld.width, lpOld.height);
		}
		return lp;
	}

	/**
	 * set customized view to left side
	 *
	 * @param view
	 *            the view to be added to left side
	 */
	public void setCustomizedLeftView(View view) {
		view.setId(ID_CUSTOMIZED_LEFT_VIEW);
		mLeftReturnImageView.setVisibility(GONE);
		mLeftTitleTextView.setVisibility(GONE);
		LayoutParams lp = makeLayoutParams(view);
		lp.addRule(CENTER_VERTICAL);
		lp.addRule(ALIGN_PARENT_LEFT);
		mLeftViewContainer.addView(view, lp);
	}

	/**
	 * set customized view to left side
	 *
	 * @param layoutId
	 *            the xml layout file id
	 */
	public void setCustomizedLeftView(int layoutId) {
		View view = inflate(getContext(), layoutId, null);
		setCustomizedLeftView(view);
	}

	/**
	 * set customized view to center
	 *
	 * @param view
	 *            the view to be added to center
	 */
	public void setCustomizedCenterView(View view) {
		view.setId(ID_CUSTOMIZED_CENTER_VIEW);
		mCenterTitleTextView.setVisibility(GONE);
		LayoutParams lp = makeLayoutParams(view);
		lp.addRule(CENTER_IN_PARENT);
		mCenterViewContainer.addView(view, lp);
	}

	/**
	 * set customized view to center
	 *
	 * @param layoutId
	 *            the xml layout file id
	 */
	public void setCustomizedCenterView(int layoutId) {
		View view = inflate(getContext(), layoutId, null);
		setCustomizedCenterView(view);
	}

	/**
	 * set customized view to right side
	 *
	 * @param view
	 *            the view to be added to right side
	 */
	public void setCustomizedRightView(View view) {
		view.setId(ID_CUSTOMIZED_RIGHT_VIEW);
		LayoutParams lp = makeLayoutParams(view);
		lp.addRule(CENTER_VERTICAL);
		lp.addRule(ALIGN_PARENT_RIGHT);
		mRightViewContainer.addView(view, lp);
	}

	/**
	 * set customized view to right side
	 *
	 * @param view
	 *            the view to be added to right side
	 * @param index
	 *            View position
	 */
	public void setCustomizedRightView(View view, int index) {
		LayoutParams lp = makeLayoutParams(view);
		lp.addRule(CENTER_VERTICAL);
		lp.addRule(ALIGN_PARENT_RIGHT);
		mRightViewContainer.addView(view, index, lp);
	}

	public RelativeLayout getLeftViewContainer() {
		return mLeftViewContainer;
	}

	public RelativeLayout getCenterViewContainer() {
		return mCenterViewContainer;
	}

	public RelativeLayout getRightViewContainer() {
		return mRightViewContainer;
	}

	public void setLeftOnClickListener(OnClickListener l) {
		mLeftViewContainer.setOnClickListener(l);
	}

	public void setCenterOnClickListener(OnClickListener l) {
		mCenterViewContainer.setOnClickListener(l);
	}

	public void setRightOnClickListener(OnClickListener l) {
		mRightViewContainer.setOnClickListener(l);
	}

}
