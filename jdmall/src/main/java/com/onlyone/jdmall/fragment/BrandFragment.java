package com.onlyone.jdmall.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.application.MyApplication;
import com.onlyone.jdmall.bean.BrandBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.DensityUtil;
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
public class BrandFragment extends SuperBaseFragment<BrandBean> {

    @Nullable
    @Bind(R.id.brand_viewpager)
    ViewPager mBrandViewpager;
    @Nullable
    @Bind(R.id.brind_listview)
    ListView  mBrindListview;

    private int PICS[] = new int[]{R.mipmap.tuijianpinpaitehuizhadan,
            R.mipmap.brand_1, R.mipmap.brand_2,
            R.mipmap.brand_3, R.mipmap.brand_4, R.mipmap.brand_5};

    private AutoScrollTask mTask;
    private BrandBean mBrandBean;


    @Override
    protected String getUrl() {
        return Url.ADDRESS_BRAND;
    }

    @Override
    protected BrandBean parseJson(String jsonStr) {

        Gson gson = new Gson();
        mBrandBean = gson.fromJson(jsonStr, BrandBean.class);
        return mBrandBean;

    }

    @Override
    protected View loadSuccessView() {

        View brandView = View.inflate(ResUtil.getContext(), R.layout.inflate_brand, null);
        ButterKnife.bind(this, brandView);
        return brandView;
    }

    @Override
    protected void refreshSuccessView(BrandBean data) {

        mBrandViewpager.setAdapter(new BrandAdapter());

        int index = Integer.MAX_VALUE / 2;
        int diff = index % PICS.length;
        index = index + diff;
        mBrandViewpager.setCurrentItem(index);

        //设置自动轮播
        if (mTask == null) {
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

        mBrindListview.setAdapter(new BrandListViewAdapter());
        setListViewHeightBasedOnChildren(mBrindListview);
    }

    class AutoScrollTask implements Runnable {

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

    private class BrandListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBrandBean.getBrand().size();
        }

        @Override
        public Object getItem(int position) {
            return mBrandBean.getBrand().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                holder.tv = new TextView(ResUtil.getContext());
                convertView = holder.tv;
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            BrandBean.BrandList brandList = mBrandBean.getBrand().get(position);
            holder.tv.setText(brandList.getKey());
            holder.tv.setTextColor(Color.BLACK);
            holder.tv.setTextSize(DensityUtil.dip2Px(18));
            int left = DensityUtil.dip2Px(15);
            int top = DensityUtil.dip2Px(6);
            int bottom = DensityUtil.dip2Px(6);
            holder.tv.setPadding(left,top,0,bottom);
            return convertView;
        }
    }
    class ViewHolder{
        TextView tv;
    }

    /**
     * 解决ListView和ScrollView冲突问题
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;//if without this statement,the listview will be a little short
        listView.setLayoutParams(params);
    }

}
