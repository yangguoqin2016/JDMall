package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * 项目名: JDMall
 * 包名: com.onlyone.jdmall.bean
 * 创建者: wt
 * 创建时间: 2016/3/5 16:58
 * 描述: ${TODO}
 */
public class HomeBean {
   public List<HomeTopicBean> homeTopic;//Array
   public String response;//home

    public class HomeTopicBean{
       public  int id;//	126
       public  String pic	;
       public  String title	;
    }
}
