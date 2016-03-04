package com.onlyone.jdmall.activity.impl;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.model.bean.HomeBean;
import com.onlyone.jdmall.presenter.BasePresenter;
import com.onlyone.jdmall.presenter.HomePresenter;

public class MainActivity extends BaseActivity<HomeBean> {
	private View     mView;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected BasePresenter<HomeBean> initPresenter(BaseActivity<HomeBean> homeBeanBaseActivity) {
		return new HomePresenter(this);
	}

	@Override
	public void OnRefreshData(HomeBean data) {
		mTextView.setText(data.toString());
	}

	@Override
	public void OnRefreshError(Exception e) {
		mTextView.setText(e.toString());
	}

	@Override
	public void OnInitView(View view) {
		mView = view;

		mTextView = (TextView) view.findViewById(R.id.tv_main);

		setContentView(view);
	}
}
