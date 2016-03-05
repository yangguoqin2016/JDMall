package com.onlyone.jdmall.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 16:09
 * @描述: ${TODO}
 */
//public class MineFragment extends SuperBaseFragment<Object> implements View.OnClickListener {
public class MineFragment extends SuperBaseFragment<Object> {

    private              MainActivity mMainActivity;
    private static final String       TAG_MINEABOUT_FRAGMENT = "tag_mineabout_fragment";
    private LinearLayout mLl_about;

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View rootView = View.inflate(ResUtil.getContext(), R.layout.fragment_mine, null);
        mLl_about = (LinearLayout) rootView.findViewById(R.id.fragment_ll_mine_about);
        initListener();
        return rootView;
    }

    private void initListener() {
        mLl_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResUtil.getContext(),"点我了",Toast.LENGTH_SHORT).show();
                FragmentManager manager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fl_content_container,new MineAboutFragment(),TAG_MINEABOUT_FRAGMENT);
                transaction.commit();
            }
        });
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
        mMainActivity = (MainActivity) getActivity();
        super.onResume();
    }

    /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back_btn:
                //退出登录
                break;
            case R.id.fragment_ll_mine_order://我的订单
                break;
            case R.id.fragment_ll_mine_address://地址管理
                break;
            case R.id.fragment_ll_mine_gift://优惠券/礼品卡
                break;
            case R.id.fragment_ll_mine_favorite://收藏夹
                break;
            case R.id.fragment_ll_mine_record://浏览记录
                break;
            case R.id.fragment_ll_mine_help://帮助中心
                break;
            case R.id.fragment_ll_mine_feedback://用户反馈
                break;
            case R.id.fragment_ll_mine_about://关于
                Toast.makeText(ResUtil.getContext(),"点我了",Toast.LENGTH_SHORT).show();
                FragmentManager manager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fl_content_container,new MineAboutFragment(),TAG_MINEABOUT_FRAGMENT);
                transaction.commit();
                break;
        }
    }*/
}
