package com.jy.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jy.framework.R;

public class CustomLoading extends FrameLayout {

	private Animation anim;
	private ImageView imageview;

	public CustomLoading(Context context) {
		this(context, null, 0);
	}

	public CustomLoading(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLoading(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		setId(R.id.progress);
	}

	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.layout_loading, this);
		imageview = (ImageView) findViewById(R.id.imageView);
		anim = AnimationUtils.loadAnimation(context, R.anim.loading_progress);
		imageview.setAnimation(anim);
	}

	public void startAnim() {
		anim.start();
	}

	public void cancelAnim() {
		anim.cancel();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return true;
	}

}
