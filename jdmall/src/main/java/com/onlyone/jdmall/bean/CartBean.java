package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.bean
 * 创建者:	落地开花
 * 创建时间:	3/5/2016 11:13
 * 描述:		购物车数据展示Bean
 */
public class CartBean {
	public String response;
	public int    totalCount;
	public int    totalPoint;
	public int    totalPrice;

	public List<CartEntity> cart;
	public List<String>     prom;

	public static class CartEntity {
		public int prodNum;

		public ProductEntity product;

		public static class ProductEntity {
			public int    buyLimit;
			public int    id;
			public String name;
			public String number;
			public String pic;
			public int    price;

			public List<ProductPropertyEntity> productProperty;

			public static class ProductPropertyEntity {
				public int    id;
				public String k;
				public String v;
			}
		}
	}
}
