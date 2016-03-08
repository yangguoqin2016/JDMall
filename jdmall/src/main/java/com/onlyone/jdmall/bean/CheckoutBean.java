package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.bean
 * 创建者:	落地开花
 * 创建时间:	3/8/2016 19:00 下午
 * 描述:		结算中心Bean
 */
public class CheckoutBean {
	/**
	 * freight : 10
	 * totalCount : 5
	 * totalPoint : 30
	 * totalPrice : 450
	 */

	public int error_code; //错误码

	public CheckoutAddupEntity      checkoutAddup;
	/**
	 * checkoutAddup : {"freight":10,"totalCount":5,"totalPoint":30,"totalPrice":450}
	 * checkoutProm : ["促销信息一","促销信息二"]
	 * deliveryList : [{"des":"周一至周五送货","type":1},{"des":"双休日及公众假期送货","type":2},
	 * {"des":"时间不限，工作日双休日及公众假期均可送货","type":3}]
	 * paymentList : [{"des":"到付-现金","type":1},{"des":"到付-POS机","type":2},{"des":"支付宝","type":1}]
	 * productList : [{"prodNum":3,"product":{"id":1,"name":"韩版时尚蕾丝裙",
	 * "pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色",
	 * "v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码",
	 * "v":"XXL"}]}},{"prodNum":2,"product":{"id":2,"name":"粉色毛衣","pic":"/images/product/detail/q1
	 * .jpg","price":100,"productProperty":[{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码",
	 * "v":"M"}]}}]
	 * response : checkOut
	 */

	public String                   response;
	public List<String>             checkoutProm;
	/**
	 * des : 周一至周五送货
	 * type : 1
	 */

	public List<DeliveryListEntity> deliveryList;
	/**
	 * des : 到付-现金
	 * type : 1
	 */

	public List<PaymentListEntity>  paymentList;
	/**
	 * prodNum : 3
	 * product : {"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,
	 * "productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码",
	 * "v":"M"},{"id":4,"k":"尺码","v":"XXL"}]}
	 */

	public List<ProductListEntity>  productList;

	public static class CheckoutAddupEntity {
		public int freight;
		public int totalCount;
		public int totalPoint;
		public int totalPrice;
	}

	public static class DeliveryListEntity {
		public String des;
		public int    type;
	}

	public static class PaymentListEntity {
		public String des;
		public int    type;
	}

	public static class ProductListEntity {
		public int           prodNum;
		/**
		 * id : 1
		 * name : 韩版时尚蕾丝裙
		 * pic : /images/product/detail/c3.jpg
		 * price : 350
		 * productProperty : [{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,
		 * "k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]
		 */

		public ProductEntity product;

		public static class ProductEntity {
			public int                         id;
			public String                      name;
			public String                      pic;
			public int                         price;
			/**
			 * id : 1
			 * k : 颜色
			 * v : 红色
			 */

			public List<ProductPropertyEntity> productProperty;

			public static class ProductPropertyEntity {
				public int    id;
				public String k;
				public String v;
			}
		}
	}
}
