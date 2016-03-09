package com.onlyone.jdmall.bean;

import java.io.Serializable;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.bean
 * 创建者:	落地开花
 * 创建时间:	3/6/2016 12:43
 * 描述:		操作购物车时使用的Bean
 */
public class CarProduct implements Serializable {
	/**
	 * 商品id
	 */
	public int id;
	/**
	 * 商品属性
	 */
	public int[] prop;

	public CarProduct() {
	}

	/**
	 * @param id   商品Id
	 * @param prop 商品属性
	 */
	public CarProduct(int id, int[] prop) {
		this.id = id;
		this.prop = prop;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CarProduct product = (CarProduct) o;

		return id == product.id;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result;
		return result;
	}
}