package com.onlyone.jdmall.fragment.car;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.CarProduct;
import com.onlyone.jdmall.bean.CheckoutBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.CarFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment.car
 * 创建者:	落地开花
 * 创建时间:	3/6/2016 19:07
 * 描述:		结算中心界面
 */
public class BalanceFragment extends BaseFragment<CheckoutBean> {
	private static final String TAG = "BalanceFragment";
	private final SPUtil mSPUtil;
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
	private String mUserId;

	private int mPayWay = 1; //默认使用现金支付

	public BalanceFragment() {
		mSPUtil = new SPUtil(ResUtil.getContext());
	}

	@Override
	protected void refreshSuccessView(CheckoutBean data) {
		mIvBalanceIntoaddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AddressListFragment addressFragment = new AddressListFragment();
				goForward(addressFragment);
			}
		});

		mIvBalanceIntocar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});

		mIvBalanceIntopay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PayWayFragment fragment = new PayWayFragment();
				Bundle args = new Bundle();
				args.putInt("payWay", mPayWay);
				fragment.setArguments(args);
				fragment.setOnResultBackListener(new OnResultBack() {
					@Override
					public void onResult(Object result) {
						int way = (int) result;
						if (way < 3) {
							mPayWay = way;
						} else {
							mPayWay = (way - 1);
						}
						LogUtil.i(TAG, "当前选择的支付方式是:", mPayWay);
					}
				});
				goForward(fragment);
			}
		});

		mTvBalanceMoney.setText("￥" + data.checkoutAddup.totalPrice);
		mTvBalanceCount.setText(String.format("共%d件", data.checkoutAddup.totalCount));

		for (int i = 0; i < data.productList.size(); i++) {
			ImageView iv = new ImageView(ResUtil.getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2Px
					(100), DensityUtil.dip2Px(100));
			int margin = DensityUtil.dip2Px(2);
			params.setMargins(margin, margin, margin, margin);
			mLlBalanceImages.addView(iv, i, params);

			String pic = data.productList.get(i).product.pic;
			Picasso.with(ResUtil.getContext())
					.load(Url.ADDRESS_SERVER + pic)
					.fit()
					.into(iv);
		}
	}

	@Override
	protected View loadSuccessView() {
		View view = View.inflate(getContext(), R.layout.balance_center, null);

		ButterKnife.bind(this, view);

		return view;
	}

	@Override
	protected void loadData(final LoadListener<CheckoutBean> listener) {

		final String carData = pickCarData();
		if (TextUtils.isEmpty(carData)) {
			Toast.makeText(ResUtil.getContext(), "没有购物车数据", Toast.LENGTH_SHORT).show();
			return;
		}

		StringRequest request = new StringRequest(Request.Method.POST, Url.ADDRESS_CHECKOUT,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Gson gson = new Gson();
						try {
							CheckoutBean bean = gson.fromJson(s, CheckoutBean.class);
							if (bean.error_code == 0) {
								listener.onSuccess(bean);
							} else {
								listener.onError(new Exception("需要登录" + bean.error_code));
							}
						} catch (JsonSyntaxException e) {
							listener.onError(e);
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						listener.onError(volleyError);
					}
				})
		{
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> header = new HashMap<>();
				header.put("userid", mUserId);
				return header;
			}

			@Override
			public byte[] getBody() throws AuthFailureError {
				String body = "sku=" + carData;
				return body.getBytes();
			}
		};
		NetUtil.getRequestQueue().add(request);
	}

	private String pickCarData() {
		SPUtil spUtil = new SPUtil(ResUtil.getContext());
		String userName = spUtil.getString(SP.USERNAME, null);
		mUserId = spUtil.getLong(SP.USERID, 0) + "";

		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(mUserId) || mUserId.equals("0")) {
			Toast.makeText(ResUtil.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
			return null;
		}

		HashMap<CarProduct, Integer> carMap = CarModel.getInstance().queryCar(userName);

		return CarFragment.buildStringFromCar(carMap).toString();
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity activity = (MainActivity) getActivity();
		View myBar = View.inflate(getContext(), R.layout.inflate_topbar_balance, null);
		activity.setTopBarView(myBar);

		myBar.findViewById(R.id.tv_balance_return).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				((HolderFragment) getParentFragment()).goBack();
			}
		});

	}

	@Override
	protected void handleError(Exception e) {
		Toast.makeText(BalanceFragment.this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	public interface OnResultBack {
		void onResult(Object result);
	}
}
