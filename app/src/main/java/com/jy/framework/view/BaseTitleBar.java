package com.jy.framework.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jy.framework.R;

/**
 * Created by jiangy on 2016/1/15.
 */
public final class BaseTitleBar extends RelativeLayout implements View.OnClickListener{

	public FrameLayout mLeftContainer;
	private LinearLayout mLeftChildrenContainer;
	public ImageView mLeftIcon;
	public TextView mLeftText;

	public FrameLayout mRightContainer;
	public TextView mCenterText;

	public FrameLayout mCenterContainer;
	private FrameLayout mRightChildrenContainer;
	public ImageView mRightIcon;

	private OnLeftRightClickListener mListener;

	public interface OnLeftRightClickListener{
		public void onLeftClick();
		public void onRightClick();
	}

	public BaseTitleBar(Context context) {
		this(context, null);
	}

	public BaseTitleBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		LayoutInflater.from(context).inflate(R.layout.base_title_bar, this);
		mLeftContainer = (FrameLayout) findViewById(R.id.base_title_bar_left_container);
		mLeftChildrenContainer = (LinearLayout) findViewById(R.id.default_title_left_view);
		mLeftIcon = (ImageView) findViewById(R.id.default_title_left_view_icon);
		mLeftText = (TextView) findViewById(R.id.default_title_text_back);
		mCenterContainer = (FrameLayout) findViewById(R.id.base_title_bar_center_container);
		mCenterText = (TextView) findViewById(R.id.default_title_text);
		mRightContainer = (FrameLayout) findViewById(R.id.base_title_bar_right_container);
		mRightChildrenContainer = (FrameLayout) findViewById(R.id.default_title_right_view);
		mRightIcon = (ImageView) findViewById(R.id.default_title_right_view_icon);

		mLeftChildrenContainer.setOnClickListener(this);
		mRightChildrenContainer.setOnClickListener(this);
	}

	public void setOnLeftRightClickListener(OnLeftRightClickListener listener){
		this.mListener = listener;
	}

	public void hideView(View view){
		view.setVisibility(View.GONE);
	}

	public void setCustomizedView(ViewGroup parent, View child){
		if(parent == null || child == null) return;
		parent.removeAllViews();
		parent.addView(child);
	}

	public void setCustomizedView(ViewGroup parent, int childLayoutId){
		if(parent == null) return;
		View child = LayoutInflater.from(parent.getContext()).inflate(childLayoutId, null);
		setCustomizedView(parent, child);
	}

	public void setBackground(View view, int resId){
		if(view == null) return;
		try {
			view.setBackgroundResource(resId);
		}catch (Exception e){
			e.printStackTrace();
			hideView(view);
			return;
		}
	}

	public void setIcon(ImageView view, int resId){
		if(view == null) return;
		try {
			view.setImageResource(resId);
		}catch (Exception e){
			e.printStackTrace();
			hideView(view);
			return;
		}

	}

	public void setText(TextView view, int stringId){
		if(view == null) return;
		String text = null;
		try {
			text = getResources().getString(stringId);
		}catch (Exception e){
			e.printStackTrace();
			hideView(view);
			return;
		}
		setText(view, text);
	}

	public void setText(TextView view, String text){
		if(view == null) return;
		if(TextUtils.isEmpty(text)){
			hideView(view);
			return;
		}
		view.setText(text);
	}

	@Override
	public void onClick(View v) {
		if(mListener == null) return;
		switch (v.getId()){
			case R.id.default_title_left_view:
				mListener.onLeftClick();
				break;
			case R.id.default_title_right_view:
				mListener.onRightClick();
				break;
			default:
				break;
		}
	}
}
