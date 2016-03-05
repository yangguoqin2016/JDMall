package com.onlyone.jdmall.pager;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.onlyone.jdmall.R;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.pager
 * 创建者:	落地开花
 * 创建时间:	3/5/2016 9:33
 * 描述:		加载视图基类
 */
public abstract class LoadPager<T> implements LoadListener<T> {
	protected Context mContext;

	protected FrameLayout mRootView;

	public LoadPager(Context context) {
		mContext = context;

		mRootView = loadContainer(mContext);
	}

	public View getRootView() {
		return mRootView;
	}

	/**
	 * 加载视图容器
	 *
	 * @param context 上下文
	 * @return 容器
	 */
	private FrameLayout loadContainer(Context context) {
		return (FrameLayout) View.inflate(context, R.layout.common_container, null);
	}

	/**
	 * 加载数据
	 */
	public final void performLoadData() {
		loadData(this);
	}

	/**
	 * 此处要异步加载数据
	 * <p/>
	 * 成功失败都要调用listener的相对应方法
	 */
	protected abstract void loadData(LoadListener<T> listener);

	@Override
	public void onSuccess(T data) {
		View view = loadSuccessView();

		mRootView.addView(view);

		refreshSuccessView(data);
	}

	/**
	 * 数据加载成功的时候要显示的视图
	 *
	 * @return 视图
	 */
	protected abstract View loadSuccessView();

	/**
	 * 使用成功的数据刷新视图
	 *
	 * @param data 数据
	 */
	protected abstract void refreshSuccessView(T data);

	@Override
	public void onError(Exception e) {
		handleError(e);
	}

	/**
	 * 子类复写这个方法处理错误
	 *
	 * @param e 错误
	 */
	protected abstract void handleError(Exception e);
}
