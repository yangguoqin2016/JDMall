package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @Author Never
 * @Date 2016/3/5 15:09
 * @Desc ${TODO}
 */
public class SearchResultBean {

    /**
     * productList : [{"id":30,"marketPrice":200,"name":"超凡奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":31,"marketPrice":260,"name":"天籁牧羊奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":32,"marketPrice":300,"name":"fullcare奶粉","pic":"/images/product/detail/q26.jpg","price":300},{"id":33,"marketPrice":300,"name":"雀巢奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":34,"marketPrice":200,"name":"安婴儿奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":35,"marketPrice":200,"name":"贝贝羊金装奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":36,"marketPrice":200,"name":"飞雀奶粉","pic":"/images/product/detail/q26.jpg","price":160}]
     * response : search
     */

    public String            response;
    /**
     * id : 30
     * marketPrice : 200
     * name : 超凡奶粉
     * pic : /images/product/detail/q26.jpg
     * price : 160
     */

    public List<ProductList> productList;

    public static class ProductList {
        public int    id;
        public float  marketPrice;
        public String name;
        public String pic;
        public float  price;
    }
}
