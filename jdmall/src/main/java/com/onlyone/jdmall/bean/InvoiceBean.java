package com.onlyone.jdmall.bean;

import java.util.List;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.bean
 * 创建者:	落地开花
 * 创建时间:	3/9/2016 10:18 上午
 * 描述:		发票类型Bean
 */
public class InvoiceBean {
	public String              response;
	public List<InvoiceEntity> invoice;

	public static class InvoiceEntity {
		public String content;
		public int    id;
	}
}
