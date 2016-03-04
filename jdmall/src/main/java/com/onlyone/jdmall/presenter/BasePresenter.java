package com.onlyone.jdmall.presenter;

import android.view.View;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:45
 * 描述:		主导器基类
 */
public abstract class BasePresenter<T> {
	protected IModelController<T> mModel;
	protected IViewController<T>  mViewController;
	protected View                mView;

	public BasePresenter(IViewController<T> view) {
		mViewController = view;

		mView = initView();
		mViewController.OnInitView(mView);
	}

	/**
	 * 开始初始化数据
	 */
	public void triggleInitData() {
		if (mModel == null) {
			mModel = initModelController();
		}
		initData(mModel);
	}

	/**
	 * 同生命周期的onResume()方法
	 */
	public void onResume() {

	}

	/**
	 * 同生命周期的onPause()方法
	 */
	public void onPause() {

	}

	/**
	 * 子类实现这个方法提供视图
	 *
	 * @return 提供的视图
	 */
	protected abstract View initView();

	/**
	 * 子类实现这个方法初始化数据
	 *
	 * @param modelController model控制器
	 */
	protected abstract void initData(IModelController<T> modelController);

	/**
	 * 子类实现这个方法来提供Model控制器
	 *
	 * @return Model控制器
	 */
	protected abstract IModelController<T> initModelController();
}
