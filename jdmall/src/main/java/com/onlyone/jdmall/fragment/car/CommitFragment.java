package com.onlyone.jdmall.fragment.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.CommitBean;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment.car
 * 创建者:	落地开花
 * 创建时间:	3/9/2016 16:14 下午
 * 描述:		${TODO}
 */
public class CommitFragment extends BaseFragment<CommitBean> {
	@Bind(R.id.success_tv_indent_num)
	TextView mSuccessTvIndentNum;
	@Bind(R.id.success_tv_pay_money)
	TextView mSuccessTvPayMoney;
	@Bind(R.id.success_tv_pay_type)
	TextView mSuccessTvPayType;
	@Bind(R.id.success_btn_continue)
	Button   mSuccessBtnContinue;
	@Bind(R.id.success_btn_check)
	Button   mSuccessBtnCheck;
	private String mDingdanHao;
	private String mDingdanQian;
	private String mDingdanFangshi;

	@Override
	protected void refreshSuccessView(CommitBean data) {

	}

	@Override
	protected View loadSuccessView() {
		View view = View.inflate(ResUtil.getContext(), R.layout.inflate_commitsuccess, null);

		ButterKnife.bind(this, view);

		mSuccessBtnContinue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).mRgBottomNav.check(R.id.rb_bottom_home);
			}
		});

		mSuccessBtnCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).mRgBottomNav.check(R.id.rb_bottom_mine);
			}
		});

		return view;
	}

	@Override
	protected void loadData(LoadListener<CommitBean> listener) {
		listener.onSuccess(null);
	}

	@Override
	protected void handleError(Exception e) {

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		Bundle arg = getArguments();
		if (arg != null) {
			mDingdanHao = arg.getString("dingdanHao");
			mDingdanQian = arg.getString("dingdanQian");
			mDingdanFangshi = arg.getString("dingdanFangshi");
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();

		View topBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_commitsuccess,
				null);

		//显示订单信息
		mSuccessTvIndentNum.setText("订单号:" + mDingdanHao);
		mSuccessTvPayMoney.setText("应付款:" + mDingdanQian);

		int payWay = Integer.parseInt(mDingdanFangshi);
		switch (payWay) {
			case 1:
				mSuccessTvPayType.setText("支付方式:到付-现金");
				break;
			case 2:
				mSuccessTvPayType.setText("支付方式:到付-POS机");
				break;
			case 3:
				mSuccessTvPayType.setText("支付方式:支付宝");
				break;
		}

		((MainActivity) getActivity()).setTopBarView(topBar);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
