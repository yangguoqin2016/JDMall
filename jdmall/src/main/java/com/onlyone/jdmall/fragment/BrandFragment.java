package com.onlyone.jdmall.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.BrandBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

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

    @Nullable
    @Bind(R.id.brind_gridview)
    GridView mBrindGridview;

    private List<BrandBean.BrandList.BrandValue> mMotherData     = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mShipinData     = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mBabyData       = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mFashionData    = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mDailyData      = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mQingjieData    = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mChildClothData = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mChengRenData   = new ArrayList();
    private List<BrandBean.BrandList.BrandValue> mHuaZhuangData  = new ArrayList();


    private List<List<BrandBean.BrandList.BrandValue>> mDataList = new ArrayList<>();

    private int PICS[] = new int[]{R.mipmap.tuijianpinpaitehuizhadan,
            R.mipmap.brand_1, R.mipmap.brand_2,
            R.mipmap.brand_3, R.mipmap.brand_4, R.mipmap.brand_5};

    private AutoScrollTask                       mTask;
    private BrandBean                            mBrandBean;
    private ImageView                            mMIvBack;
    private BrandListViewAdapter                 mAdapter;
    private BrandBean.BrandList.BrandValue       mBrandValue;
    private BrandBean.BrandList                  mBrandList;
    private List<BrandBean.BrandList.BrandValue> mValue;
    private GridAdapter                          mGridAdapter;
    private int mCurrentItem = 0;


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

        //设置默认选中第一页
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

        //一进来显示第一页
        List<BrandBean.BrandList.BrandValue> brandValues = mDataList.get(0);
        mGridAdapter = new GridAdapter(brandValues);
        mBrindGridview.setAdapter(mGridAdapter);
//        setGridViewHeightBasedOnChildren(mBrindGridview);

        mAdapter.notifyDataSetChanged();
        mGridAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();

        //页面切换时重置当前选中条目,要不然会造成ListView条目和GridView内容显示不一致的BUG
        mCurrentItem = 0;
    }


    /**
     * 加载品牌列表
     *
     * @param data
     */
    private void LoadBrandList(BrandBean data) {

        mMotherData.clear();
        mShipinData.clear();
        mBabyData.clear();
        mFashionData.clear();
        mDailyData.clear();
        mQingjieData.clear();
        mChildClothData.clear();
        mChengRenData.clear();
        mHuaZhuangData.clear();

        for (int i = 0; i < data.getBrand().size(); i++) {

            mBrandList = data.getBrand().get(i);

            mValue = mBrandList.getValue();
            for (int j = 0; j < mValue.size(); j++) {
                mBrandValue = mBrandList.getValue().get(j);
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
                    default:
                        break;
                }
            }
        }

        mDataList.add(mMotherData);
        mDataList.add(mShipinData);
        mDataList.add(mBabyData);
        mDataList.add(mChildClothData);
        mDataList.add(mFashionData);
        mDataList.add(mChengRenData);
        mDataList.add(mDailyData);
        mDataList.add(mHuaZhuangData);
        mDataList.add(mQingjieData);

        mBrindListview.setOnItemClickListener(this);
    }

    /**
     * GridView的Adapter
     */
    private class GridAdapter extends BaseAdapter {

        private final List<BrandBean.BrandList.BrandValue> mData;

        public GridAdapter(List<BrandBean.BrandList.BrandValue> value) {
            mData = value;
        }

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ResUtil.getContext(), R.layout.item_brand_gridview, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.image = (ImageView) convertView.findViewById(R.id.brand_gridview_image);
                holder.text = (TextView) convertView.findViewById(R.id.brand_gridview_text);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BrandBean.BrandList.BrandValue brandValue = mData.get(position);


            String imageUrl = Url.ADDRESS_SERVER + brandValue.getPic();
            Picasso.with(ResUtil.getContext()).load(imageUrl).into(holder.image);


            //解决图片压缩失真问题
/*            RatioLayout rl = new RatioLayout(ResUtil.getContext());
            rl.setCurState(RatioLayout.RELATIVE_WIDTH);
            float ratio = 1.58f;
            rl.setRatio(ratio);
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            rl.addView(holder.image, params);*/

            holder.text.setText(brandValue.getName());

            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView  text;
        }
    }

    class AutoScrollTask extends Handler implements Runnable {

        public void start() {
            this.removeCallbacks(this);
            this.postDelayed(this, 2000);
        }

        public void stop() {
            this.removeCallbacks(this);
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
        tv.setTextColor(Color.BLACK);
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

        //        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

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

    /**
     * ListView的Adapter
     */
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
            if (convertView == null) {
                holder = new ViewHolder();
//                holder.tv = new TextView(ResUtil.getContext());
//                convertView = holder.tv;

                convertView = View.inflate(ResUtil.getContext(),R.layout.brand_item_listvie,null);
                holder.tv = (TextView) convertView.findViewById(R.id.brand_listview_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BrandBean.BrandList brandList = mBrandBean.getBrand().get(position);
            String key = brandList.getKey();
            holder.tv.setText(key);

            if(mCurrentItem == position){
                holder.tv.setBackgroundColor(Color.parseColor("#800E1F"));
                holder.tv.setTextColor(Color.WHITE);
            }else{
                holder.tv.setBackgroundColor(Color.TRANSPARENT);
                holder.tv.setTextColor(Color.BLACK);
            }

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


    public void setGridViewHeightBasedOnChildren(GridView gridView) {
        GridAdapter gridAdapter = (GridAdapter) gridView.getAdapter();
        if (gridAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < gridAdapter.getCount() / 2 + .5; i++) {

            if (gridAdapter.getCount() == 0) {
                return;
            }
            View listItem = gridAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (int) (totalHeight + ((gridAdapter.getCount() / 2 + .5) - 1));
        params.height += 5;//if without this statement,the listview will be a little short
        gridView.setLayoutParams(params);
    }

    /**
     * 点击回退按钮调回到首页
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //		((HolderFragment)getParentFragment()).goBack();
        ((MainActivity) getActivity()).mRgBottomNav.check(R.id.rb_bottom_home);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mCurrentItem = position;

        List<BrandBean.BrandList.BrandValue> brandValues = mDataList.get(position);

        mGridAdapter = new GridAdapter(brandValues);
        mBrindGridview.setAdapter(mGridAdapter);
//        setGridViewHeightBasedOnChildren(mBrindGridview);

        mAdapter.notifyDataSetChanged();
        mGridAdapter.notifyDataSetChanged();
    }
}

