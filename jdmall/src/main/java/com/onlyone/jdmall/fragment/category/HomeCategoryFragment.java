package com.onlyone.jdmall.fragment.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.HomeCategoryBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.fragment.SuperBaseFragment;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: yangguoqin
 * @创建时间: 2016/3/5 18:31
 * @描述: ${TODO}
 */
public class HomeCategoryFragment extends SuperBaseFragment<HomeCategoryBean>
		implements View.OnClickListener
{

	@Bind(R.id.category_first_pager_first_img)
	ImageView mCategoryFirstPagerFirstImg;
	@Bind(R.id.category_first_pager_first_text)
	TextView  mCategoryFirstPagerFirstText;
	@Bind(R.id.category_first_pager_second_img)
	ImageView mCategoryFirstPagerSecondImg;
	@Bind(R.id.category_first_pager_second_text)
	TextView  mCategoryFirstPagerSecondText;
	@Bind(R.id.category_first_pager_third_img)
	ImageView mCategoryFirstPagerThirdImg;
	@Bind(R.id.category_first_pager_thrid_text)
	TextView  mCategoryFirstPagerThridText;
	@Bind(R.id.category_first_pager_four_img)
	ImageView mCategoryFirstPagerFourImg;
	@Bind(R.id.category_first_pager_four_text)
	TextView  mCategoryFirstPagerFourText;
	@Bind(R.id.category_first_pager_five_img)
	ImageView mCategoryFirstPagerFiveImg;
	@Bind(R.id.category_first_pager_five_text)
	TextView  mCategoryFirstPagerFiveText;
	@Bind(R.id.category_first_pager_six_img)
	ImageView mCategoryFirstPagerSixImg;
	@Bind(R.id.category_first_pager_six_text)
	TextView  mCategoryFirstPagerSixText;

	//访问的地址
	String baseUrl = "http://188.188.5.72:8080/market";
	@Bind(R.id.category_first_pager_title_text)
	TextView     mCategoryFirstPagerTitleText;
	@Bind(R.id.category_first_pager_first_container)
	LinearLayout mCategoryFirstPagerFirstContainer;
	@Bind(R.id.category_first_pager_second_container)
	LinearLayout mCategoryFirstPagerSecondContainer;
	@Bind(R.id.category_first_pager_third_container)
	LinearLayout mCategoryFirstPagerThirdContainer;
	@Bind(R.id.category_first_pager_four_container)
	LinearLayout mCategoryFirstPagerFourContainer;
	@Bind(R.id.category_first_pager_five_container)
	LinearLayout mCategoryFirstPagerFiveContainer;
	@Bind(R.id.category_first_pager_six_container)
	LinearLayout mCategoryFirstPagerSixContainer;

	private List<HomeCategoryBean.HomeCategoryInfoBean>
			mDatasList;
	private Map<Integer, Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>>> mSuperDatas;

	public        Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>> mFirstDatas;
	public        Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>> mSecondDatas;
	public        Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>> mThirdDatas;
	public        Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>> mFourDatas;
	public        Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>> mFiveDatas;
	public        Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
			.HomeCategoryInfoBean>> mSixDatas;
	public        MainActivity
			mMainActivity;
	private       View
			mTopBarView;
	private       MainActivity
			mActivity;
	//将总的数据拆分成每个大类对应的数据集合,做成静态,方便跳转页面时候获取到数据
	public static List<HomeCategoryBean.HomeCategoryInfoBean>
			mFirstList;
	public static List<HomeCategoryBean.HomeCategoryInfoBean>
			mSecondList;
	public static List<HomeCategoryBean.HomeCategoryInfoBean>
			mThirdList;
	public static List<HomeCategoryBean.HomeCategoryInfoBean>
			mFourList;
	public static List<HomeCategoryBean.HomeCategoryInfoBean>
			mFiveList;
	public static List<HomeCategoryBean.HomeCategoryInfoBean>
			mSixList;
	private       List<Map<HomeCategoryBean.HomeCategoryInfoBean,
			List<HomeCategoryBean.HomeCategoryInfoBean>>>
			mListDataFromSuper;
	private       HomeCategoryPagerFirstFragment
			mHomeCategoryPagerFirstFragment;
	private       HomeCategoryPagerSecondFragment
			mHomeCategoryPagerSecondFragment;
	private       HomeCategoryPagerThirdFragment
			mHomeCategoryPagerThirdFragment;
	private       HomeCategoryPagerFourFragment
			mHomeCategoryPagerFourFragment;
	private       HomeCategoryPagerFiveFragment
			mHomeCategoryPagerFiveFragment;
	private       HomeCategoryPagerSixFragment
			mHomeCategoryPagerSixFragment;

	@Override
	protected void refreshSuccessView(HomeCategoryBean data) {
		//拿多一份数据去进行分类处理
		this.mDatasList = data.category;

		//子线程中去计算
		new Thread() {
			@Override
			public void run() {
				//将总的数据分类
				classifyDatas();
				//将数据再次进行分类
				distributeDatas();
			}
		}.start();

		//数据的前六条就是商品分类的根分类节点
		System.out.println("获取第一个分类节点的访问地址:" + baseUrl + data.category.get(0).pic);

		//注意最大id为59,但对应的父ID超过153,则只能根据已有的id去设置到界面
		//妈妈专区
		Picasso.with(getContext())
				.load(baseUrl + data.category.get(1).pic)
				.centerCrop()
				.fit()
				.into(mCategoryFirstPagerFirstImg);
		//时尚女装
		Picasso.with(getContext())
				.load(baseUrl + data.category.get(9).pic)
				.centerCrop()
				.fit()
				.into(mCategoryFirstPagerSecondImg);
		//宝宝用品
		Picasso.with(getContext())
				.load(baseUrl + data.category.get(23).pic)
				.centerCrop()
				.fit()
				.into(mCategoryFirstPagerThirdImg);
		//日常用品
		Picasso.with(getContext())
				.load(baseUrl + data.category.get(33).pic)
				.centerCrop()
				.fit()
				.into(mCategoryFirstPagerFourImg);
		//儿童服饰
		Picasso.with(getContext())
				.load(baseUrl + data.category.get(55).pic)
				.centerCrop()
				.fit()
				.into(mCategoryFirstPagerFiveImg);
		//儿童玩具
		Picasso.with(getContext())
				.load(baseUrl + data.category.get(26).pic)
				.centerCrop()
				.fit()
				.into(mCategoryFirstPagerSixImg);


	}

	/**
	 * 子线程中当执行完数据分类后,再次对数据进行分到多个子类中
	 */
	private void distributeDatas() {

		mListDataFromSuper = new ArrayList<>();
		Set<Integer> keySet = mSuperDatas.keySet();
		for (Integer key : keySet) {
			Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean.HomeCategoryInfoBean>>
					map = mSuperDatas.get(key);
			//根据存储了要分配给子类数据的集合,当前遍历到的数据给了子类
			mListDataFromSuper.add(map);

			System.out.println("每个子类的map长度为:" + mListDataFromSuper.size());
		}
		initSonDataMap();
	}

	private void initSonDataMap() {
		//创建6个map表
		//分配六个大类数据,存储到集合中去
		mFirstDatas = mListDataFromSuper.get(0);
		mSecondDatas = mListDataFromSuper.get(1);
		mThirdDatas = mListDataFromSuper.get(2);
		mFourDatas = mListDataFromSuper.get(3);
		mFiveDatas = mListDataFromSuper.get(4);
		mSixDatas = mListDataFromSuper.get(5);

		//因为数据第一层点击进去后就显示这个分类的所有子类,则直接将获取到的分类拆分汇合成集合,再传递给不同的大类页面
		mFirstList = transformMap2List(mFirstDatas);
		mSecondList = transformMap2List(mSecondDatas);
		mThirdList = transformMap2List(mThirdDatas);
		mFourList = transformMap2List(mFourDatas);
		mFiveList = transformMap2List(mFiveDatas);
		mSixList = transformMap2List(mSixDatas);

	}

	/**
	 * 将六个大类的数据集合map转换为list
	 *
	 * @param data
	 * @return
	 */
	public List<HomeCategoryBean.HomeCategoryInfoBean> transformMap2List(
			Map<HomeCategoryBean.HomeCategoryInfoBean,
					List<HomeCategoryBean.HomeCategoryInfoBean>> data) {

		List<HomeCategoryBean.HomeCategoryInfoBean> list = new ArrayList<>();

		Set<Map.Entry<HomeCategoryBean.HomeCategoryInfoBean,
				List<HomeCategoryBean.HomeCategoryInfoBean>>> entrySet = data.entrySet();

		Iterator<Map.Entry<HomeCategoryBean.HomeCategoryInfoBean,
				List<HomeCategoryBean.HomeCategoryInfoBean>>> it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry<HomeCategoryBean.HomeCategoryInfoBean,
					List<HomeCategoryBean.HomeCategoryInfoBean>> info = it.next();
			list.add(info.getKey());

			List<HomeCategoryBean.HomeCategoryInfoBean> valueList = info.getValue();
			for (HomeCategoryBean.HomeCategoryInfoBean value : valueList) {
				list.add(value);
			}
		}
		return list;
	}


	/**
	 * 对分类数据进行处理
	 * 按照此计算方法计算后,会出现其中一个第33,即元素的属性中的id=129,少了这个,因为其中的isLeafNode属性和别人不同
	 */
	private void classifyDatas() {

		Map<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>> mapDatas = new HashMap<>();
		int id = 0;
		//遍历大数据集合
		for (int i = 0; i < mDatasList.size(); i++) {
			//存储根节点的id
			id = mDatasList.get(i).id;
			//如果元素的父id是0则是根节点
			if (mDatasList.get(i).parentId == 0) {
				//创建一个集合先存储第二层的元素在集合中的位置
				List<HomeCategoryBean.HomeCategoryInfoBean> list = new ArrayList<>();

				//遍历大数据集合
				for (int j = 0; j < mDatasList.size(); j++) {
					//如果isLeafNode为false,父id不为0,表示不是最终节点,且不是根节点
					if (mDatasList.get(j).isLeafNode == false && mDatasList.get(j).parentId != 0) {
						//当上一层的id为本层元素的父id
						if (id == mDatasList.get(j).parentId) {
							//得到此元素的属性中的id值
							HomeCategoryBean.HomeCategoryInfoBean homeCategoryInfoBean =
									mDatasList.get(
									j);
							if (!list.contains(homeCategoryInfoBean)) {
								//如果不包含这个下标则添加进去
								list.add(homeCategoryInfoBean);
							}
						}
					}
				}
				//当在根id知道的情况下,过滤出第二层的id,添加到list后,
				//存储了第一层根节点的id,和 这个id对应的第二层的子节点形成的集合
				mapDatas.put(id, list);
			}
		}
		//经过两个循环后得到 map<每个根节点的id,这个id对应的子id形成的集合>
		Set<Map.Entry<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>>> entrySet = mapDatas
				.entrySet();
		Iterator<Map.Entry<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>>> iterator =
				entrySet
				.iterator();

		//设置一个最终存储所有关系数据的map,除了根节点外其他都存储实体类
		mSuperDatas = new HashMap<>();

		while (iterator.hasNext()) {
			Map.Entry<Integer, List<HomeCategoryBean.HomeCategoryInfoBean>> next = iterator.next();
			//得到每个根节点的属性中的id值 和 对应的第二层的节点的属性中的id值
			Integer key = next.getKey();
			//第二层节点的分类之一,这个集合包含父id为key的元素的id值
			List<HomeCategoryBean.HomeCategoryInfoBean> value = next.getValue();

			List<HomeCategoryBean.HomeCategoryInfoBean> list = null;

			Map<HomeCategoryBean.HomeCategoryInfoBean, List<HomeCategoryBean
					.HomeCategoryInfoBean>> map = new HashMap<>();
			//遍历存储第二层节点的集合
			for (HomeCategoryBean.HomeCategoryInfoBean info : value) {
				list = new ArrayList();
				//遍历大数据集合
				for (int i = 0; i < mDatasList.size(); i++) {
					int parentId = mDatasList.get(i).parentId;
					//如果元素的父id被这个第二层节点包含,则表明这个元素是这个第二层节点的子节点
					if (info.id == parentId) {
						//将元素的属性中的id添加到集合
						list.add(mDatasList.get(i));
					}
				}
				//存储了第二层每个节点的id,和这个id对应的第三层的子节点形成的集合
				map.put(info, list);
			}
			//最终数据,存的都是元素实体类
			mSuperDatas.put(key, map);
		}

	}

	@Override
	protected View loadSuccessView() {
		View rootView = View.inflate(getContext(), R.layout.category_fragment, null);
		//获取到mainActivity,方便后面的点击跳转
		mMainActivity = (MainActivity) getActivity();
		//小刀绑定
		ButterKnife.bind(this, rootView);
		//开启对条目的点击监听
		initListener();

		return rootView;
	}

	private void initListener() {
		//对六个根分类条目进行点击监听
		mCategoryFirstPagerFirstContainer.setOnClickListener(this);
		mCategoryFirstPagerSecondContainer.setOnClickListener(this);
		mCategoryFirstPagerThirdContainer.setOnClickListener(this);
		mCategoryFirstPagerFourContainer.setOnClickListener(this);
		mCategoryFirstPagerFiveContainer.setOnClickListener(this);
		mCategoryFirstPagerSixContainer.setOnClickListener(this);
	}

	@Override
	protected String getUrl() {
		String url = Url.ADDRESS_SERVER + "/category";
		return url;
	}

	@Override
	protected void handleError(Exception e) {
		Toast.makeText(getContext(), "加载分类数据失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected HomeCategoryBean parseJson(String jsonStr) {
		Gson gson = new Gson();
		HomeCategoryBean bean = gson.fromJson(jsonStr, HomeCategoryBean.class);
		return bean;
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity = (MainActivity) getActivity();
		mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_category, null);
		mActivity.setTopBarView(mTopBarView);
		//点击返回图片返回首页
		mTopBarView.findViewById(R.id.hot_product_topbar_back)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						((HolderFragment)getParentFragment()).goBack();
					}
				});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@Override
	public void onClick(View v) {

		HolderFragment holderFragment = (HolderFragment) getParentFragment();

		switch (v.getId()) {
			case R.id.category_first_pager_first_container:
				//跳转到 妈妈专区
				if (mHomeCategoryPagerFirstFragment == null) {
					//避免多次跳转的时候new多次消耗内存,则判断对象是否为空
					mHomeCategoryPagerFirstFragment = new HomeCategoryPagerFirstFragment();
				}
				holderFragment.goForward(mHomeCategoryPagerFirstFragment);
				break;
			case R.id.category_first_pager_second_container:
				//跳转到 时尚女装
				if (mHomeCategoryPagerSecondFragment == null) {
					mHomeCategoryPagerSecondFragment = new HomeCategoryPagerSecondFragment();
				}
//				transaction.add(R.id.fl_content_container, mHomeCategoryPagerSecondFragment);
				holderFragment.goForward(mHomeCategoryPagerSecondFragment);
				break;
			case R.id.category_first_pager_third_container:
				//跳转到 宝宝用品
				if (mHomeCategoryPagerThirdFragment == null) {
					mHomeCategoryPagerThirdFragment = new HomeCategoryPagerThirdFragment();
				}
//				transaction.add(R.id.fl_content_container, mHomeCategoryPagerThirdFragment);
				holderFragment.goForward(mHomeCategoryPagerThirdFragment);
				break;
			case R.id.category_first_pager_four_container:
				//跳转到 日常用品
				if (mHomeCategoryPagerFourFragment == null) {
					mHomeCategoryPagerFourFragment = new HomeCategoryPagerFourFragment();
				}
//				transaction.add(R.id.fl_content_container, mHomeCategoryPagerFourFragment);
				holderFragment.goForward(mHomeCategoryPagerFourFragment);
				break;
			case R.id.category_first_pager_five_container:
				//跳转到 儿童服饰
				if (mHomeCategoryPagerFiveFragment == null) {
					mHomeCategoryPagerFiveFragment = new HomeCategoryPagerFiveFragment();
				}
//				transaction.add(R.id.fl_content_container, mHomeCategoryPagerFiveFragment);
				holderFragment.goForward(mHomeCategoryPagerFiveFragment);
				break;
			case R.id.category_first_pager_six_container:
				//跳转到 儿童玩具
				if (mHomeCategoryPagerSixFragment == null) {
					mHomeCategoryPagerSixFragment = new HomeCategoryPagerSixFragment();
				}
//				transaction.add(R.id.fl_content_container, mHomeCategoryPagerSixFragment);
				holderFragment.goForward(mHomeCategoryPagerSixFragment);
				break;
			default:
				break;
		}
//		transaction.commit();
	}
}
