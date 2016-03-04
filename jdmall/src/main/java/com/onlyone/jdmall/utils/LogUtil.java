package com.onlyone.jdmall.utils;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.utils
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 15:20
 * 描述:		日志输出工具类
 */
public final class LogUtil {
	/*打印日志时使用的标签*/
	public static       String sTag          = UtilConstant.LOG_TAG;
	/*日志级别为NONE*/
	public static final int    LEVEL_NONE    = 0;
	/*日志级别为ALL*/
	public static final int    LEVEL_ALL     = 63;
	/*日志级别为V*/
	public static final int    LEVEL_VERBOSE = 1;
	/*日志级别为D*/
	public static final int    LEVEL_DEBUG   = 1 << 1;
	/*日志级别为I*/
	public static final int    LEVEL_INFO    = 1 << 2;
	/*日志级别为W*/
	public static final int    LEVEL_WARN    = 1 << 3;
	/*日志级别为E*/
	public static final int    LEVEL_ERROR   = 1 << 4;
	/*日志级别为S*/
	public static final int    LEVEL_SYSTEM  = 1 << 5;

	/*日志打印级别*/
	private static int sLogLv = UtilConstant.LOG_LEVEL;

	private LogUtil() {
	}

	//*****************固定TAG的日志输出函数****************

	/**
	 * 以V级别输出日志,变长参数第一个默认是TAG,使用默认TAG请传null
	 *
	 * @param msg 日志信息
	 */
	public static void v(Object... msg) {
		if ((sLogLv & LEVEL_VERBOSE) != 0) {
			String tag = msg[0] == null ? sTag : msg[0].toString();
			String s = concatMsg(msg);
			Log.v(tag, s);
		}
	}

	/**
	 * 以D级别输出日志,变长参数第一个默认是TAG,使用默认TAG请传null
	 *
	 * @param msg 日志信息
	 */
	public static void d(Object... msg) {
		if ((sLogLv & LEVEL_DEBUG) != 0) {
			String tag = msg[0] == null ? sTag : msg[0].toString();
			String s = concatMsg(msg);
			Log.d(tag, s);
		}
	}

	/**
	 * 以I级别输出日志,变长参数第一个默认是TAG,使用默认TAG请传null
	 *
	 * @param msg 日志信息
	 */
	public static void i(Object... msg) {
		if ((sLogLv & LEVEL_INFO) != 0) {
			String tag = msg[0] == null ? sTag : msg[0].toString();
			String s = concatMsg(msg);
			Log.i(tag, s);
		}
	}

	/**
	 * 以W级别输出日志,变长参数第一个默认是TAG,使用默认TAG请传null
	 *
	 * @param msg 日志信息
	 */
	public static void w(Object... msg) {
		if ((sLogLv & LEVEL_WARN) != 0) {
			String tag = msg[0] == null ? sTag : msg[0].toString();
			String s = concatMsg(msg);
			Log.w(tag, s);
		}
	}

	/**
	 * 以E级别输出日志,变长参数第一个默认是TAG,使用默认TAG请传null
	 *
	 * @param msg 日志信息
	 */
	public static void e(Object... msg) {
		if ((sLogLv & LEVEL_ERROR) != 0) {
			String tag = msg[0] == null ? sTag : msg[0].toString();
			String s = concatMsg(msg);
			Log.e(tag, s);
		}
	}

	/**
	 * 以S级别输出日志,变长参数第一个默认是TAG,使用默认TAG请传null
	 *
	 * @param msg 日志信息
	 */
	public static void s(Object... msg) {
		if ((sLogLv & LEVEL_SYSTEM) != 0) {
			String tag = msg[0] == null ? sTag : msg[0].toString();
			String s = concatMsg(msg);
			System.out.println("----------" + tag + s + "----------");
		}
	}

	@NonNull
	private static String concatMsg(Object[] msg) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < msg.length; i++) {
			sb.append(msg[i]);
		}
		return sb.toString();
	}
}

