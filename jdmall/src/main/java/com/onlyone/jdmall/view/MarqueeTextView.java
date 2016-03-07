package com.onlyone.jdmall.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView {

	// 在布局中有使用这个控件的时候,系统在构建这个类对象时,就会调用下面两种构造方法其中的一种
	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		/*
		 * android:singleLine="true" //单行 android:focusable="true" //获取焦点
		 * android:focusableInTouchMode="true" //如果按压也能拿到焦点
		 * android:ellipsize="marquee" //省略的模式 ， 跑马灯效果
		 * android:marqueeRepeatLimit="marquee_forever" //一直跑
		 */
		setSingleLine(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		
		if(hasWindowFocus){
			super.onWindowFocusChanged(hasWindowFocus);
		}
	}
	
	
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		
		//只有当有焦点的时候才调用textview的此方法,失去焦点则什么都不做
		if(focused){
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}
	

	// 使用代码创建view的时候调用
	public MarqueeTextView(Context context) {
		super(context);
	}
	
	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}

}
