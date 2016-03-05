package com.onlyone.jdmall.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.bean.SearchBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: lianjiacheng
 * @创建时间: 2016/3/5 10:48
 * @描述: RadioGroup里面的搜索
 */
public class SearchFragment extends  SuperBaseFragment<SearchBean> implements View.OnClickListener {

    private MainActivity mMainActivity;
    public static final String TAG_SEARCHRESULT_FRAGMENT = "tag_searchresult_fragment";





    @Override
    protected String getUrl() {
        return Url.ADDRESS_SEARCH;
    }

    /**
     * 处理异常
     * @param e 异常
     */
    @Override
    protected void handleError(Exception e) {
        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        TextView tv = new TextView(ResUtil.getContext());
        tv.setText("加载数据失败,请检查下你的网络..");
        tv.setGravity(Gravity.CENTER);
        rootView.addView(tv);
    }

    @Override
    protected SearchBean parseJson(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr,SearchBean.class);
    }

    @Override
    protected View loadSuccessView() {
        //1.得到TopBar.
        View topBar = View.inflate(ResUtil.getContext(),R.layout.inflate_topbar_search,null);

        //得到TopBar的孩子,设置监听事件
        TextView tvBack = (TextView) topBar.findViewById(R.id.topbar_tv_back);
        TextView tvSearch = (TextView) topBar.findViewById(R.id.topbar_tv_search);
        tvBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        //2.首先先拿到Fragment关联的Activity
        mMainActivity = (MainActivity) getActivity();

        //3.得到MainActivity,再设置TopBar的Ui
        mMainActivity.setTopBarView(topBar);

        //先移除掉.
        FrameLayout rootView = (FrameLayout) mLoadPager.getRootView();
        rootView.removeAllViews();
        //搜索页面
        View contentView = View.inflate(ResUtil.getContext(), R.layout.inflate_search, null);
        return contentView;
    }

    @Override
    protected void refreshSuccessView(SearchBean data) {
        Log.d("vivi",data.toString());
    }

    /**
     * 返回键的点击事件
     * 返回到首页
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        	case R.id.topbar_tv_back://返回
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_home);
                break;
            case R.id.topbar_tv_search://搜索
                FragmentManager manager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fl_content_container,new SearchResultFragment(),TAG_SEARCHRESULT_FRAGMENT);
                transaction.commit();
                break;

            default:
                break;
        }
    }
}
