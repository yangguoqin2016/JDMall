package com.onlyone.jdmall.presenter;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.IViewController;
import com.onlyone.jdmall.model.IModel;
import com.onlyone.jdmall.model.bean.HomeBean;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/5/2016 0:39
 * 描述:		Home页面的主导器
 */
public class HomePresenter extends BasePresenter<HomeBean> {
	public HomePresenter(IViewController<HomeBean> view) {
		super(view);
	}

	@Override
	protected View initView() {

		TextView textView = new TextView(ResUtil.getContext());
		textView.setText("HomePresenter");
		textView.setTextSize(DensityUtil.dip2Px(20));
		textView.setTextColor(ResUtil.getColor(R.color.colorAccent));
		textView.setGravity(Gravity.CENTER);

		return textView;
	}

	@Override
	protected void initData(IModel<HomeBean> modelController) {

	}

	@Override
	protected IModel<HomeBean> initModelController() {
		return null;
	}
}
