package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @Author Never
 * @Date 2016/3/6 16:15
 * @Desc ${TODO}
 */
public class FavoriteBean {
    public String            response;
    public String            listCount;
    /**
     * id : 30
     * marketPrice : 200
     * name : 超凡奶粉
     * pic : /images/product/detail/q26.jpg
     * price : 160
     */

    public List<ProductInfo> productList;

    public class ProductInfo {
        public int    id;
        public float  marketPrice;
        public String name;
        public String pic;
        public float  price;
    }
}
