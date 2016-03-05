package com.onlyone.jdmall.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 21:53
 * 描述:		Fragment工厂类
 */
public class FragmentFactory {
	private FragmentFactory() {
	}

	private static Map<Integer, BaseFragment> sFragmentMap = new HashMap<>();

	public static final int FRAGMENT_HOME   = 0; //主页
	public static final int FRAGMENT_SEARCH = 1; //搜索
	public static final int FRAGMENT_BAND   = 2; //品牌
	public static final int FRAGMENT_CAR    = 3; //购物车
	public static final int FRAGMENT_MINE   = 4; //我的

	/**
	 * Fragment数量
	 */
	public static final int MAX_FRAGMENT_COUNT = FRAGMENT_MINE + 1;

	/**
	 * 根据传入的Fragment类型获取相对应的Fragment
	 *
	 * @param fragmentType 碎片类型
	 * @return 返回获取到的碎片实例
	 */
	public static BaseFragment getFragment(int fragmentType) {

		BaseFragment baseFragment = sFragmentMap.get(fragmentType);
		if (baseFragment != null) {
			return baseFragment;
		}

		switch (fragmentType) {
			case FRAGMENT_HOME:
				baseFragment = new HomeFragment();
				break;
			case FRAGMENT_SEARCH:
				baseFragment = new SearchFragment();
				break;
			case FRAGMENT_BAND:
				baseFragment = new HomeFragment();
				break;
			case FRAGMENT_CAR:
				baseFragment = new HomeFragment();
				break;
			case FRAGMENT_MINE:
				baseFragment = new HomeFragment();
				break;
		}

		sFragmentMap.put(fragmentType, baseFragment);

		return baseFragment;
	}
}
