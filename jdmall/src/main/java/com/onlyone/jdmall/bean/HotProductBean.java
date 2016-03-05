package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.bean
 * 创建者: LiuKe
 * 创建时间:  2016/3/5 12:35
 * 描述: 热门单品
 */
public class HotProductBean {
    public int               listCount;
    public List<ProductBean> productList;

    public class ProductBean{

        public int    id;//22
        public float  marketPrice;//200
        public String name;//	新款毛衣
        public String pic;//	/images/product/detail/q20.jpg
        public float  price;

    }
}
