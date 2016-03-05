package com.onlyone.jdmall.utils;

/**
 * 项目名:	JDMall<br/>
 * 包名:		com.onlyone.jdmall.utils<br/>
 * 创建者:	落地开花<br/>
 * 创建时间:	3/4/2016 15:26<br/>
 * 描述:		显示尺寸单位转换工具类
 */
public final class DensityUtil {
	private DensityUtil() {
	}

	/**
	 * dip转换为Px
	 *
	 * @param dpValue dip值
	 * @return px值
	 */
	public static int dip2Px(float dpValue) {
		final float scale = ResUtil.getResource().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转换为dip
	 *
	 * @param pxValue px值
	 * @return dip值
	 */
	public static int px2Dip(float pxValue) {
		final float scale = ResUtil.getResource().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
