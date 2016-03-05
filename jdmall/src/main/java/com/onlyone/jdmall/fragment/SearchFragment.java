package com.onlyone.jdmall.fragment;

import com.onlyone.jdmall.activity.IViewController;
import com.onlyone.jdmall.model.bean.HomeBean;
import com.onlyone.jdmall.presenter.BasePresenter;
import com.onlyone.jdmall.presenter.SearchPresenter;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 23:22
 * 描述:		实现BaseFragment的模板代码
 */
public class SearchFragment extends BaseFragment<HomeBean> {

	@Override
	protected BasePresenter<HomeBean> initPresenter(IViewController<HomeBean> viewController) {
		return new SearchPresenter(this);
	}

	@Override
	public void OnDataArrive(HomeBean data) {
		//此处绑定数据
	}

	@Override
	public void OnDataError(Exception e) {
		//此处设置错误视图

	}
}
