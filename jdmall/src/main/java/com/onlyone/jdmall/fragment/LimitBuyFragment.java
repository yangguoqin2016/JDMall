package com.onlyone.jdmall.fragment;

import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
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
import cn.iwgang.countdownview.CountdownView;

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
    private CountdownView mCountView;
    private ViewPager mViewPager;
    private int[] PICS = {
            R.mipmap.juxing,
            R.mipmap.hot,
            R.mipmap.home_title_pic3
    };

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
        mCountView = (CountdownView) header.findViewById(R.id.limit_buy_count_view);
        mViewPager = (ViewPager) header.findViewById(R.id.limit_buy_view_pager);
        mViewPager.setAdapter(new LimitAdapter());
        mLimitBuyLv.addHeaderView(header);
        mCountView.start(60 * 60 * 1000);
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
        mActivity.setOnBackPreseedListener(this);
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
            } else {
                return null;
            }
        }
    }

    private class LimitAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(ResUtil.getContext());
            iv.setImageResource(PICS[position % 3]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}