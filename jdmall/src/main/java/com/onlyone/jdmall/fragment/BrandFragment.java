package com.onlyone.jdmall.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.onlyone.jdmall.fragment.brand.BabyFragment;
import com.onlyone.jdmall.fragment.brand.DailyFragment;
import com.onlyone.jdmall.fragment.brand.FashionFragment;
import com.onlyone.jdmall.fragment.brand.MotherFragment;
import com.onlyone.jdmall.fragment.brand.QingjieFragment;
import com.onlyone.jdmall.fragment.brand.ShipinFragment;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.ResUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Administrator
 * @创建时间: 2016/3/5 15:21
 * @描述: ${TODO}
 */
public class BrandFragment extends SuperBaseFragment<BrandBean> implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Nullable
    @Bind(R.id.brand_viewpager)
    ViewPager mBrandViewpager;


    @Nullable
    @Bind(R.id.brind_listview)
    ListView mBrindListview;

    private List<BrandBean.BrandList.BrandValue> mMotherData  = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mShipinData  = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mBabyData    = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mFashionData = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mDailyData   = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mQingjieData = new ArrayList();

    //Fragment容器
    private List<Fragment> mFragmentList = new ArrayList();


    private int PICS[] = new int[]{R.mipmap.tuijianpinpaitehuizhadan,
            R.mipmap.brand_1, R.mipmap.brand_2,
            R.mipmap.brand_3, R.mipmap.brand_4, R.mipmap.brand_5};

    private AutoScrollTask                 mTask;
    private BrandBean                      mBrandBean;
    private ImageView                      mMIvBack;
    private BrandListViewAdapter           mAdapter;
    private BrandBean.BrandList.BrandValue mBrandValue;
    FragmentManager mManager;
    private FragmentTransaction mTransaction;


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

    /**
     * 加载成功的视图
     *
     * @return
     */
    @Override
    protected View loadSuccessView() {

        View brandView = View.inflate(ResUtil.getContext(), R.layout.inflate_brand, null);
        ButterKnife.bind(this, brandView);
        return brandView;
    }

    /**
     * 加载数据完成,刷新UI,需要判断data是否为null
     *
     * @param data 数据
     */
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

        mAdapter = new BrandListViewAdapter();
        mBrindListview.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mBrindListview);

        mMIvBack.setOnClickListener(this);

        LoadBrandList(data);
        mManager = getActivity().getSupportFragmentManager();

        //默认显示品牌列表的第一个Fragment
/*        mTransaction = mManager.beginTransaction();
        mTransaction.replace(R.id.brand_fragment_container, mFragmentList.get(0));
        mTransaction.commit();*/
    }

    /**
     * 加载品牌列表
     *
     * @param data
     */
    private void LoadBrandList(BrandBean data) {
        for (int i = 0; i < data.getBrand().size(); i++) {
            BrandBean.BrandList brandList = data.getBrand().get(i);

            for (int j = 0; j < brandList.getValue().size(); j++) {
                mBrandValue = brandList.getValue().get(j);
                switch (mBrandValue.getId()) {
                    case 1218:
                    case 1219:
                    case 1220:
                    case 1221:
                        mMotherData.add(mBrandValue); //孕妈用品
                        break;

                    case 1202:
                        mShipinData.add(mBrandValue); //营养食品
                        break;

                    case 1209:
                    case 1210:
                    case 1211:
                    case 1212:
                        mBabyData.add(mBrandValue); //宝宝用品
                        break;

                    case 1206:
                    case 1207:
                    case 1208:
                    case 1213:
                    case 1214:
                    case 1215:
                    case 1216:
                    case 1217:
                        mFashionData.add(mBrandValue); //时尚女装
                        break;

                    case 1201:
                        mDailyData.add(mBrandValue); //日常用品
                        break;

                    case 1203:
                    case 1205:
                    case 1222:
                        mQingjieData.add(mBrandValue); //清洁用品
                        break;

                }
            }
        }

        mFragmentList.add(new MotherFragment(mMotherData));
        mFragmentList.add(new ShipinFragment(mShipinData));
        mFragmentList.add(new BabyFragment(mBabyData));
        mFragmentList.add(new FashionFragment(mFashionData));
        mFragmentList.add(new DailyFragment(mDailyData));
        mFragmentList.add(new QingjieFragment(mQingjieData));

        mBrindListview.setOnItemClickListener(this);
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
        mMIvBack = (ImageView) topBrand.findViewById(R.id.brand_back);
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

        private int mCurrentPosition = 0;

        public void setCurrentPosition(int position) {
            mCurrentPosition = position;
        }

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
            if (convertView == null) {
                holder = new ViewHolder();
                holder.tv = new TextView(ResUtil.getContext());
                convertView = holder.tv;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BrandBean.BrandList brandList = mBrandBean.getBrand().get(position);
            String key = brandList.getKey();
            holder.tv.setText(key);
            holder.tv.setTextColor(Color.BLACK);
            holder.tv.setTextSize(DensityUtil.dip2Px(14));
            int left = DensityUtil.dip2Px(15);
            int top = DensityUtil.dip2Px(6);
            int bottom = DensityUtil.dip2Px(6);
            holder.tv.setPadding(left, top, 0, bottom);

            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }

    /**
     * 解决ListView和ScrollView冲突问题
     *
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


    /**
     * 点击回退按钮调回到首页
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mAdapter.setCurrentPosition(position);
        mAdapter.notifyDataSetChanged();
        switchFragment(position);
    }

    /**
     * 切换Fragment
     * @param position
     */
    private void switchFragment(int position) {

        mTransaction = mManager.beginTransaction();

        Fragment fragment = mFragmentList.get(position);

        mTransaction.replace(R.id.brand_fragment_container, fragment);

        mTransaction.commit();
    }
}

