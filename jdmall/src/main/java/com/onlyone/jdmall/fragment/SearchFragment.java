package com.onlyone.jdmall.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nineoldandroids.animation.ValueAnimator;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.adapter.MyBaseAdapter;
import com.onlyone.jdmall.bean.SearchBean;
import com.onlyone.jdmall.constance.SP;
import com.onlyone.jdmall.constance.Serialize;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.DensityUtil;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;
import com.onlyone.jdmall.utils.SPUtil;
import com.onlyone.jdmall.utils.SerializeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 10:48
 * @描述: RadioGroup里面的搜索
 */
public class SearchFragment extends SuperBaseFragment<SearchBean>
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.item_hot_arrow)
    ImageView    mItemHotArrow;
    @Bind(R.id.search_hot_item_container)
    LinearLayout mSearchHotItemContainer;
    @Bind(R.id.search_history_item_container)
    ListView     mSearchHistoryItemContainer;
    @Bind(R.id.search_scrollview)
    ScrollView   mScrollView;
    private MainActivity mMainActivity;
    public static final String TAG_SEARCHRESULT_FRAGMENT = "tag_searchresult_fragment";
    private List<String> mStringList;
    private EditText     mEtKey;

    public SPUtil mSpUtil = new SPUtil(ResUtil.getContext());
    public static View mTopBar;
    private boolean mIsHotArrowOpen = true;
    private ArrayList<String> mHistoryList;
    private boolean mIsHistoryArrowOpen = true;
    private HistoryAdapter mAdapter;

    @Override
    protected String getUrl() {
        return Url.ADDRESS_SEARCH;
    }


    /**
     * 处理异常
     *
     * @param e 异常
     */
    @Override
    protected void handleError(Exception e) {
        mHistoryList = SerializeUtil.serializeObject(Serialize.TAG_HISTORY);
        if (mHistoryList == null) {
            mHistoryList = new ArrayList<>();
        }
        //		initTopBar();

        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        rootView.addView(tv);
    }

    @Override
    public void onAttach(Context context) {
        mMainActivity = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    protected SearchBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, SearchBean.class);
    }

    @Override
    protected View loadSuccessView() {
        initTopBar();

        // 先移除掉.
        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        rootView.removeAllViews();
        // 搜索页面
        View contentView = View.inflate(ResUtil.getContext(), R.layout.inflate_search, null);
        ButterKnife.bind(this, contentView);
        // 设置箭头的点击箭头事件
        mItemHotArrow.setOnClickListener(this);
        return contentView;
    }

    private void initTopBar() {
        // 1.得到TopBar.
        mTopBar = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_search, null);

        // 得到TopBar的孩子,设置监听事件
        TextView tvBack = (TextView) mTopBar.findViewById(R.id.topbar_tv_back);
        TextView tvSearch = (TextView) mTopBar.findViewById(R.id.topbar_tv_search);
        ImageView tvIconSearch = (ImageView) mTopBar.findViewById(R.id.topbar_iv_iconsearch);
        mEtKey = (EditText) mTopBar.findViewById(R.id.topbar_et_key);

        tvBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvIconSearch.setOnClickListener(this);
        // 2.首先先拿到Fragment关联的Activity
        //		mMainActivity = (MainActivity) getActivity();

        // 3.得到MainActivity,再设置TopBar的Ui
        mMainActivity.setTopBarView(mTopBar);
    }

    @Override
    protected void refreshSuccessView(SearchBean data) {
        mStringList = data.searchKeywords;
        // 动态添加文本
        for (int i = 0; i < mStringList.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(mMainActivity);
            TextView tv = new TextView(ResUtil.getContext());
            String text = mStringList.get(i);
            tv.setText(text);
            tv.setTextColor(Color.BLACK);

            //TODO:屏幕适配
            tv.setTextSize(getTextSizeByDensity());
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = DensityUtil.dip2Px(15);
            params.topMargin = DensityUtil.dip2Px(6);
            params.bottomMargin = DensityUtil.dip2Px(6);
            linearLayout.addView(tv);
            linearLayout.setOnClickListener(this);
            // 添加热门搜索的数据到容器
            mSearchHotItemContainer.addView(linearLayout, params);
            if (i != mStringList.size() - 1) {
                View line = new View(ResUtil.getContext());
                int lineWidth = LinearLayout.LayoutParams.MATCH_PARENT;
                int lineHeight = DensityUtil.dip2Px(2);
                line.setBackgroundColor(Color.parseColor("#33000000"));
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(lineWidth,
                                                                                     lineHeight);
                // 添加线
                mSearchHotItemContainer.addView(line, lineParams);
            }
        }
        mHistoryList = SerializeUtil.serializeObject(Serialize.TAG_HISTORY);
        if (mHistoryList == null) {
            mHistoryList = new ArrayList<>();
        }
        mAdapter = new HistoryAdapter(mHistoryList);
        mSearchHistoryItemContainer.setAdapter(mAdapter);
        mSearchHistoryItemContainer.setOnItemClickListener(this);
    }

    /**
     * 在Fragment显示的时候就设置TopBar,这样就不用成功还是失败都去设置.
     */
    @Override
    public void onResume() {
        initTopBar();
        LogUtil.d("vivi", "onResume方法被调用了--------");
        super.onResume();
    }

    @Override
    public void onStart() {
        LogUtil.d("vivi", "onStart方法被调用了--------");
        super.onStart();
    }

    @Override
    public void onStop() {
        LogUtil.d("vivi", "onStop方法被调用了--------");
        super.onStop();
    }

    @Override
    public void onPause() {

        LogUtil.d("vivi", "onPause方法被调用了--------");
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.d("vivi", "onHiddenChanged方法被调用了--------" + hidden);
        super.onHiddenChanged(hidden);
    }

    /**
     * 所有点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_tv_back:// 返回
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
                break;
            case R.id.topbar_tv_search:// 搜索
            case R.id.topbar_iv_iconsearch:// 搜索
                String searchKey = mEtKey.getText().toString().trim();
                // 判空操作
                if (TextUtils.isEmpty(searchKey)) {
                    Toast.makeText(ResUtil.getContext(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                processSearchKey(searchKey, true);
                break;
            case R.id.item_hot_arrow:// 热门搜索的箭头
                mSearchHotItemContainer.measure(0, 0);
                int start = mSearchHotItemContainer.getMeasuredHeight();
                int end = 0;
                if (mIsHotArrowOpen) {
                    // 当前状态是打开,就折叠
                    doAnimationByHot(start, end);
                    doRotateAnimation(0, 180, mItemHotArrow);
                } else {
                    // 当前状态是折叠,就打开
                    doAnimationByHot(end, start);
                    doRotateAnimation(180, 0, mItemHotArrow);
                }
                mIsHotArrowOpen = !mIsHotArrowOpen;
                break;
            default: // 热门搜索的条目点击事件
                //			String clickSearchKey = ((TextView) v).getText().toString().trim();
                TextView tv = (TextView) ((LinearLayout) v).getChildAt(0);
                String clickSearchKey = tv.getText().toString().trim();
                Toast.makeText(mMainActivity, clickSearchKey, Toast.LENGTH_SHORT).show();
                processSearchKey(clickSearchKey, true);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("vivi", "onDestroy方法被调用了--------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMainActivity != null) {

            FragmentManager manager = mMainActivity.getSupportFragmentManager();
            for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
                manager.popBackStack();
            }
        }
        ButterKnife.unbind(this);
        LogUtil.d("vivi", "onDestroyView方法被调用了--------");

    }

    /**
     * 搜索厉害的Item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String historyData = mHistoryList.get(position);
        processSearchKey(historyData, false);
    }

    class HistoryAdapter extends MyBaseAdapter<String> {

        public HistoryAdapter(ArrayList<String> datas) {
            super(datas);
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
            String text = mHistoryList.get(position);
            holder.tv.setText(text);
            holder.tv.setTextColor(Color.BLACK);
            //TODO:屏幕适配
            holder.tv.setTextSize(getTextSizeByDensity());
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
     * 1.处理关键字,并且保存到sp,
     * 2.而且用系列化保存关键字到本地缓存.
     * 3.跳转到SearchResultFragment
     *
     * @param searchKey
     */
    private void processSearchKey(String searchKey, boolean isSaveKey) {
        // 保存关键字
        mSpUtil.putString(SP.KEY_SEARCHKEY, searchKey);
        // 用序列化保存搜索历史
        if (isSaveKey) {
            /**
             * 1.让用户每次搜索的数据,都显示在搜索历史的第一条.
             * 2.重复数据不添加
             */
            if (mHistoryList.contains(searchKey)) {
                mHistoryList.remove(searchKey);
                mHistoryList.add(0, searchKey);
            } else {
                mHistoryList.add(0, searchKey);
            }
        } else {
            //不保存关键字,说明当前用户是点击了搜索历史
            if (mHistoryList.get(0).equals(searchKey)) {
                //如果点击是第一个就不处理
            } else {
                mHistoryList.remove(searchKey);
                mHistoryList.add(0, searchKey);
            }
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        SerializeUtil.deserializeObject(Serialize.TAG_HISTORY, mHistoryList);

        ((HolderFragment) getParentFragment()).goForward(new SearchResultFragment());
    }

    /**
     * 热门搜索的动画
     *
     * @param start
     * @param end
     */
    private void doAnimationByHot(int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mSearchHotItemContainer.getLayoutParams();
                layoutParams.height = value;
                mSearchHotItemContainer.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    /**
     * 箭头旋转动画
     *
     * @param fromDegrees
     * @param toDegrees
     */
    private void doRotateAnimation(float fromDegrees, float toDegrees, ImageView iv) {
        RotateAnimation ra = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF,
                                                 .5f,
                                                 Animation.RELATIVE_TO_SELF, .5f);
        ra.setDuration(500);
        ra.setFillAfter(true);
        iv.startAnimation(ra);
    }

    public float getTextSizeByDensity(){
        float size = 0;
        switch ((int) DensityUtil.getDensityDpi()) {
            case 120:
                size = 20;
                break;
            case 160:
                size = 20;
                break;
            case 240:
                size = 15;
                break;
            case 320:
                size = 20;
                break;
            default:
                break;
        }
        return size;
    }
}
