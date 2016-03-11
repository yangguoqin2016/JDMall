package com.onlyone.jdmall.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.StaggeredAdapter;
import com.onlyone.jdmall.application.MyApplication;
import com.onlyone.jdmall.bean.HomeBean;
import com.onlyone.jdmall.bean.LimitBuyBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.HolderFragment;
import com.onlyone.jdmall.fragment.HomeFastSaleFragment;
import com.onlyone.jdmall.fragment.HotProductFragment;
import com.onlyone.jdmall.fragment.LimitBuyFragment;
import com.onlyone.jdmall.fragment.NewProductFragment;
import com.onlyone.jdmall.fragment.category.HomeCategoryFragment;
import com.onlyone.jdmall.manager.ExStaggeredGridLayoutManager;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	LuoDi
 * 创建时间:	3/5/2016 10:03
 * 描述:		${TODO}
 */
public class HomeFragment extends BaseFragment<Object>
        implements ViewPager.OnPageChangeListener, View.OnTouchListener, View.OnClickListener {

    @Bind(R.id.home_vp)
    ViewPager mHomeVp;
    @Bind(R.id.home_pot_container)
    LinearLayout mHomePotContainer;
    @Bind(R.id.home_ll_hot)
    LinearLayout mHomeLlHot;
    @Bind(R.id.home_ll_new)
    LinearLayout mHomeLlNew;
    @Bind(R.id.home_ll_shopping)
    LinearLayout mHomeLlShopping;
    @Bind(R.id.home_ll_sale)
    LinearLayout mHomeLlSale;
    @Bind(R.id.home_ll_recommend)
    LinearLayout mHomeLlRecommend;
    @Bind(R.id.home_ll_category)
    LinearLayout mHhomeLlCategory;
    @Bind(R.id.home_viewflipper_container)
    ViewFlipper mHomeViewflipper;
    @Bind(R.id.home_recycler_view)
    RecyclerView mHomeRecyclerView;
    private View mRootView;
    private Context mContext;
    private HomeBean mHomeBean;
    private List<HomeBean.HomeTopicBean> mHomeTopics;
    private SwitchTask mTask;
    private MainActivity mMainActivity;
    private View mAdView1;
    private View mAdView2;
    private List<LimitBuyBean.LimitBuyItemBean> mProductList;
//    private HomeGridViewAdapter mHomeGridViewAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 获得数据后，显示在视图上
     *
     * @param data 数据
     */
    @Override
    protected void refreshSuccessView(Object data) {
        //解析Json
        Gson gson = new Gson();
        mHomeBean = gson.fromJson((String) data, HomeBean.class);
        mHomeTopics = mHomeBean.homeTopic;
        mHomeVp.setAdapter(new HomeTopicAdapter());
        mHomeVp.setOnPageChangeListener(this);
        //添加点
        for (int i = 0; i < mHomeTopics.size(); i++) {
            View view = new View(ResUtil.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2Px(6),
                    DensityUtil.dip2Px(6));
            view.setBackgroundResource(R.mipmap.slide_adv_selected);
            if (i != 0) {
                params.leftMargin = DensityUtil.dip2Px(6);
                view.setBackgroundResource(R.mipmap.slide_adv_normal);
            }
            mHomePotContainer.addView(view, params);
        }

        //设置初始显示的位置
        int pos = (Integer.MAX_VALUE / 2);
        pos = pos - (pos % mHomeTopics.size());
        mHomeVp.setCurrentItem(pos);

        //开始自动轮播
        mTask = new SwitchTask();
        mTask.start();
        //ViewPager设置，触摸时间监听
        mHomeVp.setOnTouchListener(this);
//        mHomeGridViewAdapter = new HomeGridViewAdapter();


        //GridView部分请求数据
        loadDataForRecyclerView();
    }

    private void loadDataForRecyclerView() {
        Random random = new Random();
        int page = random.nextInt(20);

        String url = Url.ADDRESS_SERVER + "/limitbuy?page=" + page + "&pageNum=8";
        OkHttpClient okHttpClient = new OkHttpClient();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(url).get().build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                LogUtil.d("vovo", "====load fail====");
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                parseResponse(response);
            }
        };
        okHttpClient.newCall(request).enqueue(callback);
    }

    private void parseResponse(com.squareup.okhttp.Response response) {
        if (response.isSuccessful()) {
            LogUtil.d("vovo", "====load success====");
            String result = null;
            try {
                result = response.body().string();
                LogUtil.d("vovo", "====result====" +result);
                Gson gson = new Gson();
                mProductList = gson.fromJson(result, LimitBuyBean.class).productList;
                LogUtil.d("vovo", "====productList size====" + mProductList.size());

                MyApplication.sGlobalHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ExStaggeredGridLayoutManager staggeredLayoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        StaggeredAdapter adapter = new StaggeredAdapter(mProductList, mHomeRecyclerView);

                        if (mHomeRecyclerView != null) {
                            mHomeRecyclerView.setLayoutManager(staggeredLayoutManager);
                            mHomeRecyclerView.setAdapter(adapter);
                        }
                    }
                });
            } catch (IOException e) {
                LogUtil.d("vovo", "====gson exception====" + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * 数据加载成功后显示
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {
        //设置一次titlebar
//		changeTitleBar();
        mRootView = View.inflate(ResUtil.getContext(), R.layout.home_fragment, null);
        mAdView1 = View.inflate(ResUtil.getContext(), R.layout.home_ad_layout, null);
        mAdView2 = View.inflate(ResUtil.getContext(), R.layout.home_ad_layout2, null);
        ButterKnife.bind(this, mRootView);
        //添加点击事件的监听
        setListener();
        return mRootView;
    }

    private void setListener() {
        mHomeLlHot.setOnClickListener(this);
        mHomeLlNew.setOnClickListener(this);
        mHomeLlShopping.setOnClickListener(this);
        mHomeLlSale.setOnClickListener(this);
        mHomeLlRecommend.setOnClickListener(this);
        mHhomeLlCategory.setOnClickListener(this);

        //ViewFlipper动画
        startFilpperAnimation();
    }

    /**
     * flipper滑动效果
     */
    private void startFilpperAnimation() {
        mHomeViewflipper.addView(mAdView1);
        mHomeViewflipper.addView(mAdView2);

        mHomeViewflipper.setInAnimation(ResUtil.getContext(), R.anim.ad_in);
        mHomeViewflipper.setOutAnimation(ResUtil.getContext(), R.anim.ad_out);

        mHomeViewflipper.setFlipInterval(2000);
        mHomeViewflipper.startFlipping();
    }


    /**
     * 加载数据
     *
     * @param listener 监听
     */
    @Override
    protected void loadData(final LoadListener<Object> listener) {
        /*
            TODO: 数据的真正加载
         */
        RequestQueue requestQueue = Volley.newRequestQueue(ResUtil.getContext());
        String url = Url.ADDRESS_HOME;
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        };
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        };
        StringRequest request = new StringRequest(Request.Method.GET, url, success, error);
        requestQueue.add(request);
    }

    /**
     * 数据加载失败
     *
     * @param e 异常
     */
    @Override
    protected void handleError(Exception e) {
        //设置一次titlebar
//		changeTitleBar();

        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLACK);

        rootView.addView(tv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*//页面销毁的时候，弹出所有的栈
		if (null != mMainActivity) {
			FragmentManager manager = mMainActivity.getSupportFragmentManager();
			for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
				manager.popBackStack();
			}
		}*/

        ButterKnife.unbind(this);
    }

    /**
     * OnPageChangListener
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mHomeTopics.size();
        for (int i = 0; i < mHomeTopics.size(); i++) {
            View iv = mHomePotContainer.getChildAt(i);
            if (position == i) {
                iv.setBackgroundResource(R.mipmap.slide_adv_selected);
            } else {
                iv.setBackgroundResource(R.mipmap.slide_adv_normal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * OnTouchListener
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTask != null) {
                    mTask.stop();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mTask != null) {
                    mTask.start();
                }
                break;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        //TODO:点击跳转，其他fragment
        BaseFragment fragment = null;
        String str = "";
        switch (v.getId()) {
            case R.id.home_ll_hot://热门单品
                str = "热门单品";
                fragment = new HotProductFragment();
                break;
            case R.id.home_ll_new: //新品上架
                str = "新品上架";
                fragment = new NewProductFragment();
                break;
            case R.id.home_ll_shopping: //限时抢购
                str = "限时抢购";
                fragment = new LimitBuyFragment();
                break;
            case R.id.home_ll_sale: //促销快报
                str = "促销快报";
                fragment = new HomeFastSaleFragment();
                break;
            case R.id.home_ll_recommend: //推荐品牌
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_band);
                str = "推荐品牌";
                break;
            case R.id.home_ll_category: //商品分类
                str = "商品分类";
                fragment = new HomeCategoryFragment();
                break;
        }
        changeFragment(fragment);
    }

    private void changeFragment(BaseFragment fragment) {
        if (fragment == null) {
            return;
        }

        ((HolderFragment) getParentFragment()).goForward(fragment);
    }


    private class HomeTopicAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mHomeTopics.size();

            ImageView iv = new ImageView(ResUtil.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            HomeBean.HomeTopicBean homeTopicBean = mHomeTopics.get(position);
            String url = Url.ADDRESS_SERVER + homeTopicBean.pic;
            Picasso.with(ResUtil.getContext()).load(url).into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class SwitchTask extends Handler implements Runnable {

        @Override
        public void run() {
            if (mHomeVp!=null) {
                int currentItem = mHomeVp.getCurrentItem();
                currentItem++;
                mHomeVp.setCurrentItem(currentItem);
            }
            start();
        }

        public void start() {
            this.removeCallbacks(this);
            this.postDelayed(this, 3000);
        }

        public void stop() {
            this.removeCallbacks(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //避免切换其他页面时，报空指针
        if (mTask != null) {
            mTask.stop();
        }
        mTask = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainActivity = (MainActivity) getActivity();
        View titlBar = View.inflate(ResUtil.getContext(), R.layout.home_title, null);
        EditText homeEt = (EditText) titlBar.findViewById(R.id.home_tb_et);
        homeEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainActivity.mRgBottomNav.check(R.id.rb_bottom_search);
                }
            }
        });
        mMainActivity.setTopBarView(titlBar);
    }

}
