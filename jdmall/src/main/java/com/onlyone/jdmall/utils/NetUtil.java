package com.onlyone.jdmall.utils;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

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
	 * 使用GET方法像服务器请求数据
	 *
	 * @param url      请求链接
	 * @param isGet    是否是GET请求
	 * @param params   GET参数列表
	 * @param clazz    JsonBean类型
	 * @param listener 请求完成回调
	 * @param <T>      Bean泛型
	 */
	public static <T> void sendRequest(String url,
									   boolean isGet,
									   final Map<String, String> params,
									   final Class<T> clazz,
									   @NonNull final Listener<T> listener) {
		int method = isGet ? Request.Method.GET : Request.Method.POST;

		StringRequest stringRequest = new StringRequest(method, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Gson gson = new Gson();
						try {
							// Gson解析
							T result = gson.fromJson(s, clazz);
							listener.onSuccess(result);
						} catch (JsonSyntaxException e) {
							listener.onError(e);
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						listener.onError(volleyError);
					}
				})
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// 附加Params
				if (params != null) {
					return params;
				} else {
					return super.getParams();
				}
			}
		};

		getRequestQueue().add(stringRequest);
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

	/**
	 * 请求监听接口
	 *
	 * @param <T> Bean类型
	 */
	public interface Listener<T> {
		/**
		 * 请求成功
		 *
		 * @param result 数据
		 */
		void onSuccess(T result);

		/**
		 * 请求失败
		 *
		 * @param error 错误信息
		 */
		void onError(Exception error);
	}
}
