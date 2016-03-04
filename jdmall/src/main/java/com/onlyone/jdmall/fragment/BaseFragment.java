package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.onlyone.jdmall.activity.IViewController;
import com.onlyone.jdmall.presenter.BasePresenter;

/**
 * 项目名:	JDMall<br/>
 * 包名:		com.onlyone.jdmall.fragment<br/>
 * 创建者:	落地开花<br/>
 * 创建时间:	3/4/2016 21:58<br/>
 * 描述:		Fragment基类<br/>
 * 此类以及此类的子类只能写数据绑定的方法,也就是将数据设置到视图上
 */
public abstract class BaseFragment<T> extends Fragment implements IViewController<T> {

	protected BasePresenter<T> mPresenter;
	private   FrameLayout      mViewContainer;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		mViewContainer = new FrameLayout(getActivity());
		mPresenter = initPresenter(this);

		return mViewContainer;
	}

	@Override
	public void onResume() {
		super.onResume();

		mPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();

		mPresenter.onPause();
	}

	@Override
	public void OnViewArrive(View view) {
		//得到View的时候只是很简单的将视图添加到视图容器中
		//子类覆盖这个方法必须先用父类的此方法,或者手动将视图加入到容器中
		mViewContainer.addView(view);
	}

	/**
	 * 子类实现这个方法提供具体的主导器
	 *
	 * @param viewController 需要主导器控制的视图层
	 * @return 主导器
	 */
	protected abstract BasePresenter<T> initPresenter(IViewController<T> viewController);
}
