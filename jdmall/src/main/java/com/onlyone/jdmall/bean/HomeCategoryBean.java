package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.bean
 * @创建者: yangguoqin
 * @创建时间: 2016/3/5 19:19
 * @描述:  这里是在首页点击了商品分类跳转
 * */
public class HomeCategoryBean {
    public String                     response; //	category;
    public List<HomeCategoryInfoBean> category; //数据集合 59个 0-58

    public class HomeCategoryInfoBean {
        public int     id;          //id号
        public boolean isLeafNode;  //true表示是一个商品分类的节点
        public String  name;        //商品名
        public int     parentId;	//这个商品的父分类id
        public String  pic;      	//商品的url
        public String  tag;          //商品标签

    }

}
