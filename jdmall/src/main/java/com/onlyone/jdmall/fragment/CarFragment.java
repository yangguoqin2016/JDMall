package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.onlyone.jdmall.pager.LoadPager;
import com.onlyone.jdmall.utils.NetUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
	private static final String TAG = "CarFragment";
	@Bind(R.id.cart_count_total)
	TextView mCartCountTotal;
	@Bind(R.id.cart_money_total)
	TextView mCartMoneyTotal;
	@Bind(R.id.lv_car_list)
	ListView mListView;

	private View mBarView;

	private List<CartBean.CartEntity> mData;
	private MyAdapter                 mAdapter;

	@Override
	protected void refreshSuccessView(CartBean data) {

		mCartCountTotal.setText("数量总计: " + data.totalCount + "件");
		mCartMoneyTotal.setText("商品总金额(不含运费): " + String.format("%.2f", (float)data.totalPrice));

		//将服务器请求到的数据设置到ListView上
		if (mData == null) {
			mData = new ArrayList<>();
		}
		mData.clear();
		mData.addAll(data.cart);

		if (mAdapter == null) {
			mAdapter = new MyAdapter();
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
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
		return "sku=29:1:1|29:1:1|29:1:1";
	}

	@Override
	protected void handleError(Exception e) {

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		if (mBarView == null) {
			//加载顶部导航图视图并加入到顶部导航图中
			mBarView = View.inflate(getContext(), R.layout.inflate_car_bar, null);
			((MainActivity) getActivity()).setTopBarView(mBarView);
		}

		if (mListView == null) {
			mListView = new ListView(getContext());
		}

		mLoadPager = new LoadPager<CartBean>(getActivity()) {
			@Override
			protected void refreshSuccessView(CartBean data) {
				CarFragment.this.refreshSuccessView(data);
			}

			@Override
			protected void handleError(Exception e) {
				CarFragment.this.handleError(e);
			}

			@Override
			protected void loadData(LoadListener<CartBean> listener) {
				CarFragment.this.loadData(listener);
			}

			@Override
			protected View loadSuccessView() {
				return CarFragment.this.loadSuccessView();
			}
		};

		return mLoadPager.getRootView();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@Override
	public void onResume() {
		super.onResume();

		mLoadPager.performLoadData();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mData != null) {
				return mData.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.inflate_car_listitem, null);
				viewHolder = new ViewHolder(convertView);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			CartBean.CartEntity cartEntity = mData.get(position);
			viewHolder.mTvCarItemPrice.setText(cartEntity.product.price + "");

			Picasso.with(getContext())
					.load(Url.ADDRESS_SERVER + cartEntity.product.pic)
					.fit()
					.centerCrop()
					.into(viewHolder.mIvCarItemPic);

			return convertView;
		}


		class ViewHolder {
			@Bind(R.id.iv_car_checkbox)
			ImageView mIvCarCheckbox;
			@Bind(R.id.iv_car_item_pic)
			ImageView mIvCarItemPic;
			@Bind(R.id.tv_car_shopname)
			TextView  mTvCarShopname;
			@Bind(R.id.tv_car_item_jian)
			ImageView mTvCarItemJian;
			@Bind(R.id.tv_car_item_num)
			TextView  mTvCarItemNum;
			@Bind(R.id.tv_car_item_jia)
			ImageView mTvCarItemJia;
			@Bind(R.id.tv_car_item_price)
			TextView  mTvCarItemPrice;
			@Bind(R.id.tv_car_item_xiaoji)
			TextView  mTvCarItemXiaoji;

			ViewHolder(View view) {
				ButterKnife.bind(this, view);
			}
		}
	}
}
