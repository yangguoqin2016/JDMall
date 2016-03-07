package com.onlyone.jdmall.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.onlyone.jdmall.adapter.MyBaseAdapter;
import com.onlyone.jdmall.bean.CarProduct;
import com.onlyone.jdmall.bean.CartBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.car.BalanceFragment;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.FragmentUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private View          mBarView;

	@Override
	protected void refreshSuccessView(CartBean data) {
		//将服务器请求到的数据设置到ListView上
		mData.clear();
		mData.addAll(data.cart);
		mUnSelectedData.clear();

		//更新购物车统计数据
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

		//向服务器请求数据之前先在本地准备好要请求的数据
		String params = getParams();
		if (TextUtils.isEmpty(params)) {
			//本地没有购物车数据的时候显示空的购物车视图并且不向服务器请求数据
			showEmptyView();
			return;
		}

		String url = Url.ADDRESS_CART + "?sku=" + params;
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

		//真正准备参数
		String currentUser = getCurrentUser();

		if (TextUtils.isEmpty(currentUser)) {
			return null;
		}

		HashMap<CarProduct, Integer> savedCar = CarModel.getInstance()
				.queryCar(currentUser);
		if (savedCar == null || savedCar.size() == 0) {
			return null;
		} else {
			//本地储存的购物车有数据
			//使用本地储存的购物车数据来生成请求服务器的链接

			StringBuilder sb = new StringBuilder();
			Set<Map.Entry<CarProduct, Integer>> entrySet = savedCar.entrySet();
			for (Map.Entry<CarProduct, Integer> entry : entrySet) {
				int id = entry.getKey().id;
				int[] prop = entry.getKey().prop;
				int count = entry.getValue();

				for (int i : prop) {
					String productItem;
					if (sb.length() == 0) {
						productItem = String.format("%d:%d:%d", id, count, i);
					} else {
						productItem = String.format("|%d:%d:%d", id, count, i);
					}
					sb.append(productItem);
				}
			}

			return sb.toString();
		}
	}

	/**
	 * 获取当前登录用户的用户名
	 *
	 * @return
	 */
	private String getCurrentUser() {
		return new SPUtil(getContext()).getString(SP.USERNAME, null);
	}

	@Override
	protected void handleError(Exception e) {
		LogUtil.e(TAG, e);
		showEmptyView();
	}

	/**
	 * 显示空购物车界面
	 */
	private void showEmptyView() {
		mLoadPager.getRootView().addView(mEmptyView);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mAdapter = new MyAdapter(mData);

		//初始化空购物车的视图
		mEmptyView = View.inflate(getContext(), R.layout.inflate_car_empty, null);

		//加载顶部导航图视图并加入到顶部导航图中
		if (mBarView == null) {
			mBarView = View.inflate(getContext(), R.layout.inflate_car_bar, null);
		}

		//修改了顶部bar的视图
		((MainActivity) getActivity()).setTopBarView(mBarView);

		View tvRight = mBarView.findViewById(R.id.tv_car_bar_right);
		tvRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentUtil.replaceFragment(getActivity(), R.id.fl_content_container, new
						BalanceFragment());
			}
		});

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
		//视图不可见的时候取消网络加载任务b
		NetUtil.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return request == mRequest;
			}
		});
	}

	@Override
	public void onDestroyView() {
		ButterKnife.unbind(this);
		super.onDestroyView();

		FragmentManager manager = getActivity().getSupportFragmentManager();
		for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
			manager.popBackStack();
		}
		super.onPause();
	}

	/*-------------------- ListView适配器类 - begin --------------------*/


	class MyAdapter extends MyBaseAdapter<CartBean.CartEntity> {
		public MyAdapter(List<CartBean.CartEntity> datas) {
			super(datas);
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

			final CartBean.CartEntity cartEntity = getItem(position);

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
