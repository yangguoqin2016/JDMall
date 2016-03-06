package com.onlyone.jdmall.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.SuperBaseAdapter;
import com.onlyone.jdmall.bean.LimitBuyBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.holder.LimitBuyHolder;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
public class LimitBuyFragment extends SuperBaseFragment<LimitBuyBean> implements MainActivity.OnBackPressedListener {

    private static final int PAGENUMBER = 15;
    @Bind(R.id.limit_buy_lv)
    ListView mLimitBuyLv;
    private List<LimitBuyBean.LimitBuyItemBean> mItemBeans;
    private LimitBuyAdapter mAdapter;
    private MainActivity mActivity;

    @Override
    protected String getUrl() {
        String url = generateUrl(0, PAGENUMBER);
        return url;
    }

    private String generateUrl(int page, int pageNum) {
        String url = Url.ADDRESS_LIMIT_BUY + "?page=" + page + "&pageNum=" + pageNum;
        return url;
    }

    @Override
    protected void handleError(Exception e) {

    }

    /**
     * 解析数据
     *
     * @param jsonStr
     * @return
     */
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
        mAdapter = new LimitBuyAdapter(mLimitBuyLv, mItemBeans);
        mLimitBuyLv.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (MainActivity) getActivity();
        mActivity.addOnBackPreseedListener(this);
        changeTitleBar();
    }

    private void changeTitleBar() {
        View view = View.inflate(ResUtil.getContext(), R.layout.limit_buy_title_bar, null);
        mActivity.setTopBarView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPressed() {
        View titlBar = View.inflate(ResUtil.getContext(), R.layout.home_title, null);
        mActivity.setTopBarView(titlBar);
    }

    private class LimitBuyAdapter extends SuperBaseAdapter<LimitBuyBean.LimitBuyItemBean> {
        LimitBuyBean bean = null;

        public LimitBuyAdapter(AbsListView listView, List<LimitBuyBean.LimitBuyItemBean> datas) {
            super(listView, datas);
        }

        @Override
        public BaseHolder initSpecialHolder() {

            return new LimitBuyHolder();
        }

        @Override
        protected List<LimitBuyBean.LimitBuyItemBean> doLoadMore() throws Exception {
            SystemClock.sleep(1000);
            OkHttpClient okhttp = new OkHttpClient();
            String url = generateUrl(mItemBeans.size(), PAGENUMBER);
            Request reuqest = new Request.Builder().url(url).get().build();
            Response response = okhttp.newCall(reuqest).execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                bean = parseJson(string);
                return bean.productList;
            }else{
                return null;
            }
        }
    }
}
