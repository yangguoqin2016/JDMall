package com.onlyone.jdmall.presenter;

import android.view.View;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.model.HomeBean;
import com.onlyone.jdmall.model.HomeModelController;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:08
 * 描述:		主页的主导器
 */
public class HomePresenter extends BasePresenter<HomeBean> {

	public HomePresenter(IViewController<HomeBean> view) {
		super(view);
	}

	@Override
	protected View initView() {
		return View.inflate(ResUtil.getContext(), R.layout.activity_splash, null);
	}

	@Override
	protected void initData(IModelController<HomeBean> modelController) {
		modelController.loadData(new IModelController.IListener<HomeBean>() {
			@Override
			public void onSuccess(HomeBean data) {
				mViewController.OnRefreshData(data);
			}

			@Override
			public void onError(Exception e) {
				mViewController.OnRefreshError(e);
			}
		});
	}

	@Override
	protected IModelController<HomeBean> initModelController() {
		return new HomeModelController();
	}
}
