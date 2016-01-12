package com.jy.framework.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * <h1>懒加载Fragment</h1> 只有创建并显示的时候才会调用onCreateViewLazy方法<br>
 * <br>
 * <p/>
 * 懒加载的原理onCreateView的时候Fragment有可能没有显示出来。<br>
 * 但是调用到setUserVisibleHint(boolean isVisibleToUser),isVisibleToUser = true的时候就说明有显示出来<br>
 * 但是要考虑onCreateView和setUserVisibleHint的先后问题所以才有了下面的代码
 * <p/>
 * 注意：<br>
 * 《1》原先的Fragment的回调方法名字后面要加个Lazy，比如Fragment的onCreateView方法， 就写成onCreateViewLazy <br>
 * 《2》使用该LazyFragment会导致多一层布局深度
 * <p/>
 * Created by huzy on 2015/11/23.
 */
public class LazyFragment extends BaseFragment {

	private boolean isInit = false;

	private Bundle savedInstanceState;

	public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";

	private boolean isLazyLoad = true;

	private FrameLayout layout;

	private boolean isStart = false;

	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		super.onCreateView(savedInstanceState);
		Bundle bundle = getArguments();
		if (null != bundle) {
			isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
		}
		if (isLazyLoad) {
			if (getUserVisibleHint() && !isInit) {
				// 界面可见了，但是还没有初始化,执行创建View
				isInit = true;
				this.savedInstanceState = savedInstanceState;
				onCreateViewLazy(savedInstanceState);
			} else {
				// 创建layout,等待后面创建界面
				layout = new FrameLayout(getApplicationContext());
				layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				super.setContentView(layout);
			}
		} else {
			// 正常执行创建View过程.
			isInit = true;
			onCreateViewLazy(savedInstanceState);
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (isVisibleToUser && !isInit && getContentView() != null) {
			// 界面可见，但是还有没初始化，layout已经被设置了
			isInit = true;
			onCreateViewLazy(savedInstanceState);
			// 执行onResumeLazy()方法，其作用是一个代理
			onResumeLazy();
		}

		if (isInit && getContentView() != null) {
			// 已经被初始化了，并且layout已经存在
			if (isVisibleToUser) {
				isStart = true;
				onStartLazy();
			} else {
				isStart = false;
				onStopLazy();
			}
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
			layout.removeAllViews();
			View view = inflater.inflate(layoutResID, layout, false);
			layout.addView(view);
		} else {
			super.setContentView(layoutResID);
		}
	}

	@Override
	public void setContentView(View view) {
		if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
			layout.removeAllViews();
			layout.addView(view);
		} else {
			super.setContentView(view);
		}
	}

	@Deprecated
	@Override
	public final void onStart() {
		super.onStart();
		if (isInit && !isStart && getUserVisibleHint()) {
			isStart = true;
			onStartLazy();
		}
	}

	@Override
	@Deprecated
	public final void onResume() {
		super.onResume();
		if (isInit) {
			onResumeLazy();
		}
	}

	@Override
	@Deprecated
	public final void onPause() {
		super.onPause();
		if (isInit) {
			onPauseLazy();
		}
	}

	@Deprecated
	@Override
	public final void onStop() {
		super.onStop();
		if (isInit && isStart && getUserVisibleHint()) {
			isStart = false;
			onStopLazy();
		}
	}

	@Override
	@Deprecated
	public final void onDestroyView() {
		super.onDestroyView();
		if (isInit) {
			onDestroyViewLazy();
		}
		isInit = false;
	}

	protected void onCreateViewLazy(Bundle savedInstanceState) {

	}

	protected void onStartLazy() {

	}

	protected void onStopLazy() {

	}

	protected void onResumeLazy() {

	}

	protected void onPauseLazy() {

	}

	protected void onDestroyViewLazy() {

	}
}
