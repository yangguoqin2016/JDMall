## JDMall

实战项目京东商城

##引入第三方开源框架的方式
* 直接将jar包或者aar包拷入lib文件夹下

##技能点get√
1. Fragment的点击穿透的问题,只需要在Fragment的根布局里面设置android:clickable="true"
	<?xml version="1.0" encoding="utf-8"?>
	<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
				 android:id="@+id/fl_content_container"
				 android:clickable="true"
				 android:layout_width="match_parent"
				 android:layout_height="match_parent"
				 android:orientation="vertical">

	</FrameLayout>

##Bug列表(解决的BUG就在前面加上"已解决"三个字)：
1. 全新进入app然后点击home键退出app再重新进入之后点击首页上方的轮播图就会崩溃,错误是轮播的mTask为空
2. (已解决)ScrollView嵌套ListView不能完全显示,不行滚动的BUGBug列表：
3. (已解决)购物车页面跳出的时候出现空指针异常
4. (已解决)出现了点击系统原生返回键,回退到上一个页面,但是TopBar无法改变的BUG