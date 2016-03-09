package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.bean
 * @创建者: Administrator
 * @创建时间: 2016/3/9 8:58
 * @描述: ${TODO}
 */
public class IndentDetailMessageBean {
    public String                response;
    public OrderInfoBean         orderInfo;
    public AddressInfoBean       addressInfo;
    public DeliveryInfoBean      deliveryInfo;
    public InvoiceInfoBean       invoiceInfo;
    public List<ProductListBean> productList;
    public List<String> checkoutProm;
    public CheckoutAddupBean checkoutAddup;


    public class OrderInfoBean {
        public long   orderId;
        public String status;
        public long   time;
        public int    flag;
    }

    public class AddressInfoBean {
        public int id;
        public String name;
        public String addressArea;
        public String addressDetail;
    }

    public class DeliveryInfoBean {
        public int type;

    }
    public class InvoiceInfoBean {
        public String invoiceTitle;
        public String invoiceContent;
    }
    public class ProductListBean {
        public int prodNum;
        public ProductBean product;

        public class ProductBean{
            public int id;
            public String name;
            public String pic;
            public float price;
            public int number;
            public int uplimit;
            public List<ProductPropertyBean> productProperty;

            public class ProductPropertyBean{
                public int id;
                public String k;
                public String v;
            }
        }
    }

    public class CheckoutAddupBean{
        public int totalCount;
        public float totalPrice;
        public int totalPoint;
        public float freight;
    }
}
