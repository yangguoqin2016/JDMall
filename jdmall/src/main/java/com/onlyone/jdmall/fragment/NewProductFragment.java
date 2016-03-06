package com.onlyone.jdmall.fragment;

import android.os.SystemClock;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.SuperBaseAdapter;
import com.onlyone.jdmall.bean.HotProductBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.holder.BaseHolder;
import com.onlyone.jdmall.holder.NewProductHolder;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
    private View                             mTopBarView;
    private MainActivity mActivity;

    @Override
    public void onResume() {
        super.onResume();
        mActivity = (MainActivity) getActivity();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_new_product, null);
        mActivity.setTopBarView(mTopBarView);

        mTopBarView.findViewById(R.id.new_product_topbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
                transaction.remove(NewProductFragment.this).commit();

                //退回首页
                View titlBar = View.inflate(ResUtil.getContext(), R.layout.home_title, null);
                mActivity.setTopBarView(titlBar);
            }
        });

    }

    @Override
    protected void refreshSuccessView(List<HotProductBean.ProductBean> datas) {
        View topPic = View.inflate(ResUtil.getContext(),R.layout.new_product_top_pic,null);
        mNewProductListView.addHeaderView(topPic);
        mNewProductListView.setAdapter(new HotProductAdapter(mNewProductListView, datas));
    }

    @Override
    protected View loadSuccessView() {
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
            SystemClock.sleep(1500);
            mCurPageNum = mCurPageNum + 1;
            String url = Url.ADDRESS_SERVER + "/newproduct?page=" + mCurPageNum + "&pageNum=15&orderby=saleDown";
            //网络请求数据
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).get().build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(result,HotProductBean.class).productList;
            }else{
                mCurPageNum--;
                return null;
            }
        }
    }
}
