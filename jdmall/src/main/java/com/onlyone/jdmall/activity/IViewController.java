package com.onlyone.jdmall.activity;

import android.view.View;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:11
 * 描述:		视图控制器
 */
public interface IViewController<T> {
	/**
	 * 当数据到达的时候被调用
	 *
	 * @param data 初始化的结果数据
	 */
	void OnDataArrive(T data);

	/**
	 * 初始化数据失败的时候被调用
	 *
	 * @param e 异常
	 */
	void OnDataError(Exception e);

	/**
	 * 当视图到达的时候被调用
	 *
	 * @param view 具体的视图
	 */
	void OnViewArrive(View view);
}
