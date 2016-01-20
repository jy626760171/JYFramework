package com.jy.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jy.framework.R;
import com.jy.framework.base.activity.BaseActivity;
import com.jy.framework.view.indicatorviewpager.IndicatorViewPager;
import com.jy.framework.view.indicatorviewpager.IndicatorViewPager.IndicatorViewPagerAdapter;
import com.jy.framework.view.indicatorviewpager.indicator.Indicator;

/**
 * Created by jiangy on 2016/1/20.
 */
public class SplashActivity extends BaseActivity {
	private ViewPager mPager;
	private Indicator mIndicator;
	private IndicatorViewPager mIndicatorViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mPager = (ViewPager) findViewById(R.id.splash_pager);
		mIndicator = (Indicator) findViewById(R.id.splash_indicator);
		mIndicatorViewPager = new IndicatorViewPager(mIndicator, mPager);
		mIndicatorViewPager.setAdapter(adapter);
	}

	private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPagerAdapter() {
		private int[] images = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, 0};

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {
			if (convertView == null) {
				convertView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.tab_guide, container, false);
			}
			return convertView;
		}

		@Override
		public View getViewForPage(int position, View convertView, ViewGroup container) {
			if (position == getCount() - 1) {
				convertView = new Button(SplashActivity.this);
				((Button)convertView).setText("enter app");
				((Button) convertView).setGravity(Gravity.CENTER);
				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(SplashActivity.this, MainActivity.class));
						finish();
					}
				});
				convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			}else {
				if (convertView == null) {
					convertView = new View(SplashActivity.this);
					convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				}
				convertView.setBackgroundResource(images[position]);
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return images.length;
		}
	};

	@Override
	protected boolean enableExitApp() {
		return super.enableExitApp();
	}

	@Override
	public void onBackPressed() {
	}
}
