package com.onlyone.jdmall.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.adapter
 * 创建者: LiuKe
 * 创建时间:  2016/3/5 16:07
 * 描述: 对ListView adapter做出一定封装的adapter基类
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public List<T> mDatas;

    public MyBaseAdapter(List<T> datas) {
        mDatas = datas;
    }


    @Override
    public int getCount() {
        if(mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if(mDatas != null){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
