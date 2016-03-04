package com.onlyone.jdmall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.onlyone.jdmall.presenter.BasePresenter;
import com.onlyone.jdmall.presenter.IViewController;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.activity
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:54
 * 描述:		Activity基类
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements IViewController<T> {
	protected BasePresenter<T> mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPresenter = initPresenter(this);

		mPresenter.triggleInitData();
	}

	@Override
	protected void onPause() {
		super.onPause();

		mPresenter.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		mPresenter.onResume();
	}

	protected abstract BasePresenter<T> initPresenter(BaseActivity<T> tBaseActivity);
}
