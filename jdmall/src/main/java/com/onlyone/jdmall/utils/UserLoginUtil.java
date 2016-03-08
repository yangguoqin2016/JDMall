package com.onlyone.jdmall.utils;

import android.text.TextUtils;

import com.onlyone.jdmall.constance.SP;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.utils
 * 创建者: LiuKe
 * 创建时间:  2016/3/8 16:00
 * 描述: 判断用户是否登录及获取当前登录用户的工具类
 */
public class UserLoginUtil {

    /**
     * 判断当前是否有用户登录
     *
     * @return
     */
    public static boolean isLogin() {
        SPUtil spUtil = new SPUtil(ResUtil.getContext());
        String userName = spUtil.getString(SP.USERNAME, "");
        return !TextUtils.isEmpty(userName);
    }

    /**
     * 获取登录用户名
     *
     * @return
     */
    public static  String getLoginUser() {
        if (isLogin()) {
            SPUtil spUtil = new SPUtil(ResUtil.getContext());
            return spUtil.getString(SP.USERNAME, "");
        } else {
            return null;
        }
    }
}
