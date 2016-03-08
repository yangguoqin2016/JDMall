package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.pager.LoadListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 20:41
 * @描述: 支付方式的Fragment
 */
public class PayWayFragment extends BaseFragment<Object> implements View.OnClickListener {
	@Bind(R.id.payway_iv_cash)
	ImageView mPaywayIvCash;
	@Bind(R.id.payway_iv_pos)
	ImageView mPaywayIvPos;
	@Bind(R.id.payway_iv_zhifubao)
	ImageView mPaywayIvZhifubao;
	private MainActivity mMainActivity;

	private int mCheckState = 4;
	private BalanceFragment.OnResultBack mListener;

	@Override
	public void onAttach(Context context) {
		mMainActivity = (MainActivity) context;
		super.onAttach(context);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		Bundle arg = getArguments();
		if (arg != null) {
			mCheckState = (int) arg.get("payWay");
			if (mCheckState == 3) {
				mCheckState++;
			}
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		View topBar = View.inflate(mMainActivity, R.layout.inflate_topbar_payway, null);
		mMainActivity.setTopBarView(topBar);
		super.onResume();

		topBar.findViewById(R.id.topbar_tv_balance_center).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						goBack();
					}
				});

		mPaywayIvCash.setOnClickListener(this);
		mPaywayIvPos.setOnClickListener(this);
		mPaywayIvZhifubao.setOnClickListener(this);
	}

	@Override
	protected void refreshSuccessView(Object data) {

	}

	@Override
	protected View loadSuccessView() {
		View contentView = View.inflate(mMainActivity, R.layout.inflate_payway, null);

		ButterKnife.bind(this, contentView);

		updateCheckState();

		return contentView;
	}

	@Override
	protected void loadData(LoadListener<Object> listener) {
		listener.onSuccess(null);
	}

	@Override
	protected void handleError(Exception e) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);

		if (mListener != null) {
			mListener.onResult(mCheckState);
		}
	}

	public void updateCheckState() {
		mPaywayIvCash.setSelected((mCheckState & 1) != 0);
		mPaywayIvPos.setSelected((mCheckState & (1 << 1)) != 0);
		mPaywayIvZhifubao.setSelected((mCheckState & (1 << 2)) != 0);
	}

	@Override
	public void onClick(View v) {
		if (v == mPaywayIvCash) {
			mCheckState = 1;
		}
		if (v == mPaywayIvPos) {
			mCheckState = 1 << 1;
		}
		if (v == mPaywayIvZhifubao) {
			mCheckState = 1 << 2;
		}
		updateCheckState();
	}

	public void setOnResultBackListener(BalanceFragment.OnResultBack listener) {
		mListener = listener;
	}
}
