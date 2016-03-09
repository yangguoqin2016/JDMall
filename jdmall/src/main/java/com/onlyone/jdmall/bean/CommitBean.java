package com.onlyone.jdmall.bean;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.bean
 * 创建者:	落地开花
 * 创建时间:	3/9/2016 15:21 下午
 * 描述:		${TODO}
 */
public class CommitBean {
	public OrderInfoEntity orderInfo;

	public String response;

	public static class OrderInfoEntity {
		public String orderId;
		public int    paymentType;
		public int    price;
	}
}
