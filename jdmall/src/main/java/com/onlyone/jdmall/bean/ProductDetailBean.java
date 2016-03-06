package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.bean
 * 创建者: LiuKe
 * 创建时间:  2016/3/6 20:12
 * 描述: 描述商品详情的类
 */
public class ProductDetailBean {

    public ProductEntity product;
    public String        response;

    public static class ProductEntity {
        public boolean available;
        public int     buyLimit;   //单品购买上限
        public int     commentCount;
        public int     id;
        public String       inventoryArea;
        public int          leftTime;
        public float        limitPrice;
        public float        marketPrice;
        public String       name;
        public float        price;
        public float        score;
        public List<String> bigPic;
        public List<String> pics;

        /**
         * id : 1
         * k : 颜色
         * v : 红色
         */
        public List<ProductPropertyBean> productProperty;


        public static class ProductPropertyBean {
            public int    id;
            public String k;
            public String v;
        }
    }
}
