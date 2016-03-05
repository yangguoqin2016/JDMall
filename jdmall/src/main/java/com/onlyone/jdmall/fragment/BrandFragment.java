package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.application.MyApplication;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Administrator
 * @创建时间: 2016/3/5 15:21
 * @描述: ${TODO}
 */
public class BrandFragment extends BaseFragment {


    @Bind(R.id.brand_tv_tongbu)
    TextView  mBrandTvTongbu;
    @Bind(R.id.brand_viewpager)
    ViewPager mBrandViewpager;
    @Bind(R.id.brand_iv_gonggao2)
    ImageView mBrandIvGonggao2;
    @Bind(R.id.brand_iv_gonggao1)
    ImageView mBrandIvGonggao1;

    private int PICS[] = new int[]{R.mipmap.tuijianpinpaitehuizhadan,
            R.mipmap.brand_1, R.mipmap.brand_2,
            R.mipmap.brand_3, R.mipmap.brand_4, R.mipmap.brand_5};

    private AutoScrollTask mTask;

    @Override
    protected void refreshSuccessView(Object data) {

        mBrandViewpager.setAdapter(new BrandAdapter());

        int index = Integer.MAX_VALUE / 2;
        int diff = index % PICS.length;
        index = index - diff;
        mBrandViewpager.setCurrentItem(index);

        //设置自动轮播
        if(mTask==null){
            mTask = new AutoScrollTask();
        }
        mTask.start();


        //按下去停止轮播,放开开始轮播
        mBrandViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        mTask.stop();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        mTask.start();
                        break;
                }
                return false;
            }
        });
    }

    private class AutoScrollTask implements Runnable {

        public void start() {

            MyApplication.sGlobalHandler.postDelayed(this, 2000);
        }

        public void stop() {
            MyApplication.sGlobalHandler.removeCallbacks(this);
        }

        @Override
        public void run() {

            int currentItem = mBrandViewpager.getCurrentItem();
            currentItem++;
            mBrandViewpager.setCurrentItem(currentItem);

            start();
        }
    }

    @Override
    protected View loadSuccessView() {

        View brandView = View.inflate(ResUtil.getContext(), R.layout.inflate_brand, null);
        ButterKnife.bind(this, brandView);
        return brandView;
    }

    @Override
    protected void loadData(LoadListener listener) {
        listener.onSuccess(true);
    }

    @Override
    protected void handleError(Exception e) {
        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setGravity(Gravity.CENTER);
        rootView.addView(tv);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View topBrand = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_brand, null);
        MainActivity mainActivity = (MainActivity) getActivity();
        //设置状态栏
        mainActivity.setTopBarView(topBrand);

        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class BrandAdapter extends PagerAdapter {

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

            position = position % PICS.length;
            int pic = PICS[position];

            ImageView iv = new ImageView(ResUtil.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(pic);
            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTask != null) {
            mTask.stop();
            mTask = null;
        }
    }
}
