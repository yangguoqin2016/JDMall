package com.onlyone.jdmall.fragment;

import android.view.View;

import com.onlyone.jdmall.pager.LoadListener;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	LuoDi
 * 创建时间:	3/5/2016 10:03
 * 描述:		${TODO}
 */
public class HomeFragment extends BaseFragment<Object> {


	@Override
	protected void refreshSuccessView(Object data) {
	}

	@Override
	protected View loadSuccessView() {
		return null;
	}

	@Override
	protected void loadData(final LoadListener<Object> listener) {
	}

	@Override
	protected void handleError(Exception e) {
	}
}
