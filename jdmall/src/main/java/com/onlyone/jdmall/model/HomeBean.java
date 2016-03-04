package com.onlyone.jdmall.model;

import java.util.List;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.model
 * 创建者:	落地开花
 * 创建时间:	3/4/2016 18:21
 * 描述:		Home界面的数据Bean
 */
public class HomeBean {
	public String                response;
	public List<HomeTopicEntity> homeTopic;

	public static class HomeTopicEntity {
		public int    id;
		public String pic;
		public String title;

		@Override
		public String toString() {
			return "HomeTopicEntity{" +
					"id=" + id +
					", pic='" + pic + '\'' +
					", title='" + title + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "HomeBean{" +
				"response='" + response + '\'' +
				", homeTopic=" + homeTopic +
				'}';
	}
}
