package com.onlyone.jdmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.view
 * 创建者:	落地开花
 * 创建时间:	3/5/2016 0:27
 * 描述:		不能触摸切换的懒加载ViewPager
 */
public class NoScrollLazyViewPager extends LazyViewPager {
	public NoScrollLazyViewPager(Context context) {
		this(context, null);
	}

	public NoScrollLazyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//不对触摸事件进行响应
		return false;
	}
}
