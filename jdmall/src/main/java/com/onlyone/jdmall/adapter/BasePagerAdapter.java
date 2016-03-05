package com.onlyone.jdmall.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.adapter
 * 创建者:	落地开花
 * 创建时间:	3/5/2016 15:18
 * 描述:		ViewPager的适配器基类
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

	protected List<T> mData;

	public BasePagerAdapter(List<T> data) {
		mData = data;
	}


	@Override
	public int getCount() {
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = initView(position);
		container.addView(view);
		return view;
	}

	public abstract View initView(int position);

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}