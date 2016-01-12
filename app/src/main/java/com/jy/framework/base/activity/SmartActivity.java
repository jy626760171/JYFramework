package com.jy.framework.base.activity;

import android.os.Bundle;

import com.jy.framework.base.lifecycle.IComponentContainer;
import com.jy.framework.base.lifecycle.LifeCycleComponent;
import com.jy.framework.base.lifecycle.LifeCycleComponentManager;
import com.jy.framework.constant.GlobalConstant;
import com.jy.framework.utils.LogUtil;

/**
 * 1. manager the components when move from a lifetime to another
 */
public abstract class SmartActivity extends SmartFragmentActivity implements IComponentContainer {

	private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();

	private static final boolean DEBUG = GlobalConstant.DEBUG;

	@Override
	protected void onRestart() {
		super.onStart();
		mComponentContainer.onBecomesVisibleFromTotallyInvisible();
		if (DEBUG) {
			showStatus("onRestart");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mComponentContainer.onBecomesPartiallyInvisible();
		if (DEBUG) {
			showStatus("onPause");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
		if (DEBUG) {
			showStatus("onResume");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (DEBUG) {
			showStatus("onCreate");
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		mComponentContainer.onBecomesTotallyInvisible();
		if (DEBUG) {
			showStatus("onStop");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mComponentContainer.onDestroy();
		if (DEBUG) {
			showStatus("onDestroy");
		}
	}

	@Override
	public void addComponent(LifeCycleComponent component) {
		mComponentContainer.addComponent(component);
	}

	private void showStatus(String status) {
		final String[] className = ((Object) this).getClass().getName().split("\\.");
		LogUtil.d("smart-lifecycle", String.format("%s %s", className[className.length - 1], status));
	}
}
