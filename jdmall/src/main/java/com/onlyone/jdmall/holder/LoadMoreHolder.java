package com.onlyone.jdmall.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名: JDMall
 * 包名:  com.onlyone.jdmall.holder
 * 创建者: LiuKe
 * 创建时间:  2016/3/5 16:54
 * 描述: 上拉加载更多数据的holder
 */
public class LoadMoreHolder extends BaseHolder<Integer> {

    public static final int STATE_LOADING = 0;
    public static final int STATE_ERROR   = 1;
    public static final int STATE_NONE    = 2;

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    @Override
    public View initHolderView() {
        View view = View.inflate(ResUtil.getContext(), R.layout.item_load_more, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setDataAndRefreshUI(Integer data) {
        //显示item前先全部隐藏
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);

        switch (data) {
            case STATE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case STATE_NONE:
                break;
            default:
                break;
        }

    }
}
