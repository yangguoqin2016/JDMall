package com.onlyone.jdmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);


		System.out.print("��������ڲ���45464");
		System.out.print("在乱码老子不干了");
		System.out.print("老子不干了");
		System.out.println("并没有乱码");

		Log.d("SplashActivity", "大家好");

	}
}
