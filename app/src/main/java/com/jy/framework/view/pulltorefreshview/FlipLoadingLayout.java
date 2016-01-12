/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jy.framework.view.pulltorefreshview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.jy.framework.R;

@SuppressLint("ViewConstructor")
public class FlipLoadingLayout extends LoadingLayout {

	static final int FLIP_ANIMATION_DURATION = 150;

	private final Animation mRotateAnimation, mResetRotateAnimation;

	public FlipLoadingLayout(Context context, final PullToRefreshBase.Mode mode, final PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);

		final int rotateAngle = mode == PullToRefreshBase.Mode.PULL_FROM_START ? -180 : 180;

		mRotateAnimation = new RotateAnimation(0, rotateAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);

		mResetRotateAnimation = new RotateAnimation(rotateAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mResetRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);
	}

	// 这里根据图片资源的大小 在加载的时候进行了 重新判断大小的操作 这里自己注释掉可 根据需求更改
	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable) {
		if (null != imageDrawable) {
			final int dHeight = imageDrawable.getIntrinsicHeight();
			final int dWidth = imageDrawable.getIntrinsicWidth();

			/**
			 * We need to set the width/height of the ImageView so that it is square with each side the size of the largest drawable dimension. This is so that it doesn't clip when rotated.
			 */
			// ViewGroup.LayoutParams lp = mHeaderImage.getLayoutParams();
			// lp.width = lp.height = Math.max(dHeight, dWidth);
			// mHeaderImage.requestLayout();

			/**
			 * We now rotate the Drawable so that is at the correct rotation, and is centered.
			 */
			// mHeaderImage.setScaleType(ScaleType.MATRIX);
			// Matrix matrix = new Matrix();
			// matrix.postTranslate((lp.width - dWidth) / 2f, (lp.height - dHeight) / 2f);
			// matrix.postRotate(getDrawableRotationAngle(), lp.width / 2f, lp.height / 2f);
			// mHeaderImage.setImageMatrix(matrix);
		}
	}

	@Override
	protected void onPullImpl(float scaleOfLayout) {
		// NO-OP
	}

	@Override
	protected void pullToRefreshImpl() {
		// Only start reset Animation, we've previously show the rotate anim
		if (mRotateAnimation == mHeaderImage.getAnimation()) {
			mHeaderImage.startAnimation(mResetRotateAnimation);
		}
	}

	@Override
	protected void refreshingImpl() {
		mHeaderImage.clearAnimation();
		mHeaderImage.setVisibility(View.INVISIBLE);
		((AnimationDrawable) mHeaderProgress.getDrawable()).start();
		mHeaderProgress.setVisibility(View.VISIBLE);
	}

	@Override
	protected void releaseToRefreshImpl() {
		mHeaderImage.startAnimation(mRotateAnimation);
	}

	@Override
	protected void resetImpl() {
		mHeaderImage.clearAnimation();
		mHeaderProgress.clearAnimation();
		mHeaderProgress.setVisibility(View.GONE);
		mHeaderImage.setVisibility(View.VISIBLE);
	}

	@Override
	protected int getDefaultDrawableResId() {
		return R.mipmap.default_ptr_flip;
	}

	private float getDrawableRotationAngle() {
		float angle = 0f;
		switch (mMode) {
		case PULL_FROM_END:
			if (mScrollDirection == PullToRefreshBase.Orientation.HORIZONTAL) {
				angle = 90f;
			} else {
				angle = 180f;
			}
			break;

		case PULL_FROM_START:
			if (mScrollDirection == PullToRefreshBase.Orientation.HORIZONTAL) {
				angle = 270f;
			}
			break;

		default:
			break;
		}

		return angle;
	}

}
