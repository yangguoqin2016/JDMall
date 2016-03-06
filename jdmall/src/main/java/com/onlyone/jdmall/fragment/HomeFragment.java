package com.onlyone.jdmall.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.HomeBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名:	JDMall
 * 包名:		com.onlyone.jdmall.fragment
 * 创建者:	LuoDi
 * 创建时间:	3/5/2016 10:03
 * 描述:		${TODO}
 */
public class HomeFragment extends BaseFragment<Object> implements ViewPager.OnPageChangeListener, View.OnTouchListener, View.OnClickListener {

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
    private ScrollView mRootView;
    private Context mContext;
    private HomeBean mHomeBean;
    private List<HomeBean.HomeTopicBean> mHomeTopics;
    private SwitchTask mTask;
    private MainActivity mMainActivity;


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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2Px(6), DensityUtil.dip2Px(6));
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
    }

    /**
     * 数据加载成功后显示
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {
        mRootView = (ScrollView) View.inflate(ResUtil.getContext(), R.layout.home_fragment, null);
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
        StringRequest request = new StringRequest(url, success, error);
        requestQueue.add(request);
    }

    /**
     * 数据加载失败
     *
     * @param e 异常
     */
    @Override
    protected void handleError(Exception e) {
//        Toast.makeText(ResUtil.getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setGravity(Gravity.CENTER);
        rootView.addView(tv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                mTask.stop();
                break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTask.start();
                break;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        //TODO:点击跳转，其他fragment
        Fragment fragment = null;
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
                break;
            case R.id.home_ll_sale: //促销快报
                str = "促销快报";
                break;
            case R.id.home_ll_recommend: //推荐品牌
                str = "推荐品牌";
                break;
            case R.id.home_ll_category: //商品分类
                str = "商品分类";
                break;
        }
        changeFragment(fragment);
        Toast.makeText(ResUtil.getContext(), str, Toast.LENGTH_SHORT).show();

    }

    private void changeFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = mMainActivity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fl_content_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
            int currentItem = mHomeVp.getCurrentItem();
            currentItem++;
            mHomeVp.setCurrentItem(currentItem);
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
        Toast.makeText(ResUtil.getContext(), "Home Fragment onResume", Toast.LENGTH_SHORT).show();
        changeTitleBar();
    }

    private void changeTitleBar() {
        //设置titleBar
        View titlBar = View.inflate(ResUtil.getContext(), R.layout.home_title, null);
        //TODO:主页title搜索的逻辑
        //2.首先先拿到Fragment关联的Activity
        mMainActivity = (MainActivity) getActivity();

        //3.得到MainActivity,再设置TopBar的Ui
        mMainActivity.setTopBarView(titlBar);
    }
}
