package com.onlyone.jdmall.fragment.car;

import android.view.View;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.FragmentUtil;
import com.onlyone.jdmall.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment.car
 * 创建者:	落地开花
 * 创建时间:	3/6/2016 19:07
 * 描述:		结算中心界面
 */
public class BalanceFragment extends BaseFragment<Object> {
	private static final String TAG = "BalanceFragment";

	@Bind(R.id.tv_balance_name)
	TextView mTvBalanceName;
	@Bind(R.id.tv_balance_number)
	TextView mTvBalanceNumber;
	@Bind(R.id.tv_balance_address)
	TextView mTvBalanceAddress;

	private MainActivity mActivity;

	@Override
	protected void refreshSuccessView(Object data) {
	}

	@Override
	protected View loadSuccessView() {
		View view = View.inflate(getContext(), R.layout.balance_center, null);

		ButterKnife.bind(this, view);

		mTvBalanceAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtil.i(TAG, "点击了地址栏");
				FragmentUtil.goBack(mActivity);
			}
		});

		return view;
	}

	@Override
	protected void loadData(LoadListener<Object> listener) {
		listener.onSuccess(null);
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity = (MainActivity) getActivity();
		mActivity.setOnBackPreseedListener(null);

		View myBar = View.inflate(getContext(), R.layout.inflate_topbar_balance, null);

		mActivity.setTopBarView(myBar);
	}

	@Override
	protected void handleError(Exception e) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);

		LogUtil.i(TAG, "BalanceFragment->onDestroy()");
	}
}
