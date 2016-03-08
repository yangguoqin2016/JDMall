package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @Author Never
 * @Date 2016/3/7 14:34
 * @Desc ${收货地址管理Bean}
 */
public class AddressBean {

    /**
     * addressList : [{"addressArea":"abc","addressDetail":"abc","city":"abc","id":133,"isDefault":0,"name":"abc","phoneNumber":"123","province":"abc","zipCode":"12345"},{"addressArea":"洪山区","addressDetail":"文华路文华学院","city":"武汉市","id":134,"isDefault":0,"name":"张瑞丽","phoneNumber":"18986104910","province":"湖北","zipCode":"1008611"},{"addressArea":"洪山区","addressDetail":"街道口地铁c口","city":"武汉市","id":139,"isDefault":0,"name":"itcast","phoneNumber":"027-81970008","province":"湖北","zipCode":"430070"},{"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":146,"isDefault":1,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"}]
     * response : addressList
     */

    public String            response;
    /**
     * addressArea : abc
     * addressDetail : abc
     * city : abc
     * id : 133
     * isDefault : 0
     * name : abc
     * phoneNumber : 123
     * province : abc
     * zipCode : 12345
     */

    public List<AddressList> addressList;

    public static class AddressList implements Comparable<AddressList> {
        public String addressArea;
        public String addressDetail;
        public String city;
        public int    id;
        public int    isDefault;
        public String name;
        public String phoneNumber;
        public String province;
        public String zipCode;

        @Override
        public int compareTo(AddressList another) {
            if(this==another){
                return 0;
            }
            return another.isDefault - isDefault;
        }
    }


}
