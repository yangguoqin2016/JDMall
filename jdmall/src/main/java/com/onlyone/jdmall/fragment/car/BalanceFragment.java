package com.onlyone.jdmall.fragment.car;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.pager.LoadListener;

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
	TextView     mTvBalanceName;
	@Bind(R.id.tv_balance_number)
	TextView     mTvBalanceNumber;
	@Bind(R.id.tv_balance_address)
	TextView     mTvBalanceAddress;
	@Bind(R.id.iv_balance_intoaddress)
	ImageView    mIvBalanceIntoaddress;
	@Bind(R.id.tv_balance_pay)
	TextView     mTvBalancePay;
	@Bind(R.id.iv_balance_intopay)
	ImageView    mIvBalanceIntopay;
	@Bind(R.id.tv_balance_time)
	TextView     mTvBalanceTime;
	@Bind(R.id.tv_balance_way)
	TextView     mTvBalanceWay;
	@Bind(R.id.iv_balance_intotime)
	ImageView    mIvBalanceIntotime;
	@Bind(R.id.tv_balance_type)
	TextView     mTvBalanceType;
	@Bind(R.id.iv_balance_intoticket)
	ImageView    mIvBalanceIntoticket;
	@Bind(R.id.ll_balance_images)
	LinearLayout mLlBalanceImages;
	@Bind(R.id.tv_balance_count)
	TextView     mTvBalanceCount;
	@Bind(R.id.iv_balance_intocar)
	ImageView    mIvBalanceIntocar;
	@Bind(R.id.tv_balance_moneytitle)
	TextView     mTvBalanceMoneytitle;
	@Bind(R.id.tv_balance_money)
	TextView     mTvBalanceMoney;
	@Bind(R.id.ll_balance_moneycount)
	LinearLayout mLlBalanceMoneycount;
	@Bind(R.id.btn_balance_commit)
	Button       mBtnBalanceCommit;

	private MainActivity mActivity;

	@Override
	protected void refreshSuccessView(Object data) {
	}

	@Override
	protected View loadSuccessView() {
		View view = View.inflate(getContext(), R.layout.balance_center, null);

		ButterKnife.bind(this, view);

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
		View myBar = View.inflate(getContext(), R.layout.inflate_topbar_balance, null);
		mActivity.setTopBarView(myBar);

		myBar.findViewById(R.id.tv_balance_return).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				((HolderFragment) getParentFragment()).goBack();
			}
		});

	}

	@Override
	protected void handleError(Exception e) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
