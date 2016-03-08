package com.onlyone.jdmall.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.HomeFastSaleBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: yangguoqin
 * @创建时间: 2016/3/5 11:36
 * @描述: 这是主页点击促销快报后跳转的, 促销快报页面
 */
public class HomeFastSaleFragment extends SuperBaseFragment<HomeFastSaleBean> {
	@Bind(R.id.fastsale_listview_r1_left)
	ImageView      mFastsaleListviewR1Left;
	@Bind(R.id.fastsale_listview_r1_right)
	ImageView      mFastsaleListviewR1Right;
	@Bind(R.id.fastsale_listview_r1)
	RelativeLayout mFastsaleListviewR1;
	@Bind(R.id.fastsale_listview_r2_left)
	ImageView      mFastsaleListviewR2Left;
	@Bind(R.id.fastsale_listview_r2_right)
	ImageView      mFastsaleListviewR2Right;
	@Bind(R.id.fastsale_listview_r2)
	RelativeLayout mFastsaleListviewR2;
	@Bind(R.id.fastsale_listview_r3_left)
	ImageView      mFastsaleListviewR3Left;
	@Bind(R.id.fastsale_listview_r3_right)
	ImageView      mFastsaleListviewR3Right;
	@Bind(R.id.fastsale_listview_r3)
	RelativeLayout mFastsaleListviewR3;
	@Bind(R.id.fastsale_listview_r4_left)
	ImageView      mFastsaleListviewR4Left;
	@Bind(R.id.fastsale_listview_r4_right)
	ImageView      mFastsaleListviewR4Right;
	@Bind(R.id.fastsale_listview_r4)
	RelativeLayout mFastsaleListviewR4;
	@Bind(R.id.fastsale_scrollview)
	ScrollView     mFastsaleScrollview;


	@Bind(R.id.fastsale_listview_r1_left_container)
	RelativeLayout mFastsaleListviewR1LeftContainer;
	@Bind(R.id.fastsale_listview_r1_right_container)
	RelativeLayout mFastsaleListviewR1RightContainer;
	@Bind(R.id.fastsale_listview_r2_left_container)
	RelativeLayout mFastsaleListviewR2LeftContainer;
	@Bind(R.id.fastsale_listview_r2_right_container)
	RelativeLayout mFastsaleListviewR2RightContainer;
	@Bind(R.id.fastsale_listview_r3_left_container)
	RelativeLayout mFastsaleListviewR3LeftContainer;
	@Bind(R.id.fastsale_listview_r3_right_container)
	RelativeLayout mFastsaleListviewR3RightContainer;
	@Bind(R.id.fastsale_listview_r4_left_container)
	RelativeLayout mFastsaleListviewR4LeftContainer;
	@Bind(R.id.fastsale_listview_r4_right_container)
	RelativeLayout mFastsaleListviewR4RightContainer;

	private View         mTopBarView;
	private MainActivity mActivity;

	@Override
	protected void refreshSuccessView(HomeFastSaleBean data) {
		//拿加载成功返回的数据刷新页面

		//使用Picasso添加了两个地址,第一次使用的时候会访问网络,匹配后以后则不会访问网络
		//第一行两个
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(0).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR1Left);
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(1).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR1Right);

		//第二行两个
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(2).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR2Left);
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(3).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR2Right);

		//第三行两个
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(4).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR3Left);
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(5).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR3Right);

		//第四行两个
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(6).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR4Left);
		Picasso.with(getContext()).load(Url.ADDRESS_SERVER + data.topic.get(7).pic)
				.transform(new CropCircleTransformation()).into(mFastsaleListviewR4Right);

	}

	@Override
	protected View loadSuccessView() {
		View rootView = View.inflate(getContext(), R.layout.fastsale_fragement, null);
		ButterKnife.bind(this, rootView);

		return rootView;
	}


	@Override
	protected void handleError(Exception e) {
		//处理加载数据失败的异常
		Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();

	}


	@Override
	protected String getUrl() {
		String url = Url.ADDRESS_SERVER + "/topic?page=1&pageNum=10";
		return url;
	}

	@Override
	protected HomeFastSaleBean parseJson(String jsonStr) {
		Gson gson = new Gson();
		HomeFastSaleBean bean = gson.fromJson(jsonStr, HomeFastSaleBean.class);
		return bean;
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity = (MainActivity) getActivity();
		mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_fastsale, null);
		mActivity.setTopBarView(mTopBarView);
		//点击返回图片返回首页
		mTopBarView.findViewById(R.id.hot_product_topbar_back).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						((HolderFragment) getParentFragment()).goBack();
					}
				});
	}

	/**
	 * back键回退到首页,恢复首页的TopBar
	 */
	private void restoreHomeTopBar() {
		View titlBar = View.inflate(ResUtil.getContext(), R.layout.home_title, null);
		mActivity.setTopBarView(titlBar);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

}
