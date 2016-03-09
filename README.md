## JDMall

实战项目京东商城

##引入第三方开源框架的方式
* 直接将jar包或者aar包拷入lib文件夹下

##结算中心的地址思路分析
![](arts/address.png)

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
2.加载图片使用picasso第三方库,jar包为picasso-5.5.2.jar,存放在lib中

		问题:	当需要加载圆形图片时,github上作者介绍是在线引用两个地址:
				compile 'jp.wasabeef:picasso-transformations:2.0.0'
					// If you want to use the GPU Filters
				compile 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.3.0'
		原因:	使用时出现和picasso的jar起冲突,报出一个megerdebug的错误,是transformations那个地址,
				在第一次引用时,存在依赖加载,会去主动加载picasso的jar包,此时与项目中已有的起冲突;
		解决:	1.在线引用,删除原有的picasso的jar包或引用
				2.将上面两个文件下载下来,放在lib中,grdle中改为以下形式:
					compile(name:'picasso-transformations-2.0.0', ext: 'aar')
					compile files('lib/gpuimage-library-1.3.0.jar')

##Bug列表(解决的BUG就在前面加上"已解决"三个字)：
1. 全新进入app然后点击home键退出app再重新进入之后点击首页上方的轮播图就会崩溃,错误是轮播的mTask为空
2. (已解决)ScrollView嵌套ListView不能完全显示,不行滚动的BUGBug列表：
3. (已解决)购物车页面跳出的时候出现空指针异常
4. (已解决)出现了点击系统原生返回键,回退到上一个页面,但是TopBar无法改变的BUG
