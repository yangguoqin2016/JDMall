package com.onlyone.jdmall.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.SuperBaseAdapter;
import com.onlyone.jdmall.bean.HotProductBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.holder.NewProductHolder;
import com.onlyone.jdmall.utils.ResUtil;

import java.util.List;

/**
 * 项目名: JDMall
 * 包名: com.onlyone.jdmall.fragment
 * 创建者: LiuKe
 * 创建时间: 2016/3/5 12:31
 * 描述:用于展示新品的Fragment
 */
public class NewProductFragment extends SuperBaseFragment<List<HotProductBean.ProductBean>> {

    ListView mNewProductListView;
    /**
     * 当前加载的页数
     */
    private int mCurPageNum = 1;
    /**
     * 加载更多的数据
     */
    private List<HotProductBean.ProductBean> mLoadMoreDatas;
    /**
     * 加载跟多时的异常
     */
    private VolleyError                      mLoadError;
    private View                             mTopBarView;
    private MainActivity mActivity;

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (MainActivity) getActivity();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_new_product, null);
        mActivity.setTopBarView(mTopBarView);

    }

    @Override
    protected void refreshSuccessView(List<HotProductBean.ProductBean> datas) {
        mNewProductListView.setAdapter(new HotProductAdapter(mNewProductListView, datas));
    }

    @Override
    protected View loadSuccessView() {
        FrameLayout container = (FrameLayout) mLoadPager.getRootView();
        container.removeAllViews();

        View newProductView = View.inflate(ResUtil.getContext(), R.layout.new_product, null);
        mNewProductListView = (ListView) newProductView.findViewById(R.id.hot_product_list_view);
        return newProductView;
    }

    @Override
    protected String getUrl() {
        String url = Url.ADDRESS_SERVER + "/newproduct?page=" + mCurPageNum + "&pageNum=15&orderby=saleDown";
        return url;
    }


    @Override
    protected void handleError(Exception e) {
        Toast.makeText(ResUtil.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<HotProductBean.ProductBean> parseJson(String jsonStr) {
        Gson gson = new Gson();
        HotProductBean bean = gson.fromJson(jsonStr, HotProductBean.class);
        List<HotProductBean.ProductBean> datas = bean.productList;
        return datas;
    }

    /**
     * 热门品牌ListView展示数据所用的Adapter
     */
    private class HotProductAdapter extends SuperBaseAdapter<HotProductBean.ProductBean> {

        public HotProductAdapter(AbsListView hotProductListView, List<HotProductBean.ProductBean> datas) {
            super(hotProductListView,datas);
        }

        @Override
        public BaseHolder initSpecialHolder() {
            NewProductHolder holder = new NewProductHolder();
            return holder;
        }


        @Override
        protected List<HotProductBean.ProductBean> doLoadMore() throws Exception {
            mCurPageNum = mCurPageNum + 1;
            String url = Url.ADDRESS_SERVER + "/newproduct?page=" + mCurPageNum + "&pageNum=15&orderby=saleDown";
            //网络请求数据,该方法已属异步执行

            return null;
        }
    }
}
