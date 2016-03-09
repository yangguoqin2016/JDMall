package com.onlyone.jdmall.bean;

import java.io.Serializable;
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

    public static class ProductEntity implements Serializable {
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


        public static class ProductPropertyBean implements Serializable{
            public int    id;
            public String k;
            public String v;

            public boolean isSelected;  //是否选中
        }

        @Override
        public String toString() {
            return "ProductEntity{" +
                    "available=" + available +
                    ", buyLimit=" + buyLimit +
                    ", commentCount=" + commentCount +
                    ", id=" + id +
                    ", inventoryArea='" + inventoryArea + '\'' +
                    ", leftTime=" + leftTime +
                    ", limitPrice=" + limitPrice +
                    ", marketPrice=" + marketPrice +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", score=" + score +
                    ", bigPic=" + bigPic +
                    ", pics=" + pics +
                    ", productProperty=" + productProperty +
                    '}';
        }
    }
}
