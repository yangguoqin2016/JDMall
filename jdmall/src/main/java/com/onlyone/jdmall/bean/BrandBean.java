package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.bean
 * @创建者: Administrator
 * @创建时间: 2016/3/5 20:33
 * @描述: 推荐品牌Bean
 */
public class BrandBean {


    @Override
    public String toString() {
        return "BrandBean{" +
                "response='" + response + '\'' +
                ", brand=" + brand +
                '}';
    }

    /**
     * brand : [{"id":1,"key":"孕妈专区","value":[{"id":1218,"name":"雅培","pic":"/images/brand/c.png"},{"id":1219,"name":"贝因美","pic":"/images/brand/d.png"},{"id":1220,"name":"周生生","pic":"/images/brand/a.png"},{"id":1221,"name":"婴姿坊","pic":"/images/brand/e.png"}]},{"id":2,"key":"营养食品","value":[{"id":1202,"name":"咪咪","pic":"/images/brand/b.png"}]},{"id":3,"key":"宝宝用品","value":[{"id":1209,"name":"好奇","pic":"/images/brand/d.png"},{"id":1210,"name":"快乐宝贝","pic":"/images/brand/e.png"},{"id":1211,"name":"环球娃娃","pic":"/images/brand/f.png"},{"id":1212,"name":"Kiddy","pic":"/images/brand/a.png"}]},{"id":4,"key":"儿童服饰","value":[]},{"id":5,"key":"时尚女装","value":[{"id":1206,"name":"Nutrilon","pic":"/images/brand/a.png"},{"id":1207,"name":"Hero","pic":"/images/brand/b.png"},{"id":1208,"name":"Goo.N","pic":"/images/brand/c.png"},{"id":1213,"name":"hogar","pic":"/images/brand/b.png"},{"id":1214,"name":"奇妮孕妇装","pic":"/images/brand/c.png"},{"id":1215,"name":"Bio-oil","pic":"/images/brand/d.png"},{"id":1216,"name":"莫施","pic":"/images/brand/e.png"},{"id":1217,"name":"IMG","pic":"/images/brand/f.png"}]},{"id":6,"key":"成人用品","value":[]},{"id":7,"key":"日常用品","value":[{"id":1201,"name":"爱家","pic":"/images/brand/1378_1333165554_8.png"}]},{"id":8,"key":"化妆品","value":[]},{"id":9,"key":"清洁用品","value":[{"id":1203,"name":"防辐射","pic":"/images/brand/c.png"},{"id":1205,"name":"枕工坊","pic":"/images/brand/d.png"},{"id":1222,"name":"飞利浦","pic":"/images/brand/flp.jpg"}]}]
     * response : brand
     */

    private String          response;
    /**
     * id : 1
     * key : 孕妈专区
     * value : [{"id":1218,"name":"雅培","pic":"/images/brand/c.png"},{"id":1219,"name":"贝因美","pic":"/images/brand/d.png"},{"id":1220,"name":"周生生","pic":"/images/brand/a.png"},{"id":1221,"name":"婴姿坊","pic":"/images/brand/e.png"}]
     */

    private List<BrandList> brand;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setBrand(List<BrandList> brand) {
        this.brand = brand;
    }

    public String getResponse() {
        return response;
    }

    public List<BrandList> getBrand() {
        return brand;
    }

    public static class BrandList {
        private int              id;
        private String           key;
        /**
         * id : 1218
         * name : 雅培
         * pic : /images/brand/c.png
         */

        private List<BrandValue> value;

        public void setId(int id) {
            this.id = id;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(List<BrandValue> value) {
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public List<BrandValue> getValue() {
            return value;
        }

        public static class BrandValue {
            private int    id;
            private String name;
            private String pic;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPic() {
                return pic;
            }
        }
    }
}
