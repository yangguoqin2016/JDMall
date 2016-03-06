package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.pager.LoadPager;

/**
 * 项目名:	JDMall<br/>
 * 包名:		com.onlyone.jdmall.fragment<br/>
 * 创建者:	落地开花<br/>
 * 创建时间:	3/4/2016 21:58<br/>
 * 描述:		Fragment基类<br/>
 * 此类以及此类的子类只能写数据绑定的方法,也就是将数据设置到视图上
 */
public abstract class BaseFragment<T> extends Fragment {

	protected LoadPager<T> mLoadPager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if(!(this instanceof MineFragment)){
			MainActivity mainActivity = (MainActivity) getActivity();
			mainActivity.setHideTopBar(false);
		}
		mLoadPager = new LoadPager<T>(getActivity()) {
			@Override
			protected void refreshSuccessView(T data) {
				BaseFragment.this.refreshSuccessView(data);
			}

			@Override
			protected void handleError(Exception e) {
				BaseFragment.this.handleError(e);
			}

			@Override
			protected void loadData(LoadListener<T> listener) {
				BaseFragment.this.loadData(listener);
			}

			@Override
			protected View loadSuccessView() {
				return BaseFragment.this.loadSuccessView();
			}
		};


		mLoadPager.performLoadData();

		return mLoadPager.getRootView();
	}

	/**
	 * 使用成功数据刷新视图
	 *
	 * @param data 数据
	 */
	protected abstract void refreshSuccessView(T data);

	/**
	 * 子类实现这个方法加载成功视图
	 *
	 * @return 视图
	 */
	protected abstract View loadSuccessView();

	/**
	 * 子类实现这个方法加载数据
	 * 成功失败都必须调用listener的相对应方法
	 *
	 * @param listener 监听
	 */
	protected abstract void loadData(LoadListener<T> listener);

	/**
	 * 处理异常
	 *
	 * @param e 异常
	 */
	protected abstract void handleError(Exception e);
}
