package com.onlyone.jdmall.holder;

import android.view.View;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.holder
 * 创建者: LiuKe
 * 创建时间:  2016/3/5 16:20
 * 描述: ${TODO}
 */
public abstract class BaseHolder<T> {

    public View mHolderView;

    public  BaseHolder(){
        mHolderView = initHolderView();
        mHolderView.setTag(this);
    }

    /**
     * 初始化HolderView,留给子类实现
     *
     * @return
     */
    public abstract View initHolderView() ;


    public abstract void setDataAndRefreshUI(T data);

}
