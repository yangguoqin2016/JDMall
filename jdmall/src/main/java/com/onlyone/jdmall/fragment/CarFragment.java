package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.CartBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.view.LazyViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	落地开花
 * 创建时间:	3/5/2016 10:21
 * 描述:		购物车页面
 */
public class CarFragment extends BaseFragment<CartBean> {

	@Bind(R.id.cart_count_total)
	TextView      mCartCountTotal;
	@Bind(R.id.cart_money_total)
	TextView      mCartMoneyTotal;
	@Bind(R.id.vp_cart_main)
	LazyViewPager mVpCartMain;
	private View mBarView;

	@Override
	protected void refreshSuccessView(CartBean data) {

	}

	@Override
	protected View loadSuccessView() {
		View view = View.inflate(getContext(), R.layout.cart_layout, null);

		ButterKnife.bind(this, view);

		return view;
	}

	@Override
	protected void loadData(final LoadListener<CartBean> listener) {
		String url = Url.ADDRESS_CART + "?" + getParams();
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Gson gson = new Gson();
						try {
							CartBean cartBean = gson.fromJson(s, CartBean.class);
							listener.onSuccess(cartBean);
						} catch (JsonSyntaxException e) {
							listener.onError(e);
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				listener.onError(volleyError);
			}
		});
		NetUtil.getRequestQueue().add(stringRequest);
	}

	private String getParams() {
		return "sku=29:1:1";
	}

	@Override
	protected void handleError(Exception e) {

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		//加载顶部导航图视图并加入到顶部导航图中
		mBarView = View.inflate(getContext(), R.layout.inflate_car_bar, null);
		((MainActivity) getActivity()).setTopBarView(mBarView);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
