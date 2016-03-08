package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.utils.LogUtil;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	落地开花
 * 创建时间:	3/8/2016 13:54 下午
 * 描述:		空的Fragment,用于管理子Fragment
 */
public abstract class HolderFragment extends Fragment {
	private static final String TAG = "HolderFragment";
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		
		Fragment childFragment = getChildFragment();

		FragmentManager manager = getChildFragmentManager();

		manager.popBackStackImmediate(null, 1);

		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.fl_content_container, childFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		return View.inflate(getContext(), R.layout.common_container, null);
	}
	
	protected abstract Fragment getChildFragment();
	
	public boolean goBack() {
		FragmentManager manager = getChildFragmentManager();
		if (manager.getBackStackEntryCount() > 1) {
			manager.popBackStack();
			return true;
		} else {
			return false;
		}
	}

	public void goForward(Fragment fragment) {
		FragmentManager childFragmentManager = getChildFragmentManager();
		FragmentTransaction transaction = childFragmentManager.beginTransaction();

		transaction.replace(R.id.fl_content_container, fragment);

		transaction.addToBackStack(this.getClass().getSimpleName());
		transaction.commit();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.i(TAG, "onDestroyView");
	}
}
