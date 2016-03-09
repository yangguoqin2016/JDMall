package com.onlyone.jdmall.fragment.car;

import android.os.Bundle;
import android.os.SystemClock;
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
import com.onlyone.jdmall.bean.CommitBean;
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
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment.car
 * 创建者:	落地开花
 * 创建时间:	3/6/2016 19:07
 * 描述:		结算中心界面
 */
public class BalanceFragment extends BaseFragment<CheckoutBean> implements View.OnClickListener {
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

	private int         mPayWay      = 1; //默认使用现金支付
	private int         mDeliverTime = 1;//默认周一至周五送货
	private TicketInfo  mTicketInfo  = new TicketInfo(1, "二狗子", 1);//发票信息
	private AddressInfo mAddressInfo = new AddressInfo();

	public BalanceFragment() {
		mSPUtil = new SPUtil(ResUtil.getContext());
	}

	@Override
	protected void refreshSuccessView(CheckoutBean data) {

		updateShowInfo();

		mIvBalanceIntoaddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AddressListFragment addressFragment = new AddressListFragment();
				addressFragment.setOnResultBackListener(new OnResultBack() {
					@Override
					public void onResult(Object result) {
						mAddressInfo = (AddressInfo) result;
					}
				});
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
						int payWay = (int) result;
						if (payWay < 3) {
							mPayWay = payWay;
						} else {
							mPayWay = (payWay - 1);
						}
						LogUtil.i(TAG, "当前选择的支付方式是:", mPayWay);
					}
				});
				goForward(fragment);
			}
		});

		mIvBalanceIntotime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DeliverFragment fragment = new DeliverFragment();
				Bundle args = new Bundle();
				args.putInt("deliverWay", mDeliverTime);
				fragment.setArguments(args);
				fragment.setOnResultBackListener(new OnResultBack() {
					@Override
					public void onResult(Object result) {
						int time = (int) result;
						if (time < 3) {
							mDeliverTime = time;
						} else {
							mDeliverTime = (time - 1);
						}
						LogUtil.i(TAG, "当前选择的送货时间是:", mDeliverTime);
					}
				});
				goForward(fragment);
			}
		});

		mIvBalanceIntoticket.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ReceiptFragment fragment = new ReceiptFragment();

				Bundle args = new Bundle();
				args.putInt("ticketType", mTicketInfo.mTicketType);
				args.putInt("headerType", mTicketInfo.mTicketHeadType);
				args.putString("header", mTicketInfo.mTicketHeader);

				fragment.setArguments(args);
				fragment.setOnResultBackListener(new OnResultBack() {
					@Override
					public void onResult(Object result) {
						mTicketInfo = (TicketInfo) result;
						LogUtil.i(TAG, mTicketInfo);
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

		mBtnBalanceCommit.setOnClickListener(this);
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

		updateShowInfo();
	}

	@Override
	protected void handleError(Exception e) {
		Toast.makeText(BalanceFragment.this.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				SystemClock.sleep(1000);
				goBack();
			}
		}).start();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	/**
	 * 更新界面显示信息
	 */
	public void updateShowInfo() {

		if (mTvBalanceName != null) {
			mTvBalanceName.setText(mAddressInfo.mName);
		}

		if (mTvBalanceAddress != null) {
			mTvBalanceAddress.setText(mAddressInfo.mAddress);
		}

		if (mTvBalanceNumber != null) {
			mTvBalanceNumber.setText(mAddressInfo.mNumber);
		}

		//更新显示信息
		String payWay = "";
		switch (mPayWay) {
			case 1:
				payWay = "货到付款-现金支付(货到付款)";
				break;
			case 2:
				payWay = "货到付款-现金支付(Pos机支付)";
				break;
			case 3:
				payWay = "提前支付-在线付款(支付宝支付)";
				break;
		}

		if (mTvBalancePay != null) {
			mTvBalancePay.setText(payWay);
		}

		String sendTime = "";
		switch (mDeliverTime) {
			case 1:
				sendTime = "周一至周五送货";
				break;
			case 2:
				sendTime = "双休日及公众假期送货";
				break;
			case 3:
				sendTime = "时间不限,工作日双休日及公众假期均可送货";
				break;
		}

		if (mTvBalanceTime != null) {
			mTvBalanceTime.setText("送货时间" + sendTime);
		}

		String ticketHeader = "";
		String ticketType = "";
		switch (mTicketInfo.mTicketHeadType) {
			case 1:
				ticketHeader = "个人";
				break;
			case 2:
				ticketHeader = "公司";
				break;
		}

		switch (mTicketInfo.mTicketType) {
			case 1:
				ticketType = "图书";
				break;
			case 2:
				ticketType = "服装";
				break;
			case 3:
				ticketType = "耗材";
				break;
			case 4:
				ticketType = "软件";
				break;
			case 5:
				ticketType = "资料";
				break;
		}

		if (mTvBalanceType != null) {
			mTvBalanceType.setText(ticketHeader + "/" + ticketType);
		}
	}

	@Override
	public void onClick(View v) {

		if (mAddressInfo.isEmpty()) {
			Toast.makeText(ResUtil.getContext(), "请确认订单信息", Toast.LENGTH_SHORT).show();
			return;
		}

		StringRequest request = new StringRequest(Request.Method.POST,
				Url.Address_COMMIT,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Gson gson = new Gson();
						CommitBean bean = gson.fromJson(s, CommitBean.class);
						if (bean.response.equalsIgnoreCase("orderSubmit")) {
							LogUtil.i(TAG, s);
							//清理购物车
							CarModel.getInstance().clearCar(mSPUtil.getString(SP.USERNAME, ""));
							//提交成功,前往提交结果页面
							CommitFragment commitFragment = new CommitFragment();
							Bundle args = new Bundle();
							args.putString("dingdanHao", bean.orderInfo.orderId);
							args.putString("dingdanQian", bean.orderInfo.price + "");
							args.putString("dingdanFangshi", bean.orderInfo.paymentType + "");
							commitFragment.setArguments(args);
							goForward(commitFragment);
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(ResUtil.getContext(), "提交失败", Toast.LENGTH_SHORT).show();
					}
				})
		{
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<>();
				map.put("userid", mSPUtil.getLong(SP.USERID, 0) + "");
				return map;
			}

			@Override
			public byte[] getBody() throws AuthFailureError {
				HashMap<CarProduct, Integer> shopList = CarModel.getInstance()
						.queryCar(mSPUtil.getString(SP.USERNAME, ""));
				Set<Map.Entry<CarProduct, Integer>> entries = shopList.entrySet();
				StringBuilder sb = new StringBuilder();
				sb.append("sku=");
				for (Map.Entry<CarProduct, Integer> entry : entries) {
					CarProduct key = entry.getKey();
					Integer value = entry.getValue();
					sb.append(String.format("%d:%d:%s|", key.id, value, translateProp(key
							.prop)));
				}
				sb.substring(0, sb.length() - 1);
				sb.append("&addressId=")
						.append(mAddressInfo.mId)
						.append("&paymentType=")
						.append(mPayWay)
						.append("&deliveryType=")
						.append(mDeliverTime)
						.append("&invoiceType=")
						.append(mTicketInfo.mTicketHeadType)
						.append("&invoiceTitle=")
						.append(mTicketInfo.mTicketHeader)
						.append("&invoiceContent=")
						.append(mTicketInfo.mTicketType);

				return sb.toString().getBytes();
			}
		};

		NetUtil.getRequestQueue().add(request);
	}

	public String translateProp(int[] prop) {
		StringBuilder sb = new StringBuilder();
		for (int aProp : prop) {
			sb.append(aProp);
			sb.append(",");
		}
		sb.substring(0, sb.length() - 1);
		return sb.toString();
	}

	public interface OnResultBack {
		void onResult(Object result);
	}

	/**
	 * 发票信息
	 */
	public static class TicketInfo {
		public TicketInfo(int ticketHeadType, String ticketHeader, int ticketType) {
			mTicketHeadType = ticketHeadType;
			mTicketHeader = ticketHeader;
			mTicketType = ticketType;
		}

		public TicketInfo() {
		}

		public int    mTicketHeadType;
		public String mTicketHeader;
		public int    mTicketType;

		@Override
		public String toString() {
			return "TicketInfo{" +
					"mTicketHeadType=" + mTicketHeadType +
					", mTicketHeader='" + mTicketHeader + '\'' +
					", mTicketType=" + mTicketType +
					'}';
		}
	}

	public static class AddressInfo {
		public String mName;
		public String mNumber;
		public String mAddress;
		public int    mId;

		public AddressInfo() {
		}

		public AddressInfo(String name, String number, String address, int id) {
			mName = name;
			mNumber = number;
			mAddress = address;
			mId = id;
		}

		public boolean isEmpty() {
			if(mName == null || mNumber == null || mAddress == null) {
				return true;
			}
			return mName.isEmpty() && mNumber.isEmpty() && mAddress.isEmpty();
		}
	}
}
