package com.onlyone.jdmall.bean;

/**
 * @Author Never
 * @Date 2016/3/8 19:53
 * @Desc ${添加地址信息的bean}
 */
public class AddressAddBean {

    public String       response;//	addresssave
    public AddressBean userInfo;

    public class AddressBean {
        public String name;
        public String phone;
        public String province;
        public String detail;

    }
}
