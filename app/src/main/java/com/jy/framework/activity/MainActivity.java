package com.jy.framework.activity;

import android.os.Bundle;

import com.jy.framework.R;
import com.jy.framework.base.activity.TitleBaseActivity;

public class MainActivity extends TitleBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

}
