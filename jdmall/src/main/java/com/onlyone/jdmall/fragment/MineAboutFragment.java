package com.onlyone.jdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.activity.MainActivity;
import com.onlyone.jdmall.utils.ResUtil;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment
 * @创建者: Ashin
 * @创建时间: 2016/3/5 19:46
 * @描述: ${TODO}
 */
public class MineAboutFragment extends Fragment implements View.OnClickListener {

    private MainActivity mMainActivity;
    private View         mTopBarView;
    private TextView mAboutBack;

    @Override
    public void onResume() {
        Log.d("aaa", "MineAboutFragment----onResume");
        mTopBarView = View.inflate(ResUtil.getContext(), R.layout.inflate_topbar_mine, null);
        mAboutBack = (TextView) mTopBarView.findViewById(R.id.mine_tv_about_back);
        mAboutBack.setOnClickListener(this);
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTopBarView(mTopBarView);
        mMainActivity.setHideTopBar(false);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(ResUtil.getContext(),R.layout.mine_about,null);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(MineFragment.TAG_MINEABOUT_FRAGMENT);
        transaction.remove(fragment);
        mMainActivity.mRgBottomNav.check(R.id.rb_bottom_mine);
        transaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMainActivity.setHideTopBar(true);
    }
}
