package com.onlyone.jdmall.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.android.volley.RequestQueue;
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

	/**
	 * 有效的购物车商品
	 */
	private List<CartBean.CartEntity> mData = new ArrayList<>();

	/**
	 * 购物车中不勾选的商品
	 */
	private List<CartBean.CartEntity> mUnSelectedData = new ArrayList<>();

	private MyAdapter     mAdapter;
	private View          mEmptyView;
	private StringRequest mRequest;

	@Override
	protected void refreshSuccessView(CartBean data) {
		//将服务器请求到的数据设置到ListView上
		mData.clear();
		mData.addAll(data.cart);
		mUnSelectedData.clear();

		updateTotalInfo();

		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected View loadSuccessView() {
		View view = View.inflate(getContext(), R.layout.cart_layout, null);

		ButterKnife.bind(this, view);

		mListView.setAdapter(mAdapter);

		return view;
	}

	@Override
	protected void loadData(final LoadListener<CartBean> listener) {
		String url = Url.ADDRESS_CART + "?" + getParams();
		mRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Gson gson = new Gson();
						try {
							CartBean cartBean = gson.fromJson(s, CartBean.class);
							listener.onSuccess(cartBean);
//							listener.onError(null);
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
				});
		NetUtil.getRequestQueue().add(mRequest);
	}

	/**
	 * 更新总共的金钱数和总共的商品数
	 */
	private void updateTotalInfo() {

		float totalMoney = 0;
		int totalCount = 0;
		//首先计算出金额
		for (CartBean.CartEntity cartEntity : mData) {
			if (!mUnSelectedData.contains(cartEntity)) {
				//只有在当前商品是选中状态下改变数量才会将金额计算到总金额中
				totalMoney += cartEntity.product.price * cartEntity.prodNum;
				totalCount += cartEntity.prodNum;
			}
		}

		mCartCountTotal.setText("数量总计: " + totalCount + "件");
		mCartMoneyTotal.setText("商品总金额(不含运费): " + totalMoney);
	}

	/**
	 * 合成购物车请求服务器商品信息的参数
	 *
	 * @return 参数
	 */
	private String getParams() {
		return "sku=29:5:3|28:2:1";
	}

	@Override
	protected void handleError(Exception e) {
		mLoadPager.getRootView().removeAllViews();
		mLoadPager.getRootView().addView(mEmptyView);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		mAdapter = new MyAdapter();

		//初始化空购物车的视图
		mEmptyView = View.inflate(getContext(), R.layout.inflate_car_empty, null);

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
	public void onResume() {
		super.onResume();

		//加载顶部导航图视图并加入到顶部导航图中
		((MainActivity) getActivity()).setTopBarView(
				View.inflate(getContext(), R.layout.inflate_car_bar, null));

		mLoadPager.performLoadData();
	}

	@Override
	public void onPause() {
		super.onPause();

		//视图不可见的时候取消网络加载任务
		NetUtil.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return request == mRequest;
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	/*-------------------- ListView适配器类 - begin --------------------*/


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

			final CartBean.CartEntity cartEntity = mData.get(position);

			viewHolder.mTvCarItemPrice.setText("单价: " + cartEntity.product.price);
			viewHolder.mTvCarItemNum.setText(cartEntity.prodNum + "");
			viewHolder.mTvCarShopname.setText(cartEntity.product.name);

			if (mUnSelectedData.contains(cartEntity)) {
				viewHolder.mIvCarCheckbox.setImageDrawable(
						new ColorDrawable(Color.TRANSPARENT));
			} else {
				viewHolder.mIvCarCheckbox.setImageResource(R.mipmap.car_sel);
			}
			//计算小结的金额
			float money = cartEntity.prodNum * cartEntity.product.price;
			viewHolder.mTvCarItemXiaoji.setText(money + "");


			final ViewHolder finalViewHolder = viewHolder;
			viewHolder.mTvCarItemJian.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (cartEntity.prodNum > 0) {
						cartEntity.prodNum--;
						finalViewHolder.mTvCarItemNum.setText(cartEntity.prodNum + "");

						//计算小结的金额
						float money = cartEntity.prodNum * cartEntity.product.price;
						finalViewHolder.mTvCarItemXiaoji.setText(money + "");

						//更新总共金钱
						updateTotalInfo();
					}
				}
			});

			viewHolder.mTvCarItemJia.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (cartEntity.prodNum < cartEntity.product.buyLimit) {
						cartEntity.prodNum++;
						finalViewHolder.mTvCarItemNum.setText(cartEntity.prodNum + "");

						//计算小结的金额
						float money = cartEntity.prodNum * cartEntity.product.price;
						finalViewHolder.mTvCarItemXiaoji.setText(money + "");

						//更新总共的金额
						updateTotalInfo();
					}

				}
			});

			View.OnClickListener checkUncheckListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mUnSelectedData.contains(cartEntity)) {
						//操作之前图片应该显示为未选中状态,现在将图片设置成选中状态
						mUnSelectedData.remove(cartEntity);
						finalViewHolder.mIvCarCheckbox.setImageResource(R.mipmap.car_sel);
					} else {
						//当前图片为选中状态
						mUnSelectedData.add(cartEntity);
						finalViewHolder.mIvCarCheckbox.setImageDrawable(
								new ColorDrawable(Color.TRANSPARENT));
					}

					//更新总共的金额
					updateTotalInfo();
				}
			};

			// TODO: 3/6/2016 点击条目跳转到商品详情页面

			viewHolder.mIvCarCheckbox.setOnClickListener(checkUncheckListener);

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
				view.setTag(this);
			}
		}
	}
	/*-------------------- ListView适配器类 - end --------------------*/
}
