package com.onlyone.jdmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.System;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		System.out.println("hello");

		Log.d("SplashActivity", "你好");

		Log.d("SplashActivity", "大家好");

	}
}
