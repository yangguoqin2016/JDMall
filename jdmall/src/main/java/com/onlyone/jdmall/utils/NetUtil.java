package com.onlyone.jdmall.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 项目名:	JDMall<br/>
 * 包名:		com.onlyone.jdmall.utils<br/>
 * 创建者:	落地开花<br/>
 * 创建时间:	3/4/2016 16:16<br/>
 * 描述:		网络请求工具类<br/>
 */
public final class NetUtil {
	/**
	 * 请求池
	 */
	private static RequestQueue mQueue;

	private NetUtil() {
	}

	/**
	 * 获取请求池
	 *
	 * @return 请求池
	 */
	public static RequestQueue getRequestQueue() {
		if (mQueue == null) {
			synchronized (NetUtil.class) {
				if (mQueue == null) {
					mQueue = Volley.newRequestQueue(ResUtil.getContext());
				}
			}
		}
		return mQueue;
	}
}
