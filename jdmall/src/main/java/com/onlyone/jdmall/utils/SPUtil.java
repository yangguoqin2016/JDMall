package com.onlyone.jdmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名:	JDMall<br/>
 * 包名:		com.onlyone.jdmall.utils<br/>
 * 创建者:	落地开花<br/>
 * 创建时间:	3/4/2016 15:52<br/>
 * 描述:		SharedPreferences工具类<br/>
 */
public class SPUtil {
	private static final String SP_FILE_NAME = "mainfest";
	private final SharedPreferences mSp;

	/**
	 * 创建SPhelper对象
	 *
	 * @param context 上下文
	 */
	public SPUtil(Context context) {
		mSp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 存储String数据到SharedPreferences中
	 *
	 * @param keyName 键
	 * @param value   值
	 */
	public void putString(String keyName, String value) {
		SharedPreferences.Editor editor = mSp.edit();
		editor.putString(keyName, value);
		editor.apply();
	}

	/**
	 * 从SharedPreferences中读取String数据
	 *
	 * @param keyName      键
	 * @param defaultValue 默认值
	 * @return 返回读取到的String数据
	 */
	public String getString(String keyName, String defaultValue) {
		return mSp.getString(keyName, defaultValue);
	}

	/**
	 * 存储Int数据到SharedPreferences中
	 *
	 * @param keyName 键
	 * @param value   值
	 */
	public void putInt(String keyName, int value) {
		SharedPreferences.Editor editor = mSp.edit();
		editor.putInt(keyName, value);
		editor.apply();
	}

	/**
	 * 从SharedPreferences中读取int数据
	 *
	 * @param keyName      键
	 * @param defaultValue 默认值
	 * @return 返回读取到的int数据
	 */
	public int getInt(String keyName, int defaultValue) {
		return mSp.getInt(keyName, defaultValue);
	}

	/**
	 * 存储boolean数据到SharedPreferences
	 *
	 * @param keyName 键
	 * @param value   值
	 */
	public void putBoolean(String keyName, boolean value) {
		SharedPreferences.Editor editor = mSp.edit();
		editor.putBoolean(keyName, value);
		editor.apply();
	}

	/**
	 * 从SharedPreferences中读取boolean数据
	 *
	 * @param keyName      键
	 * @param defaultValue 默认值
	 * @return 返回读取到的boolean数据
	 */
	public boolean getBoolean(String keyName, boolean defaultValue) {
		return mSp.getBoolean(keyName, defaultValue);
	}
}