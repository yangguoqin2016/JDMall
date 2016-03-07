package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.mine.AddressManagerFragment;
import com.onlyone.jdmall.fragment.mine.MineAboutFragment;
import com.onlyone.jdmall.fragment.mine.MineHelpFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 16:09
 * @描述: ${TODO}
 */

public class MineFragment extends SuperBaseFragment<Object> implements View.OnClickListener {

    public static final String TAG_MINEABOUT_FRAGMENT = "tag_mineabout_fragment";

    public static final String TAG_MINEHELP_FRAGMENT = "tag_minehelp_fragment";

    public static final String TAG_MINEFAVORITE_FRAGMENT = "tag_minefavorite_fragment";

    public static final String TAG_MINEADDRESSMANAGER_FRAGMENT = "tag_mineaddressmanager_fragment";
    @Bind(R.id.fragment_ll_mine_order)
    LinearLayout mFragmentLlMineOrder;
    @Bind(R.id.fragment_ll_mine_address)
    LinearLayout mFragmentLlMineAddress;
    @Bind(R.id.fragment_ll_mine_gift)
    LinearLayout mFragmentLlMineGift;
    @Bind(R.id.fragment_ll_mine_favorite)
    LinearLayout mFragmentLlMineFavorite;
    @Bind(R.id.fragment_ll_mine_record)
    LinearLayout mFragmentLlMineRecord;
    @Bind(R.id.fragment_ll_mine_help)
    LinearLayout mFragmentLlMineHelp;
    @Bind(R.id.fragment_ll_mine_feedback)
    LinearLayout mFragmentLlMineFeedback;
    @Bind(R.id.fragment_ll_mine_about)
    LinearLayout mFragmentLlMineAbout;
    @Bind(R.id.mine_back_btn)
    Button       mMineBackBtn;

    private MainActivity     mMainActivity;
    private MineHelpFragment mHelpFragment;


    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View successView = View.inflate(ResUtil.getContext(), R.layout.fragment_mine, null);
        ButterKnife.bind(this, successView);
        return successView;
    }

    @Override
    public int getMethod() {
        return Request.Method.GET;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }

    @Override
    protected Object parseJson(String jsonStr) {
        return null;
    }

    @Override
    public void onResume() {
        Log.d("aaa", "onResume");
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setHideTopBar(true);

        //设置点击事件监听
        mFragmentLlMineOrder.setOnClickListener(this);
        mFragmentLlMineAddress.setOnClickListener(this);
        mFragmentLlMineGift.setOnClickListener(this);
        mFragmentLlMineFavorite.setOnClickListener(this);
        mFragmentLlMineRecord.setOnClickListener(this);
        mFragmentLlMineHelp.setOnClickListener(this);
        mFragmentLlMineFeedback.setOnClickListener(this);
        mFragmentLlMineAbout.setOnClickListener(this);
        mMineBackBtn.setOnClickListener(this);

        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("aaa", "onPause");
        mMainActivity.setHideTopBar(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("aaa", "onDestroyView");
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back_btn:
                //退出登录
                break;
            case R.id.fragment_ll_mine_order://我的订单
                break;
            case R.id.fragment_ll_mine_address://地址管理
                Fragment addressManagerFragment = new AddressManagerFragment();
                changeFragment(addressManagerFragment, TAG_MINEADDRESSMANAGER_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_gift://优惠券/礼品卡
                break;
            case R.id.fragment_ll_mine_favorite://收藏夹
                Fragment favoriteFrament = new MineFavoriteFragment();
                changeFragment(favoriteFrament, TAG_MINEFAVORITE_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_record://浏览记录
                break;
            case R.id.fragment_ll_mine_help://帮助中心
                if (mHelpFragment == null) {
                    mHelpFragment = new MineHelpFragment();
                }
                changeFragment(mHelpFragment, TAG_MINEHELP_FRAGMENT);
                break;
            case R.id.fragment_ll_mine_feedback://用户反馈
                break;
            case R.id.fragment_ll_mine_about://关于
                Toast.makeText(ResUtil.getContext(), "点我了", Toast.LENGTH_SHORT).show();
                Fragment aboutFragment = new MineAboutFragment();
                changeFragment(aboutFragment, TAG_MINEABOUT_FRAGMENT);
                break;
            default:
                break;
        }
    }

    /**
     * 实现fragment跳转
     *
     * @param fragment
     * @param tag
     */
    private void changeFragment(Fragment fragment, String tag) {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content_container, fragment, tag);
        transaction.addToBackStack("aaa");

        transaction.commit();
    }
}
