package com.onlyone.jdmall.pager;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.pager
 * 创建者:	LuoDi
 * 创建时间:	3/5/2016 9:46
 * 描述:		加载结果监听
 */
public interface LoadListener<T> {
	void onSuccess(T data);

	void onError(Exception e);
}
