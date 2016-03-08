package com.onlyone.jdmall.bean;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.bean
 * @创建者: Ashin
 * @创建时间: 2016/3/8 1:38
 * @描述: ${TODO}
 */
public class MineUserInfoBean {
    /**
     * response : userInfo
     * userInfo : {"level":"普通会员","favoritesCount":5,"userid":"20428","bonus":0,"orderCount":18}
     */
    public String response;
    public UserInfoEntity userInfo;

    public static class UserInfoEntity {
        /**
         * level : 普通会员
         * favoritesCount : 5
         * userid : 20428
         * bonus : 0
         * orderCount : 18
         */
        public String level;
        public int    favoritesCount;
        public String userid;
        public int    bonus;
        public int    orderCount;
    }

}
