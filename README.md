## JDMall

实战项目京东商城

##引入第三方开源框架的方式
* 直接将jar包或者aar包拷入lib文件夹下

Bug列表(解决的BUG就在前面加上"已解决"三个字)：
1. 全新进入app然后点击home键退出app再重新进入之后点击首页上方的轮播图就会崩溃,错误是轮播的mTask为空
2. (已解决)ScrollView嵌套ListView不能完全显示,不行滚动的BUGBug列表：
3. (已解决)购物车页面跳出的时候出现空指针异常