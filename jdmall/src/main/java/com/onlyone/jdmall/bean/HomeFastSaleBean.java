package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.bean
 * @创建者: yangguoqin
 * @创建时间: 2016/3/5 12:40
 * @描述:  这是促销快报访问网络返回的实体类数据
 */
public class HomeFastSaleBean {

    public String                      response; //
    public List<HomeFastSaleTopicBean> topic; //包含8个信息

    public class HomeFastSaleTopicBean {
        public int    id;  //产品对应ID
        public String name;//产品名字
        public String pic; //产品对应的图片url
    }

    @Override
    public String toString() {
        return "HomeFastSaleBean{" +
                "response='" + response + '\'' +
                ", topic=" + topic +
                '}';
    }
}
