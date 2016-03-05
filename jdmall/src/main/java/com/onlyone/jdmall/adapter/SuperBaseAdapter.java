package com.onlyone.jdmall.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.holder.LoadMoreHolder;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.UIUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.adapter
 * 创建者: LiuKe
 * 创建时间:  2016/3/5 16:12
 * 描述: ${TODO}
 */
public abstract class SuperBaseAdapter<T> extends MyBaseAdapter {

    private static final int VIEWTYPE_LOADMORE = 0;
    private static final int VIEWTYPE_NORMAL   = 1;
    private LoadMoreHolder mLoadMoreHolder;

    private int mCurLoadMoreState;//默认加载更多状态
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(3);
    private LoadMoreTask mLoadMoreTask;

    public SuperBaseAdapter(List<T> datas) {
        super(datas);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        } else {

            return VIEWTYPE_NORMAL;
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
                holder = initLoadMoreHolder();
            } else {
                holder = initSpecialHolder();

            }
            convertView = holder.mHolderView;
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        //刷新UI
        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            if(mCurLoadMoreState == LoadMoreHolder.STATE_LOADING) {

                //触发加载更多数据
                triggerLoadMoreData();
            }else {
                Toast.makeText(ResUtil.getContext(), "没有数据可以加载", Toast.LENGTH_SHORT).show();
            }
        } else {
            holder.setDataAndRefreshUI(mDatas.get(position));
        }
        return convertView;
    }

    /**
     * 加载更多数据
     */
    private void triggerLoadMoreData() {
        if(mLoadMoreTask == null){

            //加载时重置当前状态
            mCurLoadMoreState = LoadMoreHolder.STATE_LOADING;
            mLoadMoreHolder.setDataAndRefreshUI(mCurLoadMoreState);

            mLoadMoreTask = new LoadMoreTask();
            mExecutorService.submit(mLoadMoreTask);
        }
    }

    /**
     * 判断是否还有更多数据可以加载,子类可以根据自身情况进行覆写
     *
     * @return
     */
    public boolean hasMore() {
        return true;
    }

    /**
     * 让子类实现去加载普通view type的holder
     *
     * @return
     */
    public abstract BaseHolder initSpecialHolder();

    private BaseHolder initLoadMoreHolder() {
        if (mLoadMoreHolder == null) {

            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    /**
     * 异步加载更多数据的类
     */
    private class LoadMoreTask implements Runnable {
        private final int PAGESIZE = 15;
        @Override
        public void run() {
            //加载数据
            List<T> loadDatas = null;

            //处理数据
            try {

                loadDatas =  doLoadMore();

                if(loadDatas == null){
                    mCurLoadMoreState = LoadMoreHolder.STATE_NONE;
                }else{
                    if(loadDatas.size() == PAGESIZE){
                        mCurLoadMoreState = LoadMoreHolder.STATE_LOADING;
                    }else{
                        mCurLoadMoreState = LoadMoreHolder.STATE_NONE;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mCurLoadMoreState = LoadMoreHolder.STATE_ERROR;
            }

            //刷新UI
            final List<T> finalLoadDatas = loadDatas;
            UIUtil.getUiHandler().post(new Runnable() {
                @Override
                public void run() {
                      //刷新ListView
                    if(finalLoadDatas != null && finalLoadDatas.size() != 0){
                        mDatas.addAll(finalLoadDatas);
                        notifyDataSetChanged();
                    }

                    //刷新加载更多状态
                    mLoadMoreHolder.setDataAndRefreshUI(mCurLoadMoreState);
                }
            });
        }
    }

    /**
     * 真正开始加载更多数据,定义成抽象方法由子类去完成
     *
     * @return
     */
    protected  List<T> doLoadMore() throws Exception{
        return null;
    }
}
