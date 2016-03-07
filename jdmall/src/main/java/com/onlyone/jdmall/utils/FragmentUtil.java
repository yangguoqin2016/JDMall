package com.onlyone.jdmall.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.utils
 * 创建者:	落地开花
 * 创建时间:	3/7/2016 1:20
 * 描述:		${TODO}
 */
public final class FragmentUtil {
	private FragmentUtil() {
	}

	public static void replaceFragment(@NonNull FragmentActivity activity, int resId,
									   @NonNull Fragment fragment) {

		FragmentManager manager = activity.getSupportFragmentManager();
		FragmentTransaction transaction = manager
				.beginTransaction();

		transaction.replace(resId, fragment, fragment.getClass().getSimpleName());

		transaction.addToBackStack(fragment.getClass().getSimpleName());
		transaction.commit();
	}

	public static void addFragment(@NonNull FragmentActivity activity, int resId,
								   @NonNull Fragment fragment) {

		FragmentManager manager = activity.getSupportFragmentManager();
		FragmentTransaction transaction = manager
				.beginTransaction();

		transaction.add(resId, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/**
	 * 返回到上一级的Fragment
	 *
	 * @param activity 包含回退栈的Activity
	 */
	public static void goBack(@NonNull FragmentActivity activity) {
		activity.getSupportFragmentManager().popBackStack();
	}

	/**
	 * 切换Fragment
	 *
	 * @param activity 包含回退栈的Activity
	 * @param resId    替换的布局Id
	 * @param from     从指定Fragment
	 * @param to       跳转到指定Fragment
	 */
	public static void switchFragment(FragmentActivity activity, int resId,
									  Fragment from, Fragment to) {
		if (from != null) {

			FragmentManager manager = activity.getSupportFragmentManager();
			FragmentTransaction transaction = manager
					.beginTransaction();

			if (!to.isAdded()) {
				transaction.hide(from).add(resId, to).commit();
			} else {
				transaction.hide(from).show(to).commit();
			}
		}
	}
}
