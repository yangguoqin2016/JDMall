package com.onlyone.jdmall.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.application
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 15:06
 * 描述:		全局唯一的Application类
 */
public class MyApplication extends Application {
	/**
	 * 全局Context
	 */
	public static Context sGlobalContext;
	/**
	 * 主线程Handler
	 */
	public static Handler sGlobalHandler;
	/**
	 * 主线程ID
	 */
	public static int     sMainThreadID;

	@Override
	public void onCreate() {
		super.onCreate();

		//获取全局的Context
		sGlobalContext = getApplicationContext();

		//获取全局Handler
		sGlobalHandler = new Handler();

		//获取主线程ID
		sMainThreadID = android.os.Process.myTid();
	}
}
