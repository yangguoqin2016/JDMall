package com.onlyone.jdmall.fragment.car;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.MyBaseAdapter;
import com.onlyone.jdmall.bean.AddressBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.SuperBaseFragment_v2;
import com.onlyone.jdmall.holder.AddressListHolder;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.car
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/8 12:24
 * @描述: 地址列表的Fragment
 */
public class AddressListFragment extends SuperBaseFragment_v2<AddressBean>
		implements View.OnClickListener, AdapterView.OnItemClickListener
{
	private static final String TAG        = "AddressListFragment";
	private static final String KEY_USERID = "userid";
	@Bind(R.id.topbar_tv_back)
	TextView mTopbarTvBack;
	private MainActivity                  mMainActivity;
	private View                          mTopBar;
	private ListView                      mListView;
	private List<AddressBean.AddressList> mDatas;
	private AddressListAdapter            mAdapter;

	@Override
	public void onAttach(Context context) {
		mMainActivity = (MainActivity) context;
		super.onAttach(context);
	}

	@Override
	protected String getUrl() {
		return Url.ADDRESS_ADDRESSLIST;
	}

	@Override
	protected Map<String, String> getHeadersMap() {
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put(KEY_USERID, new SPUtil(ResUtil.getContext()).getLong(SP.USERID, 0) + "");
		return headersMap;
	}

	/**
	 * 失败时候的回调
	 *
	 * @param e 异常
	 */
	@Override
	protected void handleError(Exception e) {
		initTopBar();
		FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
		TextView tv = new TextView(ResUtil.getContext());
		tv.setText("加载数据失败,请检查下你的网络..");
		tv.setTextSize(DensityUtil.dip2Px(20));
		tv.setTextColor(Color.BLACK);
		tv.setGravity(Gravity.CENTER);
		rootView.addView(tv);
	}

	/**
	 * 解析json数据
	 *
	 * @param jsonStr
	 * @return
	 */
	@Override
	protected AddressBean parseJson(String jsonStr) {
		Gson gson = new Gson();
		AddressBean bean = gson.fromJson(jsonStr, AddressBean.class);
		return bean;
	}

	/**
	 * 成功时候的回调
	 *
	 * @return
	 */
	@Override
	protected View loadSuccessView() {
		initTopBar();
		mListView = new ListView(mMainActivity);

		mListView.setOnItemClickListener(this);

		return mListView;
	}

	/**
	 * 初始化TopBar
	 * 顺带设置点击事件
	 *
	 * @return
	 */
	private void initTopBar() {
		mTopBar = View.inflate(mMainActivity, R.layout.inflate_topbar_addresslist, null);
		ButterKnife.bind(this, mTopBar);
		mMainActivity.setTopBarView(mTopBar);
		mTopbarTvBack.setOnClickListener(this);
	}

	/**
	 * 加载成功,刷新UI
	 *
	 * @param addressBean 数据
	 */
	@Override
	protected void refreshSuccessView(AddressBean addressBean) {
		mDatas = addressBean.addressList;

		if (mDatas.size() == 0) {
			//如果地址列表为空就添加一个空的视图
			FrameLayout rootView = mLoadPager.getRootView();
			rootView.removeAllViews();
			ImageView emptyImageView = new ImageView(ResUtil.getContext());
			emptyImageView.setImageResource(R.mipmap.empty_address_list);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					DensityUtil.dip2Px(200), DensityUtil.dip2Px(200));
			params.gravity = Gravity.CENTER;
			rootView.addView(emptyImageView, params);

			Toast.makeText(ResUtil.getContext(), "当前没有地址,请前往\"我的\"页面进行添加", Toast.LENGTH_SHORT)
					.show();
		}

		//排序,让默认的地址在第一个
		Collections.sort(mDatas);
		mAdapter = new AddressListAdapter(mDatas);
		mListView.setAdapter(mAdapter);
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	//TODO:事件逻辑实现
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.topbar_tv_back://返回
				goBack();
				break;
			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		for (int i = 0; i < mListView.getChildCount(); i++) {
			AddressListHolder holder = (AddressListHolder) parent.getChildAt(position).getTag();
			holder.mItemAddresslistIvSelected.setSelected(position == i ? true : false);
		}
	}

	class AddressListAdapter extends MyBaseAdapter<AddressBean.AddressList> {
		BaseHolder holder = null;

		public AddressListAdapter(List<AddressBean.AddressList> datas) {
			super(datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				holder = new AddressListHolder();
				convertView = holder.mHolderView;
			} else {
				holder = (BaseHolder) convertView.getTag();
			}
			AddressBean.AddressList addressList = mDatas.get(position);
			holder.setDataAndRefreshUI(addressList);
			return convertView;
		}
	}
}
