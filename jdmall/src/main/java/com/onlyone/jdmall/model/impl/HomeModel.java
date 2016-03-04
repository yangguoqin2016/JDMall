package com.onlyone.jdmall.model.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.model.IModel;
import com.onlyone.jdmall.model.bean.HomeBean;
import com.onlyone.jdmall.utils.NetUtil;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.model
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:17
 * 描述:		Home页面的Model
 */
public class HomeModel implements IModel<HomeBean> {
	@Override
	public void loadData(final IListener<HomeBean> listener) {
		StringRequest request = new StringRequest(Request.Method.GET, Url.ADDRESS_HOME,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Gson gson = new Gson();
						try {
							HomeBean bean = gson.fromJson(s, HomeBean.class);
							listener.onSuccess(bean);
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
				});
		NetUtil.getRequestQueue().add(request);
	}
}
