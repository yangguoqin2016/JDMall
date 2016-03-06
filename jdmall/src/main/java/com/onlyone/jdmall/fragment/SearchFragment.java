package com.onlyone.jdmall.fragment;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.SearchBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 10:48
 * @描述: RadioGroup里面的搜索
 */
public class SearchFragment extends SuperBaseFragment<SearchBean>implements View.OnClickListener {

	@Bind(R.id.item_hot_arrow)
	ImageView    mItemHotArrow;
	@Bind(R.id.search_hot_item_container)
	LinearLayout mSearchHotItemContainer;
	@Bind(R.id.item_history_arrow)
	ImageView    mItemHistoryArrow;
	@Bind(R.id.search_history_item_container)
	ListView     mSearchHistoryItemContainer;
	private MainActivity mMainActivity;
	public static final String TAG_SEARCHRESULT_FRAGMENT = "tag_searchresult_fragment";
	private List<String> mStringList;
	private EditText     mEtKey;

	public SPUtil mSpUtil = new SPUtil(ResUtil.getContext());
	public static View mTopBar;

	@Override
	protected String getUrl() {
		return Url.ADDRESS_SEARCH;
	}

	/**
	 * 处理异常
	 *
	 * @param e
	 *            异常
	 */
	@Override
	protected void handleError(Exception e) {
		FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
		TextView tv = new TextView(ResUtil.getContext());
		tv.setText("加载数据失败,请检查下你的网络..");
		tv.setGravity(Gravity.CENTER);
		rootView.addView(tv);
	}

	@Override
	protected SearchBean parseJson(String jsonStr) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, SearchBean.class);
	}

	@Override
	protected View loadSuccessView() {

		// 先移除掉.
		FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
		rootView.removeAllViews();
		// 搜索页面
		View contentView = View.inflate(ResUtil.getContext(), R.layout.inflate_search, null);
		ButterKnife.bind(this, contentView);
		return contentView;
	}

	@Override
	protected void refreshSuccessView(SearchBean data) {
		mStringList = data.searchKeywords;
		// 动态添加文本
		for (int i = 0; i < mStringList.size(); i++) {
			TextView tv = new TextView(ResUtil.getContext());
			String text = mStringList.get(i);
			tv.setText(text);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(DensityUtil.dip2Px(18));
			int width = LinearLayout.LayoutParams.WRAP_CONTENT;
			int height = LinearLayout.LayoutParams.WRAP_CONTENT;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
			params.leftMargin = DensityUtil.dip2Px(15);
			params.topMargin = DensityUtil.dip2Px(6);
			params.bottomMargin = DensityUtil.dip2Px(6);
			tv.setOnClickListener(this);
			mSearchHotItemContainer.addView(tv, params);
			if (i != mStringList.size() - 1) {
				View line = new View(ResUtil.getContext());
				int lineWidth = LinearLayout.LayoutParams.MATCH_PARENT;
				int lineHeight = DensityUtil.dip2Px(2);
				line.setBackgroundColor(Color.parseColor("#33000000"));
				LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(lineWidth, lineHeight);
				mSearchHotItemContainer.addView(line, lineParams);
			}
		}
		mSearchHistoryItemContainer.setAdapter(new HistoryAdapter());
	}

	/**
	 * 在Fragment显示的时候就设置TopBar,这样就不用成功还是失败都去设置.
	 */
	@Override
	public void onResume() {
		// 1.得到TopBar.
		mTopBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_search, null);

		// 得到TopBar的孩子,设置监听事件
		TextView tvBack = (TextView) mTopBar.findViewById(R.id.topbar_tv_back);
		TextView tvSearch = (TextView) mTopBar.findViewById(R.id.topbar_tv_search);
		ImageView tvIconSearch = (ImageView) mTopBar.findViewById(R.id.topbar_iv_iconsearch);
		mEtKey = (EditText) mTopBar.findViewById(R.id.topbar_et_key);
		tvBack.setOnClickListener(this);
		tvSearch.setOnClickListener(this);
		tvIconSearch.setOnClickListener(this);
		// 2.首先先拿到Fragment关联的Activity
		mMainActivity = (MainActivity) getActivity();

		// 3.得到MainActivity,再设置TopBar的Ui
		mMainActivity.setTopBarView(mTopBar);
		LogUtil.d("vivi","onResume方法被调用了--------");
		super.onResume();
	}

	@Override
	public void onStart() {
		LogUtil.d("vivi","onStart方法被调用了--------");
		super.onStart();
	}

	@Override
	public void onStop() {
		LogUtil.d("vivi","onStop方法被调用了--------");
		super.onStop();
	}

	@Override
	public void onPause() {
		LogUtil.d("vivi","onPause方法被调用了--------");
		super.onPause();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			mMainActivity.setTopBarView(mTopBar);
		}
		LogUtil.d("vivi","onHiddenChanged方法被调用了--------"+hidden);
		super.onHiddenChanged(hidden);
	}


	/**
	 * 返回键的点击事件 返回到首页
	 *
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbar_tv_back:// 返回
			mMainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
			break;
		case R.id.topbar_tv_search:// 搜索
		case R.id.topbar_iv_iconsearch:// 搜索
			//保存关键字
			String searchKey = mEtKey.getText().toString().trim();
			if(TextUtils.isEmpty(searchKey)){
				Toast.makeText(ResUtil.getContext(),"搜索内容不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			processSearchKey(searchKey);
			break;
		default:
			String clickSearchKey  = ((TextView) v).getText().toString().trim();
			Toast.makeText(ResUtil.getContext(), clickSearchKey , Toast.LENGTH_SHORT).show();
			processSearchKey(clickSearchKey);
			break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	class HistoryAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			if(mStringList!=null){
				return mStringList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(mStringList!=null){
				return mStringList.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(convertView==null){
				holder = new ViewHolder();
				holder.tv = new TextView(ResUtil.getContext());
				convertView = holder.tv;
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			String text = mStringList.get(position);
			holder.tv.setText(text);
			holder.tv.setTextColor(Color.BLACK);
			holder.tv.setTextSize(DensityUtil.dip2Px(18));
			int left = DensityUtil.dip2Px(15);
			int top = DensityUtil.dip2Px(6);
			int bottom = DensityUtil.dip2Px(6);
			holder.tv.setPadding(left,top,0,bottom);
			return convertView;
		}
	}


	class ViewHolder{
		TextView tv;
	}


	/**
	 * 处理关键字,并且保存到sp,跳转到SearchResultFragment
	 * @param searchKey
	 */
	private void processSearchKey(String searchKey){
		//保存关键字
		mSpUtil.putString(SP.KEY_SEARCHKEY, searchKey);

		FragmentManager manager = mMainActivity.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		//			transaction.hide(this);
		transaction.add(R.id.fl_content_container, new SearchResultFragment(),
						TAG_SEARCHRESULT_FRAGMENT);
		transaction.commit();
	}
}
