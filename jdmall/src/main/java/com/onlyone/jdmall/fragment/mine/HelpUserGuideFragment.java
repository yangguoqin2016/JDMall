package com.onlyone.jdmall.fragment.mine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.fragment.BaseFragment;
import com.onlyone.jdmall.fragment.MineFragment;
import com.onlyone.jdmall.pager.LoadListener;
import com.onlyone.jdmall.utils.LogUtil;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.home
 * @创建者: Ashin
 * @创建时间: 2016/3/6 18:26
 * @描述: ${TODO}
 */
public class HelpUserGuideFragment extends BaseFragment<Object> {

    private MainActivity     mMainActivity;
    private MineHelpFragment mMineHelpFragment;
    private View             mTopBarView;
    private TextView         mHelpBack;
    private FragmentManager  mManager;

    @Override
    public void onPause() {
        super.onPause();
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_help, null);
        mHelpBack = (TextView) mTopBarView.findViewById(R.id.mine_tv_help_back);
        mHelpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mManager = mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();
                Fragment fragment = mManager.findFragmentByTag(MineFragment.TAG_MINEHELP_FRAGMENT);
                transaction.remove(fragment);
                mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
                transaction.commit();
            }
        });
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(mTopBarView);
        mMainActivity.setHideTopBar(false);
    }

    @Override
    public void onResume() {
        LogUtil.i("ninini", "onResume ->");
        View topBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_userguide, null);
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(topBarView);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    @Override
    protected void refreshSuccessView(Object data) {

    }

    @Override
    protected View loadSuccessView() {
        View successView = View.inflate(ResUtil.getContext(), R.layout.mine_jackaroo_guide, null);
        return successView;
    }

    @Override
    protected void loadData(LoadListener<Object> listener) {
        listener.onSuccess(null);
    }

    @Override
    protected void handleError(Exception e) {

    }
}
