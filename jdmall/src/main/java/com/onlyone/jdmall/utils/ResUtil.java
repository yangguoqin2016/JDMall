package com.onlyone.jdmall.utils;

import android.content.Context;
import android.content.res.Resources;

import com.onlyone.jdmall.application.MyApplication;

/**
 * 项目名:	JDMall<br/>
 * 包名:		com.onlyone.jdmall.utils<br/>
 * 创建者:	落地开花<br/>
 * 创建时间:	3/4/2016 15:17<br/>
 * 描述:		资源相关工具类<br/>
 */
public final class ResUtil {
	private ResUtil() {
	}

	/**
	 * 获取上下文
	 *
	 * @return 返回获取到的上下文
	 */
	public static Context getContext() {
		return MyApplication.sGlobalContext;
	}

	/**
	 * 获取Resource
	 *
	 * @return 返回获取到的Resource对象
	 */
	public static Resources getResource() {
		return getContext().getResources();
	}

	/**
	 * 获取资源文件中的字符串
	 *
	 * @param resId 资源id
	 * @return 返回获取到的字符串
	 */
	public static String getString(int resId) {
		return getResource().getString(resId);
	}

	/**
	 * 获取资源文件中的字符串
	 *
	 * @param resId      资源id
	 * @param formatArgs 格式化占位符
	 * @return 返回获取到的字符串
	 */
	public static String getString(int resId, Object... formatArgs) {
		return getResource().getString(resId, formatArgs);
	}

	/**
	 * 获取资源文件中的颜色
	 *
	 * @param resId 资源id
	 * @return 返回获取到的颜色
	 */
	public static int getColor(int resId) {
		return getResource().getColor(resId);
	}

	/**
	 * 获取资源文件中的String数组
	 *
	 * @param resId 资源id
	 * @return 返回获取到的String数组
	 */
	public static String[] getStringArray(int resId) {
		return getResource().getStringArray(resId);
	}

	/**
	 * 获取资源文件中的Dimension
	 *
	 * @param resId 资源id
	 * @return 返回获取到的Dimension
	 */
	public static float getDimension(int resId) {
		return getResource().getDimension(resId);
	}
}

