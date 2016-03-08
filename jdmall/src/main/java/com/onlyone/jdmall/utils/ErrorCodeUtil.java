package com.onlyone.jdmall.utils;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.utils
 * @创建者: Administrator
 * @创建时间: 2016/3/8 20:02
 * @描述: 错误码工具类
 */
public class ErrorCodeUtil {

    public static String codeToString(int errorCode) {

        String text = null;

        switch (errorCode) {

            case 1530:
                text = "用户名不存在或密码错误";
                break;

            case 1531:
                text = "userid请求头为空";
                break;

            case 1532:
                text = "该用户名已经被注册过了";
                break;

            case 1533:
                text = "没有登录或则需要重新登录";
                break;

            case 1534:
                text = " 请求参数错误或缺失";
                break;

            case 1535:
                text = "当前商品已经添加过收藏";
                break;

            case 1536:
                text = "商品已经添加收藏失败";
                break;

            case 1537:
                text = "取消订单失败";
                break;

            case 1538:
                text = "没有该订单详情";
                break;

            default:
                text = "未知错误";
                break;
        }
        return text;
    }
}
