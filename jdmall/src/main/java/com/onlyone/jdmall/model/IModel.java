package com.onlyone.jdmall.model;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.presenter
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:13
 * 描述:		Model控制器
 */
public interface IModel<T> {

	void loadData(IListener<T> listener);

	public interface IListener<T> {
		void onSuccess(T data);

		void onError(Exception e);
	}
}
