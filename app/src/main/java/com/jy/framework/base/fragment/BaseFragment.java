package com.jy.framework.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.framework.base.activity.SmartFragmentActivity;
import com.jy.framework.base.lifecycle.IComponentContainer;
import com.jy.framework.base.lifecycle.LifeCycleComponent;
import com.jy.framework.base.lifecycle.LifeCycleComponentManager;
import com.jy.framework.constant.GlobalConstant;
import com.jy.framework.utils.LogUtil;

import java.lang.reflect.Field;

/**
 * Created by huzy on 2015/11/23.
 */
public class BaseFragment extends Fragment implements ISmartFragment, IComponentContainer {

	private static final boolean DEBUG = GlobalConstant.DEBUG;
	protected Object mDataIn;
	private boolean mFirstResume = true;

	private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();

	protected LayoutInflater inflater;

	private View contentView;

	private ViewGroup container;

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (DEBUG) {
			showStatus("onCreate");
		}
		context = getActivity().getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (DEBUG) {
			showStatus("onCreateView");
		}

		this.inflater = inflater;
		this.container = container;

		onCreateView(savedInstanceState);

		if (null == contentView) {
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		return contentView;

	}

	/**
	 * 创建View -->ContentView
	 */
	protected void onCreateView(Bundle savedInstanceState) {

	}

	/**
	 * Only when Activity resume, not very precise. When activity recover from partly invisible, onBecomesPartiallyInvisible will be triggered.
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (!mFirstResume) {
			onBack();
		}
		if (mFirstResume) {
			mFirstResume = false;
		}
		if (DEBUG) {
			showStatus("onResume");
		}
	}

	/**
	 * Not add self to back stack when removed, so only when Activity stop
	 */
	@Override
	public void onStop() {
		super.onStop();
		if (DEBUG) {
			showStatus("onStop");
		}
		onLeave();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (DEBUG) {
			showStatus("onAttach");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (DEBUG) {
			showStatus("onActivityCreated");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (DEBUG) {
			showStatus("onStart");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (DEBUG) {
			showStatus("onPause");
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (DEBUG) {
			showStatus("onDestroyView");
		}

		contentView = null;
		container = null;
		inflater = null;
	}

	public Context getApplicationContext() {
		return context;
	}

	public void setContentView(int layoutResID) {
		setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
	}

	public void setContentView(View view) {
		contentView = view;
	}

	public View getContentView() {
		return contentView;
	}

	public View findViewById(int id) {
		if (contentView != null) return contentView.findViewById(id);
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (DEBUG) {
			showStatus("onDestroy");
		}
		mComponentContainer.onDestroy();
	}

	// http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
	@Override
	public void onDetach() {
		super.onDetach();
		if (DEBUG) {
			showStatus("onDetach");
		}
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public SmartFragmentActivity getContext() {
		return (SmartFragmentActivity) getActivity();
	}

	@Override
	public void addComponent(LifeCycleComponent component) {
		mComponentContainer.addComponent(component);
	}

	@Override
	public void onEnter(Object data) {
		mDataIn = data;
		if (DEBUG) {
			showStatus("onEnter");
		}
	}

	@Override
	public void onLeave() {
		if (DEBUG) {
			showStatus("onLeave");
		}
		mComponentContainer.onBecomesTotallyInvisible();
	}

	@Override
	public void onBack() {
		if (DEBUG) {
			showStatus("onBack");
		}
		mComponentContainer.onBecomesVisibleFromTotallyInvisible();
	}

	@Override
	public void onBackWithData(Object data) {
		if (DEBUG) {
			showStatus("onBackWithData");
		}
		mComponentContainer.onBecomesVisibleFromTotallyInvisible();
	}

	@Override
	public boolean processBackPressed() {
		return false;
	}

	private void showStatus(String status) {
		final String[] className = ((Object) this).getClass().getName().split("\\.");
		LogUtil.d("smart-lifecycle", "%s %s", className[className.length - 1], status);
	}
}
