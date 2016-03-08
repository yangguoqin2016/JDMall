package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.CarProduct;
import com.onlyone.jdmall.bean.CartBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.car.BalanceFragment;
import com.onlyone.jdmall.model.CarModel;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.NetUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
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
	 * 购物车商品集合
	 */
	private ArrayList<CartBean.CartEntity> mData = new ArrayList<>();

	private MySwipeAdapter mAdapter;
	private View           mEmptyView;
	private StringRequest  mRequest;
	private View           mBarView;
	private String         mCurrentUser;

	@Override
	protected void refreshSuccessView(CartBean data) {
		//将服务器请求到的数据设置到ListView上
		mData.clear();
		mData.addAll(data.cart);

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
			totalMoney += cartEntity.product.price * cartEntity.prodNum;
			totalCount += cartEntity.prodNum;
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

			StringBuilder sb = buildStringFromCar(savedCar);

			return sb.toString();
		}
	}

	@NonNull
	public static StringBuilder buildStringFromCar(HashMap<CarProduct, Integer> savedCar) {
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry<CarProduct, Integer>> entrySet = savedCar.entrySet();
		for (Map.Entry<CarProduct, Integer> entry : entrySet) {
			int id = entry.getKey().id;
			int[] prop = entry.getKey().prop;
			int count = entry.getValue();

			String productItem;
			if (sb.length() == 0) {
				productItem = String.format("%d:%d:%d", id, count, prop[0]);
			} else {
				productItem = String.format("|%d:%d:%d", id, count, prop[0]);
			}
			sb.append(productItem);

		}
		return sb;
	}

	/**
	 * 获取当前登录用户的用户名
	 *
	 * @return
	 */
	private String getCurrentUser() {
		mCurrentUser = new SPUtil(getContext()).getString(SP.USERNAME, null);
		return mCurrentUser;
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
		mAdapter = new MySwipeAdapter();

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
				if (mData.size() > 0) {
					((HolderFragment) getParentFragment()).goForward(new BalanceFragment());
				}
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

	class MySwipeAdapter extends BaseSwipeAdapter {

		class ViewHolder {
			@Bind(R.id.iv_car_item_pic)
			ImageView    mIvCarItemPic;
			@Bind(R.id.tv_car_shopname)
			TextView     mTvCarShopname;
			@Bind(R.id.tv_car_item_jian)
			ImageView    mTvCarItemJian;
			@Bind(R.id.tv_car_item_num)
			TextView     mTvCarItemNum;
			@Bind(R.id.tv_car_item_jia)
			ImageView    mTvCarItemJia;
			@Bind(R.id.tv_car_item_price)
			TextView     mTvCarItemPrice;
			@Bind(R.id.tv_car_item_xiaoji)
			TextView     mTvCarItemXiaoji;
			@Bind(R.id.ll_car_delete)
			LinearLayout mLl_car_delete;

			ViewHolder(View view) {
				ButterKnife.bind(this, view);
				view.setTag(this);
			}
		}

		@Override
		public int getSwipeLayoutResourceId(int i) {
			return R.id.sl_car_swipe;
		}

		@Override
		public View generateView(int position, ViewGroup viewGroup) {

			return LayoutInflater.from(CarFragment.this.getContext())
					.inflate(R.layout.inflate_car_listitem, null);
		}

		@Override
		public void fillValues(final int position, final View view) {
			final ViewHolder viewHolder = new ViewHolder(view);

			final CartBean.CartEntity cartEntity = mData.get(position);

			viewHolder.mTvCarItemPrice.setText("单价: " + cartEntity.product.price);
			viewHolder.mTvCarItemNum.setText(cartEntity.prodNum + "");
			viewHolder.mTvCarShopname.setText(cartEntity.product.name);

			//设置点击删除的监听
			viewHolder.mLl_car_delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//点击了删除条目的按钮
					mData.remove(position);
					((SwipeLayout) view.findViewById(R.id.sl_car_swipe)).close(true);
					//每次对购物车数据继续修改都保存到本地一次
					HashMap<CarProduct, Integer> productToSave = CarModel.getInstance()
							.transformData(mData);
					CarModel.getInstance().refreshCar(mCurrentUser, productToSave);
					notifyDataSetChanged();
					updateTotalInfo();
				}
			});

			//计算小结的金额
			float money = cartEntity.prodNum * cartEntity.product.price;
			viewHolder.mTvCarItemXiaoji.setText(money + "");

			viewHolder.mTvCarItemJian.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (cartEntity.prodNum > 0) {
						cartEntity.prodNum--;
						viewHolder.mTvCarItemNum.setText(cartEntity.prodNum + "");

						//计算小结的金额
						float money = cartEntity.prodNum * cartEntity.product.price;
						viewHolder.mTvCarItemXiaoji.setText(money + "");

						//每次对购物车数据继续修改都保存到本地一次
						HashMap<CarProduct, Integer> productToSave = CarModel.getInstance()
								.transformData(mData);
						CarModel.getInstance().refreshCar(mCurrentUser, productToSave);

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
						viewHolder.mTvCarItemNum.setText(cartEntity.prodNum + "");

						//计算小结的金额
						float money = cartEntity.prodNum * cartEntity.product.price;
						viewHolder.mTvCarItemXiaoji.setText(money + "");

						//每次对购物车数据继续修改都保存到本地一次
						HashMap<CarProduct, Integer> productToSave = CarModel.getInstance()
								.transformData(mData);
						CarModel.getInstance().refreshCar(mCurrentUser, productToSave);

						//更新总共的金额
						updateTotalInfo();
					}

				}
			});

			Picasso.with(getContext())
					.load(Url.ADDRESS_SERVER + cartEntity.product.pic)
					.fit()
					.centerCrop()
					.into(viewHolder.mIvCarItemPic);
		}

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
			return position;
		}
	}
	/*-------------------- ListView适配器类 - end --------------------*/
}
