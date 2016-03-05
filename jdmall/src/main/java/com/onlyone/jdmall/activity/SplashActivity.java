package com.onlyone.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.utils.UIUtil;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: 3/4/2016 需要完成Splash界面
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_splash);
	}

	private void initEvent() {
		new Thread(new Runnable() {
			@Override
			public void run() {
//				模拟Splash界面的逻辑耗时
				SystemClock.sleep(1000);

				UIUtil.runSafely(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(SplashActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				});
			}
		}).start();
	}

	private void initData() {

	}
}
