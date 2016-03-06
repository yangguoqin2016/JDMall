package com.onlyone.jdmall.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.adapter.SuperBaseAdapter;
import com.onlyone.jdmall.bean.LimitBuyBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.holder.LimitBuyHolder;
import com.onlyone.jdmall.utils.ResUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: wt
 * @创建时间: 2016/3/6 14:15
 * @描述: ${TODO}
 */
public class LimitBuyFragment extends SuperBaseFragment<LimitBuyBean> {

    @Bind(R.id.limit_buy_lv)
    ListView mLimitBuyLv;
    private List<LimitBuyBean.LimitBuyItemBean> mItemBeans;
    private LimitBuyAdapter mAdapter;

    @Override
    protected String getUrl() {
        String url = Url.ADDRESS_LIMIT_BUY+"?page=0&pageNum=10";
        return url;
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    protected LimitBuyBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        LimitBuyBean limitBuyBean = gson.fromJson(jsonStr, LimitBuyBean.class);
        return limitBuyBean;
    }

    /**
     * 初始显示成功视图
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(ResUtil.getContext(), R.layout.limit_buy_fragment, null);
        ButterKnife.bind(this, rootView);
        /*
            listview的头部
         */
        View header = View.inflate(ResUtil.getContext(), R.layout.limit_buy_header, null);
        mLimitBuyLv.addHeaderView(header);
        return rootView;
    }

    @Override
    protected void refreshSuccessView(LimitBuyBean data) {
        mItemBeans = data.productList;
        mAdapter = new LimitBuyAdapter(mLimitBuyLv ,mItemBeans );
        mLimitBuyLv.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class LimitBuyAdapter extends SuperBaseAdapter<LimitBuyBean.LimitBuyItemBean>{

        public LimitBuyAdapter(AbsListView listView, List<LimitBuyBean.LimitBuyItemBean> datas) {
            super(listView, datas);
        }

        @Override
        public BaseHolder initSpecialHolder() {

            return new LimitBuyHolder();
        }

        @Override
        protected List<LimitBuyBean.LimitBuyItemBean> doLoadMore() throws Exception {

            return super.doLoadMore();
        }
    }
}
