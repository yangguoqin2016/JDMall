package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.bean
 * @创建者: wt
 * @创建时间: 2016/3/6 14:21
 * @描述: ${TODO}
 */
public class LimitBuyBean {
    public int listCount;
    public List<LimitBuyItemBean> productList;
    public String response;

    public class LimitBuyItemBean {
        public int id;//商品ID
        public long leftTime;//剩余时间，单位为秒
        public int limitPrice;//限时特价
        public String name; //商品名称
        public String pic; //商品图片URL
        public int price; //会员价
    }
}
