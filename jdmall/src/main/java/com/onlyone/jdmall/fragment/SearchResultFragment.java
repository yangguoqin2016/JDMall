package com.onlyone.jdmall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.activity.ProductDetailActivity;
import com.onlyone.jdmall.application.MyApplication;
import com.onlyone.jdmall.bean.SearchResultBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 13:33
 * @描述: 搜索结果的Fragment
 */
public class SearchResultFragment extends SuperBaseFragment<SearchResultBean> implements View.OnClickListener {
    @Bind(R.id.searchresult_sale_jiantou)
    ImageView    mSearchresultSaleJiantou;
    @Bind(R.id.searchresult_sale)
    LinearLayout mSearchresultSale;
    @Bind(R.id.searchresult_price_jiantou)
    ImageView    mSearchresultPriceJiantouUp;
    @Bind(R.id.searchresult_price_jiantouDown)
    ImageView    mSearchresultPriceJiantouDown;
    @Bind(R.id.searchresult_price)
    LinearLayout mSearchresultPrice;
    @Bind(R.id.searchresult_evaluate_jiantou)
    ImageView    mSearchresultEvaluateJiantou;
    @Bind(R.id.searchresult_evaluate)
    LinearLayout mSearchresultEvaluate;
    @Bind(R.id.searchresult_date_jiantou)
    ImageView    mSearchresultDateJiantou;
    @Bind(R.id.searchresult_date)
    LinearLayout mSearchresultDate;
    @Bind(R.id.searchresult_lv)
    ListView     mSearchresultLv;
    @Bind(R.id.searchresult_tv_sale)
    TextView     mSearchresultTvSale;
    @Bind(R.id.searchresult_tv_price)
    TextView     mSearchresultTvPrice;
    @Bind(R.id.searchresult_tv_exaluate)
    TextView     mSearchresultTvExaluate;
    @Bind(R.id.searchresult_tv_date)
    TextView     mSearchresultTvDate;
    private SearchResultBean mResultData;

    private SPUtil mSPUtil = new SPUtil(ResUtil.getContext());
    private MainActivity mMainActivity;
    private TextView     mTvResult;
    private String       mSearchKey;
    private MyAdapter    mAdapter;

    private int     mCurOrderby;
    private boolean mIsUp;
    private int mSearchNum;
    private static final String TAG = "SearchResultFragment";
    @Override
    protected void refreshSuccessView(SearchResultBean data) {

        mResultData = data;
        if (data == null || data.productList.size() == 0) {
            // TODO: 2016/3/5 返回空界面
            mSearchNum = 0;
            /* 如果是空,返回一个空视图 */

            View view = View.inflate(ResUtil.getContext(), R.layout.item_searchresult_empty, null);

            setContentView(view);
            // 让事件不能点击
            mSearchresultSale.setClickable(false);
            mSearchresultPrice.setClickable(false);
            mSearchresultEvaluate.setClickable(false);
            mSearchresultDate.setClickable(false);

        } else {
            mSearchresultSale.setClickable(true);
            mSearchresultPrice.setClickable(true);
            mSearchresultEvaluate.setClickable(true);
            mSearchresultDate.setClickable(true);
            // 返回成功界面奶粉
            mAdapter = new MyAdapter();
            mSearchresultLv.setAdapter(mAdapter);
            mSearchNum = data.productList.size();

			/* 设置listview条目点击事件 */
            mSearchresultLv.setOnItemClickListener(new ItemCilckListner());
        }
        mTvResult.setText("搜索结果(" + mSearchNum + "条)");
    }

    private void setContentView(View view) {
        FrameLayout frameLayout = mLoadPager.getRootView();
        frameLayout.removeAllViews();
        frameLayout.addView(view);
    }

    @Override
    protected View loadSuccessView() {
        View view = View.inflate(MyApplication.sGlobalContext, R.layout.inflate_searchresult, null);
        ButterKnife.bind(this, view);
        // 设置监听事件
        mSearchresultSale.setOnClickListener(this);
        mSearchresultPrice.setOnClickListener(this);
        mSearchresultEvaluate.setOnClickListener(this);
        mSearchresultDate.setOnClickListener(this);
        return view;
    }

    @Override
    protected String getUrl() {
        // http://188.188.5.57:8080/market/search?keyword=%E5%A5%B6%E7%B2%89&page=1&pageNum=10&orderby=priceDown
        String defaultParams = "&page=1&pageNum=10&orderby=saleDown";

		/* 奶粉女装童装 */
        mSearchKey = mSPUtil.getString(SP.KEY_SEARCHKEY, "");
        mSearchKey = URLEncoder.encode(mSearchKey);

        return Url.ADDRESS_SEARCH_BYKEY + "?keyword=" + mSearchKey + defaultParams;
    }

    @Override
    protected void handleError(Exception e) {
        mTvResult.setText("搜索结果(0条)");
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setTextSize(DensityUtil.dip2Px(20));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        setContentView(tv);
    }

    @Override
    protected SearchResultBean parseJson(String jsonStr) {

        Gson gson = new Gson();
        SearchResultBean searchResultBean = gson.fromJson(jsonStr, SearchResultBean.class);

        return searchResultBean;
    }

    /**
     * 在Fragment显示的时候就设置TopBar,这样就不用成功还是失败都去设置.
     */
    @Override
    public void onResume() {
        View topBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_searchresult,
                                   null);
        TextView tvBack = (TextView) topBar.findViewById(R.id.topbar_tv_back);
        tvBack.setOnClickListener(this);
        mTvResult = (TextView) topBar.findViewById(R.id.topbar_tv_result);
        mTvResult.setText("搜索结果(" + mSearchNum + "条)");
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(topBar);
        LogUtil.d(TAG, "onResume----------");
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG, "onDestroyView----------");
        ButterKnife.unbind(this);
    }

    // 所有的点击事件
    @Override
    public void onClick(View v) {
        String orderby = null;
        switch (v.getId()) {
            case R.id.topbar_tv_back:// 返回
				((HolderFragment)getParentFragment()).goBack();
                return;
            case R.id.searchresult_sale:// 销量降序
                /**
                 * 1.再通过关键字去请求网络 2.解析json得到数据 3.再更新UI
                 */
                orderby = "saleDown";
                mCurOrderby = 0;
                break;
            case R.id.searchresult_price:// 价格
                if (mIsUp) {
                    orderby = "priceDown";//降序
                }else{
                    orderby = "priceUp";//升序
                }
                mSearchresultPriceJiantouUp.setVisibility(mIsUp?View.VISIBLE:View.GONE);
                mSearchresultPriceJiantouDown.setVisibility(mIsUp?View.GONE:View.VISIBLE);
                mIsUp = !mIsUp;
                mCurOrderby = 1;
                break;
            case R.id.searchresult_evaluate:// 评价降序
                orderby = "commentDown";
                mCurOrderby = 2;
                break;
            case R.id.searchresult_date:// 上架时间降序
                orderby = "shelvesDown";
                mCurOrderby = 3;
                break;

            default:
                break;
        }
        orderByKey(orderby);
        mSearchresultTvSale.setTextColor(mCurOrderby == 0 ? Color.RED : Color.BLACK);
        mSearchresultTvPrice.setTextColor(mCurOrderby == 1 ? Color.RED : Color.BLACK);
        mSearchresultTvExaluate.setTextColor(mCurOrderby == 2 ? Color.RED : Color.BLACK);
        mSearchresultTvDate.setTextColor(mCurOrderby == 3 ? Color.RED : Color.BLACK);
    }

    /**
     * 通过orderby排序去网络请求数据
     *
     * @param orderby
     */
    private void orderByKey(String orderby) {
        String saleUrl = Url.ADDRESS_SEARCH_BYKEY + "?keyword=" + mSearchKey +
                "&page=1&pageNum=10&orderby=" + orderby;
        RequestQueue queue = Volley.newRequestQueue(ResUtil.getContext());
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonStr) {
                SearchResultBean bean = parseJson(jsonStr);
                if (bean != null && bean.productList!=null) {
                    mResultData = bean;
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TextView tv = new TextView(ResUtil.getContext());
                tv.setText("加载数据失败,请检查下你的网络..");
                tv.setTextSize(DensityUtil.dip2Px(20));
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                setContentView(tv);
            }
        };
        StringRequest request = new StringRequest(Request.Method.GET, saleUrl, success, error);
        queue.add(request);
    }

    /**
     * 移除当前的Fragment
     */
    private void removeCurFragment() {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(SearchFragment.TAG_SEARCHRESULT_FRAGMENT);
        transaction.remove(fragment);
        transaction.commit();
    }

    @Override
    public void onPause() {
        LogUtil.d(TAG, "onPause----------");
        super.onPause();
    }

    @Override
    public void onStart() {
        LogUtil.d(TAG, "onStart----------");
        super.onStart();
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy----------");
        super.onDestroy();
    }


    class ItemCilckListner implements AdapterView.OnItemClickListener {

        /* 商品条目的点击事件 */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            FragmentManager manager = mMainActivity.getSupportFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.addToBackStack(null);
//            transaction.commit();
            //跳转商品详情页面
            SearchResultBean.ProductList productList = mResultData.productList.get(position);
            Toast.makeText(ResUtil.getContext(), productList.name,
                           Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mMainActivity, ProductDetailActivity.class);
            intent.putExtra("id",productList.id);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mMainActivity.startActivity(intent);

        }
    }

    class MyAdapter extends BaseAdapter {

        @Bind(R.id.item_searchresult_iv)
        ImageView mItemSearchresultIv;
        @Bind(R.id.item_searchresult_tv_title)
        TextView  mItemSearchresultTvTitle;
        @Bind(R.id.item_searchresult_tv_price)
        TextView  mItemSearchresultTvPrice;
        @Bind(R.id.item_searchresult_tv_oldprice)
        TextView  mItemSearchresultTvOldprice;
        @Bind(R.id.item_searchresult_tv_valuation)
        TextView  mItemSearchresultTvValuation;

        @Override
        public int getCount() {
            if (mResultData != null && mResultData.productList.size()!=0) {
                return mResultData.productList.size();
            }
            return 0;
        }

        @Override
        public SearchResultBean.ProductList getItem(int position) {
            if (mResultData.productList != null) {
                return mResultData.productList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHoler vh = null;
            if (convertView == null) {

                convertView = View.inflate(MyApplication.sGlobalContext, R.layout.item_searchresult,
                                           null);
                vh = new ViewHoler();
                ButterKnife.bind(this, convertView);
                vh.demo = mItemSearchresultIv;
                vh.title = mItemSearchresultTvTitle;
                vh.price = mItemSearchresultTvPrice;
                vh.oldprice = mItemSearchresultTvOldprice;
                vh.valuation = mItemSearchresultTvValuation;

                convertView.setTag(vh);
            } else {
                vh = (ViewHoler) convertView.getTag();
            }
            /* 给每个条目赋值 奶粉 女装 */
            SearchResultBean.ProductList productInfo = mResultData.productList.get(position);
            // vh.demo = productInfo.pic;
            vh.title.setText(productInfo.name);
            vh.price.setText(productInfo.price + "");
            vh.oldprice.setText(productInfo.marketPrice + "");
            vh.oldprice.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
            vh.valuation.setText("已有" + new Random().nextInt(50000) + "人评价");
            String uri = Url.ADDRESS_SERVER + productInfo.pic;

            Picasso.with(ResUtil.getContext()).load(uri).fit().centerCrop().into(vh.demo);

            return convertView;
        }
    }

    class ViewHoler {

        TextView title, price, oldprice, valuation;
        ImageView demo;
    }

}
