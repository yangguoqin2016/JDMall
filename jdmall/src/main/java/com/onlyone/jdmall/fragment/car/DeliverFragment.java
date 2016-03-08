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
 * @描述: 送货时间的Fragment
 */
public class DeliverFragment extends BaseFragment<Object> implements View.OnClickListener {
	@Bind(R.id.deliver_iv_all)
	ImageView mDeliverIvAll;
	@Bind(R.id.deliver_iv_weekend)
	ImageView mDeliverIvWeekend;
	@Bind(R.id.deliver_iv_weekday)
	ImageView mDeliverIvWeekday;
	private MainActivity mMainActivity;

	private int mCheckState = 1;
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
			mCheckState = (int) arg.get("deliverWay");
			if (mCheckState == 3) {
				mCheckState++;
			}
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		View topBar = View.inflate(mMainActivity, R.layout.inflate_topbar_deliver, null);
		mMainActivity.setTopBarView(topBar);
		super.onResume();

		topBar.findViewById(R.id.topbar_tv_balance_center).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						goBack();
					}
				});

		mDeliverIvAll.setOnClickListener(this);
		mDeliverIvWeekend.setOnClickListener(this);
		mDeliverIvWeekday.setOnClickListener(this);
	}

	@Override
	protected void refreshSuccessView(Object data) {

	}

	@Override
	protected View loadSuccessView() {
		View contentView = View.inflate(mMainActivity, R.layout.inflate_deliver, null);

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
		mDeliverIvAll.setSelected((mCheckState & 1) != 0);
		mDeliverIvWeekend.setSelected((mCheckState & (1 << 1)) != 0);
		mDeliverIvWeekday.setSelected((mCheckState & (1 << 2)) != 0);
	}

	@Override
	public void onClick(View v) {
		if (v == mDeliverIvAll) {
			mCheckState = 1;
		}
		if (v == mDeliverIvWeekend) {
			mCheckState = 1 << 1;
		}
		if (v == mDeliverIvWeekday) {
			mCheckState = 1 << 2;
		}
		updateCheckState();
	}

	public void setOnResultBackListener(BalanceFragment.OnResultBack listener) {
		mListener = listener;
	}
}
