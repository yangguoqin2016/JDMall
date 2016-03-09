package com.onlyone.jdmall.utils;

import android.util.TypedValue;

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


    /**
     * px转为sp.设置文本大小方面
     *
     * @param pxValue
     * @return
     */
    public static float px2Sp(float pxValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue,
                                         ResUtil.getResource().getDisplayMetrics());
    }


    /**
     * 获得当前设备DensityDpi
     * @return
     */
    public static float getDensityDpi() {
        return ResUtil.getResource().getDisplayMetrics().densityDpi;
    }

    /**
     * 获得当前设备Density
     * @return
     */
    public static float getDensity() {
        return ResUtil.getResource().getDisplayMetrics().density;
    }
}
