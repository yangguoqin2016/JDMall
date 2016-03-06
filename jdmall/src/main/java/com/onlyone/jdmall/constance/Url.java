package com.onlyone.jdmall.constance;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.constance
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:23
 * 描述:		链接常量接口
 */
public interface Url {
	String ADDRESS_SERVER = "http://10.0.3.2:8080/market";
	String ADDRESS_HOME   = ADDRESS_SERVER + "/home";
	String ADDRESS_CART = ADDRESS_SERVER + "/cart";//购物车链接
	String ADDRESS_SEARCH = ADDRESS_SERVER + "/search/recommend";//热门搜索

	String ADDRESS_SEARCH_BYKEY = ADDRESS_SERVER + "/search";//关键字搜索
	String ADDRESS_BRAND = ADDRESS_SERVER + "/brand";//热门搜索
	String ADDRESS_FAVORITE = ADDRESS_SERVER + "/favorites";//收藏夹
	String ADDRESS_LIMIT_BUY = ADDRESS_SERVER + "/limitbuy";//限时抢购
}
