package com.onlyone.jdmall.utils;

import android.os.Handler;
import android.os.Process;

import com.onlyone.jdmall.application.MyApplication;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.utils
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 15:06
 * 描述:		UI以及主线程相关工具类
 */
public final class UIUtil {
	private UIUtil() {
	}

	/**
	 * 在Ui线程运行Runnable
	 *
	 * @param runnable 需要运行的Runnable
	 */
	public static void runSafely(Runnable runnable) {
		int uiThreadId = getUiThreadId();

		int currentThreadId = Process.myTid();
		if (currentThreadId == uiThreadId) {
			//当前线程是Ui线程
			runnable.run();
		} else {
			//当前线程不是Ui线程,获得Ui线程的Handler
			Handler uiHandler = getUiHandler();
			uiHandler.post(runnable);
		}
	}

	/**
	 * 获取Ui线程的Handler
	 *
	 * @return 返回Ui线程的Handler
	 */
	public static Handler getUiHandler() {
		return MyApplication.sGlobalHandler;
	}

	/**
	 * 获得Ui线程的线程id
	 *
	 * @return 返回获得的线程id
	 */
	public static int getUiThreadId() {
		return MyApplication.sMainThreadID;
	}
}
