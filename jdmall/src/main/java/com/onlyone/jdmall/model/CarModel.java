package com.onlyone.jdmall.model;

import com.onlyone.jdmall.bean.CarProduct;
import com.onlyone.jdmall.utils.SerializeUtil;

import java.util.HashMap;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.model
 * 创建者:	LuoDi
 * 创建时间:	3/6/2016 12:42
 * 描述:		${TODO}
 */
public class CarModel {
	private static CarModel sInstance;

	private CarModel() {
	}

	public static CarModel getInstance() {
		if (sInstance == null) {
			synchronized (CarModel.class) {
				if (sInstance == null) {
					sInstance = new CarModel();
				}
			}
		}

		return sInstance;
	}

	/**
	 * 添加商品到购物车
	 *
	 * @param userName 添加到指定用户名下的购物车
	 * @param goods    进行添加的商品
	 */
	public synchronized void addToCar(String userName, CarProduct goods) {
		HashMap<CarProduct, Integer> result = SerializeUtil.serializeObject(userName);
		if (result == null) {
			result = new HashMap<>();
		}

		int afterCount = 1;
		if (result.containsKey(goods)) {
			//先前购物车里面有该商品了,需要对数量进行增加操作
			afterCount = result.get(goods); //获取该商品先前的数量
			afterCount++;
		}

		result.put(goods, afterCount);

		SerializeUtil.deserializeObject(userName, result);
	}

	/**
	 * 添加商品到购物车
	 *
	 * @param userName    添加到指定用户名的购物车下
	 * @param productId   商品Id
	 * @param productProp 商品属性
	 */
	public synchronized void addToCar(String userName, int productId, int productProp) {
		CarProduct product = new CarProduct(productId, productProp);
		addToCar(userName, product);
	}
}

