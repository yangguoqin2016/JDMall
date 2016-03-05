package com.onlyone.jdmall.presenter;

import android.view.View;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:11
 * 描述:		View视图控制器
 */
public interface IViewController<T> {
	void OnRefreshData(T data);

	void OnRefreshError(Exception e);

	void OnInitView(View view);
}
