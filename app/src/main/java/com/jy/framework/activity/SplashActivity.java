package com.jy.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jy.framework.R;
import com.jy.framework.base.activity.BaseActivity;

/**
 * Created by jiangy on 2016/1/7.
 */
public class SplashActivity extends BaseActivity{
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mHandler.sendEmptyMessageDelayed(0,2000);
	}

	@Override
	public void onBackPressed(){
		return ;
	}
}
