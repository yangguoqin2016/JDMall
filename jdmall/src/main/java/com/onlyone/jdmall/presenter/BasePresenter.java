package com.onlyone.jdmall.presenter;

import android.view.View;

import com.onlyone.jdmall.activity.IViewController;
import com.onlyone.jdmall.model.IModel;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:45
 * 描述:		主导器基类
 */
public abstract class BasePresenter<T> {
	protected IModel<T>          mModelController;
	protected IViewController<T> mViewController;
	protected View               mView;

	public BasePresenter(IViewController<T> view) {
		mViewController = view;

		mView = initView();
		mViewController.OnViewArrive(mView);
	}

	/**
	 * 开始初始化数据
	 */
	public void triggerInitData() {
		if (mModelController == null) {
			mModelController = initModelController();
		}
		if (mModelController != null) {
			initData(mModelController);
		}
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
	protected abstract void initData(IModel<T> modelController);

	/**
	 * 子类实现这个方法来提供Model控制器
	 *
	 * @return Model控制器
	 */
	protected abstract IModel<T> initModelController();
}
